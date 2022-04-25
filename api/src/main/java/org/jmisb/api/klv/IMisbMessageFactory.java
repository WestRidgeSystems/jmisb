package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;

/** Interface for IMisbMessage factory creation. */
public interface IMisbMessageFactory {

    /**
     * Universal Label associated with this message factory.
     *
     * <p>This is used to look up the factory to use to parse the corresponding message.
     *
     * @return the universal label for the factory.
     */
    UniversalLabel getUniversalLabel();

    /**
     * Create a new {@link IMisbMessage} instance from encoded bytes.
     *
     * <p>The bytes must contain the 16-byte {@link UniversalLabel} that is used to select the
     * message handler to use.
     *
     * @param bytes the encoded bytes.
     * @return IMisbMessage implementation.
     * @throws KlvParseException if the parsing failed.
     */
    IMisbMessage create(byte[] bytes) throws KlvParseException;
}
