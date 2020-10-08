package org.jmisb.api.klv.st190x;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;

/**
 * MIMD Tuple.
 *
 * <p>TODO: document.
 */
public class Tuple implements IMimdMetadataValue {

    /** Create a Tuple from value. */
    public Tuple() {}

    /**
     * Create a Tuple from encoded bytes.
     *
     * @param data the byte array to parse the Tuple from.
     * @throws KlvParseException if the parsing fails
     */
    public Tuple(byte[] data) throws KlvParseException {}

    /**
     * Parse a Tuple out of a byte array.
     *
     * @param data the byte array to parse the Tuple from.
     * @return Tuple equivalent to the byte array
     * @throws KlvParseException if the parsing fails
     */
    public static IMimdMetadataValue fromBytes(byte[] data) throws KlvParseException {
        return new Tuple(data);
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayName() {
        // TODO: clean up
        return "Tuple";
    }

    @Override
    public String getDisplayableValue() {
        return "[TODO]";
    }
}
