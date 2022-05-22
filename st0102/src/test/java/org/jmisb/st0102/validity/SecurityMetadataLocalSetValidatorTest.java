package org.jmisb.st0102.validity;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.st0102.Classification;
import org.jmisb.st0102.CountryCodingMethod;
import org.jmisb.st0102.ISecurityMetadataValue;
import org.jmisb.st0102.ObjectCountryCodeString;
import org.jmisb.st0102.ST0102Version;
import org.jmisb.st0102.SecurityMetadataConstants;
import org.jmisb.st0102.SecurityMetadataKey;
import org.jmisb.st0102.SecurityMetadataString;
import org.jmisb.st0102.localset.CcMethod;
import org.jmisb.st0102.localset.ClassificationLocal;
import org.jmisb.st0102.localset.OcMethod;
import org.jmisb.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.st0102.universalset.ClassificationUniversal;
import org.testng.annotations.Test;

/**
 * Unit tests for SecurityMetadataLocalSetValidator.
 *
 * <p>Note that some of testing is in the main LocalSet tests. This covers more of the edge cases.
 */
public class SecurityMetadataLocalSetValidatorTest {

    @Test
    public void testNoSecurityClassification() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the Security Classification metadata element.");
        assertEquals(validationResult.getTraceability(), "ST 0102.10-03");
    }

    @Test
    public void testSecurityClassificationWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationUniversal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the SecurityClassification is not of the correct type (expecting ClassificationLocal).");
    }

    @Test
    public void testNoClassifyingCountry() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the Classifying Country metadata element.");
        assertEquals(validationResult.getTraceability(), "ST 0102.10-05");
    }

    @Test
    public void testClassifyingCountryWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ClassifyingCountry, new ObjectCountryCodeString("AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the ClassifyingCountry is not of the correct type (expecting SecurityMetadataString).");
    }

    @Test
    public void testClassifyingCountryWrongStringLabel() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The SecurityMetadataString assigned to the ClassifyingCountry has the wrong label (expecting CLASSIFYING_COUNTRY).");
    }

    @Test
    public void testClassifyingCountryBadPrefix() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "AU"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Classifying country value did not start with //.");
        assertEquals(validationResult.getTraceability(), "ST 0102.12 Para 6.1.3");
    }

    @Test
    public void testNoClassifyingCountryCodingMethod() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the Classifying Country and Releasing Instructions Country Coding Method metadata element.");
        assertEquals(validationResult.getTraceability(), "ST 0102.10-04");
    }

    @Test
    public void testClassifyingCountryCodingMethodWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the CcCodingMethod is not of the correct type (expecting CcMethod).");
    }

    @Test
    public void testClassifyingCountryCodingMethodInvalidValue() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.OMITTED_VALUE));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the CcCodingMethod is not one of the allowed values.");
    }

    @Test
    public void testNoVersion() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the Security Metadata Version metadata element (required on Version 4 and later).");
        assertEquals(validationResult.getTraceability(), "ST 0102.10-56");
    }

    @Test
    public void testInvalidVersionHigh() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(SecurityMetadataKey.Version, new ST0102Version(99));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the ST0102Version is not one of the expected values [4-12].");
    }

    @Test
    public void testInvalidVersionLow() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(SecurityMetadataKey.Version, new ST0102Version(1));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the ST0102Version is not one of the expected values [4-12].");
    }

    @Test
    public void testInvalidVersionType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AS"));
        values.put(
                SecurityMetadataKey.Version,
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "ABC"));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the Version is not of the correct type (expecting ST0102Version).");
    }

    @Test
    public void testCaveatsBadType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Caveats, new OcMethod(CountryCodingMethod.C1059_THREE_LETTER));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to Caveats is not of the correct type (expecting SecurityMetadataString).");
    }

    @Test
    public void testCaveatsBadValueLabel() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.Caveats,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFICATION_COMMENTS, "ABC"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The SecurityMetadataString assigned to Caveats has the wrong label (expecting Caveats).");
    }

    @Test
    public void testCaveatsGood() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Caveats,
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "ABC"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertTrue(results.isConformant());
        assertEquals(results.getNonConformances().size(), 0);
    }

    @Test
    public void testReleaseInstructionsGood() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.ReleasingInstructions,
                new SecurityMetadataString(SecurityMetadataString.RELEASING_INSTRUCTIONS, "UK US"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertTrue(results.isConformant());
        assertEquals(results.getNonConformances().size(), 0);
    }

    @Test
    public void testReleaseInstructionsWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.ReleasingInstructions,
                new OcMethod(CountryCodingMethod.C1059_THREE_LETTER));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to ReleasingInstructions is not of the correct type (expecting SecurityMetadataString).");
    }

    @Test
    public void testReleaseInstructionsWrongValueLabel() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.ReleasingInstructions,
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "UK US"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The SecurityMetadataString assigned to ReleasingInstructions has the wrong label (expecting Releasing Instructions).");
    }

    @Test
    public void testReleaseInstructionsUnderscoreSeparator() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.ReleasingInstructions,
                new SecurityMetadataString(SecurityMetadataString.RELEASING_INSTRUCTIONS, "UK_US"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The SecurityMetadataString assigned to ReleasingInstructions has invalid separators.");
    }

    @Test
    public void testReleaseInstructionsSemicolonSeparator() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.ReleasingInstructions,
                new SecurityMetadataString(SecurityMetadataString.RELEASING_INSTRUCTIONS, "UK;US"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The SecurityMetadataString assigned to ReleasingInstructions has invalid separators.");
    }

    @Test
    public void testObjectCountryCodingMethod() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.C1059_THREE_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertTrue(results.isConformant());
        assertEquals(results.getNonConformances().size(), 0);
    }

    @Test
    public void testNoObjectCountryCodingMethod() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(validationResult.getTraceability(), "ST 0102 6.1.12");
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the expected Object Country Coding Method (required from Version 5 onwards).");
    }

    @Test
    public void testObjectCountryCodingMethodWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new CcMethod(CountryCodingMethod.C1059_THREE_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the OcCodingMethod is not of the correct type (expecting OcMethod).");
    }

    @Test
    public void testObjectCountryCodingMethodBadValue() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.OMITTED_VALUE));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the OcCodingMethod is not one of the allowed values.");
    }

    @Test
    public void testNoObjectCountryCodes() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(validationResult.getTraceability(), "ST 0102.10-23");
        assertEquals(
                validationResult.getDescription(),
                "Motion Imagery Data did not contain the Object Country Code metadata element.");
    }

    @Test
    public void testObjectCountryCodesWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ObjectCountryCodes,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "AU;NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "The value assigned to the ObjectCountryCodes is not of the correct type (expecting ObjectCountryCodeString).");
    }

    @Test
    public void testObjectCountryCodesBadValueSpace() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Object Country Codes contained space or comma separators (should be semi-colon).");
    }

    @Test
    public void testObjectCountryCodesBadValueComma() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU,NZ"));
        values.put(
                SecurityMetadataKey.Version,
                new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        SecurityMetadataLocalSet localSet = new SecurityMetadataLocalSet(values);
        ValidationResults results = SecurityMetadataLocalSetValidator.checkValidity(localSet);
        assertFalse(results.isConformant());
        assertEquals(results.getNonConformances().size(), 1);
        ValidationResult validationResult = results.getNonConformances().get(0);
        assertEquals(
                validationResult.getDescription(),
                "Object Country Codes contained space or comma separators (should be semi-colon).");
    }
}
