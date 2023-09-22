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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_lat")
    private Double lat;

    @Column(name = "location_lot")
    private Double lot;

    @OneToMany(targetEntity = Event.class,
                mappedBy = "location")
    private List<Event> events;

    public Location(Double lat, Double lot) {
        this.lat = lat;
        this.lot = lot;
    }
}
