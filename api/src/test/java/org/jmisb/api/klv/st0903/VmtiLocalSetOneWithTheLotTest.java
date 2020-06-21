package org.jmisb.api.klv.st0903;

import static org.jmisb.api.klv.st0903.vtarget.VTargetMetadataKey.VMask;
import static org.testng.Assert.*;

import java.net.URISyntaxException;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmLS;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmMetadataKey;
import org.jmisb.api.klv.st0903.ontology.OntologyLS;
import org.jmisb.api.klv.st0903.ontology.OntologyMetadataKey;
import org.jmisb.api.klv.st0903.vchip.VChipLS;
import org.jmisb.api.klv.st0903.vchip.VChipMetadataKey;
import org.jmisb.api.klv.st0903.vfeature.VFeatureLSTest;
import org.jmisb.api.klv.st0903.vmask.VMaskLSTest;
import org.jmisb.api.klv.st0903.vobject.VObjectLS;
import org.jmisb.api.klv.st0903.vobject.VObjectLSTest;
import org.jmisb.api.klv.st0903.vobject.VObjectMetadataKey;
import org.jmisb.api.klv.st0903.vtarget.TargetLocation;
import org.jmisb.api.klv.st0903.vtarget.VChip;
import org.jmisb.api.klv.st0903.vtarget.VChipSeries;
import org.jmisb.api.klv.st0903.vtarget.VFeature;
import org.jmisb.api.klv.st0903.vtarget.VMask;
import org.jmisb.api.klv.st0903.vtarget.VObject;
import org.jmisb.api.klv.st0903.vtarget.VObjectSeries;
import org.jmisb.api.klv.st0903.vtarget.VTargetMetadataKey;
import org.jmisb.api.klv.st0903.vtarget.VTargetPack;
import org.jmisb.api.klv.st0903.vtarget.VTracker;
import org.jmisb.api.klv.st0903.vtracker.VTrackerMetadataKey;
import org.testng.annotations.Test;

/** Tests for the ST0903 VMTI Local Set including nested parts */
public class VmtiLocalSetOneWithTheLotTest extends LoggerChecks {
    VmtiLocalSetOneWithTheLotTest() {
        super(VmtiLocalSet.class);
    }

    @Test
    public void parseEverything() throws KlvParseException, URISyntaxException {
        final byte[] bytes =
                new byte[] {
                    0x02,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0x82,
                    0x44,
                    0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    0x40,
                    0x03,
                    0x0E,
                    0x44,
                    0x53,
                    0x54,
                    0x4F,
                    0x5F,
                    0x41,
                    0x44,
                    0x53,
                    0x53,
                    0x5F,
                    0x56,
                    0x4D,
                    0x54,
                    0x49,
                    0x04,
                    0x01,
                    0x05,
                    0x05,
                    0x01,
                    0x1C,
                    0x06,
                    0x01,
                    0x02,
                    0x07,
                    0x03,
                    0x01,
                    0x30,
                    (byte) 0xB0,
                    0x08,
                    0x02,
                    0x07,
                    (byte) 0x80,
                    0x09,
                    0x02,
                    0x04,
                    0x38,
                    0x0A,
                    0x07,
                    0x45,
                    0x4F,
                    0x20,
                    0x4E,
                    0x6F,
                    0x73,
                    0x65,
                    0x0B,
                    0x02,
                    0x06,
                    0x40,
                    0x0C,
                    0x02,
                    0x05,
                    0x00,
                    0x0D,
                    0x22,
                    (byte) 0x01,
                    (byte) 0x68,
                    (byte) 0xF3,
                    (byte) 0x54,
                    (byte) 0x66,
                    (byte) 0x6E,
                    (byte) 0xD5,
                    (byte) 0x52,
                    (byte) 0x4C,
                    (byte) 0x0D,
                    (byte) 0x91,
                    (byte) 0x68,
                    (byte) 0xA7,
                    (byte) 0x45,
                    (byte) 0x4C,
                    (byte) 0xEB,
                    (byte) 0xA0,
                    (byte) 0x73,
                    (byte) 0x84,
                    (byte) 0x0A,
                    (byte) 0x47,
                    (byte) 0x99,
                    (byte) 0xBB,
                    (byte) 0xC0,
                    (byte) 0x4B,
                    (byte) 0xD5,
                    (byte) 0x97,
                    (byte) 0xA7,
                    (byte) 0x56,
                    (byte) 0xF6,
                    (byte) 0x40,
                    (byte) 0x92,
                    (byte) 0xAF,
                    (byte) 0x7B,
                    0x65,
                    (byte) 0x82,
                    (byte) 0x03,
                    (byte) 0x86, // Key + length
                    (byte) 0x82,
                    (byte) 0x02,
                    (byte) 0xE1, // Length of first VTarget LS
                    0x01, // Target identifier
                    0x01,
                    0x03,
                    0x06,
                    0x40,
                    0x00, // target centroid
                    0x04,
                    0x01,
                    0x1B, // target priority
                    0x05,
                    0x01,
                    0x50, // confidence
                    0x06,
                    0x02,
                    0x0A,
                    (byte) 0xCD, // target history
                    0x07,
                    0x01,
                    0x32, // percentage target pixels
                    0x08,
                    0x03,
                    (byte) 0xDA,
                    (byte) 0xA5,
                    0x20, // target colour
                    0x0C,
                    0x02,
                    0x2A,
                    (byte) 0x94,
                    0x11,
                    0x0A,
                    (byte) 0x27,
                    (byte) 0xba,
                    (byte) 0x93,
                    (byte) 0x02,
                    (byte) 0x34,
                    (byte) 0x4a,
                    (byte) 0x1a,
                    (byte) 0xdf,
                    (byte) 0x10,
                    (byte) 0x14, // Target location, basic form
                    0x13,
                    0x02,
                    0x03,
                    0x68,
                    0x14,
                    0x02,
                    0x04,
                    0x71,
                    0x15,
                    0x02,
                    0x02,
                    0x03,
                    0x16,
                    0x01,
                    0x2B,
                    0x65,
                    25, // VMask tag + length
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
                    0x0B, // Tag 1
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
                    0x02, // Tag 2
                    0x66,
                    0x5B, // VObject tag + length
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
                    0x03,
                    2,
                    0x01,
                    0x02,
                    0x04,
                    2,
                    0x30,
                    0x00,
                    0x67,
                    (byte) 0x82,
                    0x01,
                    0x76, // VFeature
                    0x01,
                    45, // VFeature tag 1 - schema
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
                    0x02,
                    (byte) 0x82,
                    0x01,
                    0x43, // VFeature tag 2 - schema feature
                    0x3C,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x44,
                    0x61,
                    0x74,
                    0x61,
                    0x42,
                    0x6C,
                    0x6F,
                    0x63,
                    0x6B,
                    0x3E,
                    0x3C,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x72,
                    0x61,
                    0x6E,
                    0x67,
                    0x65,
                    0x50,
                    0x61,
                    0x72,
                    0x61,
                    0x6D,
                    0x65,
                    0x74,
                    0x65,
                    0x72,
                    0x73,
                    0x3E,
                    0x3C,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x43,
                    0x6F,
                    0x6D,
                    0x70,
                    0x6F,
                    0x73,
                    0x69,
                    0x74,
                    0x65,
                    0x56,
                    0x61,
                    0x6C,
                    0x75,
                    0x65,
                    0x3E,
                    0x3C,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x76,
                    0x61,
                    0x6C,
                    0x75,
                    0x65,
                    0x43,
                    0x6F,
                    0x6D,
                    0x70,
                    0x6F,
                    0x6E,
                    0x65,
                    0x6E,
                    0x74,
                    0x73,
                    0x3E,
                    0x3C,
                    0x54,
                    0x65,
                    0x6D,
                    0x70,
                    0x65,
                    0x72,
                    0x61,
                    0x74,
                    0x75,
                    0x72,
                    0x65,
                    0x20,
                    0x75,
                    0x6F,
                    0x6D,
                    0x3D,
                    0x22,
                    0x75,
                    0x72,
                    0x6E,
                    0x3A,
                    0x78,
                    0x2D,
                    0x73,
                    0x69,
                    0x3A,
                    0x76,
                    0x31,
                    0x39,
                    0x39,
                    0x39,
                    0x3A,
                    0x75,
                    0x6F,
                    0x6D,
                    0x3A,
                    0x64,
                    0x65,
                    0x67,
                    0x72,
                    0x65,
                    0x65,
                    0x73,
                    0x43,
                    0x22,
                    0x3E,
                    0x74,
                    0x65,
                    0x6D,
                    0x70,
                    0x6C,
                    0x61,
                    0x74,
                    0x65,
                    0x3C,
                    0x2F,
                    0x54,
                    0x65,
                    0x6D,
                    0x70,
                    0x65,
                    0x72,
                    0x61,
                    0x74,
                    0x75,
                    0x72,
                    0x65,
                    0x3E,
                    0x3C,
                    0x50,
                    0x72,
                    0x65,
                    0x73,
                    0x73,
                    0x75,
                    0x72,
                    0x65,
                    0x20,
                    0x75,
                    0x6F,
                    0x6D,
                    0x3D,
                    0x22,
                    0x75,
                    0x72,
                    0x6E,
                    0x3A,
                    0x78,
                    0x2D,
                    0x73,
                    0x69,
                    0x3A,
                    0x76,
                    0x31,
                    0x39,
                    0x39,
                    0x39,
                    0x3A,
                    0x75,
                    0x6F,
                    0x6D,
                    0x3A,
                    0x6B,
                    0x50,
                    0x61,
                    0x22,
                    0x3E,
                    0x74,
                    0x65,
                    0x6D,
                    0x70,
                    0x6C,
                    0x61,
                    0x74,
                    0x65,
                    0x3C,
                    0x2F,
                    0x50,
                    0x72,
                    0x65,
                    0x73,
                    0x73,
                    0x75,
                    0x72,
                    0x65,
                    0x3E,
                    0x3C,
                    0x2F,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x76,
                    0x61,
                    0x6C,
                    0x75,
                    0x65,
                    0x43,
                    0x6F,
                    0x6D,
                    0x70,
                    0x6F,
                    0x6E,
                    0x65,
                    0x6E,
                    0x74,
                    0x73,
                    0x3E,
                    0x3C,
                    0x2F,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x43,
                    0x6F,
                    0x6D,
                    0x70,
                    0x6F,
                    0x73,
                    0x69,
                    0x74,
                    0x65,
                    0x56,
                    0x61,
                    0x6C,
                    0x75,
                    0x65,
                    0x3E,
                    0x3C,
                    0x2F,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x72,
                    0x61,
                    0x6E,
                    0x67,
                    0x65,
                    0x50,
                    0x61,
                    0x72,
                    0x61,
                    0x6D,
                    0x65,
                    0x74,
                    0x65,
                    0x72,
                    0x73,
                    0x3E,
                    0x3C,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x74,
                    0x75,
                    0x70,
                    0x6C,
                    0x65,
                    0x4C,
                    0x69,
                    0x73,
                    0x74,
                    0x3E,
                    0x33,
                    0x2C,
                    0x31,
                    0x30,
                    0x31,
                    0x2E,
                    0x32,
                    0x3C,
                    0x2F,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x74,
                    0x75,
                    0x70,
                    0x6C,
                    0x65,
                    0x4C,
                    0x69,
                    0x73,
                    0x74,
                    0x3E,
                    0x3C,
                    0x2F,
                    0x67,
                    0x6D,
                    0x6C,
                    0x3A,
                    0x44,
                    0x61,
                    0x74,
                    0x61,
                    0x42,
                    0x6C,
                    0x6F,
                    0x63,
                    0x6B,
                    0x3E,
                    0x68,
                    0x09, // VTracker Tag + length
                    0x02,
                    0x01,
                    0x01,
                    0x07,
                    0x01,
                    0x32,
                    0x0C,
                    0x01,
                    0x09, // Algorithm ID
                    0x69,
                    0x4E, // VChip
                    0x01,
                    0x04,
                    0x6A,
                    0x70,
                    0x65,
                    0x67, // Tag 1
                    0x03,
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
                    0x6B,
                    0x5B,
                    0x53, // composite length for first ontology
                    0x01,
                    71, // Tag 1 and length
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
                    0x08,
                    0x4D,
                    0x75,
                    0x73,
                    0x68,
                    0x72,
                    0x6F,
                    0x6F,
                    0x6D,
                    0x06, // composite length for second ontology
                    0x02,
                    0x04,
                    0x74,
                    0x65,
                    0x73,
                    0x74, // Tag 2 and length
                    (byte) 0x81,
                    (byte) 0xA0, // Length of second vtarget local set
                    0x03, // Target identifier
                    0x02,
                    0x03,
                    0x06,
                    0x40,
                    0x00,
                    0x03,
                    0x03,
                    0x06,
                    (byte) 0x8B,
                    0x0A,
                    0x04,
                    0x01,
                    0x03, // target priority
                    0x07,
                    0x01,
                    0x06, // percentage target pixels
                    0x09,
                    0x02,
                    0x33,
                    0x54, // target intensity
                    0x16,
                    0x01,
                    0x09, // algorithm ID
                    0x6A,
                    (byte) 0x81,
                    (byte) 0x85, // VChipSeries tag and length
                    78, // composite length for first chip
                    0x01,
                    0x04,
                    0x6A,
                    0x70,
                    0x65,
                    0x67, // Tag 1
                    (byte) 0x03, // Tag 3 key
                    70, // Tag 3 length
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
                    0x35, // composite length for second chip
                    0x01,
                    0x03,
                    0x70,
                    0x6e,
                    0x67, // Tag 1 - image type
                    0x02,
                    46,
                    0x68,
                    0x74,
                    0x74,
                    0x70,
                    0x73,
                    0x3A,
                    0x2F,
                    0x2F,
                    0x77,
                    0x77,
                    0x77,
                    0x2E,
                    0x67,
                    0x77,
                    0x67,
                    0x2E,
                    0x6E,
                    0x67,
                    0x61,
                    0x2E,
                    0x6D,
                    0x69,
                    0x6C,
                    0x2F,
                    0x6D,
                    0x69,
                    0x73,
                    0x62,
                    0x2F,
                    0x69,
                    0x6D,
                    0x61,
                    0x67,
                    0x65,
                    0x73,
                    0x2F,
                    0x62,
                    0x61,
                    0x6E,
                    0x6E,
                    0x65,
                    0x72,
                    0x2E,
                    0x6A,
                    0x70,
                    0x67, // Tag 2 - URI
                    0x66,
                    0x39, // Algorithm Series key + length
                    ((1 + 1 + 1)
                            + (1 + 1 + 0x14)
                            + (1 + 1 + 4)
                            + (1 + 1 + 7)
                            + (1 + 1 + 1)), // Length of Algorithm entry 1
                    0x01,
                    0x01,
                    0x09,
                    0x02,
                    0x14,
                    0x6B,
                    0x36,
                    0x5F,
                    0x79,
                    0x6F,
                    0x6C,
                    0x6F,
                    0x5F,
                    0x39,
                    0x30,
                    0x30,
                    0x30,
                    0x5F,
                    0x74,
                    0x72,
                    0x61,
                    0x63,
                    0x6B,
                    0x65,
                    0x72,
                    0x03,
                    0x04,
                    0x32,
                    0x2E,
                    0x36,
                    0x61,
                    0x04,
                    0x07,
                    0x6B,
                    0x61,
                    0x6C,
                    0x6D,
                    0x61,
                    0x6E,
                    0x6E,
                    0x05,
                    0x01,
                    0x0A,
                    0x0C, // Length of Algorithm entry 2
                    0x01,
                    0x01,
                    0x2B,
                    0x02,
                    0x07,
                    0x6f,
                    0x70,
                    0x65,
                    0x6e,
                    0x63,
                    0x76,
                    0x32,
                    0x67,
                    0x5F,
                    83, // Length of Ontology entry 1
                    0x03,
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
                    10, // Length of Ontology entry 2
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
                    0x01,
                    0x02,
                    (byte) 0x1d,
                    (byte) 0xf5 // checksum
                };
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 15);
        assertEquals(
                localSet.getField(VmtiMetadataKey.PrecisionTimeStamp).getDisplayName(),
                "Precision Time Stamp");
        assertEquals(
                localSet.getField(VmtiMetadataKey.PrecisionTimeStamp).getDisplayableValue(),
                "987654321000000");
        assertEquals(
                localSet.getField(VmtiMetadataKey.VersionNumber).getDisplayName(),
                "Version Number");
        assertEquals(
                localSet.getField(VmtiMetadataKey.VersionNumber).getDisplayableValue(), "ST0903.5");
        assertEquals(
                localSet.getField(VmtiMetadataKey.SystemName).getDisplayName(),
                "System Name/Description");
        assertEquals(
                localSet.getField(VmtiMetadataKey.SystemName).getDisplayableValue(),
                "DSTO_ADSS_VMTI");
        assertEquals(
                localSet.getField(VmtiMetadataKey.TotalTargetsInFrame).getDisplayName(),
                "Targets In Frame");
        assertEquals(
                localSet.getField(VmtiMetadataKey.TotalTargetsInFrame).getDisplayableValue(), "28");
        assertEquals(
                localSet.getField(VmtiMetadataKey.NumberOfReportedTargets).getDisplayName(),
                "Reported Targets");
        assertEquals(
                localSet.getField(VmtiMetadataKey.NumberOfReportedTargets).getDisplayableValue(),
                "2");
        assertEquals(
                localSet.getField(VmtiMetadataKey.FrameNumber).getDisplayName(), "Frame Number");
        assertEquals(localSet.getField(VmtiMetadataKey.FrameNumber).getDisplayableValue(), "78000");
        assertEquals(localSet.getField(VmtiMetadataKey.FrameWidth).getDisplayName(), "Frame Width");
        assertEquals(localSet.getField(VmtiMetadataKey.FrameWidth).getDisplayableValue(), "1920px");
        assertEquals(
                localSet.getField(VmtiMetadataKey.FrameHeight).getDisplayName(), "Frame Height");
        assertEquals(
                localSet.getField(VmtiMetadataKey.FrameHeight).getDisplayableValue(), "1080px");
        assertEquals(
                localSet.getField(VmtiMetadataKey.SourceSensor).getDisplayName(), "Source Sensor");
        assertEquals(
                localSet.getField(VmtiMetadataKey.SourceSensor).getDisplayableValue(), "EO Nose");
        assertEquals(
                localSet.getField(VmtiMetadataKey.HorizontalFieldOfView).getDisplayName(),
                "Horizontal Field of View");
        assertEquals(
                localSet.getField(VmtiMetadataKey.HorizontalFieldOfView).getDisplayableValue(),
                "12.5\u00B0");
        assertEquals(
                localSet.getField(VmtiMetadataKey.VerticalFieldOfView).getDisplayName(),
                "Vertical Field of View");
        assertEquals(
                localSet.getField(VmtiMetadataKey.VerticalFieldOfView).getDisplayableValue(),
                "10.0\u00B0");
        assertEquals(
                localSet.getField(VmtiMetadataKey.MiisId).getDisplayName(), "MIIS Core Identifier");
        assertEquals(
                localSet.getField(VmtiMetadataKey.MiisId).getDisplayableValue(),
                "0168:F354-666E-D552-4C0D-9168-A745-4CEB-A073/840A-4799-BBC0-4BD5-97A7-56F6-4092-AF7B:AA");
        assertEquals(
                localSet.getField(VmtiMetadataKey.VTargetSeries).getDisplayName(), "Target Series");
        assertTrue(localSet.getField(VmtiMetadataKey.VTargetSeries) instanceof VTargetSeries);
        VTargetSeries targetSeries =
                (VTargetSeries) localSet.getField(VmtiMetadataKey.VTargetSeries);
        assertEquals(targetSeries.getVTargets().size(), 2);
        VTargetPack targetPack1 = targetSeries.getVTargets().get(0);
        assertEquals(targetPack1.getTargetIdentifier(), 1);
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetCentroid).getDisplayName(),
                "Target Centroid");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetCentroid).getDisplayableValue(),
                "409600");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetPriority).getDisplayName(),
                "Target Priority");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetPriority).getDisplayableValue(),
                "27");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetConfidenceLevel).getDisplayName(),
                "Target Confidence");
        assertEquals(
                targetPack1
                        .getField(VTargetMetadataKey.TargetConfidenceLevel)
                        .getDisplayableValue(),
                "80%");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetHistory).getDisplayName(),
                "Target History");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetHistory).getDisplayableValue(),
                "2765");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.PercentageOfTargetPixels).getDisplayName(),
                "Percentage Target Pixels");
        assertEquals(
                targetPack1
                        .getField(VTargetMetadataKey.PercentageOfTargetPixels)
                        .getDisplayableValue(),
                "50%");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetColor).getDisplayName(),
                "Target Color");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetColor).getDisplayableValue(),
                "[218, 165, 32]");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetHAE).getDisplayName(), "Target HAE");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetHAE).getDisplayableValue(),
                "10000.0");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetLocation).getDisplayName(),
                "Target Location");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.TargetLocation).getDisplayableValue(),
                "[Location]");
        assertTrue(
                targetPack1.getField(VTargetMetadataKey.TargetLocation) instanceof TargetLocation);
        TargetLocation targetLocation =
                (TargetLocation) targetPack1.getField(VTargetMetadataKey.TargetLocation);
        assertEquals(targetLocation.getDisplayableValue(), "[Location]");
        assertEquals(targetLocation.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(targetLocation.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(targetLocation.getTargetLocation().getHae(), 3216.0, 0.02);
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.CentroidPixRow).getDisplayName(),
                "Centroid Pixel Row");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.CentroidPixRow).getDisplayableValue(),
                "872");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.CentroidPixColumn).getDisplayName(),
                "Centroid Pixel Column");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.CentroidPixColumn).getDisplayableValue(),
                "1137");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.FPAIndex).getDisplayName(), "FPA Index");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.FPAIndex).getDisplayableValue(),
                "Row 2, Col 3");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.AlgorithmId).getDisplayName(),
                "Algorithm Id");
        assertEquals(
                targetPack1.getField(VTargetMetadataKey.AlgorithmId).getDisplayableValue(), "43");
        assertTrue(targetPack1.getField(VTargetMetadataKey.VMask) instanceof VMask);
        VMask tp1mask = (VMask) targetPack1.getField(VTargetMetadataKey.VMask);
        assertEquals(tp1mask.getMask().getTags().size(), 2);
        VMaskLSTest.checkPolygonExample(tp1mask.getMask());
        VMaskLSTest.checkBitmaskExample(tp1mask.getMask());
        assertTrue(targetPack1.getField(VTargetMetadataKey.VObject) instanceof VObject);
        VObject tp1object = (VObject) targetPack1.getField(VTargetMetadataKey.VObject);
        assertEquals(tp1object.getVObject().getTags().size(), 4);
        VObjectLSTest.checkOntologyExample(tp1object.getVObject());
        VObjectLSTest.checkOntologyClassExample(tp1object.getVObject());
        VObjectLSTest.checkOntologyIdExample(tp1object.getVObject());
        VObjectLSTest.checkOntologyExample(tp1object.getVObject());
        assertTrue(targetPack1.getField(VTargetMetadataKey.VFeature) instanceof VFeature);
        VFeature tp1feature = (VFeature) targetPack1.getField(VTargetMetadataKey.VFeature);
        VFeatureLSTest.checkSchemaExample(tp1feature.getFeature());
        VFeatureLSTest.checkSchemaFeatureExample(tp1feature.getFeature());
        assertTrue(targetPack1.getField(VTargetMetadataKey.VTracker) instanceof VTracker);
        VTracker tp1tracker = (VTracker) targetPack1.getField(VTargetMetadataKey.VTracker);
        assertEquals(tp1tracker.getTracker().getTags().size(), 3);
        assertEquals(
                tp1tracker
                        .getTracker()
                        .getField(VTrackerMetadataKey.detectionStatus)
                        .getDisplayName(),
                "Detection Status");
        assertEquals(
                tp1tracker
                        .getTracker()
                        .getField(VTrackerMetadataKey.detectionStatus)
                        .getDisplayableValue(),
                "Active");
        assertEquals(
                tp1tracker.getTracker().getField(VTrackerMetadataKey.confidence).getDisplayName(),
                "Track Confidence");
        assertEquals(
                tp1tracker
                        .getTracker()
                        .getField(VTrackerMetadataKey.confidence)
                        .getDisplayableValue(),
                "50%");
        assertEquals(
                tp1tracker.getTracker().getField(VTrackerMetadataKey.algorithmId).getDisplayName(),
                "Algorithm Id");
        assertEquals(
                tp1tracker
                        .getTracker()
                        .getField(VTrackerMetadataKey.algorithmId)
                        .getDisplayableValue(),
                "9");
        assertTrue(targetPack1.getField(VTargetMetadataKey.VChip) instanceof VChip);
        VChip targetPack1Chip = (VChip) targetPack1.getField(VTargetMetadataKey.VChip);
        assertEquals(
                targetPack1Chip.getChip().getField(VChipMetadataKey.imageType).getDisplayName(),
                "Image Type");
        assertEquals(
                targetPack1Chip
                        .getChip()
                        .getField(VChipMetadataKey.imageType)
                        .getDisplayableValue(),
                "jpeg");
        assertEquals(
                targetPack1Chip.getChip().getField(VChipMetadataKey.embeddedImage).getDisplayName(),
                "Embedded Image");
        assertEquals(
                targetPack1Chip
                        .getChip()
                        .getField(VChipMetadataKey.embeddedImage)
                        .getDisplayableValue(),
                "[Image]");
        assertEquals(
                targetPack1Chip
                        .getChip()
                        .getField(VChipMetadataKey.embeddedImage)
                        .getBytes()
                        .length,
                70);
        VTargetPack targetPack3 = targetSeries.getVTargets().get(1);
        assertEquals(targetPack3.getTargetIdentifier(), 3);
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.BoundaryTopLeft).getDisplayName(),
                "Boundary Top Left");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.BoundaryTopLeft).getDisplayableValue(),
                "409600");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.BoundaryBottomRight).getDisplayName(),
                "Boundary Bottom Right");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.BoundaryBottomRight).getDisplayableValue(),
                "428810");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.TargetPriority).getDisplayName(),
                "Target Priority");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.TargetPriority).getDisplayableValue(), "3");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.PercentageOfTargetPixels).getDisplayName(),
                "Percentage Target Pixels");
        assertEquals(
                targetPack3
                        .getField(VTargetMetadataKey.PercentageOfTargetPixels)
                        .getDisplayableValue(),
                "6%");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.TargetIntensity).getDisplayName(),
                "Target Intensity");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.TargetIntensity).getDisplayableValue(),
                "13140");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.AlgorithmId).getDisplayName(),
                "Algorithm Id");
        assertEquals(
                targetPack3.getField(VTargetMetadataKey.AlgorithmId).getDisplayableValue(), "9");
        assertTrue(targetPack3.getField(VTargetMetadataKey.VChipSeries) instanceof VChipSeries);
        VChipSeries targetPack3ChipSeries =
                (VChipSeries) targetPack3.getField(VTargetMetadataKey.VChipSeries);
        assertEquals(targetPack3ChipSeries.getChips().size(), 2);
        VChipLS chip3_0 = targetPack3ChipSeries.getChips().get(0);
        assertEquals(chip3_0.getField(VChipMetadataKey.imageType).getDisplayName(), "Image Type");
        assertEquals(chip3_0.getField(VChipMetadataKey.imageType).getDisplayableValue(), "jpeg");
        assertEquals(
                chip3_0.getField(VChipMetadataKey.embeddedImage).getDisplayName(),
                "Embedded Image");
        assertEquals(
                chip3_0.getField(VChipMetadataKey.embeddedImage).getDisplayableValue(), "[Image]");
        assertEquals(chip3_0.getField(VChipMetadataKey.embeddedImage).getBytes().length, 70);
        VChipLS chip3_1 = targetPack3ChipSeries.getChips().get(1);
        assertEquals(chip3_1.getField(VChipMetadataKey.imageType).getDisplayName(), "Image Type");
        assertEquals(chip3_1.getField(VChipMetadataKey.imageType).getDisplayableValue(), "png");
        assertEquals(chip3_1.getField(VChipMetadataKey.imageUri).getDisplayName(), "Image URI");
        assertEquals(
                chip3_1.getField(VChipMetadataKey.imageUri).getDisplayableValue(),
                "https://www.gwg.nga.mil/misb/images/banner.jpg");

        VObjectSeries vobjectSeries =
                (VObjectSeries) targetPack1.getField(VTargetMetadataKey.VObjectSeries);
        assertEquals(vobjectSeries.getVObjects().size(), 2);
        VObjectLS vobject1 = vobjectSeries.getVObjects().get(0);
        assertEquals(vobject1.getField(VObjectMetadataKey.ontology).getDisplayName(), "Ontology");
        assertEquals(
                vobject1.getField(VObjectMetadataKey.ontology).getDisplayableValue(),
                "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        assertEquals(
                vobject1.getField(VObjectMetadataKey.ontologyClass).getDisplayName(),
                "Ontology Class");
        assertEquals(
                vobject1.getField(VObjectMetadataKey.ontologyClass).getDisplayableValue(),
                "Mushroom");
        VObjectLS vobject2 = vobjectSeries.getVObjects().get(1);
        assertEquals(
                vobject2.getField(VObjectMetadataKey.ontologyClass).getDisplayName(),
                "Ontology Class");
        assertEquals(
                vobject2.getField(VObjectMetadataKey.ontologyClass).getDisplayableValue(), "test");

        AlgorithmSeries algorithmSeries =
                (AlgorithmSeries) localSet.getField(VmtiMetadataKey.AlgorithmSeries);
        assertEquals(algorithmSeries.getAlgorithms().size(), 2);
        AlgorithmLS algorithm1 = algorithmSeries.getAlgorithms().get(0);
        assertEquals(algorithm1.getField(AlgorithmMetadataKey.id).getDisplayName(), "Algorithm Id");
        assertEquals(algorithm1.getField(AlgorithmMetadataKey.id).getDisplayableValue(), "9");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.name).getDisplayName(), "Algorithm Name");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.name).getDisplayableValue(),
                "k6_yolo_9000_tracker");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.version).getDisplayName(),
                "Algorithm Version");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.version).getDisplayableValue(), "2.6a");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.algorithmClass).getDisplayName(),
                "Algorithm Class");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.algorithmClass).getDisplayableValue(),
                "kalmann");
        assertEquals(
                algorithm1.getField(AlgorithmMetadataKey.nFrames).getDisplayName(),
                "Number of Frames");
        assertEquals(algorithm1.getField(AlgorithmMetadataKey.nFrames).getDisplayableValue(), "10");
        AlgorithmLS algorithm2 = algorithmSeries.getAlgorithms().get(1);
        assertEquals(algorithm2.getField(AlgorithmMetadataKey.id).getDisplayName(), "Algorithm Id");
        assertEquals(algorithm2.getField(AlgorithmMetadataKey.id).getDisplayableValue(), "43");
        assertEquals(
                algorithm2.getField(AlgorithmMetadataKey.name).getDisplayName(), "Algorithm Name");
        assertEquals(
                algorithm2.getField(AlgorithmMetadataKey.name).getDisplayableValue(), "opencv2");

        OntologySeries ontologySeries =
                (OntologySeries) localSet.getField(VmtiMetadataKey.OntologySeries);
        assertEquals(ontologySeries.getOntologies().size(), 2);
        OntologyLS ontology1 = ontologySeries.getOntologies().get(0);
        assertEquals(ontology1.getTags().size(), 2);
        assertEquals(ontology1.getField(OntologyMetadataKey.ontology).getDisplayName(), "Ontology");
        assertEquals(
                ontology1.getField(OntologyMetadataKey.ontology).getDisplayableValue(),
                "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        assertEquals(
                ontology1.getField(OntologyMetadataKey.ontologyClass).getDisplayName(),
                "Ontology Class");
        assertEquals(
                ontology1.getField(OntologyMetadataKey.ontologyClass).getDisplayableValue(),
                "Mushroom");

        OntologyLS ontology2 = ontologySeries.getOntologies().get(1);
        assertEquals(ontology2.getTags().size(), 1);
        assertEquals(
                ontology2.getField(OntologyMetadataKey.ontologyClass).getDisplayName(),
                "Ontology Class");
        assertEquals(
                ontology2.getField(OntologyMetadataKey.ontologyClass).getDisplayableValue(),
                "American");
    }
}
