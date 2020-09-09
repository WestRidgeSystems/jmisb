package org.jmisb.api.klv.st190x;

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
}
