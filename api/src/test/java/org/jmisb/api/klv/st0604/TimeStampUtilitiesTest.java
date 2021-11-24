package org.jmisb.api.klv.st0604;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;
import org.testng.annotations.Test;

/** Unit tests for ST0604 Time Stamp Utilities. */
public class TimeStampUtilitiesTest {

    @Test
    public void checkStartCodeEmulationPreventionEncoding() {
        ST0603TimeStamp timeStamp = new ST0603TimeStamp(0x0102030405060708L);
        byte[] result = TimeStampUtilities.getModifiedPrecisionTimeStamp(timeStamp);
        assertEquals(
                result,
                new byte[] {
                    0x01,
                    0x02,
                    (byte) 0xFF,
                    0x03,
                    0x04,
                    (byte) 0xFF,
                    0x05,
                    0x06,
                    (byte) 0xFF,
                    0x07,
                    0x08
                });
    }

    @Test
    public void checkStartCodeEmulationPreventionDecoding() {
        ST0603TimeStamp expectedTimeStamp = new ST0603TimeStamp(0x0102030405060708L);
        byte[] input =
                new byte[] {
                    0x4D,
                    0x49,
                    0x53,
                    0x50,
                    0x6D,
                    0x69,
                    0x63,
                    0x72,
                    0x6F,
                    0x73,
                    0x65,
                    0x63,
                    0x74,
                    0x69,
                    0x6D,
                    0x65,
                    0b00011111,
                    0x01,
                    0x02,
                    (byte) 0xFF,
                    0x03,
                    0x04,
                    (byte) 0xFF,
                    0x05,
                    0x06,
                    (byte) 0xFF,
                    0x07,
                    0x08
                };
        ST0603TimeStamp timeStamp = TimeStampUtilities.decodePrecisionTimeStamp(input);
        assertEquals(timeStamp.getMicroseconds(), expectedTimeStamp.getMicroseconds());
        TimeStatus timeStatus = TimeStampUtilities.decodeTimeStatus(input);
        assertEquals(timeStatus.getEncodedValue(), 0x1F);
        byte[] encoded = TimeStampUtilities.encodeH264(timeStatus, timeStamp);
        assertEquals(encoded, input);
    }

    @Test
    public void checkDecodeBadLength() {
        byte[] input =
                new byte[] {
                    0x4D, 0x49, 0x53, 0x50, 0x6D, 0x69, 0x63, 0x72, 0x6F, 0x73, 0x65, 0x63, 0x74,
                    0x69, 0x6D, 0x65, 0x1F
                };
        ST0603TimeStamp timeStamp = TimeStampUtilities.decodePrecisionTimeStamp(input);
        assertNull(timeStamp);
        TimeStatus timeStatus = TimeStampUtilities.decodeTimeStatus(input);
        assertNull(timeStatus);
    }

    @Test
    public void checkDecodeWrongIdentifier() {
        byte[] input =
                new byte[] {
                    0x3D,
                    0x49,
                    0x53,
                    0x50,
                    0x6D,
                    0x69,
                    0x63,
                    0x72,
                    0x6F,
                    0x73,
                    0x65,
                    0x63,
                    0x74,
                    0x69,
                    0x6D,
                    0x65,
                    0b00011111,
                    0x01,
                    0x02,
                    (byte) 0xFF,
                    0x03,
                    0x04,
                    (byte) 0xFF,
                    0x05,
                    0x06,
                    (byte) 0xFF,
                    0x07,
                    0x08
                };
        ST0603TimeStamp timeStamp = TimeStampUtilities.decodePrecisionTimeStamp(input);
        assertNull(timeStamp);
        TimeStatus timeStatus = TimeStampUtilities.decodeTimeStatus(input);
        assertNull(timeStatus);
    }

    @Test
    public void checkStartCodeEmulationPreventionDecodingH265() {
        ST0603TimeStamp expectedTimeStamp = new ST0603TimeStamp(0x0102030405060708L);
        byte[] input =
                new byte[] {
                    (byte) 0xa8,
                    (byte) 0x68,
                    (byte) 0x7d,
                    (byte) 0xd4,
                    (byte) 0xd7,
                    (byte) 0x59,
                    (byte) 0x37,
                    (byte) 0x58,
                    (byte) 0xa5,
                    (byte) 0xce,
                    (byte) 0xf0,
                    (byte) 0x33,
                    (byte) 0x8b,
                    (byte) 0x65,
                    (byte) 0x45,
                    (byte) 0xf1,
                    0b00011111,
                    0x01,
                    0x02,
                    (byte) 0xFF,
                    0x03,
                    0x04,
                    (byte) 0xFF,
                    0x05,
                    0x06,
                    (byte) 0xFF,
                    0x07,
                    0x08
                };
        ST0603TimeStamp timeStamp = TimeStampUtilities.decodePrecisionTimeStamp(input);
        assertEquals(timeStamp.getMicroseconds(), expectedTimeStamp.getMicroseconds());
        TimeStatus timeStatus = TimeStampUtilities.decodeTimeStatus(input);
        assertEquals(timeStatus.getEncodedValue(), 0x1F);
        byte[] encoded = TimeStampUtilities.encodeH265(timeStatus, timeStamp);
        assertEquals(encoded, input);
    }
}
