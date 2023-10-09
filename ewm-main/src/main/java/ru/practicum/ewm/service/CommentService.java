package ru.practicum.ewm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CommentRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.ParticipationRequestRepository;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.dto.NewCommentDto;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.model.entity.*;
import ru.practicum.ewm.model.mapper.CommentEntityDtoMapper;
import ru.practicum.ewm.model.state.CommentState;
import ru.practicum.ewm.model.state.EventState;
import ru.practicum.ewm.model.state.RequestState;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository storage;

    private final ParticipationRequestRepository requestStorage;

    private final EventRepository eventStorage;

    private final ObjectChecker checker;

    private final EntityManager em;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CommentService(CommentRepository storage,
                          ParticipationRequestRepository requestStorage,
                          EventRepository eventStorage, ObjectChecker checker,
                          EntityManager em) {
        this.storage = storage;
        this.requestStorage = requestStorage;
        this.eventStorage = eventStorage;
        this.checker = checker;
        this.em = em;
    }

    public Page<CommentDto> getCommentsByEvent(Long userId, Long eventId, Integer from, Integer size) {

        checker.checkUser(userId);
        Event event = checker.checkEvent(eventId);
        List<Comment> comments = event.getComments();
        List<CommentDto> commentsDto = comments.stream()
                .map(CommentEntityDtoMapper::mappingDtoFrom)
                .collect(Collectors.toList());

        return getPageFrom(commentsDto, from, size, "RATE");
    }

    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newComment) {
        User user = checker.checkUser(userId);
        Event event = checker.checkEvent(eventId);

        if (!event.getState().equals(EventState.PUBLISHED.toString())) {
            throw new BadRequestException("You cannot comment on an unpublished event",
                    "Incorrectly state",
                    LocalDateTime.now().format(FORMATTER));
        }

        List<ParticipationRequest> requests = requestStorage.findAllByEventId(eventId);

        Optional<ParticipationRequest> request = requests.stream()
                .filter(o -> o.getRequester().equals(user))
                .findAny();

        Comment comment = new Comment();

        comment.setIsParticipant(request.isPresent() &&
                request.get().getState().equals(RequestState.CONFIRMED.toString()));
        comment.setRate(0L);
        comment.setState(CommentState.CREATED.toString());
        comment.setContent(newComment.getContent());
        comment.setAuthor(user);
        comment.setCreatedOn(LocalDateTime.parse(LocalDateTime.now().format(FORMATTER), FORMATTER));

        return CommentEntityDtoMapper.mappingDtoFrom(storage.save(comment));
    }

    public Response userDeleteComment(Long userId, Long commentId) {
        User user = checker.checkUser(userId);
        Comment comment = checker.checkComment(commentId);

        checkCommentAuthor(comment, user);
        storage.delete(comment);

        log.info("Comment with id={} has been deleted", comment.getId());
        return new Response("Comment has been deleted");
    }

    public Response adminDeleteComment(Long commentId) {
        Comment comment = checker.checkComment(commentId);
        storage.delete(comment);
        log.info("Comment with id={} has been deleted", comment.getId());
        return new Response("Comment has been deleted");
    }

    public CommentDto editComment(Long userId, Long commentId, NewCommentDto newComment) {
        User user = checker.checkUser(userId);
        Comment comment = checker.checkComment(commentId);

        checkCommentAuthor(comment, user);

        comment.setContent(newComment.getContent());
        comment.setState(CommentState.EDITED.toString());
        comment.setEditedOn(LocalDateTime.parse(LocalDateTime.now().format(FORMATTER), FORMATTER));

        return CommentEntityDtoMapper.mappingDtoFrom(storage.save(comment));
    }

    public CommentDto rateComment(Long userId, Long commentId, Boolean rate) {
        User user = checker.checkUser(userId);
        Comment comment = checker.checkComment(commentId);

        if (comment.getAuthor().equals(user)) {
            throw new BadRequestException("You can't rate your comment",
                    "Incorrectly state",
                    LocalDateTime.now().format(FORMATTER));
        }

        if (rate) {
            comment.setRate(comment.getRate() + 1);
        } else {
            comment.setRate(comment.getRate() - 1);
        }

        return CommentEntityDtoMapper.mappingDtoFrom(storage.save(comment));
    }

    public Page<CommentDto> getCommentsByParameter(SearchParams params,
                                                   Integer from,
                                                   Integer size,
                                                   String sort) {

//        @RequestParam(value = "users", required = false) List<Long> usersId,
//        @RequestParam(value = "states", required = false) String state,
//        @RequestParam(value = "rangeStart", required = false) String rangeStart,
//        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
        LocalDateTime start = null;
        LocalDateTime end = null;

        if (params.getRangeStart() != null && params.getRangeEnd() != null) {
            start = LocalDateTime.parse(params.getRangeStart(), FORMATTER);
            end = LocalDateTime.parse(params.getRangeEnd(), FORMATTER);
            if (start.isAfter(end)) {
                throw new BadRequestException("The start cannot be later than the end",
                        "Incorrectly date time",
                        LocalDateTime.now().format(FORMATTER));
            }
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

        Root<Comment> root = cq.from(Comment.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getUsersId() != null) {
            predicates.add(cb.isTrue(root.get("author").in(params.getUsersId())));
        }

        if (params.getState() != null) {
            predicates.add(cb.equal(root.get("state"), params.getState()));
        }

        if (params.getRangeStart() != null) {

            Predicate greaterOfCreatedOn = cb.greaterThanOrEqualTo(root.get("createdOn"), start);
            Predicate greaterOfEditedOn = cb.greaterThanOrEqualTo(root.get("editedOn"), start);
            Predicate createdOrEdited = cb.or(greaterOfEditedOn, greaterOfCreatedOn);

            predicates.add(createdOrEdited);
        }

        if (params.getRangeEnd() != null) {

            Predicate lessOfCreatedOn = cb.lessThanOrEqualTo(root.get("createdOn"), end);
            Predicate lessOfEditedOn = cb.lessThanOrEqualTo(root.get("editedOn"), end);

            Predicate createdOrEdited = cb.or(lessOfCreatedOn, lessOfEditedOn);

            predicates.add(createdOrEdited);
        }

        cq.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        List<Comment> comments = em.createQuery(cq).getResultList();

        List<CommentDto> commentsDto = comments.stream()
                .map(CommentEntityDtoMapper::mappingDtoFrom)
                .collect(Collectors.toList());

        return getPageFrom(commentsDto, from, size, sort);
    }

    private Page<CommentDto> getPageFrom(List<CommentDto> list, Integer from, Integer size, String sort) {
        Sort sortBy;

        if (sort == null || sort.equals("RATE")) {
            sortBy = Sort.by("rate");
        } else if (sort.equals("ID")) {
            sortBy = Sort.by("id");
        } else if (sort.equals("DATE")) {
            sortBy = Sort.by("createdOn");
        } else {
            sortBy = Sort.by("id");
        }

        PageRequest pageRequest = PageRequest.of((from / size), size, sortBy);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<CommentDto> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private void checkCommentAuthor(Comment comment, User user) {
        if (!comment.getAuthor().equals(user)) {
            throw new BadRequestException("Not the author of the comment",
                    "Only the author can delete and edit the comment",
                    LocalDateTime.now().format(FORMATTER));
        }
    }
}
