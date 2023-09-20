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
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_annotation")
    private String annotation;

    @Column(name = "event_confirmed_request")
    private Long confirmedRequest;

    @Column(name = "event_created_on")
    private LocalDateTime createdOn;

    @Column(name = "event_description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne(targetEntity = User.class)
    private User initiator;

    @ManyToOne(targetEntity = Location.class)
    private Location location;

    @Column(name = "is_event_paid")
    private Boolean isPaid;

    @Column(name = "event_participant_limit")
    private Long participantLimit;

    @Column(name = "event_published_on")
    private LocalDateTime publishedOn;

    @Column(name = "is_event_participation_requests_moderation")
    private Boolean isRequestModeration;

    @Column(name = "event_status_id")
    private Integer statusId;

    @Column(name = "event_title")
    private String title;

    @Column(name = "event_views")
    private Long views;

    @ManyToMany(targetEntity = Compilation.class,
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                mappedBy = "events")
    private List<Compilation> compilations;

    @ManyToMany(targetEntity = Category.class,
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                mappedBy = "events")
    @Column(name = "event_category_id")
    private List<Category> categories;

    @ManyToOne(targetEntity = EventStatus.class)
    private EventStatus status;
}
