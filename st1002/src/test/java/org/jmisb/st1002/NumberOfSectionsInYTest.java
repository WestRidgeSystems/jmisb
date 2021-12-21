package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1002 Number of Sections in Y (ST 1002 Tag 18). */
public class NumberOfSectionsInYTest {
    @Test
    public void testConstructFromValue() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(1);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "1");
        assertEquals(uut.getNumberOfSections(), 1);
    }

    @Test
    public void testConstructFromValue127() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(127);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7f});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "127");
        assertEquals(uut.getNumberOfSections(), 127);
    }

    @Test
    public void testConstructFromValue129() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(129);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "129");
        assertEquals(uut.getNumberOfSections(), 129);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(new byte[] {(byte) 0x02});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getNumberOfSections(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes127() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(new byte[] {(byte) 0x7F});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7F});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "127");
        assertEquals(uut.getNumberOfSections(), 127);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        NumberOfSectionsInY uut = new NumberOfSectionsInY(new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "255");
        assertEquals(uut.getNumberOfSections(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.NumberOfSectionsInY, new byte[] {(byte) 0x02});
        assertTrue(value instanceof NumberOfSectionsInY);
        NumberOfSectionsInY uut = (NumberOfSectionsInY) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(uut.getDisplayName(), "Number of Sections in Y");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getNumberOfSections(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new NumberOfSectionsInY(0);
    }
}
