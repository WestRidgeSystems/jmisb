package org.jmisb.api.klv.st1907;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Additional unit tests for LaserSensor. */
public class AdditionalLaserSensorTest {

    public AdditionalLaserSensorTest() {}

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesConstructorBad() throws KlvParseException {
        new LaserSensor(new byte[] {0x2a, 0x05, 0x01, 0x02, 0x03, 0x04, 0x05}, 0, 7);
    }
}
