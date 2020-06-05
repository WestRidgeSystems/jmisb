package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st0102.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;

public class SecurityMetadataLocalSetTest extends LoggerChecks
{
    private SecurityMetadataLocalSet localSet;
    private static final byte[] ItemDesignatorIdValue = new byte[]{(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08, (byte)0x09, (byte)0x0A, (byte)0x0B, (byte)0x0C, (byte)0x0D, (byte)0x0E, (byte)0x0F};

    public SecurityMetadataLocalSetTest()
    {
        super(SecurityMetadataLocalSet.class);
    }

    @BeforeTest
    public void createSet()
    {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.SecurityClassification, new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(SecurityMetadataKey.CcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ClassifyingCountry, new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//US"));

        values.put(SecurityMetadataKey.OcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US;CA"));

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
    public void testDisplayHeader()
    {
        Assert.assertEquals(localSet.displayHeader(), "ST 0102 (local)");
    }

    @Test
    public void testFrameFull()
    {
        // Frame a full message
        byte[] bytes = localSet.frameMessage(false);

        // System.out.println(ArrayUtils.toHexString(bytes));

        byte[] expectedBytes = new byte[]{
            (byte)0x06, (byte)0x0e, (byte)0x2b, (byte)0x34, (byte)0x02, (byte)0x03, (byte)0x01, (byte)0x01, (byte)0x0e, (byte)0x01, (byte)0x03, (byte)0x03, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x1f,
            (byte)0x01, (byte)0x01, (byte)0x01,
            (byte)0x02, (byte)0x01, (byte)0x0d,
            (byte)0x03, (byte)0x04, (byte)0x2f, (byte)0x2f, (byte)0x55, (byte)0x53,
            (byte)0x0c, (byte)0x01, (byte)0x0d,
            (byte)0x0d, (byte)0x0a, (byte)0x00, (byte)0x55, (byte)0x00, (byte)0x53, (byte)0x00, (byte)0x3b, (byte)0x00, (byte)0x43, (byte)0x00, (byte)0x41,
            (byte)0x16, (byte)0x02, (byte)0x00, (byte)0x0c};
        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(Arrays.copyOfRange(bytes, 0, 16), KlvConstants.SecurityMetadataLocalSetUl.getBytes());

        Assert.assertEquals(bytes, expectedBytes);
    }

    @Test
    public void testFrameNested()
    {
        // Frame a nested message
        byte[] bytes = localSet.frameMessage(true);

        // System.out.println(ArrayUtils.toHexString(bytes));

        byte[] expectedBytes = new byte[]{
            (byte)0x01, (byte)0x01, (byte)0x01,
            (byte)0x02, (byte)0x01, (byte)0x0d,
            (byte)0x03, (byte)0x04, (byte)0x2f, (byte)0x2f, (byte)0x55, (byte)0x53,
            (byte)0x0c, (byte)0x01, (byte)0x0d,
            (byte)0x0d, (byte)0x0a, (byte)0x00, (byte)0x55, (byte)0x00, (byte)0x53, (byte)0x00, (byte)0x3b, (byte)0x00, (byte)0x43, (byte)0x00, (byte)0x41,
            (byte)0x16, (byte)0x02, (byte)0x00, (byte)0x0c};
        Assert.assertEquals(bytes, expectedBytes);
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
                .itemDesignatorId(ItemDesignatorIdValue)
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

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.ItemDesignatorId));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.ItemDesignatorId).getBytes(), ItemDesignatorIdValue);

        byte[] bytes = fullMessage.frameMessage(false);
        // System.out.println(ArrayUtils.toHexString(bytes));

        Assert.assertEquals(bytes[20], Classification.TOP_SECRET.getCode());
    }

    @Test
    public void testParseConstructorMultipleFields() throws KlvParseException {
        byte[] bytes = new byte[]{1, 1, 1, 2, 1, 1, 3, 4, 47, 47, 67, 65, 4, 0, 5, 0, 6, 2, 67, 65, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 2, 0, 5};
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId));
    }

    @Test
    public void testParseConstructor1() throws KlvParseException {
        byte[] bytes = new byte[]{1, 1, 1};
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(securityMetadataLocalSet.getField(SecurityMetadataKey.SecurityClassification));
        ISecurityMetadataValue val = securityMetadataLocalSet.getField(SecurityMetadataKey.SecurityClassification);
        Assert.assertTrue(val instanceof ClassificationLocal);
        ClassificationLocal clas = (ClassificationLocal)val;
        Assert.assertEquals(clas.getClassification(), Classification.UNCLASSIFIED);
    }

    @Test
    public void testParseConstructor21() throws KlvParseException {
        byte[] bytes = new byte[]{21, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId));
        ISecurityMetadataValue val = securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId);
        Assert.assertTrue(val instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId)val;
        Assert.assertEquals(id.getItemDesignatorId(), ItemDesignatorIdValue);
    }

    @Test
    public void testUnknownSecurityTag() throws KlvParseException
    {
        byte[] bytes = new byte[]{88, 1, 1};
        verifyNoLoggerMessages();
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        verifySingleLoggerMessage("Unknown Security Metadata tag: 88");
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(0, securityMetadataLocalSet.getKeys().size());
    }

    @Test
    public void testMixedKnownAndUnknownSecurityTags() throws KlvParseException
    {
        byte[] bytes = new byte[]{1, 1, 1, 2, 1, 1, 88, 1, 1, 3, 4, 47, 47, 67, 65, 4, 0, 5, 0, 6, 2, 67, 65, 22, 2, 0, 5};
        verifyNoLoggerMessages();
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        verifySingleLoggerMessage("Unknown Security Metadata tag: 88");
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(7, securityMetadataLocalSet.getKeys().size());
    }
}
