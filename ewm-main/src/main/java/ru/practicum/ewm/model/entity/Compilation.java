package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;

    @ManyToMany(targetEntity = Event.class,
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                mappedBy = "compilations")
    List<Event> events;

    @Column(name = "is_pinned_compilation")
    private Boolean isPinned;

    @Column(name = "compilation_title")
    private String title;
}
