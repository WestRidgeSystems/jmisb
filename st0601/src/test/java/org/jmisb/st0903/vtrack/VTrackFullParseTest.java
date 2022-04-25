package org.jmisb.st0903.vtrack;

import static org.testng.Assert.*;

import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.OntologySeries;
import org.jmisb.st0903.PrecisionTimeStamp;
import org.jmisb.st0903.ST0903Version;
import org.jmisb.st0903.ontology.OntologyLS;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.st0903.shared.IVTrackMetadataValue;
import org.jmisb.st0903.shared.LocationPack;
import org.jmisb.st0903.shared.VmtiTextString;
import org.jmisb.st0903.vtarget.TargetIdentifierKey;
import org.jmisb.st0903.vtarget.VChipSeries;
import org.jmisb.st0903.vtarget.VObjectSeries;
import org.jmisb.st0903.vtracker.Acceleration;
import org.jmisb.st0903.vtracker.BoundarySeries;
import org.jmisb.st0903.vtracker.DetectionStatus;
import org.jmisb.st0903.vtracker.EndTime;
import org.jmisb.st0903.vtracker.StartTime;
import org.jmisb.st0903.vtracker.TrackConfidence;
import org.jmisb.st0903.vtracker.TrackId;
import org.testng.annotations.Test;

/** Test of ST0903 VTrack parsing. */
public class VTrackFullParseTest {

    public VTrackFullParseTest() {}

    @Test
    public void checkParse() throws KlvParseException {
        byte[] bytesToParse =
                new byte[] {
                    0x02, // track timestamp
                    0x08,
                    0x00,
                    0x05,
                    (byte) 0xAD,
                    0x6D,
                    0x6B,
                    0x2A,
                    0x5D,
                    (byte) 0xA4,
                    0x03, // track id
                    0x10,
                    (byte) 0xF8,
                    (byte) 0x1D,
                    (byte) 0x4F,
                    (byte) 0xAE,
                    (byte) 0x7D,
                    (byte) 0xEC,
                    (byte) 0x11,
                    (byte) 0xD0,
                    (byte) 0xA7,
                    (byte) 0x65,
                    (byte) 0x00,
                    (byte) 0xA0,
                    (byte) 0xC9,
                    (byte) 0x1E,
                    (byte) 0x6B,
                    (byte) 0xF6,
                    0x04, // detection status.
                    0x01,
                    0x01,
                    0x05, // start time
                    0x08,
                    0x00,
                    0x05,
                    (byte) 0xAD,
                    0x6D,
                    0x40,
                    0x37,
                    (byte) 0xAB,
                    0x33,
                    0x06, // end time
                    0x08,
                    0x00,
                    0x05,
                    (byte) 0xAD,
                    0x6D,
                    0x65,
                    0x49,
                    (byte) 0xA7,
                    0x0F,
                    0x07, // boundary series
                    0x16,
                    0x0a, // Location 1 length
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x90,
                    (byte) 0xab,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1a,
                    (byte) 0xdf,
                    (byte) 0x10,
                    (byte) 0x14,
                    0x0a, // Location 2 length
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x93,
                    (byte) 0x01,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1b,
                    (byte) 0x00,
                    (byte) 0x10,
                    (byte) 0x14,
                    0x08, // algorithm
                    0x0F,
                    0x4b,
                    (byte) 0xc3,
                    (byte) 0xa1,
                    0x6c,
                    0x6d,
                    (byte) 0xc3,
                    (byte) 0xa1,
                    0x6e,
                    0x20,
                    0x46,
                    0x69,
                    0x6c,
                    0x74,
                    0x65,
                    0x72,
                    0x09, // Track Confidence
                    0x01,
                    0x50,
                    0x0a, // System name
                    0x05,
                    0x6a,
                    0x4d,
                    0x49,
                    0x53,
                    0x42,
                    0x0c, // source sensor
                    0x0F,
                    0x52,
                    0x61,
                    0x73,
                    0x70,
                    0x62,
                    0x65,
                    0x72,
                    0x72,
                    0x79,
                    0x20,
                    0x50,
                    0x69,
                    0x20,
                    0x48,
                    0x51,
                    0x0d, // numTrackPoints
                    0x01,
                    0x02,
                    0x65, // track item series
                    (byte) 0x82, // length of series
                    (byte) 0x02,
                    (byte) 0x3C,
                    (byte) 0x82, // length of first item
                    (byte) 0x01,
                    (byte) 0x01,
                    0x03, // Item 1 id
                    0x07, // Target Priority
                    0x01,
                    0x08,
                    0x05, // boundary top left
                    0x03,
                    (byte) 0x06,
                    (byte) 0x30,
                    (byte) 0x00,
                    0x06, // boundary bottom right
                    0x03,
                    (byte) 0x06,
                    (byte) 0x50,
                    (byte) 0x52,
                    0x08, // Target Confidence
                    0x01,
                    0x55,
                    0x09, // Target History
                    0x01,
                    0x07,
                    0x0a, // Percentage of target pixels
                    0x01,
                    0x04,
                    0x0c,
                    0x02,
                    0x33,
                    0x54,
                    0x0e,
                    0x16,
                    0x0a, // Location 1 length
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x90,
                    (byte) 0xab,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1a,
                    (byte) 0xdf,
                    (byte) 0x10,
                    (byte) 0x14,
                    0x0a, // Location 2 length
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x93,
                    (byte) 0x01,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1b,
                    (byte) 0x00,
                    (byte) 0x10,
                    (byte) 0x14,
                    0x0f, // velocity
                    0x06,
                    (byte) 0x4B,
                    (byte) 0x00,
                    (byte) 0x44,
                    (byte) 0xC0,
                    (byte) 0x3E,
                    (byte) 0x80,
                    0x10, // acceleration
                    0x0c,
                    (byte) 0x4B,
                    (byte) 0x00,
                    (byte) 0x44,
                    (byte) 0xC0,
                    (byte) 0x3E,
                    (byte) 0x80,
                    (byte) 0x25,
                    (byte) 0x80,
                    (byte) 0x19,
                    (byte) 0x00,
                    (byte) 0x0C,
                    (byte) 0x80,
                    0x17, // Vertical FOV
                    0x02,
                    0x05,
                    0x00,
                    0x16, // Horizontal FOV
                    0x02,
                    0x03,
                    0x00,
                    0x11, // FpaIndex
                    0x02,
                    0x03,
                    0x02,
                    0x18, // miURL
                    0x29,
                    0x68,
                    0x74,
                    0x74,
                    0x70,
                    0x73,
                    0x3a,
                    0x2f,
                    0x2f,
                    0x67,
                    0x69,
                    0x74,
                    0x68,
                    0x75,
                    0x62,
                    0x2e,
                    0x63,
                    0x6f,
                    0x6d,
                    0x2f,
                    0x57,
                    0x65,
                    0x73,
                    0x74,
                    0x52,
                    0x69,
                    0x64,
                    0x67,
                    0x65,
                    0x53,
                    0x79,
                    0x73,
                    0x74,
                    0x65,
                    0x6d,
                    0x73,
                    0x2f,
                    0x6a,
                    0x6d,
                    0x69,
                    0x73,
                    0x62,
                    0x67, // VFeature
                    0x2F,
                    0x01,
                    0x2D,
                    0x75,
                    0x72,
                    0x6e,
                    0x3a,
                    0x75,
                    0x75,
                    0x69,
                    0x64,
                    0x3a,
                    0x66,
                    0x38,
                    0x31,
                    0x64,
                    0x34,
                    0x66,
                    0x61,
                    0x65,
                    0x2d,
                    0x37,
                    0x64,
                    0x65,
                    0x63,
                    0x2d,
                    0x31,
                    0x31,
                    0x64,
                    0x30,
                    0x2d,
                    0x61,
                    0x37,
                    0x36,
                    0x35,
                    0x2d,
                    0x30,
                    0x30,
                    0x61,
                    0x30,
                    0x63,
                    0x39,
                    0x31,
                    0x65,
                    0x36,
                    0x62,
                    0x66,
                    0x36,
                    0x69, // VChip
                    78,
                    0x01, // Tag 1 - image type == jpeg
                    0x04,
                    0x6A,
                    0x70,
                    0x65,
                    0x67,
                    (byte) 0x03, // Tag 3 - the image chip
                    70,
                    (byte) 0x89,
                    (byte) 0x50,
                    (byte) 0x4E,
                    (byte) 0x47,
                    (byte) 0x0D,
                    (byte) 0x0A,
                    (byte) 0x1A,
                    (byte) 0x0A,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0D,
                    (byte) 0x49,
                    (byte) 0x48,
                    (byte) 0x44,
                    (byte) 0x52,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x06,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x1F,
                    (byte) 0x15,
                    (byte) 0xC4,
                    (byte) 0x89,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0D,
                    (byte) 0x49,
                    (byte) 0x44,
                    (byte) 0x41,
                    (byte) 0x54,
                    (byte) 0x78,
                    (byte) 0xDA,
                    (byte) 0x63,
                    (byte) 0x64,
                    (byte) 0xD8,
                    (byte) 0xF8,
                    (byte) 0xFF,
                    (byte) 0x3F,
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0x1A,
                    (byte) 0x02,
                    (byte) 0xB1,
                    (byte) 0x49,
                    (byte) 0xC5,
                    (byte) 0x4C,
                    (byte) 0x37,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x49,
                    (byte) 0x45,
                    (byte) 0x4E,
                    (byte) 0x44,
                    (byte) 0xAE,
                    (byte) 0x42,
                    (byte) 0x60,
                    (byte) 0x82,
                    (byte) 0x82, // Length of second item,
                    (byte) 0x01,
                    (byte) 0x35,
                    0x42, // item 2 id
                    0x01, // target timestamp
                    0x08,
                    0x00,
                    0x05,
                    (byte) 0xAD,
                    0x6D,
                    0x6B,
                    0x1A,
                    0x5D,
                    (byte) 0xA4,
                    0x02, // pix num
                    0x03,
                    0x06,
                    0x40,
                    0x00,
                    0x03, // pix row
                    0x02,
                    (byte) 0x03,
                    (byte) 0x68,
                    0x04, // pix col
                    0x02,
                    (byte) 0x04,
                    (byte) 0x71,
                    0x07, // Target Priority
                    0x01,
                    0x05,
                    0x0b, // Target colour
                    0x03,
                    (byte) 0xDA,
                    (byte) 0xA5,
                    0x20,
                    0x0d, // target location
                    0x10,
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x93,
                    (byte) 0x02,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1a,
                    (byte) 0xdf,
                    (byte) 0x10,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x00,
                    (byte) 0x60,
                    (byte) 0x51,
                    (byte) 0x3C,
                    0x12, // Frame Number
                    0x02,
                    0x20,
                    0x02,
                    0x13, // MiisId
                    0x22,
                    (byte) 0x01,
                    (byte) 0x70,
                    (byte) 0xF5,
                    (byte) 0x92,
                    (byte) 0xF0,
                    (byte) 0x23,
                    (byte) 0x73,
                    (byte) 0x36,
                    (byte) 0x4A,
                    (byte) 0xF8,
                    (byte) 0xAA,
                    (byte) 0x91,
                    (byte) 0x62,
                    (byte) 0xC0,
                    (byte) 0x0F,
                    (byte) 0x2E,
                    (byte) 0xB2,
                    (byte) 0xDA,
                    (byte) 0x16,
                    (byte) 0xB7,
                    (byte) 0x43,
                    (byte) 0x41,
                    (byte) 0x00,
                    (byte) 0x08,
                    (byte) 0x41,
                    (byte) 0xA0,
                    (byte) 0xBE,
                    (byte) 0x36,
                    (byte) 0x5B,
                    (byte) 0x5A,
                    (byte) 0xB9,
                    (byte) 0x6A,
                    (byte) 0x36,
                    (byte) 0x45,
                    0x14, // Frame width
                    0x02,
                    0x03,
                    0x7f,
                    0x15, // Frame height
                    0x03,
                    0x01,
                    0x40,
                    0x08,
                    0x65, // VMask
                    0x19,
                    0x01,
                    0x09,
                    0x02,
                    0x39,
                    (byte) 0xAA,
                    0x02,
                    0x39,
                    (byte) 0xBF,
                    0x02,
                    0x3B,
                    0x0B,
                    0x02,
                    0x0C,
                    0x03,
                    0x01,
                    0x4A,
                    0x02,
                    0x03,
                    0x01,
                    0x59,
                    0x04,
                    0x03,
                    0x01,
                    0x6A,
                    0x02,
                    0x66, // VObject
                    0x49,
                    0x01, // First tag in VObject - Ontology
                    0x47,
                    0x68,
                    0x74,
                    0x74,
                    0x70,
                    0x73,
                    0x3A,
                    0x2F,
                    0x2F,
                    0x72,
                    0x61,
                    0x77,
                    0x2E,
                    0x67,
                    0x69,
                    0x74,
                    0x68,
                    0x75,
                    0x62,
                    0x75,
                    0x73,
                    0x65,
                    0x72,
                    0x63,
                    0x6F,
                    0x6E,
                    0x74,
                    0x65,
                    0x6E,
                    0x74,
                    0x2E,
                    0x63,
                    0x6F,
                    0x6D,
                    0x2F,
                    0x6F,
                    0x77,
                    0x6C,
                    0x63,
                    0x73,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2D,
                    0x6F,
                    0x6E,
                    0x74,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x67,
                    0x79,
                    0x2F,
                    0x6D,
                    0x61,
                    0x73,
                    0x74,
                    0x65,
                    0x72,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2E,
                    0x6F,
                    0x77,
                    0x6C,
                    0x6a, // VChipSeries
                    0x0d,
                    0x05, // length of first chip in series
                    0x01,
                    0x03,
                    0x70,
                    0x6e,
                    0x67,
                    0x06, // length of second chip in series
                    0x01,
                    0x04,
                    0x6A,
                    0x70,
                    0x65,
                    0x67,
                    0x6b, // VObjectSeries
                    0x5B,
                    83, // composite length for first ontology
                    0x01,
                    71,
                    0x68,
                    0x74,
                    0x74,
                    0x70,
                    0x73,
                    0x3A,
                    0x2F,
                    0x2F,
                    0x72,
                    0x61,
                    0x77,
                    0x2E,
                    0x67,
                    0x69,
                    0x74,
                    0x68,
                    0x75,
                    0x62,
                    0x75,
                    0x73,
                    0x65,
                    0x72,
                    0x63,
                    0x6F,
                    0x6E,
                    0x74,
                    0x65,
                    0x6E,
                    0x74,
                    0x2E,
                    0x63,
                    0x6F,
                    0x6D,
                    0x2F,
                    0x6F,
                    0x77,
                    0x6C,
                    0x63,
                    0x73,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2D,
                    0x6F,
                    0x6E,
                    0x74,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x67,
                    0x79,
                    0x2F,
                    0x6D,
                    0x61,
                    0x73,
                    0x74,
                    0x65,
                    0x72,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2E,
                    0x6F,
                    0x77,
                    0x6C,
                    0x02,
                    8,
                    0x4D,
                    0x75,
                    0x73,
                    0x68,
                    0x72,
                    0x6F,
                    0x6F,
                    0x6D,
                    6, // composite length for second ontology
                    0x02,
                    4, // Tag 2 and length
                    0x74,
                    0x65,
                    0x73,
                    0x74,
                    0x67, // Ontology Series
                    0x5F,
                    0x53, // Length of Ontology entry 1
                    0x03,
                    0x47,
                    0x68,
                    0x74,
                    0x74,
                    0x70,
                    0x73,
                    0x3A,
                    0x2F,
                    0x2F,
                    0x72,
                    0x61,
                    0x77,
                    0x2E,
                    0x67,
                    0x69,
                    0x74,
                    0x68,
                    0x75,
                    0x62,
                    0x75,
                    0x73,
                    0x65,
                    0x72,
                    0x63,
                    0x6F,
                    0x6E,
                    0x74,
                    0x65,
                    0x6E,
                    0x74,
                    0x2E,
                    0x63,
                    0x6F,
                    0x6D,
                    0x2F,
                    0x6F,
                    0x77,
                    0x6C,
                    0x63,
                    0x73,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2D,
                    0x6F,
                    0x6E,
                    0x74,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x67,
                    0x79,
                    0x2F,
                    0x6D,
                    0x61,
                    0x73,
                    0x74,
                    0x65,
                    0x72,
                    0x2F,
                    0x70,
                    0x69,
                    0x7A,
                    0x7A,
                    0x61,
                    0x2E,
                    0x6F,
                    0x77,
                    0x6C,
                    0x04,
                    0x08,
                    0x4D,
                    0x75,
                    0x73,
                    0x68,
                    0x72,
                    0x6F,
                    0x6F,
                    0x6D,
                    0x0a, // Length of Ontology entry 2
                    0x04,
                    0x08,
                    0x41,
                    0x6d,
                    0x65,
                    0x72,
                    0x69,
                    0x63,
                    0x61,
                    0x6e,
                    0x0b, // version
                    0x01,
                    0x04
                };
        VTrackLocalSet vtrackLocalSet = new VTrackLocalSet(bytesToParse);
        assertNotNull(vtrackLocalSet);
        assertEquals(vtrackLocalSet.getIdentifiers().size(), 14);
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackTimeStamp));
        IVTrackMetadataValue v2 = vtrackLocalSet.getField(VTrackMetadataKey.TrackTimeStamp);
        assertTrue(v2 instanceof PrecisionTimeStamp);
        assertEquals(v2.getDisplayName(), "Precision Time Stamp");
        assertEquals(
                ((PrecisionTimeStamp) v2).getDisplayableValueDateTime(),
                "2020-08-22T01:39:04.532388");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackId));
        IVTrackMetadataValue v3 = vtrackLocalSet.getField(VTrackMetadataKey.TrackId);
        assertTrue(v3 instanceof TrackId);
        TrackId trackId = (TrackId) v3;
        assertEquals(trackId.getDisplayableValue(), "F81D-4FAE-7DEC-11D0-A765-00A0-C91E-6BF6");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackStatus));
        IVTrackMetadataValue v4 = vtrackLocalSet.getField(VTrackMetadataKey.TrackStatus);
        assertTrue(v4 instanceof DetectionStatus);
        DetectionStatus trackStatus = (DetectionStatus) v4;
        assertEquals(trackStatus, DetectionStatus.ACTIVE);
        assertEquals(trackStatus.getDetectionStatus(), 1);
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackStartTime));
        IVTrackMetadataValue v5 = vtrackLocalSet.getField(VTrackMetadataKey.TrackStartTime);
        assertTrue(v5 instanceof StartTime);
        assertEquals(v5.getDisplayName(), "Start Time");
        assertEquals(((StartTime) v5).getDisplayableValueDateTime(), "2020-08-22T01:27:03.983923");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackEndTime));
        IVTrackMetadataValue v6 = vtrackLocalSet.getField(VTrackMetadataKey.TrackEndTime);
        assertTrue(v6 instanceof EndTime);
        assertEquals(v6.getDisplayName(), "End Time");
        assertEquals(((EndTime) v6).getDisplayableValueDateTime(), "2020-08-22T01:37:25.919503");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackBoundarySeries));
        IVTrackMetadataValue v7 = vtrackLocalSet.getField(VTrackMetadataKey.TrackBoundarySeries);
        assertTrue(v7 instanceof BoundarySeries);
        assertEquals(v7.getDisplayName(), "Boundary");
        assertEquals(v7.getDisplayableValue(), "[Location Series]");
        BoundarySeries boundarySeries = (BoundarySeries) v7;
        assertEquals(boundarySeries.getLocations().size(), 2);
        LocationPack location1 = boundarySeries.getLocations().get(0);
        assertEquals(location1.getLat(), -10.54246008396, 0.000001);
        assertEquals(location1.getLon(), 29.15789008141, 0.01);
        assertEquals(location1.getHae(), 3216.0, 0.01);
        LocationPack location2 = boundarySeries.getLocations().get(1);
        assertEquals(location2.getLat(), -10.54238867760, 0.000001);
        assertEquals(location2.getLon(), 29.15789818763, 0.01);
        assertEquals(location2.getHae(), 3216.0, 0.01);
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackAlgorithm));
        IVTrackMetadataValue v8 = vtrackLocalSet.getField(VTrackMetadataKey.TrackAlgorithm);
        assertTrue(v8 instanceof VmtiTextString);
        assertEquals(v8.getDisplayName(), "Algorithm");
        assertEquals(v8.getDisplayableValue(), "Kálmán Filter");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.TrackConfidence));
        IVTrackMetadataValue v9 = vtrackLocalSet.getField(VTrackMetadataKey.TrackConfidence);
        assertTrue(v9 instanceof TrackConfidence);
        assertEquals(v9.getDisplayName(), "Track Confidence");
        assertEquals(v9.getDisplayableValue(), "80%");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.SystemName));
        IVTrackMetadataValue v10 = vtrackLocalSet.getField(VTrackMetadataKey.SystemName);
        assertTrue(v10 instanceof VmtiTextString);
        assertEquals(v10.getDisplayableValue(), "jMISB");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.VersionNumber));
        IVTrackMetadataValue v11 = vtrackLocalSet.getField(VTrackMetadataKey.VersionNumber);
        assertTrue(v11 instanceof ST0903Version);
        assertEquals(v11.getDisplayableValue(), "ST0903.4");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.SourceSensor));
        IVTrackMetadataValue v12 = vtrackLocalSet.getField(VTrackMetadataKey.SourceSensor);
        assertTrue(v12 instanceof VmtiTextString);
        assertEquals(v12.getDisplayableValue(), "Raspberry Pi HQ");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.NumTrackPoints));
        IVTrackMetadataValue v13 = vtrackLocalSet.getField(VTrackMetadataKey.NumTrackPoints);
        assertTrue(v13 instanceof NumTrackPoints);
        assertEquals(v13.getDisplayName(), "Num Track Points");
        assertEquals(v13.getDisplayableValue(), "2");
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.VTrackItemSeries));
        VTrackItemSeries trackItemSeries =
                (VTrackItemSeries) vtrackLocalSet.getField(VTrackMetadataKey.VTrackItemSeries);
        assertNotNull(trackItemSeries);
        assertEquals(trackItemSeries.getIdentifiers().size(), 2);
        assertTrue(trackItemSeries.getIdentifiers().contains(new TargetIdentifierKey(3)));
        assertTrue(trackItemSeries.getIdentifiers().contains(new TargetIdentifierKey(66)));
        VTrackItem trackItem0 = (VTrackItem) trackItemSeries.getField(new TargetIdentifierKey(3));
        assertEquals(trackItem0.getTargetIdentifier(), 3);
        assertEquals(trackItem0.getIdentifiers().size(), 16);
        assertTrue(
                trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.BoundaryTopLeftPixNum));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.BoundaryTopLeftPixNum)
                        .getDisplayableValue(),
                "405504");
        assertTrue(
                trackItem0
                        .getIdentifiers()
                        .contains(VTrackItemMetadataKey.BoundaryBottomRightPixNum));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.BoundaryBottomRightPixNum)
                        .getDisplayableValue(),
                "413778");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.TargetPriority).getDisplayableValue(),
                "8");
        assertTrue(
                trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.TargetConfidenceLevel));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.TargetConfidenceLevel)
                        .getDisplayableValue(),
                "85%");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.TargetHistory));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.TargetHistory).getDisplayableValue(),
                "7");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.PercentTargetPixels));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.PercentTargetPixels)
                        .getDisplayableValue(),
                "4%");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.TargetIntensity));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.TargetIntensity).getDisplayableValue(),
                "13140");
        assertTrue(
                trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.TargetBoundarySeries));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.TargetBoundarySeries)
                        .getDisplayableValue(),
                "[Location Series]");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.Velocity));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.Velocity).getDisplayableValue(),
                "[Velocity]");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.Acceleration));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.Acceleration).getDisplayableValue(),
                "[Acceleration]");
        Acceleration trackItem0acceleration =
                (Acceleration) trackItem0.getField(VTrackItemMetadataKey.Acceleration);
        assertEquals(trackItem0acceleration.getAcceleration().getEast(), 300.0, 0.0001);
        assertEquals(trackItem0acceleration.getAcceleration().getNorth(), 200.0, 0.0001);
        assertEquals(trackItem0acceleration.getAcceleration().getUp(), 100.0, 0.0001);
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.FpaIndex));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.FpaIndex).getDisplayableValue(),
                "Row 3, Col 2");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.SensorHorizontalFov));
        assertEquals(
                trackItem0
                        .getField(VTrackItemMetadataKey.SensorHorizontalFov)
                        .getDisplayableValue(),
                "6.0°");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.SensorVerticalFov));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.SensorVerticalFov).getDisplayableValue(),
                "10.0°");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.MotionImageryUrl));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.MotionImageryUrl).getDisplayableValue(),
                "https://github.com/WestRidgeSystems/jmisb");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.VFeature));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.VFeature).getDisplayableValue(),
                "[VFeature]");
        assertTrue(trackItem0.getIdentifiers().contains(VTrackItemMetadataKey.VChip));
        assertEquals(
                trackItem0.getField(VTrackItemMetadataKey.VChip).getDisplayableValue(), "[VChip]");
        VTrackItem trackItem1 = (VTrackItem) trackItemSeries.getField(new TargetIdentifierKey(66));
        assertEquals(trackItem1.getTargetIdentifier(), 66);
        assertEquals(trackItem1.getIdentifiers().size(), 15);
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetTimeStamp));
        IVTrackItemMetadataValue targetTimeStampValue =
                trackItem1.getField(VTrackItemMetadataKey.TargetTimeStamp);
        assertEquals(
                ((PrecisionTimeStamp) targetTimeStampValue).getDisplayableValueDateTime(),
                "2020-08-22T01:39:03.483812");
        assertTrue(
                trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetCentroidPixNum));
        assertEquals(
                trackItem1
                        .getField(VTrackItemMetadataKey.TargetCentroidPixNum)
                        .getDisplayableValue(),
                "409600");
        assertTrue(
                trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetCentroidPixRow));
        assertEquals(
                trackItem1
                        .getField(VTrackItemMetadataKey.TargetCentroidPixRow)
                        .getDisplayableValue(),
                "872");
        assertTrue(
                trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetCentroidPixCol));
        assertEquals(
                trackItem1
                        .getField(VTrackItemMetadataKey.TargetCentroidPixCol)
                        .getDisplayableValue(),
                "1137");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.TargetPriority).getDisplayableValue(),
                "5");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetColor));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.TargetColor).getDisplayableValue(),
                "[218, 165, 32]");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.TargetLocation));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.TargetLocation).getDisplayableValue(),
                "[Location]");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.VideoFrameNumber));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.VideoFrameNumber).getDisplayableValue(),
                "8194");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.MiisId));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.MiisId).getDisplayableValue(),
                "0170:F592-F023-7336-4AF8-AA91-62C0-0F2E-B2DA/16B7-4341-0008-41A0-BE36-5B5A-B96A-3645:D3");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.FrameWidth));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.FrameWidth).getDisplayableValue(),
                "895px");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.FrameHeight));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.FrameHeight).getDisplayableValue(),
                "81928px");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.VMask));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.VMask).getDisplayableValue(), "[VMask]");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.VObject));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.VObject).getDisplayableValue(),
                "[VObject]");
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.VChipSeries));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.VChipSeries).getDisplayableValue(),
                "[Chip Series]");
        VChipSeries chipSeries =
                (VChipSeries) trackItem1.getField(VTrackItemMetadataKey.VChipSeries);
        assertEquals(chipSeries.getChips().size(), 2);
        assertTrue(trackItem1.getIdentifiers().contains(VTrackItemMetadataKey.VObjectSeries));
        assertEquals(
                trackItem1.getField(VTrackItemMetadataKey.VObjectSeries).getDisplayableValue(),
                "[VObject Series]");
        VObjectSeries objectSeries =
                (VObjectSeries) trackItem1.getField(VTrackItemMetadataKey.VObjectSeries);
        assertEquals(objectSeries.getVObjects().size(), 2);
        assertTrue(vtrackLocalSet.getIdentifiers().contains(VTrackMetadataKey.OntologySeries));
        IVTrackMetadataValue v103 = vtrackLocalSet.getField(VTrackMetadataKey.OntologySeries);
        assertTrue(v103 instanceof OntologySeries);
        assertEquals(v103.getDisplayName(), "Ontology Series");
        assertEquals(v103.getDisplayableValue(), "[Ontologies]");
        OntologySeries ontologies = (OntologySeries) v103;
        assertNotNull(ontologies);
        assertEquals(ontologies.getOntologies().size(), 2);
        List<OntologyLS> ontologys = ontologies.getOntologies();
        assertEquals(ontologys.size(), 2);
        OntologyLS algo1 = ontologys.get(0);
        assertEquals(algo1.getTags().size(), 2);
        OntologyLS algo2 = ontologys.get(1);
        assertEquals(algo2.getTags().size(), 1);
    }
}
