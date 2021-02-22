/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.api.klv.st0806;

/**
 * Shared implementation for AircraftMGRSLatitudeBandAndGridSquare and
 * FrameCentreMGRSLatitudeBandAndGridSquare.
 */
public abstract class AbstractMGRSLatitudeBandAndGridSquare extends RvtString
        implements IRvtMetadataValue {
    private static final int REQUIRED_LENGTH = 3;
    private static final int REQUIRED_NUM_BYTES = 3;

    /**
     * Create from value.
     *
     * @param name the display name for the specific implementation
     * @param value The string value, which can only use the ASCII subset of UTF-8.
     */
    public AbstractMGRSLatitudeBandAndGridSquare(String name, String value) {
        super(name, value);
        if (value.length() != REQUIRED_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " is three character string");
        }
    }

    /**
     * Create from encoded bytes.
     *
     * @param name the display name for the specific implementation
     * @param bytes Encoded byte array
     */
    public AbstractMGRSLatitudeBandAndGridSquare(String name, byte[] bytes) {
        super(name, bytes);
        if (bytes.length != REQUIRED_NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is three byte string");
        }
    }

    /**
     * Get the alpha code for the latitude band.
     *
     * @return single character string.
     */
    public String getLatitudeBand() {
        return this.stringValue.substring(0, 1);
    }

    /**
     * Get the grid square designator.
     *
     * @return two characters specifying the grid square in MGRS.
     */
    public String getGridSquare() {
        return this.stringValue.substring(1);
    }
}
