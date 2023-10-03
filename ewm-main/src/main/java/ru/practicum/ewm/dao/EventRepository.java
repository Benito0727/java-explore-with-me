package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByIdIn(List<Long> eventsId);

    List<Event> findEventsByInitiatorId(Long initiatorId);

    List<Event> findAllByInitiatorIdIn(List<Long> initiatorId);

    List<Event> findAllByStateIn(List<String> states);

    List<Event> findAllByCategoryIdIn(List<Long> categoryId);

    List<Event> findEventsByEventDateIsAfterAndEventDateBefore(LocalDateTime start,
                                                               LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStateIn(List<Long> initiatorsId,
                                              List<String> states);

    List<Event> findAllByInitiatorIdInAndCategoryIdIn(List<Long> initiatorsId,
                                                      List<Long> categoriesId);

    List<Event> findAllByInitiatorIdInAndEventDateIsAfterAndEventDateIsBefore(List<Long> initiatorsId,
                                                                            LocalDateTime start,
                                                                            LocalDateTime end);

    List<Event> findAllByStateInAndCategoryIdIn(List<String> states,
                                                     List<Long> categoriesId);

    List<Event> findAllByStateInAndEventDateAfterAndEventDateBefore(List<String> states,
                                                                   LocalDateTime start,
                                                                   LocalDateTime end);

    List<Event> findAllByCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> categoriesId,
                                                                         LocalDateTime start,
                                                                         LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdIn(List<Long> initiatorsId,
                                                                     List<String> states,
                                                                     List<Long> categoriesId);

    List<Event> findAllByInitiatorIdInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                         List<Long> categoriesId,
                                                                                         LocalDateTime start,
                                                                                         LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStateInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                 List<String> states,
                                                                                 LocalDateTime start,
                                                                                 LocalDateTime end);

    List<Event> findAllByEventDateAfter(LocalDateTime start);

    List<Event> findAllByEventDateBefore(LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndEventDateAfter(List<Long> initiatorsId,
                                                      LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndEventDateBefore(List<Long> initiatorsId,
                                                     LocalDateTime end);

    List<Event> findAllByCategoryIdInAndEventDateAfter(List<Long> categoriesId,
                                                       LocalDateTime start);

    List<Event> findAllByCategoryIdInAndEventDateBefore(List<Long> categoriesId,
                                                        LocalDateTime end);

    List<Event> findAllByStateInAndEventDateAfter(List<String> states,
                                                 LocalDateTime start);

    List<Event> findAllByStateInAndEventDateBefore(List<String> states,
                                                  LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndCategoryIdInAndEventDateAfter(List<Long> initiatorsId,
                                                                       List<Long> categoriesId,
                                                                       LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryIdInAndEventDateBefore(List<Long> initiatorsId,
                                                                        List<Long> categoriesId,
                                                                        LocalDateTime end);

    List<Event> findAllByStateInAndCategoryIdInAndEventDateBefore(List<String> states,
                                                                       List<Long> categoriesId,
                                                                       LocalDateTime end);

    List<Event> findAllByStateInAndCategoryIdInAndEventDateAfter(List<String> states,
                                                                      List<Long> categoriesId,
                                                                      LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryIdInAndStateInAndEventDateAfter(List<Long> initiatorsId,
                                                                                      List<Long> categoriesId,
                                                                                      List<String> states,
                                                                                      LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryIdInAndStateInAndEventDateBefore(List<Long> initiatorsId,
                                                                                       List<Long> categoriesId,
                                                                                       List<String> states,
                                                                                       LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStateInAndEventDateAfter(List<Long> initiatorsId,
                                                               List<String> states,
                                                               LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndStateInAndEventDateBefore(List<Long> initiatorsId,
                                                               List<String> states,
                                                               LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                                        List<String> states,
                                                                                                        List<Long> categoriesId,
                                                                                                        LocalDateTime start,
                                                                                                        LocalDateTime end);

    List<Event> findAllByStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<String> states,
                                                                                        List<Long> categoriesId,
                                                                                        LocalDateTime start,
                                                                                        LocalDateTime end);
}
