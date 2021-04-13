package org.jmisb.api.klv.st1902;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

/**
 * MIMD Identifier.
 *
 * <p>The MIMD instance Identifier (mimdId) is a pair of two unsigned integer values that uniquely
 * identifies a specific instance of a class within the MIMD Model.
 *
 * <p>The first unsigned integer is an instance identifier serial number. The minimum serial number
 * value is one; however, classes may have attributes that reference a serial number set to zero to
 * indicate special meanings (e.g. a MISB ST 1906 Stage class instance uses a parentStage reference
 * of zero to signify the Stage is a root stage).
 *
 * <p>The second unsigned integer is the group identifier to which the instance belongs. The first
 * group is group zero which is the default group. If an instance belongs to the default group, the
 * mimdId does not need to specify the group’s identity, which saves bandwidth. Systems using more
 * than one group need to add a group identifier to each mimdId when the group is greater than zero.
 *
 * <p>Together the serial number and group identifier provide a unique identity within the whole
 * MIMD Model instance hierarchy.
 *
 * <p>When creating class instances the mimdId attribute is optional unless another class instance
 * references it. When a class instance does not have other class instances referencing it there is
 * no need to create a mimdId, thus reducing data bits and saving bandwidth. To allow for
 * packet-to-packet instance correlation (e.g., Report-on-Change), the mimdId remains constant for a
 * class instance from MIMD Packet-to-MIMD Packet for the duration of the MIMD Stream.
 */
public class MimdId implements IMimdMetadataValue {

    private final int serialNumber;
    private final int groupIdentifier;

    /**
     * Create a MimdId from value using default group identifier.
     *
     * @param serialNumber the serial number of this MimdId (not zero)
     */
    public MimdId(int serialNumber) {
        this(serialNumber, 0);
    }

    /**
     * Create a MimdId from values.
     *
     * @param serialNumber the serial number of this MimdId
     * @param groupIdentifier the group identifier for this MimdId
     */
    public MimdId(int serialNumber, int groupIdentifier) {
        this.serialNumber = serialNumber;
        this.groupIdentifier = groupIdentifier;
    }

    /**
     * Create a MimdId from encoded bytes.
     *
     * @param data the byte array to parse the MimdId from.
     * @throws KlvParseException if the parsing fails
     */
    public MimdId(byte[] data) throws KlvParseException {
        try {
            BerField serialField = BerDecoder.decode(data, 0, true);
            serialNumber = serialField.getValue();
            if (serialField.getLength() < data.length) {
                BerField groupField = BerDecoder.decode(data, serialField.getLength(), true);
                groupIdentifier = groupField.getValue();
            } else {
                groupIdentifier = 0;
            }
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Parse a MimdId out of a byte array.
     *
     * @param data the byte array to parse the MimdId from.
     * @return MimdId equivalent to the byte array
     * @throws KlvParseException if the parsing fails
     */
    public static MimdId fromBytes(byte[] data) throws KlvParseException {
        return new MimdId(data);
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.appendAsOID(serialNumber);
        if (groupIdentifier != 0) {
            arrayBuilder.appendAsOID(groupIdentifier);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayName() {
        return "MIMD Id";
    }

    @Override
    public String getDisplayableValue() {
        return "[" + serialNumber + ", " + groupIdentifier + ']';
    }

    /**
     * The serial number for this MimdId.
     *
     * @return serial number as an unsigned integer value
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * The group identifier for this MimdId.
     *
     * <p>Group identifier 0 is the default group.
     *
     * @return group identifier as an unsigned integer value
     */
    public int getGroupIdentifier() {
        return groupIdentifier;
    }

    @Override
    public String toString() {
        return "MimdId{"
                + "serialNumber="
                + serialNumber
                + ", groupIdentifier="
                + groupIdentifier
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.serialNumber;
        hash = 29 * hash + this.groupIdentifier;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MimdId other = (MimdId) obj;
        if (this.serialNumber != other.serialNumber) {
            return false;
        }
        if (this.groupIdentifier != other.groupIdentifier) {
            return false;
        }
        return true;
    }
}
