package org.jmisb.api.klv.st0102.universalset;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st0102.*;
import org.jmisb.api.klv.st0102.Classification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SecurityMetadataUniversalSetTest {
    private SecurityMetadataUniversalSet universalSet;

    @BeforeTest
    public void createSet() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationUniversal(Classification.UNCLASSIFIED));

        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new SecurityMetadataString(
                        SecurityMetadataString.COUNTRY_CODING_METHOD, "ISO-3166 Two Letter"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//US"));

        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new SecurityMetadataString(
                        SecurityMetadataString.OBJECT_COUNTRY_CODING_METHOD,
                        "ISO-3166 Two Letter"));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(12));

        universalSet = new SecurityMetadataUniversalSet(values);
    }

    @Test
    void testUniversalLabel() {
        // Check that the correct universal label is applied
        Assert.assertEquals(
                universalSet.getUniversalLabel(), KlvConstants.SecurityMetadataUniversalSetUl);
    }

    @Test
    public void testDisplayHeader() {
        Assert.assertEquals(universalSet.displayHeader(), "ST 0102 (universal)");
    }

    @Test
    public void testFrameFull() {
        // Frame a full message
        byte[] bytes = universalSet.frameMessage(false);

        // System.out.println(ArrayUtils.toHexString(bytes));

        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, 0, 16),
                KlvConstants.SecurityMetadataUniversalSetUl.getBytes());

        // Check that the length field was encoded correctly
        BerField lengthField = BerDecoder.decode(bytes, 16, false);
        Assert.assertEquals(bytes.length, 16 + lengthField.getLength() + lengthField.getValue());

        // Check that the first key corresponds to the security classification UL
        int offset = 16 + lengthField.getLength();
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + 16),
                SecurityMetadataConstants.securityClassificationUl.getBytes());

        // Check that the value equals "UNCLASSIFIED//"
        offset += 16;
        lengthField = BerDecoder.decode(bytes, offset, false);
        offset += lengthField.getLength();
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + lengthField.getValue()),
                "UNCLASSIFIED//".getBytes(StandardCharsets.US_ASCII));
    }

    @Test
    public void testFrameFullNested() {
        // Frame a full message
        byte[] bytes = universalSet.frameMessage(true);

        // Check that the first key corresponds to the security classification UL
        int offset = 0;
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + 16),
                SecurityMetadataConstants.securityClassificationUl.getBytes());

        // Check that the value equals "UNCLASSIFIED//"
        offset += 16;
        BerField lengthField = BerDecoder.decode(bytes, offset, false);
        offset += lengthField.getLength();
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + lengthField.getValue()),
                "UNCLASSIFIED//".getBytes(StandardCharsets.US_ASCII));
    }

    @Test
    public void testFullFromBuilder() {
        // Create a message with all fields populated
        SecurityMetadataUniversalSet fullMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.TOP_SECRET)
                        .ccMethod("ISO-3166 Three Letter")
                        .classifyingCountry("//USA")
                        .sciShiInfo("Hello, World!")
                        .caveats("Caveat emptor!")
                        .releasingInstructions("Not for release")
                        .classifiedBy("Yours Truly")
                        .derivedFrom("Some such thing")
                        .classificationReason("Just because")
                        .declassificationDate(LocalDate.of(2028, 10, 31))
                        .markingSystem("None")
                        .ocMethod("ISO-3166 Two Letter")
                        .objectCountryCodes("US;CA")
                        .classificationComments("No comment")
                        .version(10)
                        .ccmDate(LocalDate.of(2010, 12, 25))
                        .ocmDate(LocalDate.of(1998, 5, 27))
                        .build();

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.CcCodingMethod));
        Assert.assertEquals(
                fullMessage.getField(SecurityMetadataKey.CcCodingMethod).getBytes(),
                "ISO-3166 Three Letter".getBytes());

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
        //        System.out.println(ArrayUtils.toHexString(bytes));

        String topSecret = "TOP SECRET//";
        int classificationOffset = 16 + 3 + 16 + 1;
        String actual =
                new String(
                        Arrays.copyOfRange(
                                bytes,
                                classificationOffset,
                                classificationOffset + topSecret.length()));
        Assert.assertEquals(actual, topSecret);
    }

    @Test
    public void testMinimalFromBuilder() {
        // Create a message with minimum fields populated
        SecurityMetadataUniversalSet minimalMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                        .ccMethod("ISO-3166 Three Letter")
                        .classifyingCountry("//AUS")
                        .ocMethod("ISO-3166 Two Letter")
                        .objectCountryCodes("AU")
                        .version(12)
                        .build();
        verifyMinimalUniversalSet(minimalMessage, "ISO-3166 Three Letter", "ISO-3166 Two Letter");
    }

    @Test
    public void testMinimalFromBuilderDefaultVersion() {
        SecurityMetadataUniversalSet minimalMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                        .ccMethod("ISO-3166 Three Letter")
                        .classifyingCountry("//AUS")
                        .ocMethod("ISO-3166 Two Letter")
                        .objectCountryCodes("AU")
                        .build();
        verifyMinimalUniversalSet(minimalMessage, "ISO-3166 Three Letter", "ISO-3166 Two Letter");
    }

    @Test
    public void testMinimalFromBuilderDefaultCCMethod() {
        SecurityMetadataUniversalSet minimalMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                        .classifyingCountry("//AUS")
                        .ocMethod("ISO-3166 Two Letter")
                        .objectCountryCodes("AU")
                        .version(12)
                        .build();
        verifyMinimalUniversalSet(minimalMessage, "GENC Two Letter", "ISO-3166 Two Letter");
    }

    @Test
    public void testMinimalFromBuilderDefaultOCMethod() {
        SecurityMetadataUniversalSet minimalMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                        .ccMethod("ISO-3166 Three Letter")
                        .classifyingCountry("//AUS")
                        .objectCountryCodes("AU")
                        .version(12)
                        .build();
        verifyMinimalUniversalSet(minimalMessage, "ISO-3166 Three Letter", "GENC Two Letter");
    }

    @Test
    public void testMinimalFromBuilderWithDefaults() {
        // This isn't really valid, because the default is two letter, but its a unit test.
        SecurityMetadataUniversalSet minimalMessage =
                new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                        .classifyingCountry("//AUS")
                        .objectCountryCodes("AU")
                        .build();
        verifyMinimalUniversalSet(minimalMessage, "GENC Two Letter", "GENC Two Letter");
    }

    public void verifyMinimalUniversalSet(
            SecurityMetadataUniversalSet minimalMessage, String ccMethod, String ocMethod) {
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.SecurityClassification));
        Assert.assertEquals(
                minimalMessage
                        .getField(SecurityMetadataKey.SecurityClassification)
                        .getDisplayableValue(),
                "UNCLASSIFIED//");
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.CcCodingMethod));
        Assert.assertEquals(
                minimalMessage.getField(SecurityMetadataKey.CcCodingMethod).getDisplayableValue(),
                ccMethod);
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.ClassifyingCountry));
        Assert.assertEquals(
                minimalMessage
                        .getField(SecurityMetadataKey.ClassifyingCountry)
                        .getDisplayableValue(),
                "//AUS");
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.OcCodingMethod));
        Assert.assertEquals(
                minimalMessage.getField(SecurityMetadataKey.OcCodingMethod).getDisplayableValue(),
                ocMethod);
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.ObjectCountryCodes));
        Assert.assertEquals(
                minimalMessage
                        .getField(SecurityMetadataKey.ObjectCountryCodes)
                        .getDisplayableValue(),
                "AU");
        Assert.assertNotNull(minimalMessage.getField(SecurityMetadataKey.Version));
        Assert.assertEquals(
                minimalMessage.getField(SecurityMetadataKey.Version).getDisplayableValue(), "12");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingReqField1() {
        // Missing a required field - Object Country Codes
        new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                .ccMethod("ISO-3166 Three Letter")
                .classifyingCountry("//AUS")
                .ocMethod("ISO-3166 Two Letter")
                .version(12)
                .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingReqField2() {
        // Missing a required field - classifying country code
        new SecurityMetadataUniversalSet.Builder(Classification.UNCLASSIFIED)
                .ccMethod("ISO-3166 Three Letter")
                .ocMethod("ISO-3166 Two Letter")
                .objectCountryCodes("AU")
                .version(12)
                .build();
    }

    @Test
    public void parseBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x02, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x02, (byte) 0x08, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x6b, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x03, (byte) 0x02, (byte) 0x08, (byte) 0x02, (byte) 0x01, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x0e, (byte) 0x55, (byte) 0x4e, (byte) 0x43, (byte) 0x4c,
                            (byte) 0x41, (byte) 0x53,
                    (byte) 0x53, (byte) 0x49, (byte) 0x46, (byte) 0x49, (byte) 0x45, (byte) 0x44,
                            (byte) 0x2f, (byte) 0x2f,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                            (byte) 0x01, (byte) 0x03,
                    (byte) 0x07, (byte) 0x01, (byte) 0x20, (byte) 0x01, (byte) 0x02, (byte) 0x07,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x13, (byte) 0x49, (byte) 0x53, (byte) 0x4f, (byte) 0x2d, (byte) 0x33,
                            (byte) 0x31, (byte) 0x36,
                    (byte) 0x36, (byte) 0x20, (byte) 0x54, (byte) 0x77, (byte) 0x6f, (byte) 0x20,
                            (byte) 0x4c, (byte) 0x65,
                    (byte) 0x74, (byte) 0x74, (byte) 0x65, (byte) 0x72, (byte) 0x06, (byte) 0x0e,
                            (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01,
                            (byte) 0x20, (byte) 0x01,
                    (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x2f,
                            (byte) 0x2f, (byte) 0x55,
                    (byte) 0x53, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x0e, (byte) 0x01, (byte) 0x02, (byte) 0x05, (byte) 0x04,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x0C
                };
        SecurityMetadataUniversalSet universalSet = new SecurityMetadataUniversalSet(bytes);
        Assert.assertEquals(universalSet.displayHeader(), "ST 0102 (universal)");
        Assert.assertEquals(
                universalSet.getUniversalLabel(), KlvConstants.SecurityMetadataUniversalSetUl);
        Assert.assertEquals(universalSet.getKeys().size(), 4);
        Assert.assertTrue(
                universalSet.getKeys().contains(SecurityMetadataKey.SecurityClassification));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.SecurityClassification).getDisplayName(),
                "Classification");
        Assert.assertEquals(
                universalSet
                        .getField(SecurityMetadataKey.SecurityClassification)
                        .getDisplayableValue(),
                "UNCLASSIFIED//");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.CcCodingMethod));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.CcCodingMethod).getDisplayName(),
                "Country Coding Method");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.CcCodingMethod).getDisplayableValue(),
                "ISO-3166 Two Letter");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.ClassifyingCountry));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.ClassifyingCountry).getDisplayName(),
                "Classifying Country");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.ClassifyingCountry).getDisplayableValue(),
                "//US");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.Version));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.Version).getDisplayName(),
                "ST0102 Version");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.Version).getDisplayableValue(), "12");
    }
}
