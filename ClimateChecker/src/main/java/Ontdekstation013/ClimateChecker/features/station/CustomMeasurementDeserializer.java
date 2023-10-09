package Ontdekstation013.ClimateChecker.features.station;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomMeasurementDeserializer extends StdDeserializer {
    public CustomMeasurementDeserializer() {
        this(null);
    }

    public CustomMeasurementDeserializer(Class<?> vc) {
        super(vc);
    }
    @Override
    public Measurement deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        Measurement measurement = new Measurement();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // try catch block
        int stationId = node.get("id").asInt();
        measurement.setStationId(stationId);
        LocalDateTime timeStamp = LocalDateTime.parse(node.get("timestamp").asText());
        measurement.setTimeStamp(timeStamp);
        float latitude = node.get("latitude").floatValue();
        measurement.setLastLatitude(latitude);
        float longitude = node.get("longitude").floatValue();
        measurement.setLastLongitude(longitude);
        float temperature = node.get("temperature").floatValue();
        measurement.setTemperature(temperature);
        return measurement;
    }

}
