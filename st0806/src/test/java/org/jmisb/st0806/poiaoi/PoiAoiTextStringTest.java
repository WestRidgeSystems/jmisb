package org.jmisb.st0806.poiaoi;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class PoiAoiTextStringTest {
    @Test
    public void testConstructFromValue() {
        RvtPoiAoiTextString textString =
                new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_TEXT, "QFJ");
        assertEquals(textString.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
        assertEquals(textString.getDisplayableValue(), "QFJ");
    }

    @Test
    public void testConstructFromEncoded() {
        RvtPoiAoiTextString textString =
                new RvtPoiAoiTextString(
                        RvtPoiAoiTextString.POI_AOI_TEXT,
                        new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(textString.getDisplayableValue(), "QFJ");
        assertEquals(textString.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A};
        IRvtPoiAoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiText, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(textString.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(textString.getDisplayableValue(), "QFJ");
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
    }

    @Test
    public void testFactoryTextAoi() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x52, (byte) 0x4F, (byte) 0x5A, (byte) 0x20, (byte) 0x46, (byte) 0x55
                };
        IRvtPoiAoiMetadataValue v = RvtAoiLocalSet.createValue(RvtAoiMetadataKey.PoiAoiText, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {
                    (byte) 0x52, (byte) 0x4F, (byte) 0x5A, (byte) 0x20, (byte) 0x46, (byte) 0x55
                });
        assertEquals(textString.getDisplayableValue(), "ROZ FU");
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
    }

    @Test
    public void testFactorySourceIcon() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x54,
                    (byte) 0x32,
                    (byte) 0x35,
                    (byte) 0x32,
                    (byte) 0x35,
                    (byte) 0x42,
                    (byte) 0x59
                };
        IRvtPoiAoiMetadataValue v =
                RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiSourceIcon, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {
                    (byte) 0x54,
                    (byte) 0x32,
                    (byte) 0x35,
                    (byte) 0x32,
                    (byte) 0x35,
                    (byte) 0x42,
                    (byte) 0x59
                });
        assertEquals(textString.getDisplayableValue(), "T2525BY");
        assertEquals(textString.getDisplayName(), "POI Source Icon");
        assertEquals(textString.getValue(), "T2525BY");
    }

    @Test
    public void testFactorySourceID() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x31,
                    (byte) 0x32,
                    (byte) 0x33,
                    (byte) 0x34,
                    (byte) 0x20,
                    (byte) 0x41,
                    (byte) 0x42
                };
        IRvtPoiAoiMetadataValue v =
                RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiSourceId, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {
                    (byte) 0x31,
                    (byte) 0x32,
                    (byte) 0x33,
                    (byte) 0x34,
                    (byte) 0x20,
                    (byte) 0x41,
                    (byte) 0x42
                });
        assertEquals(textString.getDisplayableValue(), "1234 AB");
        assertEquals(textString.getDisplayName(), "POI/AOI Source ID");
    }

    @Test
    public void testFactorySourceIDAoi() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x41, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34};
        IRvtPoiAoiMetadataValue v =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.PoiAoiSourceId, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {(byte) 0x41, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34});
        assertEquals(textString.getDisplayableValue(), "A1234");
        assertEquals(textString.getDisplayName(), "POI/AOI Source ID");
    }

    @Test
    public void testFactoryPoiAoiLabel() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x41, (byte) 0x33};
        IRvtPoiAoiMetadataValue v =
                RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiLabel, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(textString.getBytes(), new byte[] {(byte) 0x41, (byte) 0x33});
        assertEquals(textString.getDisplayableValue(), "A3");
        assertEquals(textString.getDisplayName(), "POI/AOI Label");
    }

    @Test
    public void testFactoryPoiAoiLabelAoi() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x42, (byte) 0x38};
        IRvtPoiAoiMetadataValue v =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.PoiAoiLabel, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(textString.getBytes(), new byte[] {(byte) 0x42, (byte) 0x38});
        assertEquals(textString.getDisplayableValue(), "B8");
        assertEquals(textString.getDisplayName(), "POI/AOI Label");
    }

    @Test
    public void testFactoryOperationID() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x4F, (byte) 0x50, (byte) 0x20, (byte) 0x53, (byte) 0x6F, (byte) 0x6C
                };
        IRvtPoiAoiMetadataValue v =
                RvtPoiLocalSet.createValue(RvtPoiMetadataKey.OperationId, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {
                    (byte) 0x4F, (byte) 0x50, (byte) 0x20, (byte) 0x53, (byte) 0x6F, (byte) 0x6C
                });
        assertEquals(textString.getDisplayableValue(), "OP Sol");
        assertEquals(textString.getDisplayName(), "Operation ID");
    }

    @Test
    public void testFactoryOperationIDAoi() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x4F, (byte) 0x50, (byte) 0x20, (byte) 0x53, (byte) 0x6F, (byte) 0x6C
                };
        IRvtPoiAoiMetadataValue v =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.OperationId, bytes);
        assertTrue(v instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) v;
        assertEquals(
                textString.getBytes(),
                new byte[] {
                    (byte) 0x4F, (byte) 0x50, (byte) 0x20, (byte) 0x53, (byte) 0x6F, (byte) 0x6C
                });
        assertEquals(textString.getDisplayableValue(), "OP Sol");
        assertEquals(textString.getDisplayName(), "Operation ID");
    }
}
