package org.jmisb.api.klv.st0601.dto;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Location DTO
 */
public class LocationTest
{

    @Test
    public void hashTest()
    {
        Location loc = makeLocation();
        assertEquals(loc.hashCode(), 0xc1f03e83);
        loc.setLatitude(2.001);
        assertEquals(loc.hashCode(), 0xe7b6b704);
    }

    @Test
    public void equalsSameObject()
    {
        Location loc = makeLocation();
        assertTrue(loc.equals(loc));
    }

    @Test
    public void equalsSameValues()
    {
        Location loc1 = makeLocation();
        Location loc2 = makeLocation();
        assertTrue(loc1.equals(loc2));
        assertTrue(loc2.equals(loc1));
        assertTrue(loc1 != loc2);
    }

    @Test
    public void equalsNull()
    {
        Location loc = makeLocation();
        assertFalse(loc.equals(null));
    }

    @Test
    public void equalsDifferentClass()
    {
        Location loc = makeLocation();
        assertFalse(loc.equals(new String("blah")));
    }

    protected Location makeLocation()
    {
        Location loc = new Location();
        loc.setLatitude(2.0);
        loc.setLongitude(3.0);
        loc.setHAE(1.0);
        return loc;
    }

    @Test
    public void equalsDifferentLat()
    {
        Location loc1 = makeLocation();
        Location loc2 = makeLocation();
        assertTrue(loc1.equals(loc2));
        loc2.setLatitude(99.9);
        assertFalse(loc1.equals(loc2));
    }

    @Test
    public void equalsDifferentLon()
    {
        Location loc1 = makeLocation();
        Location loc2 = makeLocation();
        assertTrue(loc1.equals(loc2));
        loc2.setLongitude(99.9);
        assertFalse(loc1.equals(loc2));
    }

    @Test
    public void equalsDifferentHae()
    {
        Location loc1 = makeLocation();
        Location loc2 = makeLocation();
        assertTrue(loc1.equals(loc2));
        loc2.setHAE(99.9);
        assertFalse(loc1.equals(loc2));
    }

    @Test
    public void equalsOperations()
    {
        Location loc1 = makeLocation();
        Location loc2 = makeLocation();
        assertEquals(loc1, loc2);
        loc2.setHAE(99.9);
        assertNotEquals(loc1, loc2);
    }
}
