package ru.practicum.ewm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_lat")
    private Double lat;

    @Column(name = "location_lon")
    private Double lon;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Event> events;

    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
