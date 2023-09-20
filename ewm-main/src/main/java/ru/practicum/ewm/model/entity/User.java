package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_name")
    private String name;

    @OneToMany(targetEntity = Event.class,
                mappedBy = "initiator")
    private List<Event> events;

    @OneToMany(targetEntity = ParticipationRequest.class,
                mappedBy = "requesterId")
    private List<ParticipationRequest> requests;
}
