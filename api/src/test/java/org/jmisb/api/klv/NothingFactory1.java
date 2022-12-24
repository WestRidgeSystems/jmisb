package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;

/** A test factory. */
public class NothingFactory1 implements IMisbMessageFactory {

    @Override
    public UniversalLabel getUniversalLabel() {
        return NothingMessage1.UNIVERSAL_LABEL;
    }

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        return new NothingMessage1();
    }
}
