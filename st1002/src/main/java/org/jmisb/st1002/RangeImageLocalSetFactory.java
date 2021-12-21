package org.jmisb.st1002;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for RangeImageLocalSet. */
public class RangeImageLocalSetFactory implements IMisbMessageFactory {

    @Override
    public RangeImageLocalSet create(byte[] bytes) throws KlvParseException {
        return new RangeImageLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return RangeImageLocalSet.RangeImageLocalSetUl;
    }
}
