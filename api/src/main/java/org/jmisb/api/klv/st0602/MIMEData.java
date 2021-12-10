package org.jmisb.api.klv.st0602;

/**
 * MIME encoded data of annotation message.
 *
 * <p>This is the data part of the annotation. For example, if the annotation is specified (via
 * {@link MIMEMediaType}) to be be PNG, this the data corresponding to the PNG image.
 */
public class MIMEData implements IAnnotationMetadataValue {

    private final byte[] data;

    /**
     * Construct from byte array.
     *
     * @param bytes the data
     */
    public MIMEData(byte[] bytes) {
        data = bytes;
    }

    @Override
    public byte[] getBytes() {
        return data;
    }

    @Override
    public String getDisplayName() {
        return "MIME Data";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("byte[%d]", data.length);
    }
}
