package org.jmisb.api.klv.st0102.universalset;

import static org.jmisb.core.klv.ArrayUtils.arrayFromChunks;

import java.time.LocalDate;
import java.util.*;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;
import org.jmisb.api.klv.st0102.*;

/** Security Metadata Universal Set message packet defined by ST 0102. */
public class SecurityMetadataUniversalSet extends SecurityMetadataMessage {
    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Key/value pairs to be included in the message
     */
    public SecurityMetadataUniversalSet(
            SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values) {
        this.map = values;
    }

    /**
     * Create a Security Metadata Universal Set message by parsing the given byte array.
     *
     * @param bytes Byte array containing a Security Metadata Universal Set message
     * @throws KlvParseException if a parsing error occurs
     */
    public SecurityMetadataUniversalSet(byte[] bytes) throws KlvParseException {
        // Parse the length field
        BerField lengthField = BerDecoder.decode(bytes, UniversalLabel.LENGTH, false);
        int lengthLength = lengthField.getLength();
        int offset = UniversalLabel.LENGTH + lengthLength;
        int valueLength = lengthField.getValue();

        // Parse fields out of the array
        List<UdsField> fields = UdsParser.parseFields(bytes, offset, valueLength);

        // Convert field data based on ST 0102
        for (UdsField field : fields) {
            SecurityMetadataKey key = SecurityMetadataKey.getKey(field.getKey());
            ISecurityMetadataValue value =
                    UniversalSetFactory.createValue(
                            SecurityMetadataKey.getKey(field.getKey()), field.getValue());
            setField(key, value);
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.SecurityMetadataUniversalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        // List representing all keys and values as primitive byte arrays. Avoids boxing/unboxing
        // individual bytes for efficiency.
        List<byte[]> chunks = new ArrayList<>();

        // Add all values from map
        for (Map.Entry<SecurityMetadataKey, ISecurityMetadataValue> entry : map.entrySet()) {
            SecurityMetadataKey key = entry.getKey();
            ISecurityMetadataValue value = entry.getValue();
            byte[] bytes = value.getBytes();
            if (bytes != null && bytes.length > 0) {
                chunks.add(key.getUl().getBytes());
                chunks.add(BerEncoder.encode(bytes.length));
                chunks.add(bytes.clone());
            }
        }

        // Figure out value length
        int valueLength = 0;
        for (byte[] chunk : chunks) {
            valueLength += chunk.length;
        }

        // Determine total length
        int totalLength;
        if (isNested) {
            totalLength = valueLength;
        } else {
            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend key field (UL) into front of the list
            chunks.add(0, KlvConstants.SecurityMetadataUniversalSetUl.getBytes());

            // Compute full message length
            totalLength = UniversalLabel.LENGTH + lengthField.length + valueLength;
        }

        // Allocate array and write all chunks
        return arrayFromChunks(chunks, totalLength);
    }

    @Override
    public String displayHeader() {
        return "ST 0102 (universal)";
    }

    /** Builder class for {@link SecurityMetadataUniversalSet} objects. */
    public static class Builder {
        private final Classification classification;
        private SecurityMetadataString ccMethod;
        private SecurityMetadataString classifyingCountry;
        private SecurityMetadataString sciShiInfo;
        private SecurityMetadataString caveats;
        private SecurityMetadataString releasingInstructions;
        private SecurityMetadataString classifiedBy;
        private SecurityMetadataString derivedFrom;
        private SecurityMetadataString classificationReason;
        private DeclassificationDate declassificationDate;
        private SecurityMetadataString markingSystem;
        private SecurityMetadataString ocMethod;
        private ObjectCountryCodeString objectCountryCodes;
        private SecurityMetadataString classificationComments;
        private ST0102Version version;
        private CcmDate ccmDate;
        private OcmDate ocmDate;

        /**
         * Constructor.
         *
         * @param classification the classification to use in this {@link
         *     SecurityMetadataUniversalSet}
         */
        public Builder(Classification classification) {
            this.classification = classification;
        }

        /**
         * Coding method for Classifying Country and Releasing Instructions.
         *
         * <p>This indicates how the {@link #classifyingCountry} and {@link #releasingInstructions}
         * are to be interpreted.
         *
         * <p>This item is required for the security universal set to be valid. If it is not
         * provided, {@code "GENC Two Letter"} will be used as a default value.
         *
         * <p>Consider setting {@link #ccmDate} to indicate a specific version of the coding method.
         *
         * @param value the country coding method
         * @return instance of this Builder, to support method chaining.
         */
        public Builder ccMethod(String value) {
            this.ccMethod =
                    new SecurityMetadataString(SecurityMetadataString.COUNTRY_CODING_METHOD, value);
            return this;
        }

        /**
         * The classifying country.
         *
         * <p>This specifies which country classified the data (or possibly which country's security
         * rules / guidance are being used for the classification). The value is the country code
         * preceded by {@code //}. For example, if the {@link #ccMethod} is {@code "ISO-3166 Three
         * Letter"}, then Australia would be represented as {@code "//AUS"}.
         *
         * <p>This item is required for the security universal set to be valid. There is no default
         * value, and the {@link #build()} will fail if it is not provided.
         *
         * @param value the country code (including the required "//" part).
         * @return instance of this Builder, to support method chaining.
         */
        public Builder classifyingCountry(String value) {
            this.classifyingCountry =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, value);
            return this;
        }

        /**
         * Security Sensitive Compartmented Information (SCI) / Special Handling Instructions (SHI)
         * information.
         *
         * <p>When special handling instructions or compartmented information are used, Motion
         * Imagery Data shall contain the Sensitive Compartmented Information (SCI) / Special
         * Handling Instructions (SHI) metadata element. When used, SCI/SHI digraphs, trigraphs, or
         * compartment names shall be added identifying a single or a combination of special
         * handling instructions.
         *
         * <p>A single entry shall be ended with a double forward slash “//”.
         *
         * <p>Multiple digraphs, trigraphs, or compartment names shall be separated by a single
         * forward slash “/”.
         *
         * <p>Multiple digraphs, trigraphs, or compartment names shall be ended with a double
         * forward slash “//”.
         *
         * @param value the SCI / SHI information, including the ending double forward slash and any
         *     single forward slash separators.
         * @return instance of this Builder, to support method chaining.
         */
        public Builder sciShiInfo(String value) {
            this.sciShiInfo =
                    new SecurityMetadataString(SecurityMetadataString.SCI_SHI_INFO, value);
            return this;
        }

        /**
         * Security Caveats.
         *
         * <p>The Caveats metadata element represents pertinent caveats (or code words) from each
         * category of the appropriate security entity register. Entries in this field may be
         * abbreviated or spelled out as free text entries.
         *
         * <p>Value is the caveat text or abbreviation. For example, if the Motion Imagery was
         * considered Unclassified, For Official Use Only, the value might be: {@code "FOUO"}.
         *
         * @param value the caveat text or abbreviation
         * @return instance of this Builder, to support method chaining.
         */
        public Builder caveats(String value) {
            this.caveats = new SecurityMetadataString(SecurityMetadataString.CAVEATS, value);
            return this;
        }

        /**
         * The countries that the motion imagery data is releasable to.
         *
         * <p>The Releasing Instructions metadata element contains a list of country codes to
         * indicate the countries to which the Motion Imagery Data is releasable.
         *
         * <p>Value is the country codes for those countries (as specified in the {@link
         * #classifyingCountry(String value)} separated by spaces. For example, if the {@link
         * #ccMethod} is {@code "ISO-3166 Three Letter"}, then release to Canada and the United
         * States would be represented as {@code "CAN USA"}.
         *
         * <p>This item is required if releasing instructions are required.
         *
         * @param value the space separated list of country codes.
         * @return instance of this Builder, to support method chaining.
         */
        public Builder releasingInstructions(String value) {
            this.releasingInstructions =
                    new SecurityMetadataString(
                            SecurityMetadataString.RELEASING_INSTRUCTIONS, value);
            return this;
        }

        /**
         * Classified By.
         *
         * <p>The Classified By metadata element identifies the name and type of authority used to
         * classify the Motion Imagery Data. The metadata element is free text and can contain
         * either the original classification authority name and position or personal identifier, or
         * the title of the document or security classification guide used to classify the data.
         *
         * <p>An example might be {@code "Mark W. Ewing, ODNI Chief Management Officer")}.
         *
         * @param value the classified by text
         * @return instance of this Builder, to support method chaining.
         */
        public Builder classifiedBy(String value) {
            this.classifiedBy =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFIED_BY, value);
            return this;
        }

        /**
         * Derived From.
         *
         * <p>The Derived From metadata element contains information about the original source of
         * data from which the classification was derived. The metadata element is free text.
         *
         * <p>Value is the derivative source text. For example, if the classification was derived
         * from multiple sources, it could be shown as {@code "Multiple Sources"}.
         *
         * @param value the derived from text
         * @return instance of this Builder, to support method chaining.
         */
        public Builder derivedFrom(String value) {
            this.derivedFrom =
                    new SecurityMetadataString(SecurityMetadataString.DERIVED_FROM, value);
            return this;
        }

        /**
         * Classification Reason.
         *
         * <p>The Classification Reason metadata element contains the reason for classification or a
         * citation from a document. The metadata element is free text.
         *
         * <p>For example, if the classification reason is in section 1.4(c), it could be shown as
         * {@code "1.4(c)"}.
         *
         * @param value the classification reason or citation
         * @return instance of this Builder, to support method chaining.
         */
        public Builder classificationReason(String value) {
            this.classificationReason =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFICATION_REASON, value);
            return this;
        }

        /**
         * Declassification Date.
         *
         * <p>The Declassification Date metadata element provides a date when the classified
         * material may be automatically declassified.
         *
         * @param value the declassification date
         * @return instance of this Builder, to support method chaining.
         */
        public Builder declassificationDate(LocalDate value) {
            this.declassificationDate = new DeclassificationDate(value);
            return this;
        }

        /**
         * Classification and Marking System.
         *
         * <p>The Classification and Marking System metadata element identifies the classification
         * or marking system used in the Security Metadata set as determined by the appropriate
         * security entity for the country originating the data. This is free text.
         *
         * @param value text describing or identifying the classification and marking system
         * @return instance of this Builder, to support method chaining.
         */
        public Builder markingSystem(String value) {
            this.markingSystem =
                    new SecurityMetadataString(SecurityMetadataString.MARKING_SYSTEM, value);
            return this;
        }

        /**
         * Object Country Coding Method.
         *
         * <p>The Object Country Coding Method metadata element identifies the coding method for the
         * {@link #objectCountryCodes} metadata. This element allows use of GEC two-letter or
         * four-letter alphabetic country code (legacy systems only); ISO-3166 two-letter,
         * three-letter, or three-digit numeric; STANAG 1059 two-letter or three-letter codes; and
         * GENC two-letter, three-letter, three-digit numeric or administrative subdivisions.
         *
         * <p>This item is required for the universal set to be valid. If it is not provided, {@code
         * "GENC Two Letter"} will be used as a default value.
         *
         * @param value the country coding method to be used for object country codes
         * @return instance of this Builder, to support method chaining.
         */
        public Builder ocMethod(String value) {
            this.ocMethod =
                    new SecurityMetadataString(
                            SecurityMetadataString.OBJECT_COUNTRY_CODING_METHOD, value);
            return this;
        }

        /**
         * Object Country Codes.
         *
         * <p>The Object Country Codes metadata element contains a value identifying the country or
         * countries that are the object of the Motion Imagery Data.
         *
         * <p>Multiple Object Country Codes shall be separated by a semi-colon “;” (no spaces).
         *
         * <p>The object country code of the geographic region lying under the center of the image
         * frame shall populate the Object Country Code metadata element. The object country codes
         * of other represented geographic regions may be included in addition to the country code
         * of the geographic region under the center of the image frame.
         *
         * <p>This item is required for the security universal set to be valid. There is no default
         * value, and the {@link #build()} will fail if it is not provided.
         *
         * @param value the country code, or codes separated with semi-colon characters
         * @return instance of this Builder, to support method chaining.
         */
        public Builder objectCountryCodes(String value) {
            this.objectCountryCodes = new ObjectCountryCodeString(value);
            return this;
        }

        /**
         * Classification Comments.
         *
         * <p>The Classification Comments metadata element allows for security related comments and
         * format changes necessary in the future. This field may be used in addition to those
         * required by appropriate security entity and is optional.
         *
         * <p>The Classification Comments metadata element shall only be used to convey
         * non-essential security-related information.
         *
         * @param value free text classification comments
         * @return instance of this Builder, to support method chaining.
         */
        public Builder classificationComments(String value) {
            this.classificationComments =
                    new SecurityMetadataString(
                            SecurityMetadataString.CLASSIFICATION_COMMENTS, value);
            return this;
        }

        /**
         * Version of Security Metadata Standard.
         *
         * <p>The Version metadata element indicates the version number of MISB ST 0102 referenced.
         * For example, ST 0102.12 would have the value {@code 12}.
         *
         * <p>{@link org.jmisb.api.klv.st0102.SecurityMetadataConstants#ST_VERSION_NUMBER} will
         * provide the current version supported by jMISB.
         *
         * @param value the version number of ST 0102 document
         * @return instance of this Builder, to support method chaining.
         */
        public Builder version(int value) {
            this.version = new ST0102Version(value);
            return this;
        }

        /**
         * Country Coding Method Version Date.
         *
         * <p>This metadata item provides the effective date (promulgation date) of the source (FIPS
         * 10-4, ISO 3166, GENC, or STANAG 1059) used for the Classifying Country and Releasing
         * Instructions Country Coding Method. As ISO 3166 is updated by dated circulars, not by
         * version revision, the ISO 8601 YYYY-MM-DD formatted date is used. The valid country codes
         * (and the corresponding meanings) can vary between versions of those coding methods, so
         * this metadata item can be used to identify the exact version of the coding method.
         *
         * @param value the effective date of the Country Coding Method source document.
         * @return instance of this Builder, to support method chaining.
         */
        public Builder ccmDate(LocalDate value) {
            this.ccmDate = new CcmDate(value);
            return this;
        }

        /**
         * Object Country Coding Method Version Date.
         *
         * <p>This metadata item provides the effective date (promulgation date) of the source (FIPS
         * 10-4, ISO 3166, GENC, or STANAG 1059) used for the Object Country Coding Method. As ISO
         * 3166 is updated by dated circulars, not by version revision, the ISO 8601 YYYY-MM-DD
         * formatted date is used. The valid country codes (and the corresponding meanings) can vary
         * between versions of those coding methods, so this metadata item can be used to identify
         * the exact version of the coding method.
         *
         * @param value the effective date of the Country Coding Method source document.
         * @return instance of this Builder, to support method chaining.
         */
        public Builder ocmDate(LocalDate value) {
            this.ocmDate = new OcmDate(value);
            return this;
        }

        /**
         * Build the security universal set.
         *
         * <p>This takes the security information specified in the previous Builder calls and builds
         * a @@link SecurityMetadataUniversalSet}, filling in any default values.
         *
         * @return the resulting universal set.
         */
        public SecurityMetadataUniversalSet build() {
            return new SecurityMetadataUniversalSet(this);
        }
    }

    /**
     * Construct from Builder.
     *
     * @param builder The builder
     */
    private SecurityMetadataUniversalSet(Builder builder) {
        setField(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationUniversal(builder.classification));

        if (builder.ccMethod != null) {
            setField(SecurityMetadataKey.CcCodingMethod, builder.ccMethod);
        } else {
            setField(
                    SecurityMetadataKey.CcCodingMethod,
                    new SecurityMetadataString(
                            SecurityMetadataString.COUNTRY_CODING_METHOD, "GENC Two Letter"));
        }

        if (builder.classifyingCountry != null) {
            setField(SecurityMetadataKey.ClassifyingCountry, builder.classifyingCountry);
        } else {
            throw new IllegalArgumentException("Classifying Country is a required field");
        }

        if (builder.sciShiInfo != null) {
            setField(SecurityMetadataKey.SciShiInfo, builder.sciShiInfo);
        }
        if (builder.caveats != null) {
            setField(SecurityMetadataKey.Caveats, builder.caveats);
        }
        if (builder.releasingInstructions != null) {
            setField(SecurityMetadataKey.ReleasingInstructions, builder.releasingInstructions);
        }
        if (builder.classifiedBy != null) {
            setField(SecurityMetadataKey.ClassifiedBy, builder.classifiedBy);
        }
        if (builder.derivedFrom != null) {
            setField(SecurityMetadataKey.DerivedFrom, builder.derivedFrom);
        }
        if (builder.classificationReason != null) {
            setField(SecurityMetadataKey.ClassificationReason, builder.classificationReason);
        }
        if (builder.declassificationDate != null) {
            setField(SecurityMetadataKey.DeclassificationDate, builder.declassificationDate);
        }
        if (builder.markingSystem != null) {
            setField(SecurityMetadataKey.MarkingSystem, builder.markingSystem);
        }

        if (builder.ocMethod != null) {
            setField(SecurityMetadataKey.OcCodingMethod, builder.ocMethod);
        } else {
            setField(
                    SecurityMetadataKey.OcCodingMethod,
                    new SecurityMetadataString(
                            SecurityMetadataString.OBJECT_COUNTRY_CODING_METHOD,
                            "GENC Two Letter"));
        }

        if (builder.objectCountryCodes != null) {
            setField(SecurityMetadataKey.ObjectCountryCodes, builder.objectCountryCodes);
        } else {
            throw new IllegalArgumentException("Object Country Codes is a required field");
        }

        if (builder.classificationComments != null) {
            setField(SecurityMetadataKey.ClassificationComments, builder.classificationComments);
        }

        if (builder.version != null) {
            setField(SecurityMetadataKey.Version, builder.version);
        } else {
            setField(
                    SecurityMetadataKey.Version,
                    new ST0102Version(SecurityMetadataConstants.ST_VERSION_NUMBER));
        }

        if (builder.ccmDate != null) {
            setField(SecurityMetadataKey.CcCodingMethodVersionDate, builder.ccmDate);
        }
        if (builder.ocmDate != null) {
            setField(SecurityMetadataKey.OcCodingMethodVersionDate, builder.ocmDate);
        }
    }
}
