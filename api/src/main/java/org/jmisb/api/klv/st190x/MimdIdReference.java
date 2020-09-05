package org.jmisb.api.klv.st190x;

public class MimdIdReference implements IMimdMetadataValue {

    public MimdIdReference(byte[] data, int par, int length) {
        // TODO
    }

    public static IMimdMetadataValue fromBytes(byte[] data) {
        return new MimdIdReference(data, 0, data.length);
    }

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public String getDisplayName() {
        return "REF<>";
    }

    @Override
    public String getDisplayableValue() {
        return "[TODO]";
    }
}
