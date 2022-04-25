package org.jmisb.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0601.dto.Location;
import org.jmisb.st0601.dto.Waypoint;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WaypointListTest {
    private final byte[] st_example_bytes =
            new byte[] {
                (byte) 0x0F, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x40,
                        (byte) 0x71, (byte) 0xD8, (byte) 0x94, (byte) 0x19, (byte) 0xBD,
                        (byte) 0xBF, (byte) 0xE7, (byte) 0x08, (byte) 0x98, (byte) 0x00,
                (byte) 0x0F, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x02, (byte) 0x40,
                        (byte) 0x71, (byte) 0xD3, (byte) 0x88, (byte) 0x19, (byte) 0xBC,
                        (byte) 0xCE, (byte) 0x24, (byte) 0x08, (byte) 0xFC, (byte) 0x00,
                (byte) 0x0F, (byte) 0x02, (byte) 0x7F, (byte) 0xFF, (byte) 0x01, (byte) 0x40,
                        (byte) 0x71, (byte) 0xE3, (byte) 0x08, (byte) 0x19, (byte) 0xBF,
                        (byte) 0x2C, (byte) 0x1B, (byte) 0x07, (byte) 0xD0, (byte) 0x00,
                (byte) 0x0F, (byte) 0x03, (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x40,
                        (byte) 0x71, (byte) 0xE5, (byte) 0xAF, (byte) 0x19, (byte) 0xBF,
                        (byte) 0x5A, (byte) 0xA7, (byte) 0x09, (byte) 0x60, (byte) 0x00
            };

    @Test
    public void testConstructFromValue() {
        List<Waypoint> waypoints = new ArrayList<>();
        Waypoint wp0 = new Waypoint();
        // (0, 1, (1, 1), (38.889422, -77.035162, 200))
        waypoints.add(wp0);
        wp0.setWaypointID(0);
        wp0.setProsecutionOrder((short) 1);
        wp0.setManualMode(true);
        wp0.setAdhocSource(true);
        Location wp0Location = new Location();
        wp0Location.setLatitude(38.889422);
        wp0Location.setLongitude(-77.035162);
        wp0Location.setHAE(200.0);
        wp0.setLocation(wp0Location);
        Waypoint wp1 = new Waypoint();
        // (1, 2, (1, 0), (38.889268, -77.049918, 250))
        waypoints.add(wp1);
        wp1.setWaypointID(1);
        wp1.setProsecutionOrder((short) 2);
        wp1.setAdhocSource(true);
        wp1.setManualMode(false);
        Location wp1Location = new Location();
        wp1Location.setLatitude(38.889268);
        wp1Location.setLongitude(-77.049918);
        wp1Location.setHAE(250.0);
        wp1.setLocation(wp1Location);
        Waypoint wp2 = new Waypoint();
        // (2, 32767, (0, 1), (38.889741, -77.012933, 100))
        wp2.setWaypointID(2);
        wp2.setProsecutionOrder((short) 32767);
        wp2.setAdhocSource(false);
        wp2.setManualMode(true);
        Location wp2Location = new Location();
        wp2Location.setLatitude(38.889741);
        wp2Location.setLongitude(-77.012933);
        wp2Location.setHAE(100.0);
        wp2.setLocation(wp2Location);
        waypoints.add(wp2);
        Waypoint wp3 = new Waypoint();
        // (3, -2, (0, 0), (38.889822, -77.010092, 300))
        wp3.setWaypointID(3);
        wp3.setProsecutionOrder((short) -2);
        wp3.setAdhocSource(false);
        wp3.setManualMode(false);
        Location wp3Location = new Location();
        wp3Location.setLatitude(38.889822);
        wp3Location.setLongitude(-77.010092);
        wp3Location.setHAE(300.0);
        wp3.setLocation(wp3Location);
        waypoints.add(wp3);
        WaypointList list = new WaypointList(waypoints);
        checkWaypointList(list);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        WaypointList list = new WaypointList(st_example_bytes);
        checkWaypointList(list);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.WaypointList, st_example_bytes);
        Assert.assertTrue(v instanceof WaypointList);
        WaypointList list = (WaypointList) v;
        checkWaypointList(list);
    }

    private void checkWaypointList(WaypointList list) {
        List<Waypoint> waypoints = list.getWaypoints();
        Assert.assertEquals(waypoints.size(), 4);
        Waypoint wp0 = waypoints.get(0);
        Assert.assertEquals(wp0.getWaypointID(), 0);
        Assert.assertEquals(wp0.getProsecutionOrder(), 1);
        Assert.assertTrue(wp0.isManualMode());
        Assert.assertTrue(wp0.isAdhocSource());
        Assert.assertEquals(wp0.getLocation().getLatitude(), 38.889422, 0.000001);
        Assert.assertEquals(wp0.getLocation().getLongitude(), -77.035162, 0.000001);
        Assert.assertEquals(wp0.getLocation().getHAE(), 200.0, 0.0001);
        Waypoint wp1 = waypoints.get(1);
        Assert.assertEquals(wp1.getWaypointID(), 1);
        Assert.assertEquals(wp1.getProsecutionOrder(), 2);
        Assert.assertFalse(wp1.isManualMode());
        Assert.assertTrue(wp1.isAdhocSource());
        Assert.assertEquals(wp1.getLocation().getLatitude(), 38.889268, 0.000001);
        Assert.assertEquals(wp1.getLocation().getLongitude(), -77.049918, 0.000001);
        Assert.assertEquals(wp1.getLocation().getHAE(), 250.0, 0.0001);
        Waypoint wp2 = waypoints.get(2);
        Assert.assertEquals(wp2.getWaypointID(), 2);
        Assert.assertEquals(wp2.getProsecutionOrder(), 32767);
        Assert.assertTrue(wp2.isManualMode());
        Assert.assertFalse(wp2.isAdhocSource());
        Assert.assertEquals(wp2.getLocation().getLatitude(), 38.889741, 0.000001);
        Assert.assertEquals(wp2.getLocation().getLongitude(), -77.012933, 0.000001);
        Assert.assertEquals(wp2.getLocation().getHAE(), 100.0, 0.0001);
        Waypoint wp3 = waypoints.get(3);
        Assert.assertEquals(wp3.getWaypointID(), 3);
        Assert.assertEquals(wp3.getProsecutionOrder(), -2);
        Assert.assertFalse(wp3.isManualMode());
        Assert.assertFalse(wp3.isAdhocSource());
        Assert.assertEquals(wp3.getLocation().getLatitude(), 38.889822, 0.000001);
        Assert.assertEquals(wp3.getLocation().getLongitude(), -77.010092, 0.000001);
        Assert.assertEquals(wp3.getLocation().getHAE(), 300.0, 0.0001);
        Assert.assertEquals(list.getBytes(), st_example_bytes);
        Assert.assertEquals(list.getDisplayableValue(), "[Waypoint List]");
        Assert.assertEquals(list.getDisplayName(), "Waypoint List");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badLength() throws KlvParseException {
        new WaypointList(new byte[] {0x01});
    }
}
