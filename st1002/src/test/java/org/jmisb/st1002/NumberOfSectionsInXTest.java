package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1002 Number of Sections in X (ST 1002 Tag 17). */
public class NumberOfSectionsInXTest {
    @Test
    public void testConstructFromValue() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(1);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "1");
        assertEquals(uut.getNumberOfSections(), 1);
    }

    @Test
    public void testConstructFromValue127() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(127);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7f});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "127");
        assertEquals(uut.getNumberOfSections(), 127);
    }

    @Test
    public void testConstructFromValue129() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(129);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "129");
        assertEquals(uut.getNumberOfSections(), 129);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(new byte[] {(byte) 0x02});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getNumberOfSections(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes127() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(new byte[] {(byte) 0x7F});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7F});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "127");
        assertEquals(uut.getNumberOfSections(), 127);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        NumberOfSectionsInX uut = new NumberOfSectionsInX(new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "255");
        assertEquals(uut.getNumberOfSections(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.NumberOfSectionsInX, new byte[] {(byte) 0x02});
        assertTrue(value instanceof NumberOfSectionsInX);
        NumberOfSectionsInX uut = (NumberOfSectionsInX) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(uut.getDisplayName(), "Number of Sections in X");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getNumberOfSections(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new NumberOfSectionsInX(0);
    }
}
