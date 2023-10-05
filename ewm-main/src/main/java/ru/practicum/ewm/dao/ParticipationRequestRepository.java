package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.entity.ParticipationRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByIdIn(List<Long> requestsId);

    List<ParticipationRequest> findAllByEventIdAndState(Long eventId, String state);

    Integer countAllByEventIdAndState(Long eventId, String state);

    Optional<ParticipationRequest> findParticipationRequestsByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long requesterId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByEventIdIn(List<Long> eventsId);
}
