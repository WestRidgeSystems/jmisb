package org.jmisb.st1002;

import static org.testng.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for the ST 1002 Range Image Local Set. */
public class RangeImageLocalSetTest extends LoggerChecks {

    public RangeImageLocalSetTest() {
        super(RangeImageLocalSet.class);
    }

    @Test
    public void parse() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x32,
                    (byte) 0x3E
                };
        RangeImageLocalSetFactory factory = new RangeImageLocalSetFactory();
        assertEquals(
                factory.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x0c,
                    0x00, 0x00, 0x00
                });
        RangeImageLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1002 Range Image");
        assertEquals(localSet.getUniversalLabel(), RangeImageLocalSet.RangeImageLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
    }

    @Test
    public void parseWithUnknown() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x7F,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0xc7
                };
        RangeImageLocalSetFactory factory = new RangeImageLocalSetFactory();
        assertEquals(
                factory.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x0c,
                    0x00, 0x00, 0x00
                });
        verifyNoLoggerMessages();
        RangeImageLocalSet localSet = factory.create(bytes);
        verifySingleLoggerMessage("Unknown Range Image Metadata tag: 127");
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1002 Range Image");
        assertEquals(localSet.getUniversalLabel(), RangeImageLocalSet.RangeImageLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
    }

    @Test
    public void fromNestedBytes() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x32,
                    (byte) 0x3E
                };
        RangeImageLocalSet localSet =
                RangeImageLocalSet.fromNestedBytes(
                        new byte[] {0x0B, 0x01, 0x2, 0x15, 0x02, (byte) 0xd9, 0x21}, 0, 7);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1002 Range Image");
        assertEquals(localSet.getUniversalLabel(), RangeImageLocalSet.RangeImageLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(
                localSet.frameMessage(true),
                new byte[] {0x0B, 0x01, 0x2, 0x15, 0x02, (byte) 0xd9, 0x21});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badChecksum() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x31,
                    (byte) 0x3E
                };
        verifyNoLoggerMessages();
        RangeImageLocalSetFactory factory = new RangeImageLocalSetFactory();
        factory.create(bytes);
        verifySingleLoggerMessage("Bad checksum");
    }

    @Test
    public void buildFromValues() throws KlvParseException {

        Map<RangeImageMetadataKey, IRangeImageMetadataValue> values = new HashMap<>();
        values.put(RangeImageMetadataKey.DocumentVersion, new ST1002VersionNumber(2));
        RangeImageLocalSet localSet = new RangeImageLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1002 Range Image");
        assertEquals(localSet.getUniversalLabel(), RangeImageLocalSet.RangeImageLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        final byte[] expectedBytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x32,
                    (byte) 0x3E
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertEquals(
                localSet.frameMessage(true),
                new byte[] {0x0B, 0x01, 0x2, 0x15, 0x02, (byte) 0xd9, 0x21});
    }

    @Test
    public void buildFromValuesWithCRCAndUnknown() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x0C,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x0B,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x15,
                    (byte) 0x02,
                    (byte) 0x32,
                    (byte) 0x3E
                };
        Map<RangeImageMetadataKey, IRangeImageMetadataValue> values = new HashMap<>();
        values.put(
                RangeImageMetadataKey.Undefined,
                new IRangeImageMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        throw new UnsupportedOperationException("Should not be called");
                    }

                    @Override
                    public String getDisplayName() {
                        return "bogus name";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "bogus value";
                    }
                });
        values.put(
                RangeImageMetadataKey.CRC16CCITT,
                new IRangeImageMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        throw new UnsupportedOperationException("Should not be called");
                    }

                    @Override
                    public String getDisplayName() {
                        throw new UnsupportedOperationException("Should not be called");
                    }

                    @Override
                    public String getDisplayableValue() {
                        throw new UnsupportedOperationException("Should not be called");
                    }
                });
        values.put(RangeImageMetadataKey.DocumentVersion, new ST1002VersionNumber(2));

        RangeImageLocalSet localSet = new RangeImageLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(
                localSet.frameMessage(true),
                new byte[] {0x0B, 0x01, 0x2, 0x15, 0x02, (byte) 0xd9, 0x21});
    }

    @Test
    public void createUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(RangeImageMetadataKey.Undefined, new byte[] {0x01});
        verifySingleLoggerMessage("Unknown Range Image Metadata tag: Undefined");
        assertNull(value);
    }

    @Test
    public void buildFromValuesWithSectionData() throws KlvParseException {
        Map<RangeImageMetadataKey, IRangeImageMetadataValue> values = new HashMap<>();
        values.put(
                RangeImageMetadataKey.PrecisionTimeStamp,
                new ST1002PrecisionTimeStamp(LocalDateTime.of(2023, Month.JANUARY, 25, 19, 50)));
        values.put(RangeImageMetadataKey.DocumentVersion, new ST1002VersionNumber(2));
        values.put(
                RangeImageMetadataKey.Undefined,
                new IRangeImageMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        throw new UnsupportedOperationException("Should not be called.");
                    }

                    @Override
                    public String getDisplayName() {
                        throw new UnsupportedOperationException("Should not be called.");
                    }

                    @Override
                    public String getDisplayableValue() {
                        throw new UnsupportedOperationException("Should not be called.");
                    }
                });
        SectionDataList sectionDataList = new SectionDataList();
        sectionDataList.add(
                new SectionData(1, 1, new double[][] {{100.0, 200.0}, {150.0, 250.0}}, null));
        sectionDataList.add(
                new SectionData(1, 2, new double[][] {{300.0, 400.0}, {350.0, 450.0}}, null));
        values.put(RangeImageMetadataKey.SectionDataVLP, sectionDataList);
        values.put(RangeImageMetadataKey.NumberOfSectionsInX, new NumberOfSectionsInX(1));
        values.put(RangeImageMetadataKey.NumberOfSectionsInY, new NumberOfSectionsInX(2));
        RangeImageLocalSet localSet = new RangeImageLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1002 Range Image");
        assertEquals(localSet.getUniversalLabel(), RangeImageLocalSet.RangeImageLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 6);
        byte[] bytes = localSet.frameMessage(false);
        RangeImageLocalSet parsedLocalSet = new RangeImageLocalSet(bytes);
        // Undefined entry got dropped out.
        assertEquals(parsedLocalSet.getIdentifiers().size(), 5);
    }
}
