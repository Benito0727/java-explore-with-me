package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "state")
    private String state;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToMany(mappedBy = "comments")
    private List<Event> events;

    @Column(name = "edited_on")
    private LocalDateTime editedOn;

    @Column(name = "rate")
    private Long rate;

    @Column(name = "isParticipant")
    private Boolean isParticipant;
}
