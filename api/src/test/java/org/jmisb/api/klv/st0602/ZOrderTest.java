package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Z-Order implementation. */
public class ZOrderTest {

    public ZOrderTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        ZOrder uut = new ZOrder(8);
        assertEquals(uut.getDisplayName(), "Z-Order");
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getBytes(), new byte[] {0x08});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        ZOrder uut = new ZOrder(new byte[] {0x08});
        assertEquals(uut.getDisplayName(), "Z-Order");
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getBytes(), new byte[] {0x08});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(AnnotationMetadataKey.ZOrder, new byte[] {0x08});
        assertTrue(value instanceof ZOrder);
        ZOrder uut = (ZOrder) value;
        assertEquals(uut.getDisplayName(), "Z-Order");
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getBytes(), new byte[] {0x08});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesInconsistentLength() throws KlvParseException {
        new ZOrder(new byte[] {0x08, 0x01});
    }
}
