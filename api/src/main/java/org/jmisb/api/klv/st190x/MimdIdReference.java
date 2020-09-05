package org.jmisb.api.klv.st190x;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

public class MimdIdReference implements IMimdMetadataValue {

    private long serialId;
    private long groupId = 0;

    /**
     * Construct a MimdIdReference from encoded bytes.
     *
     * @param data the bytes to build from
     * @param offset the offset into {@code bytes} to start parsing from
     * @param numBytes the number of bytes to parse
     * @throws KlvParseException if parsing fails
     */
    public MimdIdReference(byte[] data, final int offset, int numBytes) throws KlvParseException {
        int index = offset;
        try {
            BerField serialIdField = BerDecoder.decode(data, index, true);
            serialId = serialIdField.getValue();
            index += serialIdField.getLength();
            if (index < offset + numBytes) {
                BerField groupIdField = BerDecoder.decode(data, index, true);
                groupId = groupIdField.getValue();
            }
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Build a MimdIdReference from encoded bytes.
     *
     * @param data the bytes to build from
     * @return new MimdIdReference corresponding to the encoded byte array.
     * @throws KlvParseException if parsing fails
     */
    public static MimdIdReference fromBytes(byte[] data) throws KlvParseException {
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

    /**
     * Get the {@code MimdId} serial identifier that this reference points to.
     *
     * @return serial identifier
     */
    public long getSerialId() {
        return serialId;
    }

    /**
     * Get the {@code MimdId} group identifier that this reference points to.
     *
     * @return group identifier
     */
    public long getGroupId() {
        return groupId;
    }
}
