package org.jmisb.api.klv.st0102.universalset;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import org.jmisb.api.klv.st0102.CcmDate;
import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.DeclassificationDate;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ItemDesignatorId;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.jmisb.api.klv.st0102.OcmDate;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

public class UniversalSetFactoryTest
{
    @Test
    public void testCreate()
    {
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.SecurityClassification,
                "UNCLASSIFIED//".getBytes());
        Assert.assertTrue(value instanceof ClassificationUniversal);
        ClassificationUniversal classification = (ClassificationUniversal) value;
        Assert.assertEquals(classification.getClassification(), Classification.UNCLASSIFIED);

        String ccMethod = "GENC Two Letter";
        value = UniversalSetFactory.createValue(SecurityMetadataKey.CcCodingMethod,
                ccMethod.getBytes());
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString string = (SecurityMetadataString) value;
        Assert.assertEquals(string.getValue(), ccMethod);
    }

    @Test
    public void testCreateItemDesignatorId() {
        byte[] itemDesignatorId = new byte[]{(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ItemDesignatorId, itemDesignatorId);
        Assert.assertTrue(value instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) value;
        Assert.assertEquals(id.getItemDesignatorId(), itemDesignatorId);
    }

    @Test
    public void testSciShiCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.SciShiInfo, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Security-SCI/SHI Information");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testDerivedFromCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.DerivedFrom, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Derived From");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testCaveatsCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.Caveats, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Caveats");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testReleasingInstructionsCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ReleasingInstructions, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Releasing Instructions");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testClassificationReasonCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ClassificationReason, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Classification Reason");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testClassificationCommentsCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ClassificationComments, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Classification Comments");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testMarkingSystemCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.MarkingSystem, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Marking System");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testClassifiedByCreate()
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f, (byte)0x2c, (byte)0x20, (byte)0x57, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ClassifiedBy, bytes);
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString s = (SecurityMetadataString) value;
        Assert.assertEquals(s.getDisplayName(), "Classified By");
        Assert.assertEquals(s.getDisplayableValue(), "Hello, World!");
        Assert.assertEquals(s.getValue(), "Hello, World!");
    }

    @Test
    public void testOcMethodCreate()
    {
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.OcCodingMethod, "GENC Two Letter".getBytes(StandardCharsets.UTF_8));
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString string = (SecurityMetadataString) value;
        Assert.assertEquals(string.getValue(), "GENC Two Letter");
        Assert.assertEquals(string.getDisplayableValue(), "GENC Two Letter");
        Assert.assertEquals(string.getDisplayName(), "Object Country Coding Method");
    }

    @Test
    public void testCcMethodDateCreate()
    {
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.CcCodingMethodVersionDate, new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertNotNull(value);
        Assert.assertTrue(value instanceof CcmDate);
        CcmDate ccmDate = (CcmDate) value;
        assertEquals(ccmDate.getValue(), LocalDate.of(2020, Month.APRIL, 25));
        byte[] bytes = ccmDate.getBytes();
        assertEquals(bytes, new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "2020-04-25");
    }

    @Test
    public void testOcmDateCreate()
    {
        byte[] bytes = new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.OcCodingMethodVersionDate, bytes);
        Assert.assertTrue(value instanceof OcmDate);
        OcmDate ocmDate = (OcmDate) value;
        assertNotNull(ocmDate);
        assertEquals(ocmDate.getValue(), LocalDate.of(2020, Month.JANUARY, 25));
        byte[] bytesOut = ocmDate.getBytes();
        assertEquals(bytesOut, new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ocmDate.getDisplayableValue(), "2020-01-25");
    }

    @Test
    public void testObjectCountryCodeCreate()
    {
        byte[] bytes = new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ObjectCountryCodes, bytes);
        Assert.assertTrue(value instanceof ObjectCountryCodeString);
        ObjectCountryCodeString objectCountryCode = (ObjectCountryCodeString) value;
        Assert.assertEquals(objectCountryCode.getValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(objectCountryCode.getBytes(), new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41});
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        byte[] bytes = new byte[]{0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.DeclassificationDate, bytes);
        Assert.assertTrue(value instanceof DeclassificationDate);
        DeclassificationDate declassificationDate = (DeclassificationDate) value;
        assertNotNull(declassificationDate);
        assertEquals(declassificationDate.getValue(), LocalDate.of(2051, Month.MAY, 5));
        assertEquals(declassificationDate.getDisplayableValue(), "20510505");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void noSuchTag() {
        UniversalSetFactory.createValue(SecurityMetadataKey.Undefined, new byte[]{0x00});
    }
}
