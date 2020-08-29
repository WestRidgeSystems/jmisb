package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/** JSON serialiser for MovingFeaturesCollection */
public class MovingFeaturesCollectionSerialiser extends StdSerializer<MovingFeaturesCollection> {

    public MovingFeaturesCollectionSerialiser() {
        this(null);
    }

    public MovingFeaturesCollectionSerialiser(Class<MovingFeaturesCollection> t) {
        super(t);
    }

    @Override
    public void serialize(MovingFeaturesCollection t, JsonGenerator jg, SerializerProvider sp)
            throws IOException {
        jg.writeStartObject();
        jg.writeStringField("type", "FeatureCollection");
        jg.writeArrayFieldStart("features");
        for (MovingPoint p : t.getFeatures()) {
            jg.writeObject(p);
        }
        jg.writeEndArray();
        jg.writeEndObject();
    }
}
