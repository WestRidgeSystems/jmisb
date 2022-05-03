package org.jmisb.st1602;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class CompositeImagingLocalSetUnknownTest extends LoggerChecks {

    public CompositeImagingLocalSetUnknownTest() {
        super(CompositeImagingLocalSet.class);
    }

    private static final byte[] BYTES =
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
                0x68,
                0x01,
                0x07,
                0x12,
                0x01,
                0x4
            };

    @Test
    public void checkFromBytes() throws KlvParseException {
        this.verifyNoLoggerMessages();
        CompositeImagingLocalSet uut =
                CompositeImagingLocalSet.fromNestedBytes(BYTES, 0, BYTES.length);
        this.verifySingleLoggerMessage("Unknown Composite Imaging tag: Undefined");
        assertEquals(uut.displayHeader(), "ST 1602 Composite Imaging");
        assertEquals(uut.getDisplayName(), "Composite Imaging Local Set");
        assertEquals(uut.getDisplayableValue(), "Composite Imaging");
        assertEquals(uut.getIdentifiers().size(), 6);
        assertTrue(
                uut.getIdentifiers()
                        .containsAll(
                                Set.<CompositeImagingKey>of(
                                        CompositeImagingKey.DocumentVersion,
                                        CompositeImagingKey.SubImageColumns,
                                        CompositeImagingKey.SubImageRows,
                                        CompositeImagingKey.SubImagePositionX,
                                        CompositeImagingKey.SubImagePositionY,
                                        CompositeImagingKey.ZOrder)));
        ICompositeImagingValue docVersion = uut.getField(CompositeImagingKey.DocumentVersion);
        assertTrue(docVersion instanceof ST1602DocumentVersion);
        assertEquals(docVersion.getDisplayName(), "Document Version");
        assertEquals(docVersion.getDisplayableValue(), "ST 1602.1");
        ICompositeImagingValue zOrder = uut.getField(CompositeImagingKey.ZOrder);
        assertTrue(zOrder instanceof ZOrder);
        assertEquals(zOrder.getDisplayName(), "Z-Order");
        assertEquals(zOrder.getDisplayableValue(), "4");
    }

    @Test
    public void checkFrameUnknown() {
        Map<CompositeImagingKey, ICompositeImagingValue> values = new HashMap<>();
        values.put(CompositeImagingKey.DocumentVersion, new ST1602DocumentVersion(2));
        values.put(
                CompositeImagingKey.Undefined,
                new ICompositeImagingValue() {
                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x00};
                    }

                    @Override
                    public String getDisplayName() {
                        return "Unknown item";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "Unknown";
                    }
                });
        CompositeImagingLocalSet localSet = new CompositeImagingLocalSet(values);
        this.verifyNoLoggerMessages();
        byte[] bytes = localSet.frameMessage(true);
        this.verifySingleLoggerMessage("Skipping undefined Composite Imaging tag: 0");
        assertEquals(bytes, new byte[] {0x02, 0x01, 0x02});
        bytes = localSet.frameMessage(false);
        this.verifySingleLoggerMessage("Skipping undefined Composite Imaging tag: 0");
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x02, 0x01, 0x02
                });
    }
}
