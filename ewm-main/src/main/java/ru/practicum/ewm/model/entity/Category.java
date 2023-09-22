package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_title")
    private String title;

    @ManyToMany(targetEntity = Event.class,
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    private List<Event> events;
}