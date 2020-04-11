package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.st0102.*;

/**
 * Dynamically create {@link ISecurityMetadataValue}s from {@link SecurityMetadataKey}s.
 */
public class UniversalSetFactory
{
    private UniversalSetFactory() {}

    /**
     * Create a {@link ISecurityMetadataValue} instance from encoded bytes
     *
     * @param key Tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     *
     * @throws IllegalArgumentException if input is invalid
     */
    public static ISecurityMetadataValue createValue(SecurityMetadataKey key, byte[] bytes)
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented. Mark all
        // unimplemented tags with TODO.
        switch (key)
        {
            case SecurityClassification:
                return new ClassificationUniversal(bytes);
            case CcCodingMethod:
            case ClassifyingCountry:
            case SciShiInfo:
            case Caveats:
            case ReleasingInstructions:
            case ClassifiedBy:
            case DerivedFrom:
            case ClassificationReason:
                return new SecurityMetadataString(bytes);
            case DeclassificationDate:
                return new DeclassificationDate(bytes);
            case MarkingSystem:
                return new SecurityMetadataString(bytes);
            case OcCodingMethod:
            case ObjectCountryCodes:
            case ClassificationComments:
                return new SecurityMetadataString(bytes);
            case ItemDesignatorId:
                return new ItemDesignatorId(bytes);
            case Version:
                return new ST0102Version(bytes);
            case CcCodingMethodVersionDate:
                return new CcmDate(bytes);
            case OcCodingMethodVersionDate:
                return new OcmDate(bytes);
        }
        throw new IllegalArgumentException("Unrecognized tag: " + key);
    }
}
