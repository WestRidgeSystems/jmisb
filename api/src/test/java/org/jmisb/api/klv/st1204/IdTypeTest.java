package org.jmisb.api.klv.st1204;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class IdTypeTest {
    
    public IdTypeTest() {
    }

    @Test
    public void checkEnumValue() {
        assertEquals(IdType.None.getValue(), 0);
        assertEquals(IdType.Managed.getValue(), 1);
        assertEquals(IdType.Virtual.getValue(), 2);
        assertEquals(IdType.Physical.getValue(), 3);
    }

    @Test
    public void checkFromValue() {
        assertEquals(IdType.fromValue(0), IdType.None);
        assertEquals(IdType.fromValue(1), IdType.Managed);
        assertEquals(IdType.fromValue(2), IdType.Virtual);
        assertEquals(IdType.fromValue(3), IdType.Physical);
    }
    
    @Test
    public void checkOutOfRangeValue() {
        assertEquals(IdType.fromValue(-1), IdType.None);
        assertEquals(IdType.fromValue(4), IdType.None);
    }
}
