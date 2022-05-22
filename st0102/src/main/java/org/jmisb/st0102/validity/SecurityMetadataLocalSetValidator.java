package org.jmisb.st0102.validity;

import java.util.ArrayList;
import java.util.List;
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

/**
 * Validator for ST 0102 local sets.
 *
 * <p>This provides facility to check the content of the Security Metadata local set. Not all
 * requirements can be validated (e.g. it is possible to check that a required marking is present,
 * but not possible to say that the marking corresponds to the required classification for the
 * content).
 *
 * <p>The intent of this is to help identify potential problems when generating (or amending) ST
 * 0102 Security Metadata. It has limited applicability to testing metadata on video received from
 * other sources. The outputs are not intended to be shown to typical users - it would usually be
 * more appropriate to log rather than put into a user interface.
 */
public class SecurityMetadataLocalSetValidator {

    private SecurityMetadataLocalSetValidator() {};

    /**
     * Check the validity of a local set.
     *
     * @param localSet the local set to validate
     * @return the validation results
     */
    public static ValidationResults checkValidity(SecurityMetadataLocalSet localSet) {
        ValidationResults result = new ValidationResults();
        result.addResults(validateSecurityClassification(localSet));
        result.addResults(
                validateClassifyingCountryAndReleasingInstructionsCountryCodingMethod(localSet));
        result.addResults(validateClassifyingCountry(localSet));
        // There are potentially SCI-related requirements from ST 0102 Section 6.1.4 that could be
        // added in here.
        result.addResults(validateCaveats(localSet));
        result.addResults(validateReleasingInstructions(localSet));
        result.addResults(validateClassifiedBy(localSet));
        result.addResults(validateDerivedFrom(localSet));
        result.addResults(validateClassificationReason(localSet));
        result.addResults(validateClassificationAndMarkingSystem(localSet));
        result.addResults(validateObjectCountryCodingMethod(localSet));
        result.addResults(validateObjectCountryCodes(localSet));
        result.addResults(validateClassificationComments(localSet));
        result.addResults(validateVersion(localSet));
        return result;
    }

    private static List<ValidationResult> validateSecurityClassification(
            SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.SecurityClassification)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 0102.10-03");
            result.setDescription(
                    "Motion Imagery Data contained the Security Classification metadata element.");
            results.add(result);
            ISecurityMetadataValue v =
                    localSet.getField(SecurityMetadataKey.SecurityClassification);
            if (!(v instanceof ClassificationLocal)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the SecurityClassification is not of the correct type (expecting ClassificationLocal).");
                results.add(typeCheckResult);
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102.10-03");
            result.setDescription(
                    "Motion Imagery Data did not contain the Security Classification metadata element.");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult>
            validateClassifyingCountryAndReleasingInstructionsCountryCodingMethod(
                    SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.CcCodingMethod)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 0102.10-04");
            result.setDescription(
                    "Motion Imagery Data contained the Classifying Country and Releasing Instructions Country Coding Method metadata element.");
            results.add(result);
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.CcCodingMethod);
            if (!(v instanceof CcMethod)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the CcCodingMethod is not of the correct type (expecting CcMethod).");
                results.add(typeCheckResult);
            } else {
                CcMethod ccMethod = (CcMethod) v;
                if (ccMethod.getMethod().equals(CountryCodingMethod.OMITTED_VALUE)) {
                    ValidationResult valueCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    valueCheckResult.setDescription(
                            "The value assigned to the CcCodingMethod is not one of the allowed values.");
                    results.add(valueCheckResult);
                } else {
                    ValidationResult valueCheckResult = new ValidationResult(Validity.Conforms);
                    valueCheckResult.setDescription(
                            "The value assigned to the CcCodingMethod is one of the allowed values.");
                    results.add(valueCheckResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102.10-04");
            result.setDescription(
                    "Motion Imagery Data did not contain the Classifying Country and Releasing Instructions Country Coding Method metadata element.");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult> validateClassifyingCountry(
            SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.ClassifyingCountry)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 0102.10-05");
            result.setDescription(
                    "Motion Imagery Data contained the Classifying Country metadata element.");
            results.add(result);
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.ClassifyingCountry);
            if (!(v instanceof SecurityMetadataString)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the ClassifyingCountry is not of the correct type (expecting SecurityMetadataString).");
                results.add(typeCheckResult);
            } else {
                SecurityMetadataString securityMetadataString = (SecurityMetadataString) v;
                results.add(checkPrefixOnCountryCode(securityMetadataString));
                if (!securityMetadataString
                        .getDisplayName()
                        .equals(SecurityMetadataString.CLASSIFYING_COUNTRY)) {
                    ValidationResult typeCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    typeCheckResult.setDescription(
                            "The SecurityMetadataString assigned to the ClassifyingCountry has the wrong label (expecting CLASSIFYING_COUNTRY).");
                    results.add(typeCheckResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102.10-05");
            result.setDescription(
                    "Motion Imagery Data did not contain the Classifying Country metadata element.");
            results.add(result);
        }
        return results;
    }

    private static ValidationResult checkPrefixOnCountryCode(
            SecurityMetadataString securityMetadataString) {
        if (securityMetadataString.getValue().startsWith("//")) {
            ValidationResult prefixResult = new ValidationResult(Validity.Conforms);
            prefixResult.setTraceability("ST 0102.12 Para 6.1.3");
            prefixResult.setDescription("Classifying country value starts with //");
            return prefixResult;
        } else {
            ValidationResult prefixResult = new ValidationResult(Validity.DoesNotConform);
            prefixResult.setTraceability("ST 0102.12 Para 6.1.3");
            prefixResult.setDescription("Classifying country value did not start with //.");
            return prefixResult;
        }
    }

    private static List<ValidationResult> validateClassifiedBy(SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet, SecurityMetadataKey.ClassifiedBy, SecurityMetadataString.CLASSIFIED_BY);
    }

    private static List<ValidationResult> validateCaveats(SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet, SecurityMetadataKey.Caveats, SecurityMetadataString.CAVEATS);
    }

    private static List<ValidationResult> validateReleasingInstructions(
            SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.ReleasingInstructions)) {
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.ReleasingInstructions);
            if (!(v instanceof SecurityMetadataString)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to "
                                + SecurityMetadataKey.ReleasingInstructions.toString()
                                + " is not of the correct type (expecting SecurityMetadataString).");
                results.add(typeCheckResult);
            } else {
                SecurityMetadataString securityMetadataString = (SecurityMetadataString) v;
                if (!securityMetadataString
                        .getDisplayName()
                        .equals(SecurityMetadataString.RELEASING_INSTRUCTIONS)) {
                    ValidationResult typeCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    typeCheckResult.setDescription(
                            "The SecurityMetadataString assigned to "
                                    + SecurityMetadataKey.ReleasingInstructions.toString()
                                    + " has the wrong label (expecting "
                                    + SecurityMetadataString.RELEASING_INSTRUCTIONS
                                    + ").");
                    results.add(typeCheckResult);
                }
                String releaseInstructionsText = securityMetadataString.getDisplayableValue();
                if (releaseInstructionsText.contains("_")
                        || releaseInstructionsText.contains(";")) {
                    ValidationResult valueCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    valueCheckResult.setTraceability("ST 0102.10-16");
                    valueCheckResult.setDescription(
                            "The SecurityMetadataString assigned to "
                                    + SecurityMetadataKey.ReleasingInstructions.toString()
                                    + " has invalid separators.");
                    results.add(valueCheckResult);
                }
            }
        }
        return results;
    }

    private static List<ValidationResult> validateDerivedFrom(SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet, SecurityMetadataKey.DerivedFrom, SecurityMetadataString.DERIVED_FROM);
    }

    private static List<ValidationResult> validateClassificationAndMarkingSystem(
            SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet, SecurityMetadataKey.MarkingSystem, SecurityMetadataString.MARKING_SYSTEM);
    }

    private static List<ValidationResult> validateClassificationReason(
            SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet,
                SecurityMetadataKey.ClassificationReason,
                SecurityMetadataString.CLASSIFICATION_REASON);
    }

    private static List<ValidationResult> validateClassificationComments(
            SecurityMetadataLocalSet localSet) {
        return validateSecurityMetadataString(
                localSet,
                SecurityMetadataKey.ClassificationComments,
                SecurityMetadataString.CLASSIFICATION_COMMENTS);
    }

    private static List<ValidationResult> validateSecurityMetadataString(
            SecurityMetadataLocalSet localSet, SecurityMetadataKey key, String label) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(key)) {
            ISecurityMetadataValue v = localSet.getField(key);
            if (!(v instanceof SecurityMetadataString)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to "
                                + key.toString()
                                + " is not of the correct type (expecting SecurityMetadataString).");
                results.add(typeCheckResult);
            } else {
                SecurityMetadataString securityMetadataString = (SecurityMetadataString) v;
                if (!securityMetadataString.getDisplayName().equals(label)) {
                    ValidationResult typeCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    typeCheckResult.setDescription(
                            "The SecurityMetadataString assigned to "
                                    + key.toString()
                                    + " has the wrong label (expecting "
                                    + label
                                    + ").");
                    results.add(typeCheckResult);
                }
            }
        }
        return results;
    }

    private static List<ValidationResult> validateObjectCountryCodingMethod(
            SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.OcCodingMethod)) {
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.OcCodingMethod);
            if (!(v instanceof OcMethod)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the OcCodingMethod is not of the correct type (expecting OcMethod).");
                results.add(typeCheckResult);
            } else {
                OcMethod ocMethod = (OcMethod) v;
                if (ocMethod.getMethod().equals(CountryCodingMethod.OMITTED_VALUE)) {
                    ValidationResult valueCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    valueCheckResult.setDescription(
                            "The value assigned to the OcCodingMethod is not one of the allowed values.");
                    results.add(valueCheckResult);
                } else {
                    ValidationResult valueCheckResult = new ValidationResult(Validity.Conforms);
                    valueCheckResult.setDescription(
                            "The value assigned to the OcCodingMethod is one of the allowed values.");
                    results.add(valueCheckResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102 6.1.12");
            result.setDescription(
                    "Motion Imagery Data did not contain the expected Object Country Coding Method (required from Version 5 onwards).");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult> validateVersion(SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.Version)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 0102.10-56");
            result.setDescription(
                    "Motion Imagery Data contained the Security Metadata Version metadata element.");
            results.add(result);
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.Version);
            if (!(v instanceof ST0102Version)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the Version is not of the correct type (expecting ST0102Version).");
                results.add(typeCheckResult);
            } else {
                ST0102Version version = (ST0102Version) v;
                if ((version.getVersion() < 4)
                        || (version.getVersion() > SecurityMetadataConstants.ST_VERSION_NUMBER)) {
                    ValidationResult valueCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    valueCheckResult.setDescription(
                            String.format(
                                    "The value assigned to the ST0102Version is not one of the expected values [4-%d].",
                                    SecurityMetadataConstants.ST_VERSION_NUMBER));
                    results.add(valueCheckResult);
                } else {
                    ValidationResult valueCheckResult = new ValidationResult(Validity.Conforms);
                    valueCheckResult.setDescription(
                            "The value assigned to the ST0102Version is one of the expected values.");
                    results.add(valueCheckResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102.10-56");
            result.setDescription(
                    "Motion Imagery Data did not contain the Security Metadata Version metadata element (required on Version 4 and later).");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult> validateObjectCountryCodes(
            SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        if (localSet.getIdentifiers().contains(SecurityMetadataKey.ObjectCountryCodes)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 0102.10-23");
            result.setDescription(
                    "Motion Imagery Data contained the Object Country Code metadata element.");
            results.add(result);
            ISecurityMetadataValue v = localSet.getField(SecurityMetadataKey.ObjectCountryCodes);
            if (!(v instanceof ObjectCountryCodeString)) {
                ValidationResult typeCheckResult = new ValidationResult(Validity.DoesNotConform);
                typeCheckResult.setDescription(
                        "The value assigned to the ObjectCountryCodes is not of the correct type (expecting ObjectCountryCodeString).");
                results.add(typeCheckResult);
            } else {
                ObjectCountryCodeString objectCountryCodeString = (ObjectCountryCodeString) v;
                String countryCodes = objectCountryCodeString.getValue();
                if (countryCodes.contains(",") || countryCodes.contains(" ")) {
                    ValidationResult valueCheckResult =
                            new ValidationResult(Validity.DoesNotConform);
                    valueCheckResult.setTraceability("ST 0102.10-24");
                    valueCheckResult.setDescription(
                            "Object Country Codes contained space or comma separators (should be semi-colon).");
                    results.add(valueCheckResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 0102.10-23");
            result.setDescription(
                    "Motion Imagery Data did not contain the Object Country Code metadata element.");
            results.add(result);
        }
        return results;
    }
}
