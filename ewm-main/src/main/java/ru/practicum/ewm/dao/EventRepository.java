package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorIdIn(List<Long> initiatorId);

    List<Event> findAllByStatusNameIn(List<String> status);

    List<Event> findAllByCategoryIn(List<Long> categoryId);

    List<Event> findEventsByEventDateIsAfterAndEventDateBefore(LocalDateTime start,
                                                               LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStatusNameIn(List<Long> initiatorsId,
                                              List<String> status);

    List<Event> findAllByInitiatorIdInAndCategoryIn(List<Long> initiatorsId,
                                                List<Long> categoriesId);

    List<Event> findAllByInitiatorIdInAndEventDateIsAfterAndEventDateIsBefore(List<Long> initiatorsId,
                                                                            LocalDateTime start,
                                                                            LocalDateTime end);

    List<Event> findAllByStatusNameInAndCategoryIn(List<String> status,
                                           List<Long> categoriesId);

    List<Event> findAllByStatusNameInAndEventDateAfterAndEventDateBefore(List<String> status,
                                                                   LocalDateTime start,
                                                                   LocalDateTime end);

    List<Event> findAllByCategoryInAndEventDateAfterAndEventDateBefore(List<Long> categoriesId,
                                                                     LocalDateTime start,
                                                                     LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStatusNameInAndCategoryIn(List<Long> initiatorsId,
                                                         List<String> status,
                                                         List<Long> categoriesId);

    List<Event> findAllByInitiatorIdInAndCategoryInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                 List<Long> categoriesId,
                                                                                 LocalDateTime start,
                                                                                 LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStatusNameInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                 List<String> status,
                                                                                 LocalDateTime start,
                                                                                 LocalDateTime end);

    List<Event> findAllByEventDateAfter(LocalDateTime start);

    List<Event> findAllByEventDateBefore(LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndEventDateAfter(List<Long> initiatorsId,
                                                      LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndEventDateBefore(List<Long> initiatorsId,
                                                     LocalDateTime end);

    List<Event> findAllByCategoryInAndEventDateAfter(List<Long> categoriesId,
                                                   LocalDateTime start);

    List<Event> findAllByCategoryInAndEventDateBefore(List<Long> categoriesId,
                                                    LocalDateTime end);

    List<Event> findAllByStatusNameInAndEventDateAfter(List<String> status,
                                                 LocalDateTime start);

    List<Event> findAllByStatusNameInAndEventDateBefore(List<String> status,
                                                  LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndCategoryInAndEventDateAfter(List<Long> initiatorsId,
                                                                 List<Long> categoriesId,
                                                                 LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryInAndEventDateBefore(List<Long> initiatorsId,
                                                                  List<Long> categoriesId,
                                                                  LocalDateTime end);

    List<Event> findAllByStatusNameInAndCategoryInAndEventDateBefore(List<String> status,
                                                             List<Long> categoriesId,
                                                             LocalDateTime end);

    List<Event> findAllByStatusNameInAndCategoryInAndEventDateAfter(List<String> status,
                                                            List<Long> categoriesId,
                                                            LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryInAndStatusNameInAndEventDateAfter(List<Long> initiatorsId,
                                                                          List<Long> categoriesId,
                                                                          List<String> status,
                                                                          LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndCategoryInAndStatusNameInAndEventDateBefore(List<Long> initiatorsId,
                                                                           List<Long> categoriesId,
                                                                           List<String> status,
                                                                           LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStatusNameInAndEventDateAfter(List<Long> initiatorsId,
                                                               List<String> status,
                                                               LocalDateTime start);

    List<Event> findAllByInitiatorIdInAndStatusNameInAndEventDateBefore(List<Long> initiatorsId,
                                                               List<String> status,
                                                               LocalDateTime end);

    List<Event> findAllByInitiatorIdInAndStatusNameInAndCategoryInAndEventDateAfterAndEventDateBefore(List<Long> initiatorsId,
                                                                                            List<String> status,
                                                                                            List<Long> categoriesId,
                                                                                            LocalDateTime start,
                                                                                            LocalDateTime end);

    List<Event> findAllByStatusNameInAndCategoryInAndEventDateAfterAndEventDateBefore(List<String> status,
                                                                              List<Long> categoriesId,
                                                                              LocalDateTime start,
                                                                              LocalDateTime end);
}
