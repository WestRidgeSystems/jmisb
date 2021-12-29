package org.jmisb.api.klv.st1902;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.st1903.MIMD;

/** Factory method for MIMD. */
public class MimdLocalSetFactory implements IMisbMessageFactory {

    @Override
    public MIMD create(byte[] bytes) throws KlvParseException {
        return new MIMD(bytes);
    }
}
