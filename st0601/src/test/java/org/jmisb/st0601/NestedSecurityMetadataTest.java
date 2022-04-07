package org.jmisb.st0601;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0102.ISecurityMetadataValue;
import org.jmisb.st0102.SecurityMetadataKey;
import org.jmisb.st0102.SecurityMetadataString;
import org.jmisb.st0102.localset.SecurityMetadataLocalSet;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedSecurityMetadataTest {
    private final byte[] localSetAsBytes =
            new byte[] {
                (byte) 0x05,
                (byte) 0x05,
                (byte) 0x54,
                (byte) 0x65,
                (byte) 0x73,
                (byte) 0x54,
                (byte) 0x31
            };

    @Test
    public void testConstructFromLocalSet() {
        NestedSecurityMetadata nestedSecurityMetadata = new NestedSecurityMetadata(makeLocalSet());
        checkResults(nestedSecurityMetadata);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedSecurityMetadata nestedSecurityMetadata = new NestedSecurityMetadata(localSetAsBytes);
        checkResults(nestedSecurityMetadata);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.SecurityLocalMetadataSet, localSetAsBytes);
        Assert.assertTrue(value instanceof NestedSecurityMetadata);
        NestedSecurityMetadata nestedSecurityMetadata = (NestedSecurityMetadata) value;
        checkResults(nestedSecurityMetadata);
    }

    private void checkResults(NestedSecurityMetadata nestedSecurityMetadata) {
        Assert.assertNotNull(nestedSecurityMetadata);
        Assert.assertEquals(nestedSecurityMetadata.getDisplayName(), "Security");
        Assert.assertEquals(nestedSecurityMetadata.getDisplayableValue(), "[Security metadata]");
        Assert.assertEquals(
                nestedSecurityMetadata
                        .getLocalSet()
                        .getField(SecurityMetadataKey.Caveats)
                        .getDisplayableValue(),
                "TesT1");
        byte[] bytes = nestedSecurityMetadata.getBytes();
        Assert.assertEquals(bytes, localSetAsBytes);
        Assert.assertEquals(nestedSecurityMetadata.getIdentifiers().size(), 1);
        Assert.assertTrue(
                nestedSecurityMetadata.getIdentifiers().contains(SecurityMetadataKey.Caveats));
        Assert.assertEquals(
                nestedSecurityMetadata.getField(SecurityMetadataKey.Caveats).getDisplayableValue(),
                "TesT1");
    }

    private SecurityMetadataLocalSet makeLocalSet() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> securityKeyValuePairs =
                new TreeMap<>();
        securityKeyValuePairs.put(
                SecurityMetadataKey.Caveats,
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "TesT1"));
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(securityKeyValuePairs);
        return securityMetadataLocalSet;
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        new NestedSecurityMetadata(
                new byte[] {
                    0x00,
                    0x00,
                    0x17,
                    0x0a,
                    (byte) 0x9b,
                    (byte) 0xc7,
                    (byte) 0x95,
                    (byte) 0xb6,
                    0x78,
                    (byte) 0xe7,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x07,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00
                });
    }
}
