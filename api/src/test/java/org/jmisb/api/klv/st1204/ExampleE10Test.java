package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/**
 * ST1204 Example E10: Minor ID
 *
 * <pre>
 * 0102:03DD-9DEE-FB48-477B-8204-B050-6F6B-2A33:25
 * Usage Byte = 02 = 0 00 00 0 1 0 = (0=None,0=None,0=None,1=Included)
 * Version (1)
 * KLV: 060E2B3401010101 0E01040503000000 12010203DD9DEEFB 48477B8204B0506F 6B2A33
 * Text: 0102:03DD-9DEE-FB48-477B-8204-B050-6F6B-2A33:25
 * </pre>
 */
public class ExampleE10Test {

    public ExampleE10Test() {}

    @Test
    public void checkBuildFromString() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString("0102:03DD-9DEE-FB48-477B-8204-B050-6F6B-2A33:25");
        verifyCoreIdentifierE10(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0xDD,
                    (byte) 0x9D,
                    (byte) 0xEE,
                    (byte) 0xFB,
                    (byte) 0x48,
                    (byte) 0x47,
                    (byte) 0x7B,
                    (byte) 0x82,
                    (byte) 0x04,
                    (byte) 0xB0,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x6B,
                    (byte) 0x2A,
                    (byte) 0x33
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE10(coreIdentifier);
        CoreIdentifier copy = new CoreIdentifier(coreIdentifier);
        verifyCoreIdentifierE10(copy);
    }

    private void verifyCoreIdentifierE10(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.None);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.None);
        assertEquals(coreIdentifier.getSensorUUID(), null);
        assertEquals(coreIdentifier.getPlatformUUID(), null);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(
                coreIdentifier.getMinorUUID(),
                UUID.fromString("03DD9DEE-FB48-477B-8204-B0506F6B2A33"));
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0xDD,
                    (byte) 0x9D,
                    (byte) 0xEE,
                    (byte) 0xFB,
                    (byte) 0x48,
                    (byte) 0x47,
                    (byte) 0x7B,
                    (byte) 0x82,
                    (byte) 0x04,
                    (byte) 0xB0,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x6B,
                    (byte) 0x2A,
                    (byte) 0x33
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0102:03DD-9DEE-FB48-477B-8204-B050-6F6B-2A33:25";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
