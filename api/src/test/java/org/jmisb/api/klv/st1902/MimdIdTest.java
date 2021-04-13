package org.jmisb.api.klv.st1902;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for MimdId. */
public class MimdIdTest {

    public MimdIdTest() {}

    @Test
    public void defaultGroup() {
        MimdId uut = new MimdId(3);
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[3, 0]");
        assertEquals(uut.getSerialNumber(), 3);
        assertEquals(uut.getGroupIdentifier(), 0);
        assertEquals(uut.getBytes(), new byte[] {0x03});
    }

    @Test
    public void explicitDefaultGroup() {
        MimdId uut = new MimdId(3, 0);
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[3, 0]");
        assertEquals(uut.getSerialNumber(), 3);
        assertEquals(uut.getGroupIdentifier(), 0);
        assertEquals(uut.getBytes(), new byte[] {0x03});
        assertEquals(uut.toString(), "MimdId{serialNumber=3, groupIdentifier=0}");
    }

    @Test
    public void nondefaultGroup() {
        MimdId uut = new MimdId(3, 8);
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[3, 8]");
        assertEquals(uut.getSerialNumber(), 3);
        assertEquals(uut.getGroupIdentifier(), 8);
        assertEquals(uut.getBytes(), new byte[] {0x03, 0x08});
    }

    @Test
    public void fromBytesDefaultGroup() throws KlvParseException {
        MimdId uut = new MimdId(new byte[] {0x40});
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[64, 0]");
        assertEquals(uut.getSerialNumber(), 64);
        assertEquals(uut.getGroupIdentifier(), 0);
        assertEquals(uut.getBytes(), new byte[] {0x40});
    }

    @Test
    public void fromBytesNonDefaultGroup() throws KlvParseException {
        MimdId uut = new MimdId(new byte[] {0x40, 0x03});
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[64, 3]");
        assertEquals(uut.getSerialNumber(), 64);
        assertEquals(uut.getGroupIdentifier(), 3);
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x03});
        assertEquals(uut.toString(), "MimdId{serialNumber=64, groupIdentifier=3}");
    }

    @Test
    public void fromBytesDefaultGroupLongSerial() throws KlvParseException {
        MimdId uut = new MimdId(new byte[] {(byte) 0x83, 0x04});
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[388, 0]");
        assertEquals(uut.getSerialNumber(), 388);
        assertEquals(uut.getGroupIdentifier(), 0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x83, 0x04});
    }

    @Test
    public void fromBytesDefaultGroupLongGroup() throws KlvParseException {
        MimdId uut = new MimdId(new byte[] {0x07, (byte) 0x82, 0x05});
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[7, 261]");
        assertEquals(uut.getSerialNumber(), 7);
        assertEquals(uut.getGroupIdentifier(), 261);
        assertEquals(uut.getBytes(), new byte[] {0x07, (byte) 0x82, 0x05});
    }

    @Test
    public void fromBytesDefaultGroupLongSerialAndGroup() throws KlvParseException {
        MimdId uut = new MimdId(new byte[] {(byte) 0x81, 0x07, (byte) 0x82, 0x05});
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[135, 261]");
        assertEquals(uut.getSerialNumber(), 135);
        assertEquals(uut.getGroupIdentifier(), 261);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, 0x07, (byte) 0x82, 0x05});
    }

    @Test
    public void fromValueDefaultGroupLongSerialAndGroup() throws KlvParseException {
        MimdId uut = new MimdId(135, 261);
        assertEquals(uut.getDisplayName(), "MIMD Id");
        assertEquals(uut.getDisplayableValue(), "[135, 261]");
        assertEquals(uut.getSerialNumber(), 135);
        assertEquals(uut.getGroupIdentifier(), 261);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, 0x07, (byte) 0x82, 0x05});
        assertEquals(uut.toString(), "MimdId{serialNumber=135, groupIdentifier=261}");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadSerial() throws KlvParseException {
        new MimdId(new byte[] {(byte) 0x83});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadGroup() throws KlvParseException {
        new MimdId(new byte[] {(byte) 0x83, 0x04, (byte) 0x81});
    }

    @Test
    public void equals() throws KlvParseException {
        MimdId uut1 = new MimdId(135, 261);
        MimdId uut2 = new MimdId(135, 261);
        assertEquals(uut1, uut2);
        assertTrue(uut1.equals(uut2));
        assertTrue(uut2.equals(uut1));
        assertEquals(uut1.hashCode(), uut2.hashCode());
    }

    @Test
    public void equalsSelf() throws KlvParseException {
        MimdId uut1 = new MimdId(135, 261);
        assertEquals(uut1, uut1);
        assertTrue(uut1.equals(uut1));
        assertEquals(uut1.hashCode(), uut1.hashCode());
    }

    @Test
    public void notEquals() throws KlvParseException {
        MimdId uut1_1 = new MimdId(1, 1);
        MimdId uut1_2 = new MimdId(1, 2);
        MimdId uut2_1 = new MimdId(2, 1);
        assertFalse(uut1_1.equals(uut1_2));
        assertFalse(uut1_2.equals(uut1_1));
        assertFalse(uut1_1.equals(uut2_1));
        assertFalse(uut2_1.equals(uut1_1));
        assertFalse(uut1_2.equals(uut2_1));
        assertFalse(uut2_1.equals(uut1_2));
        assertNotEquals(uut1_1.hashCode(), uut2_1.hashCode());
        assertNotEquals(uut1_2.hashCode(), uut2_1.hashCode());
        assertNotEquals(uut1_1.hashCode(), uut1_2.hashCode());
    }

    @Test
    public void notEqualsNull() throws KlvParseException {
        MimdId uut1_1 = new MimdId(1, 1);
        assertFalse(uut1_1.equals(null));
        assertNotEquals(uut1_1, null);
    }

    @Test
    public void notEqualsDifferentType() throws KlvParseException {
        MimdId uut1_1 = new MimdId(1, 1);
        assertFalse(uut1_1.equals("2"));
    }
}
