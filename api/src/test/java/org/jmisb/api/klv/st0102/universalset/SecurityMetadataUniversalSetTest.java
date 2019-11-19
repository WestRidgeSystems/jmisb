package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0102.*;
import org.jmisb.api.klv.st0102.Classification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecurityMetadataUniversalSetTest
{
    private SecurityMetadataUniversalSet universalSet;

    @BeforeTest
    public void createSet()
    {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.SecurityClassification, new ClassificationUniversal(Classification.UNCLASSIFIED));

        values.put(SecurityMetadataKey.CcCodingMethod, new SecurityMetadataString("ISO-3166 Two Letter"));
        values.put(SecurityMetadataKey.ClassifyingCountry, new SecurityMetadataString("//US"));

        values.put(SecurityMetadataKey.OcCodingMethod, new SecurityMetadataString("ISO-3166 Two Letter"));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new SecurityMetadataString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(12));

        universalSet = new SecurityMetadataUniversalSet(values);
    }

    @Test
    void testUniversalLabel()
    {
        // Check that the correct universal label is applied
        Assert.assertEquals(universalSet.getUniversalLabel(), KlvConstants.SecurityMetadataUniversalSetUl);
    }

    @Test
    public void testDisplayHeader()
    {
        Assert.assertEquals(universalSet.displayHeader(), "ST 0102 (universal)");
    }

    @Test
    public void testFrameFull()
    {
        // Frame a full message
        byte[] bytes = universalSet.frameMessage(false);

        // System.out.println(ArrayUtils.toHexString(bytes));

        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(Arrays.copyOfRange(bytes, 0, 16), KlvConstants.SecurityMetadataUniversalSetUl.getBytes());

        // Check that the length field was encoded correctly
        BerField lengthField = BerDecoder.decode(bytes, 16, false);
        Assert.assertEquals(bytes.length, 16 + lengthField.getLength() + lengthField.getValue());

        // Check that the first key corresponds to the security classification UL
        int offset = 16 + lengthField.getLength();
        Assert.assertEquals(Arrays.copyOfRange(bytes, offset, offset + 16), SecurityMetadataConstants.securityClassificationUl.getBytes());

        // Check that the value equals "UNCLASSIFIED//"
        offset += 16;
        lengthField = BerDecoder.decode(bytes, offset, false);
        offset += lengthField.getLength();
        Assert.assertEquals(Arrays.copyOfRange(bytes, offset, offset + lengthField.getValue()),
                "UNCLASSIFIED//".getBytes(StandardCharsets.US_ASCII));
    }

    @Test
    public void testFullFromBuilder()
    {
        // Create a message with all fields populated
        SecurityMetadataUniversalSet fullMessage = new SecurityMetadataUniversalSet.Builder(Classification.TOP_SECRET)
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
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.CcCodingMethod).getBytes(), "ISO-3166 Three Letter".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.Caveats));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.Caveats).getBytes(), "Caveat emptor!".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.DeclassificationDate));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.DeclassificationDate).getBytes(), "20281031".getBytes());

        Assert.assertNotNull(fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate));
        Assert.assertEquals(fullMessage.getField(SecurityMetadataKey.CcCodingMethodVersionDate).getBytes(), "2010-12-25".getBytes());

        byte[] bytes = fullMessage.frameMessage(false);
//        System.out.println(ArrayUtils.toHexString(bytes));

        String topSecret = "TOP SECRET//";
        int classificationOffset = 16 + 3 + 16 + 1;
        String actual = new String(
                Arrays.copyOfRange(bytes, classificationOffset, classificationOffset + topSecret.length()));
        Assert.assertEquals(actual, topSecret);
    }
}
