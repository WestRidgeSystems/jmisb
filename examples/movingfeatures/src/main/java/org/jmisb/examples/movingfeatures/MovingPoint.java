package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for a single MovingPoint moving feature.
 *
 * <p>A temporal geometry represents the trajectory of a time-parameterized 0-dimensional geometric
 * primitive (Point) representing a single geographic position at a time position (instant) within
 * its temporal domain. Intuitively, this type depicts a set of curves in a spatiotemporal domain. A
 * MovingPoint is used to express mf:AbstractTrajectory in the OGC Moving Features standard. For
 * example, the movement information of people, vehicles, or hurricanes can be shared by instances
 * of the "MovingPoint" type.
 */
@JsonSerialize(using = MovingPointSerialiser.class)
public class MovingPoint {

    private int id;
    private final List<Coordinate> coordinatesList = new ArrayList<>();
    private final List<ZonedDateTime> datetimesList = new ArrayList<>();

    public MovingPoint(int id) {
        this.id = id;
    }

    public List<Coordinate> getCoordinatesList() {
        return coordinatesList;
    }

    public List<ZonedDateTime> getDatetimesList() {
        return datetimesList;
    }

    int getId() {
        return id;
    }
}
