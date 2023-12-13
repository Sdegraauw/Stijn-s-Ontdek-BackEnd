package Ontdekstation013.ClimateChecker.features.meetjestad;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetJeStadDTO {
    private int id;
    private Instant timestamp;
    private float latitude;
    private float longitude;
    private Float temperature;
    private Float humidity;
}
