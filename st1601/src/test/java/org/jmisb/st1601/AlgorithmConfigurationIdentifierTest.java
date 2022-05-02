package org.jmisb.st1601;

import static org.testng.Assert.*;

import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Algorithm Configuration Identifier. */
public class AlgorithmConfigurationIdentifierTest {

    private static final String TEST_UUID_AS_STRING = "5d9bbe5d-a2b9-4b48-83cf-0edf759b1509";
    private static final UUID TEST_UUID = UUID.fromString(TEST_UUID_AS_STRING);
    private static final byte[] TEST_UUID_BYTES =
            new byte[] {
                (byte) 0x5d,
                (byte) 0x9b,
                (byte) 0xbe,
                (byte) 0x5d,
                (byte) 0xa2,
                (byte) 0xb9,
                (byte) 0x4b,
                (byte) 0x48,
                (byte) 0x83,
                (byte) 0xcf,
                (byte) 0x0e,
                (byte) 0xdf,
                (byte) 0x75,
                (byte) 0x9b,
                (byte) 0x15,
                (byte) 0x09
            };

    @Test
    public void testConstructFromValue() {
        AlgorithmConfigurationIdentifier uut = new AlgorithmConfigurationIdentifier(TEST_UUID);
        assertEquals(uut.getBytes(), TEST_UUID_BYTES);
        assertEquals(uut.getDisplayName(), "Algorithm Configuration Identifier");
        assertEquals(uut.getDisplayableValue(), TEST_UUID_AS_STRING);
        assertEquals(uut.getUUID(), TEST_UUID);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        AlgorithmConfigurationIdentifier uut =
                new AlgorithmConfigurationIdentifier(TEST_UUID_BYTES);
        assertEquals(uut.getBytes(), TEST_UUID_BYTES);
        assertEquals(uut.getDisplayName(), "Algorithm Configuration Identifier");
        assertEquals(uut.getDisplayableValue(), TEST_UUID_AS_STRING);
        assertEquals(uut.getUUID(), TEST_UUID);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromEncodedBytesBad() throws KlvParseException {
        new AlgorithmConfigurationIdentifier(new byte[] {0x00});
    }
}
