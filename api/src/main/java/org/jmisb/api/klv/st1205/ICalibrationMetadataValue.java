package org.jmisb.api.klv.st1205;

import org.jmisb.api.klv.IKlvValue;

/**
 * Calibration Pack metadata value.
 *
 * <p>All values for the Calibration Pack local set implement this interface.
 */
public interface ICalibrationMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}
