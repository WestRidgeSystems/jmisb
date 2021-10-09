package org.jmisb.api.klv.st0602;

public class MediaDescription implements IAnnotationMetadataValue {

    public MediaDescription(byte[] bytes) {}

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public String getDisplayName() {
        return "Media Description";
    }

    @Override
    public String getDisplayableValue() {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }
}
