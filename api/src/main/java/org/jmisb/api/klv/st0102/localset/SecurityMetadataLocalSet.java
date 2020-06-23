package org.jmisb.api.klv.st0102.localset;

import static org.jmisb.api.klv.KlvConstants.SecurityMetadataLocalSetUl;
import static org.jmisb.core.klv.ArrayUtils.arrayFromChunks;

import java.time.LocalDate;
import java.util.*;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;
import org.jmisb.api.klv.st0102.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Security Metadata Local Set message packet defined by ST 0102 */
public class SecurityMetadataLocalSet extends SecurityMetadataMessage {
    private static Logger logger = LoggerFactory.getLogger(SecurityMetadataLocalSet.class);
    /**
     * Create the message from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the message
     */
    public SecurityMetadataLocalSet(SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values) {
        this.map = values;
    }

    /**
     * Create a Security Metadata Local Set message by parsing the given byte array
     *
     * @param bytes Byte array containing a Security Metadata Local Set message
     * @param hasKeyAndLength Flag to indicate if {@code bytes} includes header fields
     * @throws KlvParseException if a parsing error occurs
     */
    public SecurityMetadataLocalSet(byte[] bytes, boolean hasKeyAndLength)
            throws KlvParseException {
        int offset = 0;
        int valueLength = bytes.length;

        if (hasKeyAndLength) {
            // Parse the length field
            BerField lengthField = BerDecoder.decode(bytes, UniversalLabel.LENGTH, false);
            int lengthLength = lengthField.getLength();
            offset = UniversalLabel.LENGTH + lengthLength;
            valueLength = lengthField.getValue();
        }

        // Parse fields out of the array
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, valueLength);

        // Convert field data based on ST 0102
        for (LdsField field : fields) {
            SecurityMetadataKey key = SecurityMetadataKey.getKey(field.getTag());

            if (key == SecurityMetadataKey.Undefined) {
                logger.info("Unknown Security Metadata tag: {}", field.getTag());
            } else {
                ISecurityMetadataValue value = LocalSetFactory.createValue(key, field.getData());
                setField(key, value);
            }
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return SecurityMetadataLocalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        // List representing all tags and values as primitive byte arrays. Avoids boxing/unboxing
        // individual bytes for efficiency.
        List<byte[]> chunks = new ArrayList<>();

        // Add all values from map
        for (Map.Entry<SecurityMetadataKey, ISecurityMetadataValue> entry : map.entrySet()) {
            SecurityMetadataKey key = entry.getKey();
            ISecurityMetadataValue value = entry.getValue();
            byte[] bytes = value.getBytes();
            if (bytes != null && bytes.length > 0) {
                chunks.add(new byte[] {(byte) key.getTag()});
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
            chunks.add(0, SecurityMetadataLocalSetUl.getBytes());

            // Compute full message length
            totalLength = UniversalLabel.LENGTH + lengthField.length + valueLength;
        }

        // Allocate array and write all chunks
        return arrayFromChunks(chunks, totalLength);
    }

    @Override
    public String displayHeader() {
        return "ST 0102 (local)";
    }

    /** Builder class for {@link SecurityMetadataLocalSet} objects */
    public static class Builder {
        private final Classification classification;
        private CcMethod ccMethod;
        private SecurityMetadataString classifyingCountry;
        private SecurityMetadataString sciShiInfo;
        private SecurityMetadataString caveats;
        private SecurityMetadataString releasingInstructions;
        private SecurityMetadataString classifiedBy;
        private SecurityMetadataString derivedFrom;
        private SecurityMetadataString classificationReason;
        private DeclassificationDate declassificationDate;
        private SecurityMetadataString markingSystem;
        private OcMethod ocMethod;
        private ObjectCountryCodeString objectCountryCodes;
        private SecurityMetadataString classificationComments;
        private ST0102Version version;
        private CcmDate ccmDate;
        private OcmDate ocmDate;

        public Builder(Classification classification) {
            this.classification = classification;
        }

        public Builder ccMethod(CountryCodingMethod value) {
            this.ccMethod = new CcMethod(value);
            return this;
        }

        public Builder classifyingCountry(String value) {
            this.classifyingCountry =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, value);
            return this;
        }

        public Builder sciShiInfo(String value) {
            this.sciShiInfo =
                    new SecurityMetadataString(SecurityMetadataString.SCI_SHI_INFO, value);
            return this;
        }

        public Builder caveats(String value) {
            this.caveats = new SecurityMetadataString(SecurityMetadataString.CAVEATS, value);
            return this;
        }

        public Builder releasingInstructions(String value) {
            this.releasingInstructions =
                    new SecurityMetadataString(
                            SecurityMetadataString.RELEASING_INSTRUCTIONS, value);
            return this;
        }

        public Builder classifiedBy(String value) {
            this.classifiedBy =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFIED_BY, value);
            return this;
        }

        public Builder derivedFrom(String value) {
            this.derivedFrom =
                    new SecurityMetadataString(SecurityMetadataString.DERIVED_FROM, value);
            return this;
        }

        public Builder classificationReason(String value) {
            this.classificationReason =
                    new SecurityMetadataString(SecurityMetadataString.CLASSIFICATION_REASON, value);
            return this;
        }

        public Builder declassificationDate(LocalDate value) {
            this.declassificationDate = new DeclassificationDate(value);
            return this;
        }

        public Builder markingSystem(String value) {
            this.markingSystem =
                    new SecurityMetadataString(SecurityMetadataString.MARKING_SYSTEM, value);
            return this;
        }

        public Builder ocMethod(CountryCodingMethod value) {
            this.ocMethod = new OcMethod(value);
            return this;
        }

        public Builder objectCountryCodes(String value) {
            this.objectCountryCodes = new ObjectCountryCodeString(value);
            return this;
        }

        public Builder classificationComments(String value) {
            this.classificationComments =
                    new SecurityMetadataString(
                            SecurityMetadataString.CLASSIFICATION_COMMENTS, value);
            return this;
        }

        public Builder version(int value) {
            this.version = new ST0102Version(value);
            return this;
        }

        public Builder ccmDate(LocalDate value) {
            this.ccmDate = new CcmDate(value);
            return this;
        }

        public Builder ocmDate(LocalDate value) {
            this.ocmDate = new OcmDate(value);
            return this;
        }

        public SecurityMetadataLocalSet build() {
            return new SecurityMetadataLocalSet(this);
        }
    }

    /**
     * Construct from Builder
     *
     * @param builder The builder
     */
    private SecurityMetadataLocalSet(Builder builder) {
        setField(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(builder.classification));

        if (builder.ccMethod != null) {
            setField(SecurityMetadataKey.CcCodingMethod, builder.ccMethod);
        } else {
            setField(
                    SecurityMetadataKey.CcCodingMethod,
                    new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
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
                    new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
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
