package org.jmisb.st0805;

import org.jmisb.st0805.CotSensor;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for CotSensor. */
public class CotSensorTest {

    public CotSensorTest() {}

    @Test
    public void serialise() {
        CotSensor uut = new CotSensor();
        uut.setAzimuth(10.3);
        uut.setFov(3.53);
        uut.setVerticalFov(2.45);
        uut.setModel("EON");
        uut.setRange(1234.5);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(
                sb.toString(),
                "<sensor azimuth='10.3' fov='3.53' vfov='2.45' model='EON' range='1234.5'/>");
    }

    @Test
    public void serialiseEmpty() {
        CotSensor uut = new CotSensor();
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "<sensor/>");
    }
}
