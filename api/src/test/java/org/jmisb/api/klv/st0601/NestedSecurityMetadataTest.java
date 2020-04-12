package org.jmisb.api.klv.st0601;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedSecurityMetadataTest
{
    @Test
    public void testConstructFromLocalSet()
    {
        NestedSecurityMetadata nestedSecurityMetadata = new NestedSecurityMetadata(makeLocalSet());
        Assert.assertNotNull(nestedSecurityMetadata);
        Assert.assertEquals(nestedSecurityMetadata.getDisplayName(), "Security");
        Assert.assertEquals(nestedSecurityMetadata.getDisplayableValue(), "[Security metadata]");
        Assert.assertEquals(nestedSecurityMetadata.getLocalSet().getField(SecurityMetadataKey.Caveats).getDisplayableValue(), "TesT1");
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException
    {
        SecurityMetadataLocalSet localSet = makeLocalSet();
        byte[] localSetAsByteArray = localSet.frameMessage(true);
        NestedSecurityMetadata nestedSecurityMetadata = new NestedSecurityMetadata(localSetAsByteArray);
        Assert.assertNotNull(nestedSecurityMetadata);

        Assert.assertEquals(nestedSecurityMetadata.getDisplayName(), "Security");
        Assert.assertEquals(nestedSecurityMetadata.getDisplayableValue(), "[Security metadata]");
        Assert.assertEquals(nestedSecurityMetadata.getLocalSet().getField(SecurityMetadataKey.Caveats).getDisplayableValue(), "TesT1");
        byte[] bytes = nestedSecurityMetadata.getBytes();
        Assert.assertEquals(bytes, localSetAsByteArray);
    }

    private SecurityMetadataLocalSet makeLocalSet() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> securityKeyValuePairs = new TreeMap<>();
        securityKeyValuePairs.put(SecurityMetadataKey.Caveats, new SecurityMetadataString(SecurityMetadataString.CAVEATS, "TesT1"));
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(securityKeyValuePairs);
        return securityMetadataLocalSet;
    }
}
