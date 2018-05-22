package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st0102.*;
import org.jmisb.core.klv.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecurityMetadataLocalSetTest
{
    private SecurityMetadataLocalSet localSet;

    @BeforeTest
    public void createSet()
    {
        SortedMap<SecurityMetadataKey, SecurityMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.SecurityClassification, new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(SecurityMetadataKey.CcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ClassifyingCountry, new SecurityMetadataString("//US"));

        values.put(SecurityMetadataKey.OcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new SecurityMetadataString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(12));

        localSet = new SecurityMetadataLocalSet(values);
    }

    @Test
    void testUniversalLabel()
    {
        // Check that the correct universal label is applied
        Assert.assertEquals(localSet.getUniversalLabel(), KlvConstants.SecurityMetadataLocalSetUl);
    }

    @Test
    public void testFrameFull()
    {
        // Frame a full message
        byte[] bytes = localSet.frameMessage(false);

        // System.out.println(ArrayUtils.toHexString(bytes));

        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(Arrays.copyOfRange(bytes, 0, 16), KlvConstants.SecurityMetadataLocalSetUl.getBytes());

        // Value length = 3 + 3 + 6 + 3 + 7 + 4 = 26 bytes
        Assert.assertEquals(bytes[16], 26);
    }

    @Test
    public void testFrameNested()
    {
        // Frame a nested message
        byte[] bytes = localSet.frameMessage(true);

        // System.out.println(ArrayUtils.toHexString(bytes));

        // Value length = 3 + 3 + 6 + 3 + 7 + 4 = 26 bytes
        Assert.assertEquals(bytes.length, 26);
    }

    @Test
    public void testMinimumFromBuilder()
    {
        // Create a message equivalent to localSet
        SecurityMetadataLocalSet builderSet = new SecurityMetadataLocalSet.Builder(Classification.UNCLASSIFIED)
                .classifyingCountry("//US")
                .objectCountryCodes("US;CA")
                .build();

        byte[] bytes1 = localSet.frameMessage(false);
        byte[] bytes2 = builderSet.frameMessage(false);
        Assert.assertEquals(bytes1, bytes2);

        bytes1 = localSet.frameMessage(true);
        bytes2 = builderSet.frameMessage(true);
        Assert.assertEquals(bytes1, bytes2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingReqField()
    {
        // Missing a required field - Object Country Codes
        new SecurityMetadataLocalSet.Builder(Classification.UNCLASSIFIED)
                .classifyingCountry("//US")
                .build();
    }

    @Test
    public void testFullFromBuilder()
    {
        // Create a message with all fields populated
        SecurityMetadataLocalSet fullMessage = new SecurityMetadataLocalSet.Builder(Classification.TOP_SECRET)
                .ccMethod(CountryCodingMethod.ISO3166_THREE_LETTER)
                .classifyingCountry("//USA")
                .sciShiInfo("Hello, World!")
                .caveats("Caveat emptor!")
                .releasingInstructions("Not for release")
                .classifiedBy("Yours Truly")
                .derivedFrom("Some such thing")
                .classificationReason("Just because")
                .declassificationDate(LocalDate.of(2028, 10, 31))
                .markingSystem("None")
                .ocMethod(CountryCodingMethod.ISO3166_TWO_LETTER)
                .objectCountryCodes("US;CA")
                .classificationComments("No comment")
                .version(10)
                .ccmDate(LocalDate.of(2010, 12, 25))
                .ocmDate(LocalDate.of(1998, 5, 27))
                .build();

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.Caveats));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.Caveats).getBytes(), "Caveat emptor!".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.DeclassificationDate));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.DeclassificationDate).getBytes(), "20281031".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate).getBytes(), "2010-12-25".getBytes());

        byte[] bytes = fullMessage.frameMessage(false);
        // System.out.println(ArrayUtils.toHexString(bytes));

        Assert.assertEquals(bytes[21], Classification.TOP_SECRET.getCode());
    }
}
