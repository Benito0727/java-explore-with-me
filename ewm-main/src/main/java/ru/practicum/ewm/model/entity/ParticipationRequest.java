package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "participation_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    private Long id;

    @ManyToMany(targetEntity = Event.class,
                mappedBy = "id")
    @Column(name = "participation_request_event_id")
    private List<Event> events;

    @ManyToOne(targetEntity = User.class)
    private User requesterId;

    @Column(name = "participation_request_created_on")
    private LocalDateTime createdOn;

    @ManyToOne(targetEntity = ParticipationRequestsStatus.class)
    @Column(name = "participation_request_status_id")
    private Integer statusId;
}
