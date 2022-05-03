package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Check Local Set with all items. */
public class CompositeImagingLocalSetFullTest {

    static final byte[] LOCAL_SET_BYTES =
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
                0x47,
                0x01,
                0x08,
                (byte) 0x00,
                (byte) 0x04,
                (byte) 0x59,
                (byte) 0xf4,
                (byte) 0xA6,
                (byte) 0xaa,
                (byte) 0x4a,
                (byte) 0xa8,
                0x02,
                0x01,
                0x01,
                0x03,
                0x02,
                0x05,
                0x20,
                0x04,
                0x02,
                0x03,
                0x23,
                0x05,
                0x02,
                0x05,
                0x12,
                0x06,
                0x02,
                0x03,
                0x1F,
                0x07,
                0x01,
                0x02,
                0x08,
                0x01,
                0x05,
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
                0x0d,
                0x02,
                0x05,
                0x70,
                0x0e,
                0x02,
                0x07,
                0x7a,
                0x0F,
                0x01,
                0x04,
                0x10,
                0x01,
                0x03,
                0x11,
                0x01,
                0x4C,
                0x12,
                0x01,
                0x04
            };

    @Test
    public void checkFromBytes() throws KlvParseException {
        CompositeImagingLocalSet uut = new CompositeImagingLocalSet(LOCAL_SET_BYTES);
        checkLocalSetValues(uut);
    }

    static void checkLocalSetValues(CompositeImagingLocalSet uut) {
        assertEquals(uut.displayHeader(), "ST 1602 Composite Imaging");
        assertEquals(uut.getDisplayName(), "Composite Imaging Local Set");
        assertEquals(uut.getDisplayableValue(), "Composite Imaging");
        assertEquals(
                uut.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x02,
                    0x00, 0x00, 0x00
                });
        assertEquals(uut.getIdentifiers().size(), 18);
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.PrecisionTimeStamp));
        ICompositeImagingValue v = uut.getField(CompositeImagingKey.PrecisionTimeStamp);
        assertTrue(v instanceof PrecisionTimeStamp);
        assertEquals(v.getDisplayableValue(), "1224807209913000");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.DocumentVersion));
        v = uut.getField(CompositeImagingKey.DocumentVersion);
        assertTrue(v instanceof ST1602DocumentVersion);
        assertEquals(v.getDisplayableValue(), "ST 1602.1");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageRows));
        v = uut.getField(CompositeImagingKey.SourceImageRows);
        assertTrue(v instanceof SourceImageRows);
        assertEquals(v.getDisplayableValue(), "1312px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageColumns));
        v = uut.getField(CompositeImagingKey.SourceImageColumns);
        assertTrue(v instanceof SourceImageColumns);
        assertEquals(v.getDisplayableValue(), "803px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageAOIRows));
        v = uut.getField(CompositeImagingKey.SourceImageAOIRows);
        assertTrue(v instanceof SourceImageAOIRows);
        assertEquals(v.getDisplayableValue(), "1298px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageAOIColumns));
        v = uut.getField(CompositeImagingKey.SourceImageAOIColumns);
        assertTrue(v instanceof SourceImageAOIColumns);
        assertEquals(v.getDisplayableValue(), "799px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageAOIPositionX));
        v = uut.getField(CompositeImagingKey.SourceImageAOIPositionX);
        assertTrue(v instanceof SourceImageAOIPositionX);
        assertEquals(v.getDisplayableValue(), "2px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.SourceImageAOIPositionY));
        v = uut.getField(CompositeImagingKey.SourceImageAOIPositionY);
        assertTrue(v instanceof SourceImageAOIPositionY);
        assertEquals(v.getDisplayableValue(), "5px");
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
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ActiveSubImageRows));
        v = uut.getField(CompositeImagingKey.ActiveSubImageRows);
        assertTrue(v instanceof ActiveSubImageRows);
        assertEquals(v.getDisplayableValue(), "1392px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ActiveSubImageColumns));
        v = uut.getField(CompositeImagingKey.ActiveSubImageColumns);
        assertTrue(v instanceof ActiveSubImageColumns);
        assertEquals(v.getDisplayableValue(), "1914px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ActiveSubImageOffsetX));
        v = uut.getField(CompositeImagingKey.ActiveSubImageOffsetX);
        assertTrue(v instanceof ActiveSubImageOffsetX);
        assertEquals(v.getDisplayableValue(), "4px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ActiveSubImageOffsetY));
        v = uut.getField(CompositeImagingKey.ActiveSubImageOffsetY);
        assertTrue(v instanceof ActiveSubImageOffsetY);
        assertEquals(v.getDisplayableValue(), "3px");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.Transparency));
        v = uut.getField(CompositeImagingKey.Transparency);
        assertTrue(v instanceof Transparency);
        assertEquals(v.getDisplayableValue(), "76");
        assertTrue(uut.getIdentifiers().contains(CompositeImagingKey.ZOrder));
        v = uut.getField(CompositeImagingKey.ZOrder);
        assertTrue(v instanceof ZOrder);
        assertEquals(v.getDisplayableValue(), "4");
        byte[] framedBytes = uut.frameMessage(false);
        assertEquals(framedBytes, LOCAL_SET_BYTES);
    }
}
