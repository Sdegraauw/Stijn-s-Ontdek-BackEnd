package Ontdekstation013.ClimateChecker.features.sensor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import Ontdekstation013.ClimateChecker.features.station.StationOld;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long SensorID;

    @ManyToOne
    @JoinColumn(name= "stationID")
    private StationOld station;

    private int sensorData;

    @ManyToOne
    @JoinColumn(name = "sensorTypeID")
    private SensorType sensorType;

    private boolean activeData;


    public Sensor(long id, int sensorData, SensorType sensorType, StationOld station){
        this.SensorID = id;
        this.station = station;
        this.sensorData = sensorData;
        this.sensorType = sensorType;
    }
    public Sensor(long id, int sensorData, SensorType sensorType, StationOld station, boolean activeData){
        this.SensorID = id;
        this.station = station;
        this.sensorData = sensorData;
        this.sensorType = sensorType;
        this.activeData = activeData;
    }

    public Sensor(int sensorData, SensorType sensorType, StationOld station){
        this.station = station;
        this.sensorData = sensorData;
        this.sensorType = sensorType;
    }
    public Sensor(int sensorData, SensorType sensorType, StationOld station, boolean activeData){
        this.station = station;
        this.sensorData = sensorData;
        this.sensorType = sensorType;
        this.activeData = activeData;
    }
}
