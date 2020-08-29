package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/** 2D or 3D coordinate point. */
@JsonSerialize(using = CoordinateSerialiser.class)
class Coordinate {
    private Double latitude;
    private Double longitude;
    private Double hae;

    public Coordinate(Double longitude, Double latitude, Double hae) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.hae = hae;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHae() {
        return hae;
    }

    public void setHae(Double hae) {
        this.hae = hae;
    }

    @Override
    public String toString() {
        return "Coordinate{"
                + "latitude="
                + latitude
                + ", longitude="
                + longitude
                + ", hae="
                + hae
                + '}';
    }
}
