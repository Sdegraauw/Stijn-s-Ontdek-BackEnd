package Ontdekstation013.ClimateChecker.features.meetjestad;

import lombok.Getter;

import java.time.Instant;

@Getter
public class MeetJeStadDTO {
    private int id;
    private Instant timestamp;
    private float latitude;
    private float longitude;
    private Float temperature;
    private Float humidity;
}
