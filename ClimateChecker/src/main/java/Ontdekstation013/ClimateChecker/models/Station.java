package Ontdekstation013.ClimateChecker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long StationID;

    @OneToMany(
            mappedBy = "station",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Sensor> sensors = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "userID")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    private float height;

    private String Name;



    public Station(long id, List<Sensor> sensors, User owner, Location location, float height, String name) {
        this.StationID = id;
        this.sensors = sensors;
        this.owner = owner;
        this.location = location;
        this.height = height;
        this.Name = name;
    }
}
