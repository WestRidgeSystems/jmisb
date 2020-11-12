package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST1206 SAR Motion Imagery Local Set. */
public class SARMILocalSetTest extends LoggerChecks {

    SARMILocalSetTest() {
        super(SARMILocalSet.class);
    }

    @Test
    public void parseTags() throws KlvParseException {
        final byte[] bytes = new byte[] {0x03, 0x01, 0x01};
        SARMILocalSet localSet = new SARMILocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST1206 SAR Motion Imagery");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.SARMILocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkLookDirectionExample(localSet);
        assertEquals(
                localSet.frameMessage(false),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x0D,
                    0x00, 0x00, 0x00, 0x03, 0x03, 0x01, 0x01
                });
        assertEquals(localSet.frameMessage(true), new byte[] {0x03, 0x01, 0x01});
    }

    @Test
    public void parseTagsWithUnknown() throws KlvParseException {
        final byte[] bytes = new byte[] {0x4f, 0x02, 0x00, 0x00, 0x03, 0x01, 0x01};
        SARMILocalSet localSet = new SARMILocalSet(bytes);
        this.verifySingleLoggerMessage("Unknown SAR Motion Imagery Metadata tag: 79");
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST1206 SAR Motion Imagery");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.SARMILocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkLookDirectionExample(localSet);
        assertEquals(
                localSet.frameMessage(false),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x0D,
                    0x00, 0x00, 0x00, 0x03, 0x03, 0x01, 0x01
                });
        assertEquals(localSet.frameMessage(true), new byte[] {0x03, 0x01, 0x01});
    }

    @Test
    public void createFromValues() throws KlvParseException {
        final Map<SARMIMetadataKey, ISARMIMetadataValue> values = new HashMap<>();
        values.put(SARMIMetadataKey.LookDirection, new LookDirection((byte) 1));
        SARMILocalSet localSet = new SARMILocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST1206 SAR Motion Imagery");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.SARMILocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkLookDirectionExample(localSet);
        assertEquals(
                localSet.frameMessage(false),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x0D,
                    0x00, 0x00, 0x00, 0x03, 0x03, 0x01, 0x01
                });
        assertEquals(localSet.frameMessage(true), new byte[] {0x03, 0x01, 0x01});
    }

    private void checkLookDirectionExample(SARMILocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(SARMIMetadataKey.LookDirection));
        ISARMIMetadataValue v = localSet.getField(SARMIMetadataKey.LookDirection);
        assertEquals(v.getDisplayName(), "Look Direction");
        assertEquals(v.getDisplayableValue(), "Right");
        assertTrue(v instanceof LookDirection);
        LookDirection lookDirection =
                (LookDirection) localSet.getField(SARMIMetadataKey.LookDirection);
        assertEquals(lookDirection.getLookDirection(), 1);
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        ISARMIMetadataValue unknown =
                SARMILocalSet.createValue(SARMIMetadataKey.Undefined, new byte[] {0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown SAR Motion Imagery Metadata tag: Undefined");
        assertNull(unknown);
    }
}
