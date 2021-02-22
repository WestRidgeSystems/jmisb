package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for FpaIndexPack. */
public class FpaIndexPackTest {

    @Test
    public void testGetFpaRow() {
        FpaIndexPack instance = new FpaIndexPack((short) 10, (short) 20);
        short result = instance.getFpaRow();
        assertEquals(result, 10);
    }

    @Test
    public void testSetFpaRow() {
        FpaIndexPack instance = new FpaIndexPack((short) 10, (short) 20);
        assertEquals(instance.getFpaRow(), 10);
        instance.setFpaRow((short) 15);
        assertEquals(instance.getFpaRow(), 15);
    }

    @Test
    public void testGetFpaColumn() {
        FpaIndexPack instance = new FpaIndexPack((short) 10, (short) 20);
        short result = instance.getFpaColumn();
        assertEquals(result, 20);
    }

    @Test
    public void testSetFpaColumn() {
        FpaIndexPack instance = new FpaIndexPack((short) 10, (short) 20);
        assertEquals(instance.getFpaColumn(), 20);
        instance.setFpaColumn((short) 25);
        assertEquals(instance.getFpaColumn(), 25);
    }
}
