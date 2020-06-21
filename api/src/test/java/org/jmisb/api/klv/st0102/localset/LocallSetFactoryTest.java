package org.jmisb.api.klv.st0102.localset;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import org.jmisb.api.klv.st0102.CcmDate;
import org.jmisb.api.klv.st0102.DeclassificationDate;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ItemDesignatorId;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.jmisb.api.klv.st0102.OcmDate;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for LocalSetFactory.
 *
 * <p>Note: you shouldn't use these unit tests for what a valid field value is within the US way of
 * doing security markings.
 */
public class LocallSetFactoryTest {

    @Test
    public void testItemDesignatorIdCreate() {
        byte[] itemDesignatorId =
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
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.ItemDesignatorId, itemDesignatorId);
        Assert.assertTrue(value instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) value;
        Assert.assertEquals(id.getBytes(), itemDesignatorId);
        Assert.assertEquals(id.getItemDesignatorId(), itemDesignatorId);
    }

    @Test
    public void testObjectCountryCodeCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x55,
                    (byte) 0x00,
                    (byte) 0x53,
                    (byte) 0x00,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x43,
                    (byte) 0x00,
                    (byte) 0x41
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.ObjectCountryCodes, bytes);
        Assert.assertTrue(value instanceof ObjectCountryCodeString);
        ObjectCountryCodeString objectCountryCode = (ObjectCountryCodeString) value;
        Assert.assertEquals(objectCountryCode.getBytes(), bytes);
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
    }

    @Test
    public void testClassifiedByCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x53,
                    (byte) 0x43,
                    (byte) 0x47,
                    (byte) 0x44,
                    (byte) 0x20,
                    (byte) 0x66,
                    (byte) 0x6f,
                    (byte) 0x72,
                    (byte) 0x20,
                    (byte) 0x53,
                    (byte) 0x79,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x65,
                    (byte) 0x6d,
                    (byte) 0x20,
                    (byte) 0x58
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.ClassifiedBy, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString classifiedBy = (SecurityMetadataString) value;
        Assert.assertEquals(classifiedBy.getBytes(), bytes);
        Assert.assertEquals(classifiedBy.getDisplayName(), "Classified By");
        Assert.assertEquals(classifiedBy.getDisplayableValue(), "SCGD for System X");
    }

    @Test
    public void testDerivedFromCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x4d,
                    (byte) 0x75,
                    (byte) 0x6c,
                    (byte) 0x74,
                    (byte) 0x69,
                    (byte) 0x70,
                    (byte) 0x6c,
                    (byte) 0x65,
                    (byte) 0x20,
                    (byte) 0x53,
                    (byte) 0x6f,
                    (byte) 0x75,
                    (byte) 0x72,
                    (byte) 0x63,
                    (byte) 0x65,
                    (byte) 0x73
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.DerivedFrom, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString derivedFrom = (SecurityMetadataString) value;
        Assert.assertEquals(derivedFrom.getBytes(), bytes);
        Assert.assertEquals(derivedFrom.getDisplayName(), "Derived From");
        Assert.assertEquals(derivedFrom.getDisplayableValue(), "Multiple Sources");
        Assert.assertEquals(derivedFrom.getValue(), "Multiple Sources");
    }

    @Test
    public void testClassificationReasonCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x4d,
                    (byte) 0x75,
                    (byte) 0x6c,
                    (byte) 0x74,
                    (byte) 0x69,
                    (byte) 0x70,
                    (byte) 0x6c,
                    (byte) 0x65,
                    (byte) 0x20,
                    (byte) 0x53,
                    (byte) 0x6f,
                    (byte) 0x75,
                    (byte) 0x72,
                    (byte) 0x63,
                    (byte) 0x65,
                    (byte) 0x73
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.ClassificationReason, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString classificationReason = (SecurityMetadataString) value;
        Assert.assertEquals(classificationReason.getBytes(), bytes);
        Assert.assertEquals(classificationReason.getDisplayName(), "Classification Reason");
        Assert.assertEquals(classificationReason.getDisplayableValue(), "Multiple Sources");
        Assert.assertEquals(classificationReason.getValue(), "Multiple Sources");
    }

    @Test
    public void testMarkingSystemCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x41,
                    (byte) 0x75,
                    (byte) 0x73,
                    (byte) 0x74,
                    (byte) 0x72,
                    (byte) 0x61,
                    (byte) 0x6c,
                    (byte) 0x69,
                    (byte) 0x61,
                    (byte) 0x6e,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x53,
                    (byte) 0x50,
                    (byte) 0x46
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.MarkingSystem, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString markingSystem = (SecurityMetadataString) value;
        Assert.assertEquals(markingSystem.getBytes(), bytes);
        Assert.assertEquals(markingSystem.getDisplayName(), "Marking System");
        Assert.assertEquals(markingSystem.getDisplayableValue(), "Australian PSPF");
        Assert.assertEquals(markingSystem.getValue(), "Australian PSPF");
    }

    @Test
    public void testClassificationCommentsCreate() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x42,
                    (byte) 0x6f,
                    (byte) 0x62,
                    (byte) 0x21,
                    (byte) 0x20,
                    (byte) 0x57,
                    (byte) 0x65,
                    (byte) 0x20,
                    (byte) 0x6c,
                    (byte) 0x69,
                    (byte) 0x6b,
                    (byte) 0x65,
                    (byte) 0x20,
                    (byte) 0x42,
                    (byte) 0x6f,
                    (byte) 0x62,
                    (byte) 0x2e,
                    (byte) 0x2e,
                    (byte) 0x2e
                };
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.ClassificationComments, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString classificationComments = (SecurityMetadataString) value;
        Assert.assertEquals(classificationComments.getBytes(), bytes);
        Assert.assertEquals(classificationComments.getDisplayName(), "Classification Comments");
        Assert.assertEquals(classificationComments.getDisplayableValue(), "Bob! We like Bob...");
        Assert.assertEquals(classificationComments.getValue(), "Bob! We like Bob...");
    }

    @Test
    public void testDeclassificationDateCreate() {
        byte[] bytes = new byte[] {0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35};
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.DeclassificationDate, bytes);
        Assert.assertTrue(value instanceof DeclassificationDate);
        DeclassificationDate declassificationDate = (DeclassificationDate) value;
        assertNotNull(declassificationDate);
        assertEquals(declassificationDate.getValue(), LocalDate.of(2051, Month.MAY, 5));
        byte[] bytesOut = declassificationDate.getBytes();
        assertEquals(bytesOut, new byte[] {0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertEquals(declassificationDate.getDisplayName(), "Declassification Date");
        assertEquals(declassificationDate.getDisplayableValue(), "20510505");
    }

    @Test
    public void testCcmDateCreate() {
        byte[] bytes = new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35};
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.CcCodingMethodVersionDate, bytes);
        Assert.assertTrue(value instanceof CcmDate);
        CcmDate ccmDate = (CcmDate) value;
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), LocalDate.of(2020, Month.APRIL, 25));
        byte[] bytesOut = ccmDate.getBytes();
        assertEquals(
                bytesOut, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayName(), "Country Coding Method Version Date");
        assertEquals(ccmDate.getDisplayableValue(), "2020-04-25");
    }

    @Test
    public void testOcmDateCreate() {
        byte[] bytes = new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35};
        ISecurityMetadataValue value =
                LocalSetFactory.createValue(SecurityMetadataKey.OcCodingMethodVersionDate, bytes);
        Assert.assertTrue(value instanceof OcmDate);
        OcmDate ocmDate = (OcmDate) value;
        assertNotNull(ocmDate);
        assertEquals(ocmDate.getValue(), LocalDate.of(2020, Month.JANUARY, 25));
        byte[] bytesOut = ocmDate.getBytes();
        assertEquals(
                bytesOut, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ocmDate.getDisplayableValue(), "2020-01-25");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void noSuchTag() {
        LocalSetFactory.createValue(SecurityMetadataKey.Undefined, new byte[] {0x00});
    }
}
