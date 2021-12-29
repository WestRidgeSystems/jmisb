package org.jmisb.api.klv.st0601;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st1108.st1108_3.metric.*;
import org.testng.annotations.Test;

/** @author bradh */
public class ControlCommandsKeyTest {

    public ControlCommandsKeyTest() {}

    @Test
    public void checkToString() {
        ControlCommandsKey key = new ControlCommandsKey(2);
        assertEquals(key.toString(), "Command 2");
    }

    @Test
    public void checkHash() {
        ControlCommandsKey key = new ControlCommandsKey(2);
        assertEquals(key.hashCode(), 337);
    }

    @Test
    public void checkCompareTo() {
        ControlCommandsKey key = new ControlCommandsKey(2);
        ControlCommandsKey keyHigher = new ControlCommandsKey(4);
        assertTrue(key.compareTo(keyHigher) < 0);
    }

    @Test
    public void checkEquals() {
        ControlCommandsKey key = new ControlCommandsKey(2);
        assertFalse(key.equals(null));
        assertFalse(key.equals(new ControlCommandsKey(3)));
        assertFalse(key.equals(2));
        assertTrue(key.equals(key));
    }
}
