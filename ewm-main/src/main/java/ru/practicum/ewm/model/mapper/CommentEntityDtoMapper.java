package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.model.entity.Comment;

import java.time.format.DateTimeFormatter;

public class CommentEntityDtoMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CommentDto mappingDtoFrom(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthor(comment.getAuthor().getId());
        dto.setCreatedOn(comment.getCreatedOn().format(FORMATTER));
        dto.setState(comment.getState());
        if (comment.getEditedOn() != null) {
            dto.setEditedOn(comment.getEditedOn().format(FORMATTER));
        }
        if (comment.getIsParticipant() != null) {
            dto.setIsParticipant(comment.getIsParticipant());
        }
        dto.setRate(comment.getRate());
        return dto;
    }
}
