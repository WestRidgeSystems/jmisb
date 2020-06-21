package org.jmisb.api.klv.st0903.algorithm;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.annotations.Test;

/** Tests for the ST0903 Algorithm LS. */
public class AlgorithmLSTest extends LoggerChecks {
    public AlgorithmLSTest() {
        super(AlgorithmLS.class);
    }

    @Test
    public void parseTag1() throws KlvParseException {
        final byte[] bytes = new byte[] {0x01, 0x03, 0x01, 0x03, (byte) 0x98};
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 1);
        checkIdExample(algorithmLS);
    }

    @Test
    public void parseTag2() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x02, 0x14, 0x6B, 0x36, 0x5F, 0x79, 0x6F, 0x6C, 0x6F, 0x5F, 0x39, 0x30, 0x30,
                    0x30, 0x5F, 0x74, 0x72, 0x61, 0x63, 0x6B, 0x65, 0x72
                };
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 1);
        checkNameExample(algorithmLS);
    }

    @Test
    public void parseTag3() throws KlvParseException {
        final byte[] bytes = new byte[] {0x03, 0x04, 0x32, 0x2E, 0x36, 0x61};
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 1);
        checkVersionExample(algorithmLS);
    }

    @Test
    public void parseTag4() throws KlvParseException {
        final byte[] bytes = new byte[] {0x04, 0x07, 0x6B, 0x61, 0x6C, 0x6D, 0x61, 0x6E, 0x6E};
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 1);
        checkClassExample(algorithmLS);
    }

    @Test
    public void parseTag5() throws KlvParseException {
        final byte[] bytes = new byte[] {0x05, 0x01, 0x0A};
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 1);
        checkNumFramesExample(algorithmLS);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x02,
                    (byte) 0x80,
                    (byte) 0xCA, // No such tag
                    0x01,
                    0x03,
                    0x01,
                    0x03,
                    (byte) 0x98,
                    0x02,
                    0x14,
                    0x6B,
                    0x36,
                    0x5F,
                    0x79,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x5F,
                    0x39,
                    0x30,
                    0x30,
                    0x30,
                    0x5F,
                    0x74,
                    0x72,
                    0x61,
                    0x63,
                    0x6B,
                    0x65,
                    0x72,
                    0x03,
                    0x04,
                    0x32,
                    0x2E,
                    0x36,
                    0x61,
                    0x04,
                    0x07,
                    0x6B,
                    0x61,
                    0x6C,
                    0x6D,
                    0x61,
                    0x6E,
                    0x6E,
                    0x05,
                    0x01,
                    0x0A
                };
        verifyNoLoggerMessages();
        AlgorithmLS algorithmLS = new AlgorithmLS(bytes, 0, bytes.length);
        this.verifySingleLoggerMessage("Unknown VMTI Algorithm Metadata tag: 6");
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 5);
        checkIdExample(algorithmLS);
        checkClassExample(algorithmLS);
        checkNameExample(algorithmLS);
        checkVersionExample(algorithmLS);
        checkNumFramesExample(algorithmLS);
    }

    public void checkIdExample(AlgorithmLS algorithmLS) {
        assertTrue(algorithmLS.getTags().contains(AlgorithmMetadataKey.id));
        IVmtiMetadataValue v = algorithmLS.getField(AlgorithmMetadataKey.id);
        assertEquals(v.getDisplayName(), "Algorithm Id");
        assertEquals(v.getDisplayableValue(), "66456");
        assertTrue(v instanceof AlgorithmId);
        AlgorithmId id = (AlgorithmId) algorithmLS.getField(AlgorithmMetadataKey.id);
        assertEquals(id.getValue(), 66456);
        assertEquals(id.getBytes(), new byte[] {0x01, 0x03, (byte) 0x98});
    }

    public void checkNumFramesExample(AlgorithmLS algorithmLS) {
        assertTrue(algorithmLS.getTags().contains(AlgorithmMetadataKey.nFrames));
        IVmtiMetadataValue v = algorithmLS.getField(AlgorithmMetadataKey.nFrames);
        assertEquals(v.getDisplayName(), "Number of Frames");
        assertEquals(v.getDisplayableValue(), "10");
        assertTrue(v instanceof NumberOfFrames);
        NumberOfFrames nFrames =
                (NumberOfFrames) algorithmLS.getField(AlgorithmMetadataKey.nFrames);
        assertEquals(nFrames.getValue(), 10);
        assertEquals(nFrames.getBytes(), new byte[] {0x0A});
    }

    public void checkNameExample(AlgorithmLS algorithmLS) {
        final String stringVal = "k6_yolo_9000_tracker";
        assertTrue(algorithmLS.getTags().contains(AlgorithmMetadataKey.name));
        IVmtiMetadataValue v = algorithmLS.getField(AlgorithmMetadataKey.name);
        assertEquals(v.getDisplayName(), "Algorithm Name");
        assertEquals(v.getDisplayName(), VmtiTextString.ALGORITHM_NAME);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) algorithmLS.getField(AlgorithmMetadataKey.name);
        assertEquals(text.getValue(), stringVal);
    }

    public void checkVersionExample(AlgorithmLS algorithmLS) {
        final String stringVal = "2.6a";
        assertTrue(algorithmLS.getTags().contains(AlgorithmMetadataKey.version));
        IVmtiMetadataValue v = algorithmLS.getField(AlgorithmMetadataKey.version);
        assertEquals(v.getDisplayName(), "Algorithm Version");
        assertEquals(v.getDisplayName(), VmtiTextString.ALGORITHM_VERSION);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[] {0x32, 0x2E, 0x36, 0x61});
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) algorithmLS.getField(AlgorithmMetadataKey.version);
        assertEquals(text.getValue(), stringVal);
    }

    public void checkClassExample(AlgorithmLS algorithmLS) {
        final String stringVal = "kalmann";
        assertTrue(algorithmLS.getTags().contains(AlgorithmMetadataKey.algorithmClass));
        IVmtiMetadataValue v = algorithmLS.getField(AlgorithmMetadataKey.algorithmClass);
        assertEquals(v.getDisplayName(), "Algorithm Class");
        assertEquals(v.getDisplayName(), VmtiTextString.ALGORITHM_CLASS);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[] {0x6B, 0x61, 0x6C, 0x6D, 0x61, 0x6E, 0x6E});
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text =
                (VmtiTextString) algorithmLS.getField(AlgorithmMetadataKey.algorithmClass);
        assertEquals(text.getValue(), stringVal);
    }

    @Test
    public void createUnknownTag() throws KlvParseException {
        final byte[] bytes = new byte[] {0x6A, 0x70};
        verifyNoLoggerMessages();
        IVmtiMetadataValue value = AlgorithmLS.createValue(AlgorithmMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized Algorithm tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException {
        Map<AlgorithmMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        values.put(AlgorithmMetadataKey.id, new AlgorithmId(66456));
        IVmtiMetadataValue name =
                AlgorithmLS.createValue(
                        AlgorithmMetadataKey.name,
                        new byte[] {
                            0x6B, 0x36, 0x5F, 0x79, 0x6F, 0x6C, 0x6F, 0x5F, 0x39, 0x30, 0x30, 0x30,
                            0x5F, 0x74, 0x72, 0x61, 0x63, 0x6B, 0x65, 0x72
                        });
        values.put(AlgorithmMetadataKey.name, name);
        IVmtiMetadataValue version =
                AlgorithmLS.createValue(
                        AlgorithmMetadataKey.version, new byte[] {0x32, 0x2E, 0x36, 0x61});
        values.put(AlgorithmMetadataKey.version, version);
        IVmtiMetadataValue klass =
                AlgorithmLS.createValue(
                        AlgorithmMetadataKey.algorithmClass,
                        new byte[] {0x6B, 0x61, 0x6C, 0x6D, 0x61, 0x6E, 0x6E});
        values.put(AlgorithmMetadataKey.algorithmClass, klass);
        values.put(AlgorithmMetadataKey.nFrames, new NumberOfFrames(10));
        AlgorithmLS algorithmLS = new AlgorithmLS(values);
        assertNotNull(algorithmLS);
        assertEquals(algorithmLS.getTags().size(), 5);
        checkIdExample(algorithmLS);
        checkNameExample(algorithmLS);
        checkClassExample(algorithmLS);
        checkVersionExample(algorithmLS);
        checkNumFramesExample(algorithmLS);
        byte[] expectedBytes =
                new byte[] {
                    0x01,
                    0x03,
                    0x01,
                    0x03,
                    (byte) 0x98,
                    0x02,
                    0x14,
                    0x6B,
                    0x36,
                    0x5F,
                    0x79,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x5F,
                    0x39,
                    0x30,
                    0x30,
                    0x30,
                    0x5F,
                    0x74,
                    0x72,
                    0x61,
                    0x63,
                    0x6B,
                    0x65,
                    0x72,
                    0x03,
                    0x04,
                    0x32,
                    0x2E,
                    0x36,
                    0x61,
                    0x04,
                    0x07,
                    0x6B,
                    0x61,
                    0x6C,
                    0x6D,
                    0x61,
                    0x6E,
                    0x6E,
                    0x05,
                    0x01,
                    0x0A
                };
        assertEquals(algorithmLS.getBytes(), expectedBytes);
    }
}
