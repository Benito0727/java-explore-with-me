package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "annotation")
    private String annotation;  // 1

    @Column(name = "description")
    private String description;  // 1

    @Column(name = "created_on")
    private LocalDateTime createdOn;  // 1

    @Column(name = "event_date")
    private LocalDateTime eventDate;  // 1

    @Column(name = "is_event_paid")
    private Boolean isPaid;

    @Column(name = "event_state")
    private String state;

    @Column(name = "event_participant_limit")
    private Long participantLimit;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "is_event_participant_request_moderation")
    private Boolean isRequestModeration;

    @Column(name = "views")
    private Long views;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToMany(mappedBy = "events")
    private List<Compilation> compilations;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
