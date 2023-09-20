package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "participation_requests_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestsStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer id;

    @Column(name = "status_name")
    private String name;

    @OneToMany(targetEntity = ParticipationRequest.class)
    private List<ParticipationRequest> requests;
}
