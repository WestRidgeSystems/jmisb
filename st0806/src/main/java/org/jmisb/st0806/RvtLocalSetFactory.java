package org.jmisb.st0806;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory for RvtLocalSet. */
public class RvtLocalSetFactory implements IMisbMessageFactory {

    @Override
    public RvtLocalSet create(byte[] bytes) throws KlvParseException {
        return new RvtLocalSet(bytes, false);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return RvtLocalSet.RvtLocalSetUl;
    }
}
