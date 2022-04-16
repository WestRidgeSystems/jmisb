package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;

/** Another test factory. */
public class NothingFactory2 implements IMisbMessageFactory {

    @Override
    public UniversalLabel getUniversalLabel() {
        return NothingMessage2.UNIVERSAL_LABEL;
    }

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        return new NothingMessage2();
    }
}
