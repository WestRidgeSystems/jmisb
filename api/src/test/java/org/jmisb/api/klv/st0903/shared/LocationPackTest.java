package org.jmisb.api.klv.st0903.shared;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 * Test for LocationPack
 */
public class LocationPackTest
{
    @Test
    public void testLat()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        locPack.setLat(-45.2);
        assertEquals(locPack.getLat(), -45.2, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }

    @Test
    public void testLon()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        locPack.setLon(-45.2);
        assertEquals(locPack.getLon(), -45.2, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }

    @Test
    public void testHae()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        locPack.setHae(1000.0);
        assertEquals(locPack.getHae(), 1000.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }

    @Test
    public void testSigEast()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        locPack.setSigEast(2.1);
        assertEquals(locPack.getSigEast(), 2.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }

    @Test
    public void testSigNorth()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        locPack.setSigNorth(2.1);
        assertEquals(locPack.getSigNorth(), 2.1, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }

    @Test
    public void testRho()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
        locPack.setRhoEastNorth(0.2);
        assertEquals(locPack.getRhoEastNorth(), 0.2, 0.001);
        locPack.setRhoEastUp(0.4);
        assertEquals(locPack.getRhoEastUp(), 0.4, 0.001);
        locPack.setRhoNorthUp(0.6);
        assertEquals(locPack.getRhoNorthUp(), 0.6, 0.001);
    }


    @Test
    public void testSiUp()
    {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        assertEquals(locPack.getLat(), -10.5423886331461, 0.0001);
        assertEquals(locPack.getLon(), 29.157890122923, 0.0001);
        assertEquals(locPack.getHae(), 3216.0, 0.2);
        assertEquals(locPack.getSigEast(), 0.1, 0.03);
        assertEquals(locPack.getSigNorth(), 3.0, 0.03);
        assertEquals(locPack.getSigUp(), 649.9, 0.03);
        locPack.setSigUp(2.1);
        assertEquals(locPack.getSigUp(), 2.1, 0.03);
        assertEquals(locPack.getRhoEastNorth(), -1.0, 0.001);
        assertEquals(locPack.getRhoEastUp(), 0.0, 0.001);
        assertEquals(locPack.getRhoNorthUp(), 0.5, 0.001);
    }
}
