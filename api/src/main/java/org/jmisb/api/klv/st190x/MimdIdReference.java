package org.jmisb.api.klv.st190x;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

public class MimdIdReference implements IMimdMetadataValue {

    private int serialNumber;
    private int groupId = 0;

    /**
     * Construct a MimdIdReference from values.
     *
     * @param serialNumber the serial number of the reference item
     * @param groupIdentifier the group identifier of the reference item
     */
    public MimdIdReference(int serialNumber, int groupIdentifier) {
        this.serialNumber = serialNumber;
        this.groupId = groupIdentifier;
    }

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
            serialNumber = serialIdField.getValue();
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
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.appendAsOID(serialNumber);
        if (groupId != 0) {
            arrayBuilder.appendAsOID(groupId);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayName() {
        return "REF<>";
    }

    @Override
    public String getDisplayableValue() {
        return "[" + serialNumber + ", " + groupId + ']';
    }

    /**
     * Get the {@code MimdId} serial number that this reference points to.
     *
     * @return serial number
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * Get the {@code MimdId} group identifier that this reference points to.
     *
     * @return group identifier
     */
    public int getGroupId() {
        return groupId;
    }
}
