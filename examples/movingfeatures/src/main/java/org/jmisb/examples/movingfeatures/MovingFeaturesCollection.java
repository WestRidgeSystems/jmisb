package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@JsonSerialize(using = MovingFeaturesCollectionSerialiser.class)
public class MovingFeaturesCollection {
    private final Map<Integer, MovingPoint> collection = new TreeMap<>();

    boolean containsKey(Integer targetIdentifier) {
        return collection.containsKey(targetIdentifier);
    }

    void put(Integer targetIdentifier, MovingPoint movingPoint) {
        collection.put(targetIdentifier, movingPoint);
    }

    void addCoordinates(
            Integer targetIdentifier, Coordinate coordinatePair, ZonedDateTime timestamp) {
        if (!collection.containsKey(targetIdentifier)) {
            collection.put(targetIdentifier, new MovingPoint(targetIdentifier));
        }
        List<ZonedDateTime> dateTimes = collection.get(targetIdentifier).getDatetimesList();
        if (dateTimes.contains(timestamp)) {
            // MF Trajectory requires dates to be unique.
            return;
        }
        collection.get(targetIdentifier).getCoordinatesList().add(coordinatePair);
        collection.get(targetIdentifier).getDatetimesList().add(timestamp);
    }

    Iterable<MovingPoint> getFeatures() {
        return collection.values();
    }
}
