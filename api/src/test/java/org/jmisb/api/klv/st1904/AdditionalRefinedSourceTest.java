package org.jmisb.api.klv.st1904;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Additional unit tests for RefinedSource. */
public class AdditionalRefinedSourceTest {

    public AdditionalRefinedSourceTest() {}

    @Test
    public void checkFromBytesConstructor() throws KlvParseException {
        byte[] bytes = getValidBytes();
        RefinedSource refinedSource = new RefinedSource(bytes, 0, 3);
        checkRefinedSourceResult(refinedSource);
    }

    @Test
    public void checkFromBytes() throws KlvParseException {
        byte[] bytes = getValidBytes();
        RefinedSource refinedSource = RefinedSource.fromBytes(bytes);
        checkRefinedSourceResult(refinedSource);
    }

    private static byte[] getValidBytes() {
        return new byte[] {(byte) 0x23, (byte) 0x01, (byte) 0x03};
    }

    private void checkRefinedSourceResult(RefinedSource refinedSource) {
        assertEquals(refinedSource.getIdentifiers().size(), 1);
        assertTrue(refinedSource.getIdentifiers().contains(RefinedSourceMetadataKey.attributeId));
        RefinedSource_AttributeId attributeId =
                (RefinedSource_AttributeId)
                        refinedSource.getField(RefinedSourceMetadataKey.attributeId);
        assertEquals(attributeId.getValue(), 3);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesConstructorBadAtrributeId() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x23,
                    (byte) 0x09,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        new RefinedSource(bytes, 0, 9);
    }
}
