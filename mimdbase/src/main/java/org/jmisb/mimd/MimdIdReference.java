package org.jmisb.mimd;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

/**
 * MIMD Id Reference.
 *
 * <p>This models the MIMD "Ref" usage, where a value can cross-reference to another value by group
 * identifier and serial number.
 */
public class MimdIdReference implements IMimdMetadataValue {

    private final int serialNumber;
    private final int groupId;
    private final String displayName;
    private final String refTargetType;

    /**
     * Construct a MimdIdReference from values.
     *
     * @param serialNumber the serial number of the reference item
     * @param groupIdentifier the group identifier of the reference item
     * @param name the display name of the reference
     * @param target the class type this reference points to
     */
    public MimdIdReference(int serialNumber, int groupIdentifier, String name, String target) {
        this.serialNumber = serialNumber;
        this.groupId = groupIdentifier;
        this.displayName = name;
        this.refTargetType = target;
    }

    /**
     * Construct a MimdIdReference from encoded bytes.
     *
     * @param data the bytes to build from
     * @param offset the offset into {@code bytes} to start parsing from
     * @param numBytes the number of bytes to parse
     * @param name the display name of the reference
     * @param target the class type this reference points to
     * @throws KlvParseException if parsing fails
     */
    public MimdIdReference(byte[] data, final int offset, int numBytes, String name, String target)
            throws KlvParseException {
        int index = offset;
        try {
            BerField serialIdField = BerDecoder.decode(data, index, true);
            serialNumber = serialIdField.getValue();
            index += serialIdField.getLength();
            if (index < offset + numBytes) {
                BerField groupIdField = BerDecoder.decode(data, index, true);
                groupId = groupIdField.getValue();
            } else {
                groupId = 0;
            }
            this.displayName = name;
            this.refTargetType = target;
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Build a MimdIdReference from encoded bytes.
     *
     * @param data the bytes to build from
     * @param name the display name of the reference
     * @param target the class type this reference points to
     * @return new MimdIdReference corresponding to the encoded byte array.
     * @throws KlvParseException if parsing fails
     */
    public static MimdIdReference fromBytes(byte[] data, String name, String target)
            throws KlvParseException {
        return new MimdIdReference(data, 0, data.length, name, target);
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
        return this.displayName;
    }

    @Override
    public String getDisplayableValue() {
        return "REF<" + refTargetType + ">(" + serialNumber + ", " + groupId + ')';
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
