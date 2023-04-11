package org.jmisb.st1002;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for Section Data. */
public class SectionDataTest {

    public SectionDataTest() {}

    @Test
    public void checkConstructFromBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x3F,
                    (byte) 0xB9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A,
                    0x3F,
                    (byte) 0xC9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertEquals(uut.getArrayOfUncertaintyValues(), new double[][] {{0.1}, {0.2}});
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Section Data");
        assertEquals(uut.getDisplayableValue(), "Section [5,1]");
    }

    @Test
    public void checkConstructFromBytesNoUncertainties() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48,
                    0x08,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28,
                    (byte) 0xF6
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 12.0);
        assertEquals(uut.getPlaneYScaleFactor(), 83.12);
        assertEquals(uut.getPlaneConstantValue(), 12.98);
        assertEquals(uut.getBytes(), bytes);
    }

    @Test
    public void checkConstructFromBytesNoScaleFactor() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 12.0);
        assertEquals(uut.getPlaneYScaleFactor(), 83.12);
        assertEquals(uut.getPlaneConstantValue(), 0.0);
        assertEquals(uut.getDisplayName(), "Section Data");
        assertEquals(uut.getDisplayableValue(), "Section [5,1]");
    }

    @Test
    public void checkConstructFromBytesNoYScale() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 12.0);
        assertEquals(uut.getPlaneYScaleFactor(), 0.0);
        assertEquals(uut.getPlaneConstantValue(), 0.0);
    }

    @Test
    public void checkConstructFromBytesNoXScale() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 0.0);
        assertEquals(uut.getPlaneYScaleFactor(), 0.0);
        assertEquals(uut.getPlaneConstantValue(), 0.0);
        assertEquals(uut.getBytes(), bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructFromBytesBadXScaleLength() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x07,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48,
                    0x08,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28,
                    (byte) 0xF6
                };
        new SectionData(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructFromBytesBadYScaleLength() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x07,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    0x48,
                    0x08,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28,
                    (byte) 0xF6
                };
        new SectionData(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructFromBytesBadConstantLength() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48,
                    0x07,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28
                };
        new SectionData(bytes);
    }

    @Test
    public void checkConstructFromBytesFloat() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x04,
                    0x41,
                    0x40,
                    0x00,
                    0x00,
                    0x04,
                    0x42,
                    (byte) 0xA6,
                    0x3D,
                    0x71,
                    0x04,
                    0x41,
                    0x4F,
                    (byte) 0xAE,
                    0x14
                };
        SectionData uut = new SectionData(bytes);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 12.0, 0.00001);
        assertEquals(uut.getPlaneYScaleFactor(), 83.12, 0.00001);
        assertEquals(uut.getPlaneConstantValue(), 12.98, 0.00001);
    }

    @Test
    public void checkConstructFromValues() throws KlvParseException {
        SectionData uut =
                new SectionData(
                        5, 1, new double[][] {{2.4}, {-8.67}}, new double[][] {{0.1}, {0.2}});
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertEquals(uut.getArrayOfUncertaintyValues(), new double[][] {{0.1}, {0.2}});
        byte[] expectedBytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x3F,
                    (byte) 0xB9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A,
                    0x3F,
                    (byte) 0xC9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A
                };

        assertEquals(uut.getBytes(), expectedBytes);
    }

    @Test
    public void checkConstructFromValuesNullUncertainties() throws KlvParseException {
        SectionData uut = new SectionData(5, 1, new double[][] {{2.4}, {-8.67}}, null);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        byte[] expectedBytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00
                };

        assertEquals(uut.getBytes(), expectedBytes);
        SectionData copy = new SectionData(uut);
        assertEquals(copy.getSectionNumberX(), 5);
        assertEquals(copy.getSectionNumberY(), 1);
        assertEquals(copy.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(copy.getArrayOfUncertaintyValues());
        assertEquals(copy.getPlaneXScaleFactor(), 0.0);
        assertEquals(copy.getPlaneYScaleFactor(), 0.0);
        assertEquals(copy.getPlaneConstantValue(), 0.0);
        assertEquals(copy.getDisplayName(), "Section Data");
        assertEquals(copy.getDisplayableValue(), "Section [5,1]");
    }

    @Test
    public void checkConstructFromValuesWithPlanar() throws KlvParseException {
        SectionData uut =
                new SectionData(5, 1, new double[][] {{2.4}, {-8.67}}, null, 12.0, 83.12, 12.98);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertNull(uut.getArrayOfUncertaintyValues());
        assertEquals(uut.getPlaneXScaleFactor(), 12.0);
        assertEquals(uut.getPlaneYScaleFactor(), 83.12);
        assertEquals(uut.getPlaneConstantValue(), 12.98);
        byte[] expectedBytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x00,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48,
                    0x08,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28,
                    (byte) 0xF6
                };
        assertEquals(uut.getBytes(), expectedBytes);
    }

    @Test
    public void checkConstructFromValuesWithUncertaintiesAndPlanar() throws KlvParseException {
        SectionData uut =
                new SectionData(
                        5,
                        1,
                        new double[][] {{2.4}, {-8.67}},
                        new double[][] {{0.1}, {0.2}},
                        12.0,
                        83.12,
                        12.98);
        assertEquals(uut.getSectionNumberX(), 5);
        assertEquals(uut.getSectionNumberY(), 1);
        assertEquals(uut.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertEquals(uut.getArrayOfUncertaintyValues(), new double[][] {{0.1}, {0.2}});
        assertEquals(uut.getPlaneXScaleFactor(), 12.0);
        assertEquals(uut.getPlaneYScaleFactor(), 83.12);
        assertEquals(uut.getPlaneConstantValue(), 12.98);
        byte[] expectedBytes =
                new byte[] {
                    0x01,
                    0x05,
                    0x01,
                    0x01,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x40,
                    0x03,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    (byte) 0xc0,
                    0x21,
                    0x57,
                    0x0a,
                    0x3d,
                    0x70,
                    (byte) 0xa3,
                    (byte) 0xd7,
                    0x15,
                    0x02,
                    0x02,
                    0x01,
                    0x08,
                    0x01,
                    0x3F,
                    (byte) 0xB9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A,
                    0x3F,
                    (byte) 0xC9,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A,
                    0x08,
                    0x40,
                    0x28,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x40,
                    0x54,
                    (byte) 0xC7,
                    (byte) 0xAE,
                    0x14,
                    0x7A,
                    (byte) 0xE1,
                    0x48,
                    0x08,
                    0x40,
                    0x29,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    0x5C,
                    0x28,
                    (byte) 0xF6
                };
        assertEquals(uut.getBytes(), expectedBytes);
        assertEquals(uut.getDisplayName(), "Section Data");
        assertEquals(uut.getDisplayableValue(), "Section [5,1]");
        SectionData copy = new SectionData(uut);
        assertEquals(copy.getSectionNumberX(), 5);
        assertEquals(copy.getSectionNumberY(), 1);
        assertEquals(copy.getArrayOfMeasuredValues(), new double[][] {{2.4}, {-8.67}});
        assertEquals(copy.getArrayOfUncertaintyValues(), new double[][] {{0.1}, {0.2}});
        assertEquals(copy.getPlaneXScaleFactor(), 12.0);
        assertEquals(copy.getPlaneYScaleFactor(), 83.12);
        assertEquals(copy.getPlaneConstantValue(), 12.98);
        assertEquals(copy.getDisplayName(), "Section Data");
        assertEquals(copy.getDisplayableValue(), "Section [5,1]");
    }
}
