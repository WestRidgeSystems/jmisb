package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

class CoordinateSerialiser extends StdSerializer<Coordinate> {

    public CoordinateSerialiser() {
        this(null);
    }

    public CoordinateSerialiser(Class<Coordinate> t) {
        super(t);
    }

    @Override
    public void serialize(Coordinate t, JsonGenerator jg, SerializerProvider sp)
            throws IOException {
        jg.writeStartArray();
        jg.writeNumber(t.getLongitude());
        jg.writeNumber(t.getLatitude());
        if (t.getHae() != null) {
            jg.writeNumber(t.getHae());
        }
        jg.writeEndArray();
    }
}
