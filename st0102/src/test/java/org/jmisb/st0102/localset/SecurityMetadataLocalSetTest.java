package org.jmisb.st0102.localset;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.st0102.Classification;
import org.jmisb.st0102.CountryCodingMethod;
import org.jmisb.st0102.ISecurityMetadataValue;
import org.jmisb.st0102.ItemDesignatorId;
import org.jmisb.st0102.LoggerChecks;
import org.jmisb.st0102.ObjectCountryCodeString;
import org.jmisb.st0102.ST0102Version;
import org.jmisb.st0102.SecurityMetadataKey;
import org.jmisb.st0102.SecurityMetadataString;
import org.jmisb.st0102.validity.SecurityMetadataLocalSetValidator;
import org.jmisb.st0102.validity.ValidationResults;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SecurityMetadataLocalSetTest extends LoggerChecks {
    private SecurityMetadataLocalSet localSet;
    private static final byte[] ItemDesignatorIdValue =
            new byte[] {
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x03,
                (byte) 0x04,
                (byte) 0x05,
                (byte) 0x06,
                (byte) 0x07,
                (byte) 0x08,
                (byte) 0x09,
                (byte) 0x0A,
                (byte) 0x0B,
                (byte) 0x0C,
                (byte) 0x0D,
                (byte) 0x0E,
                (byte) 0x0F
            };

    public SecurityMetadataLocalSetTest() {
        super(SecurityMetadataLocalSet.class);
    }

    @BeforeTest
    public void createSet() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//US"));

        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(12));

        localSet = new SecurityMetadataLocalSet(values);
    }

    @Test
    public void testUniversalLabel() {
        // Check that the correct universal label is applied
        Assert.assertEquals(
                localSet.getUniversalLabel(), SecurityMetadataLocalSet.SecurityMetadataLocalSetUl);
    }

    @Test
    public void testDisplayHeader() {
        Assert.assertEquals(localSet.displayHeader(), "ST 0102 (local)");
    }

    @Test
    public void testFrameFull() throws KlvParseException {
        // Frame a full message
        byte[] bytes = localSet.frameMessage(false);

        // System.out.println(ArrayUtils.toHexString(bytes));

        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0e,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x1f,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x0d,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x2f,
                    (byte) 0x2f,
                    (byte) 0x55,
                    (byte) 0x53,
                    (byte) 0x0c,
                    (byte) 0x01,
                    (byte) 0x0d,
                    (byte) 0x0d,
                    (byte) 0x0a,
                    (byte) 0x00,
                    (byte) 0x55,
                    (byte) 0x00,
                    (byte) 0x53,
                    (byte) 0x00,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x43,
                    (byte) 0x00,
                    (byte) 0x41,
                    (byte) 0x16,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x0c
                };
        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, 0, 16),
                SecurityMetadataLocalSet.SecurityMetadataLocalSetUl.getBytes());

        Assert.assertEquals(bytes, expectedBytes);
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(bytes, true);
        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.SecurityClassification));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.SecurityClassification).getDisplayableValue(),
                "UNCLASSIFIED");
        Assert.assertEquals(
                localSet.getField((IKlvKey) SecurityMetadataKey.SecurityClassification)
                        .getDisplayableValue(),
                "UNCLASSIFIED");
        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.Version));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.Version).getDisplayableValue(), "12");

        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.CcCodingMethod));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.CcCodingMethod).getDisplayableValue(),
                "GENC_TWO_LETTER");

        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.ClassifyingCountry));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.ClassifyingCountry).getDisplayableValue(),
                "//US");

        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.OcCodingMethod));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.OcCodingMethod).getDisplayableValue(),
                "GENC_TWO_LETTER");

        Assert.assertNotNull(localSet.getField(SecurityMetadataKey.ObjectCountryCodes));
        Assert.assertEquals(
                localSet.getField(SecurityMetadataKey.ObjectCountryCodes).getDisplayableValue(),
                "US;CA");
    }

    @Test
    public void testFrameNested() {
        // Frame a nested message
        byte[] bytes = localSet.frameMessage(true);

        // System.out.println(ArrayUtils.toHexString(bytes));

        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x0d,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x2f,
                    (byte) 0x2f,
                    (byte) 0x55,
                    (byte) 0x53,
                    (byte) 0x0c,
                    (byte) 0x01,
                    (byte) 0x0d,
                    (byte) 0x0d,
                    (byte) 0x0a,
                    (byte) 0x00,
                    (byte) 0x55,
                    (byte) 0x00,
                    (byte) 0x53,
                    (byte) 0x00,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x43,
                    (byte) 0x00,
                    (byte) 0x41,
                    (byte) 0x16,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x0c
                };
        Assert.assertEquals(bytes, expectedBytes);
    }

    @Test
    public void testValidity() {
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        Assert.assertTrue(results.isConformant());
        Assert.assertEquals(results.getNonConformances().size(), 0);
    }

    @Test
    public void testMinimumFromBuilder() {
        // Create a message equivalent to localSet
        SecurityMetadataLocalSet builderSet =
                new SecurityMetadataLocalSet.Builder(Classification.UNCLASSIFIED)
                        .classifyingCountry("//US")
                        .objectCountryCodes("US;CA")
                        .build();

        byte[] bytes1 = localSet.frameMessage(false);
        byte[] bytes2 = builderSet.frameMessage(false);
        Assert.assertEquals(bytes1, bytes2);

        bytes1 = localSet.frameMessage(true);
        bytes2 = builderSet.frameMessage(true);
        Assert.assertEquals(bytes1, bytes2);

        ValidationResults validationResults =
                SecurityMetadataLocalSetValidator.checkValidity(builderSet);
        Assert.assertTrue(validationResults.isConformant());
        Assert.assertEquals(validationResults.getNonConformances().size(), 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingReqField1() {
        // Missing a required field - Object Country Codes
        new SecurityMetadataLocalSet.Builder(Classification.UNCLASSIFIED)
                .classifyingCountry("//US")
                .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingReqField2() {
        // Missing a required field - Classifying Country
        new SecurityMetadataLocalSet.Builder(Classification.UNCLASSIFIED)
                .objectCountryCodes("US;CA")
                .build();
    }

    @Test
    public void testFullFromBuilder() {
        // Create a message with all fields populated
        SecurityMetadataLocalSet fullMessage =
                new SecurityMetadataLocalSet.Builder(Classification.TOP_SECRET)
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
        Assert.assertEquals(
                fullMessage.getField(SecurityMetadataKey.Caveats).getBytes(),
                "Caveat emptor!".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.DeclassificationDate));
        Assert.assertEquals(
                fullMessage.getField(SecurityMetadataKey.DeclassificationDate).getBytes(),
                "20281031".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate));
        Assert.assertEquals(
                fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate).getBytes(),
                "2010-12-25".getBytes());

        byte[] bytes = fullMessage.frameMessage(false);
        // System.out.println(ArrayUtils.toHexString(bytes));

        Assert.assertEquals(bytes[20], Classification.TOP_SECRET.getCode());

        ValidationResults validationResults =
                SecurityMetadataLocalSetValidator.checkValidity(fullMessage);
        Assert.assertTrue(validationResults.isConformant());
        Assert.assertEquals(validationResults.getNonConformances().size(), 0);
    }

    @Test
    public void testParseConstructorMultipleFields() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    1, 1, 1, 2, 1, 1, 3, 4, 47, 47, 67, 65, 4, 0, 5, 0, 6, 2, 67, 65, 21, 16, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 2, 0, 5
                };
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(
                securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId));
    }

    @Test
    public void testParseConstructor1() throws KlvParseException {
        byte[] bytes = new byte[] {1, 1, 1};
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(
                securityMetadataLocalSet.getField(SecurityMetadataKey.SecurityClassification));
        ISecurityMetadataValue val =
                securityMetadataLocalSet.getField(SecurityMetadataKey.SecurityClassification);
        Assert.assertTrue(val instanceof ClassificationLocal);
        ClassificationLocal clas = (ClassificationLocal) val;
        Assert.assertEquals(clas.getClassification(), Classification.UNCLASSIFIED);
    }

    @Test
    public void testParseConstructor21() throws KlvParseException {
        byte[] bytes = new byte[] {21, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(
                securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId));
        ISecurityMetadataValue val =
                securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId);
        Assert.assertTrue(val instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) val;
        Assert.assertEquals(id.getItemDesignatorId(), ItemDesignatorIdValue);
    }

    @Test
    public void testUnknownSecurityTag() throws KlvParseException {
        byte[] bytes = new byte[] {88, 1, 1};
        verifyNoLoggerMessages();
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(bytes, false);
        verifySingleLoggerMessage("Unknown Security Metadata tag: 88");
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(0, securityMetadataLocalSet.getKeys().size());
    }

    @Test
    public void testMixedKnownAndUnknownSecurityTags() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    1, 1, 1, 2, 1, 1, 88, 1, 1, 3, 4, 47, 47, 67, 65, 4, 0, 5, 0, 6, 2, 67, 65, 22,
                    2, 0, 5
                };
        verifyNoLoggerMessages();
        SecurityMetadataLocalSet securityMetadataLocalSet =
                new SecurityMetadataLocalSet(bytes, false);
        verifySingleLoggerMessage("Unknown Security Metadata tag: 88");
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(7, securityMetadataLocalSet.getKeys().size());
    }
}
