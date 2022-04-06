package org.jmisb.st0903.vtracker;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for the AccelerationPack DTO. */
public class AccelerationPackTest {

    @Test
    public void eastSetter() {
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0);
        assertEquals(accelerationPack.getEast(), 300.0, 0.1);
        accelerationPack.setEast(301.0);
        assertEquals(accelerationPack.getEast(), 301.0, 0.1);
    }

    @Test
    public void northSetter() {
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0);
        assertEquals(accelerationPack.getNorth(), 200.0, 0.1);
        accelerationPack.setNorth(199.0);
        assertEquals(accelerationPack.getNorth(), 199.0, 0.1);
    }

    @Test
    public void upSetter() {
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0);
        assertEquals(accelerationPack.getUp(), 100.0, 0.1);
        accelerationPack.setUp(101.0);
        assertEquals(accelerationPack.getUp(), 101.0, 0.1);
    }

    @Test
    public void sigEastSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(accelerationPack.getSigEast(), 30.0, 0.1);
        accelerationPack.setSigEast(31.0);
        assertEquals(accelerationPack.getSigEast(), 31.0, 0.1);
    }

    @Test
    public void sigNorthSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(accelerationPack.getSigNorth(), 20.0, 0.1);
        accelerationPack.setSigNorth(21.0);
        assertEquals(accelerationPack.getSigNorth(), 21.0, 0.1);
    }

    @Test
    public void sigUpSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(accelerationPack.getSigUp(), 10.0, 0.1);
        accelerationPack.setSigUp(11.0);
        assertEquals(accelerationPack.getSigUp(), 11.0, 0.1);
    }

    @Test
    public void rhoEastNorthSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(accelerationPack.getRhoEastNorth(), 0.75, 0.1);
        accelerationPack.setRhoEastNorth(0.78);
        assertEquals(accelerationPack.getRhoEastNorth(), 0.78, 0.1);
    }

    @Test
    public void rhoEastUpSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(accelerationPack.getRhoEastUp(), 0.5, 0.1);
        accelerationPack.setRhoEastUp(0.58);
        assertEquals(accelerationPack.getRhoEastUp(), 0.58, 0.1);
    }

    @Test
    public void rhoNorthUpSetter() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(accelerationPack.getRhoNorthUp(), 0.25, 0.1);
        accelerationPack.setRhoNorthUp(0.28);
        assertEquals(accelerationPack.getRhoNorthUp(), 0.28, 0.1);
    }
}
