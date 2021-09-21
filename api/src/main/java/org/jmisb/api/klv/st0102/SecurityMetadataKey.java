package org.jmisb.api.klv.st0102;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.UniversalLabel;

/**
 * ST 0102 key.
 *
 * <p>Each metadata element within ST0102 consists of a key represented by an instance of this
 * enumeration, and a value represented by an instance implementing {@link ISecurityMetadataValue}.
 *
 * <p>The key has two representations - a tag number for the local set, and a universal label for
 * the universal set.
 */
public enum SecurityMetadataKey implements IKlvKey {
    Undefined(
            0,
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    })),
    /**
     * Security Classification.
     *
     * <p>Value is a {@link org.jmisb.api.klv.st0102.localset.ClassificationLocal} in the local set
     * and a {@link org.jmisb.api.klv.st0102.universalset.ClassificationUniversal} in the universal
     * set.
     *
     * <p>This item is required for the security local set or universal set to be valid.
     */
    SecurityClassification(1, SecurityMetadataConstants.securityClassificationUl),
    /**
     * Coding method for Classifying Country and Releasing Instructions.
     *
     * <p>This indicates how the {@link #ClassifyingCountry} and {@link #ReleasingInstructions} are to
     * be interpreted. Value is a {@link CountryCodingMethod}.
     *
     * <p>This item is required for the security local set or universal set to be valid.
     */
    CcCodingMethod(2, SecurityMetadataConstants.ccCodingMethodUl),
    /**
     * The classifying country.
     *
     * <p>This specifies which country classified the data (or possibly which country's security
     * rules / guidance are being used for the classification). Value is a {@link
     * SecurityMetadataString} with a {@code displayName} of {@code CLASSIFYING_COUNTRY} and a
     * {@code value} corresponding to the country code preceded by {@code //}.
     *
     * <p>For example, if the {@link #CcCodingMethod} is {@code ISO3166_THREE_LETTER}, then Australia
     * would be represented as {@code new
     * SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AUS")}.
     *
     * <p>This item is required for the security local set or universal set to be valid.
     */
    ClassifyingCountry(3, SecurityMetadataConstants.classifyingCountryUl),
    /**
     * Security Sensitive Compartmented Information (SCI) / Special Handling Instructions (SHI)
     * information.
     *
     * <p>When special handling instructions or compartmented information are used, Motion Imagery
     * Data shall contain the Sensitive Compartmented Information (SCI) / Special Handling
     * Instructions (SHI) metadata element. When used, SCI/SHI digraphs, trigraphs, or compartment
     * names shall be added identifying a single or a combination of special handling instructions.
     *
     * <p>A single entry shall be ended with a double forward slash “//”.
     *
     * <p>Multiple digraphs, trigraphs, or compartment names shall be separated by a single forward
     * slash “/”.
     *
     * <p>Multiple digraphs, trigraphs, or compartment names shall be ended with a double forward
     * slash “//”.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * SCI_SHI_INFO} and a {@code value} corresponding to the rules above. For example, if the
     * Motion Imagery was in the fictitious "Butter Popcorn" compartment, the value might be: {@code
     * new SecurityMetadataString(SecurityMetadataString.SCI_SHI_INFO, "BUTTER POPCORN//")}.
     */
    SciShiInfo(4, SecurityMetadataConstants.sciShiInfoUl),
    /**
     * Security Caveats.
     *
     * <p>The Caveats metadata element represents pertinent caveats (or code words) from each
     * category of the appropriate security entity register. Entries in this field may be
     * abbreviated or spelled out as free text entries.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code CAVEATS}
     * and a {@code value} of the caveat text or abbreviation. For example, if the Motion Imagery
     * was considered Unclassified, For Official Use Only, the value might be: {@code new
     * SecurityMetadataString(SecurityMetadataString.CAVEATS, "FOUO")}.
     */
    Caveats(5, SecurityMetadataConstants.caveatsUl),
    /**
     * The countries to which the motion imagery data is releasable to.
     *
     * <p>The Releasing Instructions metadata element contains a list of country codes to indicate
     * the countries to which the Motion Imagery Data is releasable.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * RELEASING_INSTRUCTIONS} and a {@code value} corresponding to the country codes separated by
     * spaces. For example, if the {@link #CcCodingMethod} is {@code ISO3166_THREE_LETTER}, then
     * release to Canada and the United States would be represented as {@code new
     * SecurityMetadataString(SecurityMetadataString.RELEASING_INSTRUCTIONS, "CAN USA")}.
     *
     * <p>This item is required if releasing instructions are required.
     */
    ReleasingInstructions(6, SecurityMetadataConstants.releasingInstructionsUl),
    /**
     * Classified By.
     *
     * <p>The Classified By metadata element identifies the name and type of authority used to
     * classify the Motion Imagery Data. The metadata element is free text and can contain either
     * the original classification authority name and position or personal identifier, or the title
     * of the document or security classification guide used to classify the data.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * CLASSIFIED_BY} and a {@code value} corresponding to the classified by text. For example, if
     * the classification was performed by "Mark W. Ewing, ODNI Chief Management Officer", it could
     * be shown as {@code new SecurityMetadataString(SecurityMetadataString.CLASSIFIED_BY, "Mark W.
     * Ewing, ODNI Chief Management Officer")}.
     */
    ClassifiedBy(7, SecurityMetadataConstants.classifiedByUl),
    /**
     * Derived From.
     *
     * <p>The Derived From metadata element contains information about the original source of data
     * from which the classification was derived. The metadata element is free text.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * DERIVED_FROM} and a {@code value} corresponding to the derivative source. For example, if the
     * classification was derived from multiple sources, it could be shown as {@code new
     * SecurityMetadataString(SecurityMetadataString.DERIVED_FROM, "Multiple Sources")}.
     */
    DerivedFrom(8, SecurityMetadataConstants.derivedFromUl),
    /**
     * Classification Reason.
     *
     * <p>The Classification Reason metadata element contains the reason for classification or a
     * citation from a document. The metadata element is free text.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * CLASSIFICATION_REASON} and a {@code value} corresponding to the classification reason or
     * citation. For example, if the classification reason is in section 1.4(c), it could be shown
     * as {@code new SecurityMetadataString(SecurityMetadataString.CLASSIFICATION_REASON,
     * "1.4(c)")}.
     */
    ClassificationReason(9, SecurityMetadataConstants.classificationReasonUl),
    /**
     * Declassification Date.
     *
     * <p>The Declassification Date metadata element provides a date when the classified material
     * may be automatically declassified.
     *
     * <p>Value is a {@link DeclassificationDate}. As an example, if the declassification date (from
     * policy) is 31 December 2039, this would be {@code new DeclassificationDate(LocalDate.of(2039,
     * 12, 31))}.
     */
    DeclassificationDate(10, SecurityMetadataConstants.declassificationDateUl),
    /**
     * Classification and Marking System.
     *
     * <p>The Classification and Marking System metadata element identifies the classification or
     * marking system used in the Security Metadata set as determined by the appropriate security
     * entity for the country originating the data. This is free text.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * MARKING_SYSTEM} and a {@code value} corresponding to the classification and marking system.
     */
    MarkingSystem(11, SecurityMetadataConstants.markingSystemUl),
    /**
     * Object Country Coding Method.
     *
     * <p>The Object Country Coding Method metadata element identifies the coding method for the
     * {@link #ObjectCountryCodes} metadata. This element allows use of GEC two-letter or four-letter
     * alphabetic country code (legacy systems only); ISO-3166 two-letter, three-letter, or
     * three-digit numeric; STANAG 1059 two-letter or three-letter codes; and GENC two-letter,
     * three-letter, three-digit numeric or administrative subdivisions. Early (ST0102-5 and
     * earlier) versions allowed this element to be optional; its absence indicates the default GENC
     * two-letter coding method was used in the Object Country Code element.
     *
     * <p>Value is an {@link org.jmisb.api.klv.st0102.localset.OcMethod} for security metadata local
     * set. For example, {@code new OcMethod(CountryCodingMethod.ISO3166_TWO_LETTER)}.
     *
     * <p>Value is an {@link ObjectCountryCodeString} for security metadata universal set.
     *
     * <p>This item is required for the security local set or universal set to be valid.
     */
    OcCodingMethod(12, SecurityMetadataConstants.ocCodingMethodUl),
    /**
     * Object Country Codes.
     *
     * <p>The Object Country Codes metadata element contains a value identifying the country or
     * countries that are the object of the Motion Imagery Data.
     *
     * <p>Multiple Object Country Codes shall be separated by a semi-colon “;” (no spaces).
     *
     * <p>The object country code of the geographic region lying under the center of the image frame
     * shall populate the Object Country Code metadata element. The object country codes of other
     * represented geographic regions may be included in addition to the country code of the
     * geographic region under the center of the image frame.
     *
     * <p>Value is a {@link ObjectCountryCodeString} with a {@code value} corresponding to the
     * country code or codes. For example, if the {@link #OcCodingMethod} is {@code
     * ISO3166_THREE_LETTER}, then Australia would be represented as {@code new
     * ObjectCountryCodeString("AUS")}. If the imagery also contained part of New Zealand, then it
     * could be represented {@code new ObjectCountryCodeString("AUS;NZL")}
     */
    ObjectCountryCodes(13, SecurityMetadataConstants.objectCountryCodesUl),
    /**
     * Classification Comments.
     *
     * <p>The Classification Comments metadata element allows for security related comments and
     * format changes necessary in the future. This field may be used in addition to those required
     * by appropriate security entity and is optional.
     *
     * <p>The Classification Comments metadata element shall only be used to convey non-essential
     * security-related information.
     *
     * <p>Value is a {@link SecurityMetadataString} with a {@code displayName} of {@code
     * CLASSIFICATION_COMMENTS} and a {@code value} providing the free text comments.
     */
    ClassificationComments(14, SecurityMetadataConstants.classificationCommentsUl),
    // TODO: 15 - 18, UMID - this is a difficult case for UL variation.
    /**
     * Stream Identifier (Deprecated).
     *
     * <p>This is a legacy identifier, no longer valid, provided to support ST0102.10 and earlier.
     *
     * <p>In MPEG-2 Program Streams and Packetized Elementary Streams (PES) it specifies the type
     * and number of the Elementary Stream. In MPEG-2 Transport Streams the id may be set by the
     * user to any valid value which correctly describes the Elementary Stream type.
     *
     * <p>Note that the current MISB position is that the security metadata applies to the
     * information as a whole - there is no marking of individual streams.
     *
     * <p>Value is a StreamId. It should not be instantiated, and there is no "construct from value"
     * for StreamId.
     */
    StreamId(19, SecurityMetadataConstants.streamIdUl),
    /**
     * Transport Stream Identifier (Deprecated).
     *
     * <p>This is a legacy identifier, no longer valid, provided to support ST0102.10 and earlier.
     *
     * <p>When multiple Transport Streams are present in a network environment the transport stream
     * identifier uniquely identifies a specific Transport Stream from any other Transport Stream to
     * remove any ambiguity. Its value is defined by the originator.
     *
     * <p>Note that the current MISB position is that the security metadata applies to the
     * information as a whole - there is no marking of individual streams.
     *
     * <p>Value is a TransportStreamId. It should not be instantiated, and there is no "construct
     * from value" for TransportStreamId.
     */
    TransportStreamId(20, SecurityMetadataConstants.transportStreamIdUl),
    /**
     * Item Designator Identifier (Deprecated).
     *
     * <p>This is a legacy identifier, no longer valid, provided to support ST0102.10 and earlier.
     *
     * <p>Note that the current MISB position is that the security metadata applies to the
     * information as a whole - there is no marking of individual items.
     *
     * <p>Value is an ItemDesignatorId. It should not be instantiated, and there is no "construct
     * from value" for ItemDesignatorId.
     */
    ItemDesignatorId(21, SecurityMetadataConstants.itemDesignatorIdUl),
    /**
     * Version of Security Metadata Standard.
     *
     * <p>The Version metadata element indicates the version number of MISB ST 0102 referenced.
     *
     * <p>Value is a {@link ST0102Version}. As an example, {@code new
     * ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER)} for the current version supported
     * by jMISB.
     */
    Version(22, SecurityMetadataConstants.versionUl),
    /**
     * Country Coding Method Version Date.
     *
     * <p>This metadata item provides the effective date (promulgation date) of the source (FIPS
     * 10-4, ISO 3166, GENC, or STANAG 1059) used for the Classifying Country and Releasing
     * Instructions Country Coding Method. As ISO 3166 is updated by dated circulars, not by version
     * revision, the ISO 8601 YYYY-MM-DD formatted date is used. The valid country codes (and the
     * corresponding meanings) can vary between versions of those coding methods, so this metadata
     * item can be used to identify the exact version of the coding method.
     *
     * <p>Value is a CcmDate. For example, if using a GENC baseline of 14 November 2013, this could
     * be {@code new CcmDate(LocalDate.of(2013, 11, 14))}.
     */
    CcCodingMethodVersionDate(23, SecurityMetadataConstants.ccCodingMethodVersionDateUl),
    /**
     * Object Country Coding Method Version Date.
     *
     * <p>This metadata item provides the effective date (promulgation date) of the source (FIPS
     * 10-4, ISO 3166, GENC, or STANAG 1059) used for the Object Country Coding Method. As ISO 3166
     * is updated by dated circulars, not by version revision, the ISO 8601 YYYY-MM-DD formatted
     * date is used. The valid country codes (and the corresponding meanings) can vary between
     * versions of those coding methods, so this metadata item can be used to identify the exact
     * version of the coding method.
     *
     * <p>Value is an OcmDate. For example, if using a GENC baseline of 14 November 2013, this could
     * be {@code new CcmDate(LocalDate.of(2013, 11, 14))}.
     */
    OcCodingMethodVersionDate(24, SecurityMetadataConstants.ocCodingMethodVersionDateUl);

    private final int tag;
    private final UniversalLabel ul;

    private static final Map<Integer, SecurityMetadataKey> tagTable = new HashMap<>();
    private static final Map<UniversalLabel, SecurityMetadataKey> ulTable = new HashMap<>();

    static {
        for (SecurityMetadataKey key : values()) {
            tagTable.put(key.tag, key);
            ulTable.put(key.ul, key);
        }
    }

    SecurityMetadataKey(int tag, UniversalLabel ul) {
        this.tag = tag;
        this.ul = ul;
    }

    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Get the {@link UniversalLabel} for this metadata key.
     *
     * @return the universal label for this key.
     */
    public UniversalLabel getUl() {
        return ul;
    }

    /**
     * Look up the SecurityMetadataKey by tag value.
     *
     * <p>As an example, looking up {@code 5} will return {@link #Caveats}.
     *
     * @param tag the tag value
     * @return the corresponding SecurityMetadataKey.
     */
    public static SecurityMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }

    /**
     * Look up the SecurityMetadataKey by universal label.
     *
     * @param ul the universal label.
     * @return the corresponding SecurityMetadataKey.
     */
    public static SecurityMetadataKey getKey(UniversalLabel ul) {
        return ulTable.getOrDefault(ul, Undefined);
    }
}
