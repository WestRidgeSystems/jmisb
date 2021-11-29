package org.jmisb.api.klv.st1205;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory method for CalibrationLocalSet.
 *
 * <p>This is used to link the ST 1205 Calibration Pack handling into the wider jmisb
 * implementation, and is not usually required directly.
 */
public class CalibrationPackFactory implements IMisbMessageFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalibrationPackFactory.class);

    @Override
    public CalibrationLocalSet create(byte[] bytes) throws KlvParseException {
        return new CalibrationLocalSet(bytes);
    }
}
