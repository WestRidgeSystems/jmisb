package org.jmisb.api.klv.st0903.vtracker;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for the VelocityPack DTO. */
public class VelocityPackTest {

    @Test
    public void eastSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0);
        assertEquals(velPack.getEast(), 300.0, 0.1);
        velPack.setEast(301.0);
        assertEquals(velPack.getEast(), 301.0, 0.1);
    }

    @Test
    public void northSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0);
        assertEquals(velPack.getNorth(), 200.0, 0.1);
        velPack.setNorth(199.0);
        assertEquals(velPack.getNorth(), 199.0, 0.1);
    }

    @Test
    public void upSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0);
        assertEquals(velPack.getUp(), 100.0, 0.1);
        velPack.setUp(101.0);
        assertEquals(velPack.getUp(), 101.0, 0.1);
    }

    @Test
    public void sigEastSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(velPack.getSigEast(), 30.0, 0.1);
        velPack.setSigEast(31.0);
        assertEquals(velPack.getSigEast(), 31.0, 0.1);
    }

    @Test
    public void sigNorthSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(velPack.getSigNorth(), 20.0, 0.1);
        velPack.setSigNorth(21.0);
        assertEquals(velPack.getSigNorth(), 21.0, 0.1);
    }

    @Test
    public void sigUpSetter() {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0, 30.0, 20.0, 10.0);
        assertEquals(velPack.getSigUp(), 10.0, 0.1);
        velPack.setSigUp(11.0);
        assertEquals(velPack.getSigUp(), 11.0, 0.1);
    }

    @Test
    public void rhoEastNorthSetter() {
        VelocityPack velPack =
                new VelocityPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(velPack.getRhoEastNorth(), 0.75, 0.1);
        velPack.setRhoEastNorth(0.78);
        assertEquals(velPack.getRhoEastNorth(), 0.78, 0.1);
    }

    @Test
    public void rhoEastUpSetter() {
        VelocityPack velPack =
                new VelocityPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(velPack.getRhoEastUp(), 0.5, 0.1);
        velPack.setRhoEastUp(0.58);
        assertEquals(velPack.getRhoEastUp(), 0.58, 0.1);
    }

    @Test
    public void rhoNorthUpSetter() {
        VelocityPack velPack =
                new VelocityPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        assertEquals(velPack.getRhoNorthUp(), 0.25, 0.1);
        velPack.setRhoNorthUp(0.28);
        assertEquals(velPack.getRhoNorthUp(), 0.28, 0.1);
    }
}
