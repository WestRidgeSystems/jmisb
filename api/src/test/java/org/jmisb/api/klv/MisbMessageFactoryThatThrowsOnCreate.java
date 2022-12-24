package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;

/** A test factory to check error handling */
public class MisbMessageFactoryThatThrowsOnCreate implements IMisbMessageFactory {

    // This isn't real - only used for unit testing.
    public static final UniversalLabel UNIVERSAL_LABEL =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x03
                    });

    @Override
    public UniversalLabel getUniversalLabel() {
        return UNIVERSAL_LABEL;
    }

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        throw new KlvParseException("MisbMessageFactoryThatThrowsOnCreate::create()");
    }
}
