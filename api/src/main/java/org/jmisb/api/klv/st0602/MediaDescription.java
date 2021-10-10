package org.jmisb.api.klv.st0602;

import java.nio.charset.StandardCharsets;

/**
 * Media description.
 *
 * <p>Freeform textual description (per SMPTE ISO/IEC RP 210) providing title or text description of
 * enclosed MIME data.
 */
public class MediaDescription implements IAnnotationMetadataValue {

    private final String description;
    /**
     * Create from value.
     *
     * @param mediaDescription the media description.
     */
    public MediaDescription(String mediaDescription) {
        description = mediaDescription;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of ASCII encoded text
     */
    public MediaDescription(byte[] bytes) {
        description = new String(bytes, StandardCharsets.US_ASCII);
    }

    @Override
    public byte[] getBytes() {
        return description.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayName() {
        return "Media Description";
    }

    @Override
    public String getDisplayableValue() {
        return description;
    }
}
