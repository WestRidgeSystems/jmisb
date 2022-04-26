package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Check Local Set with just mandatory items. */
public class CompositeImagingLocalSetMinimalTest {

    private static final byte[] LOCAL_SET_BYTES =
            new byte[] {
                0x06,
                0x0E,
                0x2B,
                0x34,
                0x02,
                0x0B,
                0x01,
                0x01,
                0x0E,
                0x01,
                0x03,
                0x03,
                0x02,
                0x00,
                0x00,
                0x00,
                0x16,
                0x02,
                0x01,
                0x01,
                0x09,
                0x02,
                0x05,
                0x78,
                0x0a,
                0x02,
                0x07,
                (byte) 0x80,
                0x0b,
                0x02,
                0x03,
                (byte) 0xc0,
                0x0c,
                0x02,
                0x02,
                (byte) 0xbc,
                0x12,
                0x01,
                0x04
            };

    private static final byte[] LOCAL_SET_BYTES_NESTED =
            new byte[] {
                0x02,
                0x01,
                0x01,
                0x09,
                0x02,
                0x05,
                0x78,
                0x0a,
                0x02,
                0x07,
                (byte) 0x80,
                0x0b,
                0x02,
                0x03,
                (byte) 0xc0,
                0x0c,
                0x02,
                0x02,
                (byte) 0xbc,
                0x12,
                0x01,
                0x04
            };

    @Test
    public void checkFromBytes() throws KlvParseException {
        CompositeImagingLocalSet uut = new CompositeImagingLocalSet(LOCAL_SET_BYTES);
        checkLocalSetValues(uut);
    }

    @Test
    public void checkFromBytesNested() throws KlvParseException {
        CompositeImagingLocalSet uut =
                CompositeImagingLocalSet.fromNestedBytes(
                        LOCAL_SET_BYTES_NESTED, 0, LOCAL_SET_BYTES_NESTED.length);
        checkLocalSetValues(uut);
    }

    private void checkLocalSetValues(CompositeImagingLocalSet uut) {
        assertEquals(uut.displayHeader(), "ST 1602 Composite Imaging");
        assertEquals(uut.getDisplayName(), "Composite Imaging Local Set");
        assertEquals(uut.getDisplayableValue(), "Composite Imaging");
        assertEquals(
                uut.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x02,
                    0x00, 0x00, 0x00
                });
        assertEquals(uut.getIdentifiers().size(), 6);
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.DocumentVersion));
        ICompositeImagingValue v = uut.getField(CompositeImagingKey.DocumentVersion);
        assertTrue(v instanceof ST1602DocumentVersion);
        assertEquals(v.getDisplayableValue(), "ST 1602.1");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SubImageRows));
        v = uut.getField(CompositeImagingKey.SubImageRows);
        assertTrue(v instanceof SubImageRows);
        assertEquals(v.getDisplayableValue(), "1400px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SubImageColumns));
        v = uut.getField(CompositeImagingKey.SubImageColumns);
        assertTrue(v instanceof SubImageColumns);
        assertEquals(v.getDisplayableValue(), "1920px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SubImagePositionX));
        v = uut.getField(CompositeImagingKey.SubImagePositionX);
        assertTrue(v instanceof SubImagePositionX);
        assertEquals(v.getDisplayableValue(), "960px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SubImagePositionY));
        v = uut.getField(CompositeImagingKey.SubImagePositionY);
        assertTrue(v instanceof SubImagePositionY);
        assertEquals(v.getDisplayableValue(), "700px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ZOrder));
        v = uut.getField(CompositeImagingKey.ZOrder);
        assertTrue(v instanceof ZOrder);
        assertEquals(v.getDisplayableValue(), "4");
        assertEquals(uut.frameMessage(false), LOCAL_SET_BYTES);
        assertEquals(uut.frameMessage(true), LOCAL_SET_BYTES_NESTED);
    }
}
