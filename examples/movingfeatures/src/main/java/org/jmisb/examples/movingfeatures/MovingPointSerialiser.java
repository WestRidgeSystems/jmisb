package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** JSON serialiser for MovingPoint */
public class MovingPointSerialiser extends StdSerializer<MovingPoint> {

    public MovingPointSerialiser() {
        this(null);
    }

    public MovingPointSerialiser(Class<MovingPoint> t) {
        super(t);
    }

    @Override
    public void serialize(MovingPoint t, JsonGenerator jg, SerializerProvider sp)
            throws IOException {
        if (t.getCoordinatesList().size() < 2) {
            return;
        }
        jg.writeStartObject();
        jg.writeStringField("type", "Feature");
        jg.writeNumberField("id", t.getId());
        jg.writeObjectFieldStart("geometry");
        jg.writeStringField("type", "LineString");
        jg.writeArrayFieldStart("coordinates");
        for (Coordinate coordinate : t.getCoordinatesList()) {
            jg.writeObject(coordinate);
        }
        jg.writeEndArray();
        jg.writeEndObject();
        jg.writeObjectFieldStart("properties");
        jg.writeArrayFieldStart("datetimes");
        List<String> datetimeAsString = new ArrayList<>();
        for (ZonedDateTime zdt : t.getDatetimesList()) {
            jg.writeString(zdt.format(DateTimeFormatter.ISO_DATE_TIME));
        }
        jg.writeEndArray();
        jg.writeEndObject();
        jg.writeEndObject();
    }
}
