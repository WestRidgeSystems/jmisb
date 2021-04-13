package org.jmisb.api.klv.st1908;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import org.testng.annotations.Test;

/** Additional unit tests for ImagerSystem. */
public class AdditionalImagerSystemTest {

    public AdditionalImagerSystemTest() {}

    @Test
    public void fromValues() throws KlvParseException {
        SortedMap<ImagerSystemMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(ImagerSystemMetadataKey.name, new ImagerSystem_Name("Test Name"));
        ImagerSystem uut = new ImagerSystem(values);
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 1);
        IKlvKey identifier = (IKlvKey) uut.getIdentifiers().toArray()[0];
        assertEquals(identifier, ImagerSystemMetadataKey.name);
        assertEquals(uut.getField(identifier).getDisplayableValue(), "Test Name");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x09,
                    (byte) 0x54,
                    (byte) 0x65,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x20,
                    (byte) 0x4e,
                    (byte) 0x61,
                    (byte) 0x6D,
                    (byte) 0x65
                });
    }

    @Test
    public void fromBytesConstructor() throws KlvParseException {
        byte bytes[] =
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x09,
                    (byte) 0x54,
                    (byte) 0x65,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x20,
                    (byte) 0x4e,
                    (byte) 0x61,
                    (byte) 0x6D,
                    (byte) 0x65
                };
        ImagerSystem uut = new ImagerSystem(bytes, 0, bytes.length);
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 1);
        IKlvKey identifier = (IKlvKey) uut.getIdentifiers().toArray()[0];
        assertEquals(identifier, ImagerSystemMetadataKey.name);
        assertEquals(uut.getField(identifier).getDisplayableValue(), "Test Name");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x09,
                    (byte) 0x54,
                    (byte) 0x65,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x20,
                    (byte) 0x4e,
                    (byte) 0x61,
                    (byte) 0x6D,
                    (byte) 0x65
                });
    }

    @Test
    public void fromBytes() throws KlvParseException {
        byte bytes[] =
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x09,
                    (byte) 0x54,
                    (byte) 0x65,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x20,
                    (byte) 0x4e,
                    (byte) 0x61,
                    (byte) 0x6D,
                    (byte) 0x65
                };
        ImagerSystem uut = ImagerSystem.fromBytes(bytes);
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 1);
        IKlvKey identifier = (IKlvKey) uut.getIdentifiers().toArray()[0];
        assertEquals(identifier, ImagerSystemMetadataKey.name);
        assertEquals(uut.getField(identifier).getDisplayableValue(), "Test Name");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x09,
                    (byte) 0x54,
                    (byte) 0x65,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x20,
                    (byte) 0x4e,
                    (byte) 0x61,
                    (byte) 0x6D,
                    (byte) 0x65
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x22, (byte) 0x02, (byte) 0x54};
        ImagerSystem.fromBytes(bytes);
    }
}
