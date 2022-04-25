package org.jmisb.api.klv;

import static org.jmisb.api.klv.NothingMessage1.UNIVERSAL_LABEL;

import org.jmisb.api.common.KlvParseException;

/** A test factory. */
public class NothingFactory1 implements IMisbMessageFactory {

    @Override
    public UniversalLabel getUniversalLabel() {
        return UNIVERSAL_LABEL;
    }

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        return new NothingMessage1();
    }
}
