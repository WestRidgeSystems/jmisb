package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1002 Range Image Enumerations (ST 1002 Tag 12). */
public class RangeImageEnumerationsTest {
    @Test
    public void testConstructFromValue() {
        RangeImageEnumerations uut =
                new RangeImageEnumerations(
                        RangeImageCompressionMethod.PLANAR_FIT,
                        RangeImageryDataType.DEPTH,
                        RangeImageSource.RANGE_SENSOR);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01001001});
        assertEquals(uut.getDisplayName(), "Range Image Enumerations");
        assertEquals(uut.getDisplayableValue(), "Range Sensor | Depth Range Image | Planar Fit");
        assertEquals(uut.getCompressionMethod(), RangeImageCompressionMethod.PLANAR_FIT);
        assertEquals(uut.getDataType(), RangeImageryDataType.DEPTH);
        assertEquals(uut.getRangeImageSource(), RangeImageSource.RANGE_SENSOR);
    }

    @Test
    public void testConstructFromBytes() {
        RangeImageEnumerations uut = new RangeImageEnumerations(new byte[] {(byte) 0b01001001});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01001001});
        assertEquals(uut.getDisplayName(), "Range Image Enumerations");
        assertEquals(uut.getDisplayableValue(), "Range Sensor | Depth Range Image | Planar Fit");
        assertEquals(uut.getCompressionMethod(), RangeImageCompressionMethod.PLANAR_FIT);
        assertEquals(uut.getDataType(), RangeImageryDataType.DEPTH);
        assertEquals(uut.getRangeImageSource(), RangeImageSource.RANGE_SENSOR);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.RangeImageEnumerations,
                        new byte[] {(byte) 0b01001001});
        assertTrue(value instanceof RangeImageEnumerations);
        RangeImageEnumerations uut = (RangeImageEnumerations) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01001001});
        assertEquals(uut.getDisplayName(), "Range Image Enumerations");
        assertEquals(uut.getDisplayableValue(), "Range Sensor | Depth Range Image | Planar Fit");
        assertEquals(uut.getCompressionMethod(), RangeImageCompressionMethod.PLANAR_FIT);
        assertEquals(uut.getDataType(), RangeImageryDataType.DEPTH);
        assertEquals(uut.getRangeImageSource(), RangeImageSource.RANGE_SENSOR);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Range Image Source cannot be UNKNOWN")
    public void testSerialisationBadSource() {
        RangeImageEnumerations uut =
                new RangeImageEnumerations(
                        RangeImageCompressionMethod.NO_COMPRESSION,
                        RangeImageryDataType.DEPTH,
                        RangeImageSource.UNKNOWN);
        uut.getBytes();
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Range Imagery Data Type cannot be UNKNOWN")
    public void testSerialisationBadDataType() {
        RangeImageEnumerations uut =
                new RangeImageEnumerations(
                        RangeImageCompressionMethod.NO_COMPRESSION,
                        RangeImageryDataType.UNKNOWN,
                        RangeImageSource.COMPUTATIONALLY_EXTRACTED);
        uut.getBytes();
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Range Image Compression Method cannot be UNKNOWN")
    public void testSerialisationBadCompressionMethod() {
        RangeImageEnumerations uut =
                new RangeImageEnumerations(
                        RangeImageCompressionMethod.UNKNOWN,
                        RangeImageryDataType.PERSPECTIVE,
                        RangeImageSource.COMPUTATIONALLY_EXTRACTED);
        uut.getBytes();
    }
}
