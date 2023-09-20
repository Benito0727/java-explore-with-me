package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "events_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer status;

    @Column(name = "status_name")
    private String name;

    @OneToMany(targetEntity = Event.class,
                fetch = FetchType.EAGER,
                mappedBy = "status")
    private List<Event> events;
}
