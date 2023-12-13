package Ontdekstation013.ClimateChecker.features.meetjestad.Serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantDeserializer implements JsonDeserializer<Instant> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString();
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
