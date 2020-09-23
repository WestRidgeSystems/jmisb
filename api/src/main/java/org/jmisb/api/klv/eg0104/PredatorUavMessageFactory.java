package org.jmisb.api.klv.eg0104;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for PredatorUavMessage. */
public class PredatorUavMessageFactory implements IMisbMessageFactory {

    @Override
    public PredatorUavMessage create(byte[] bytes) throws KlvParseException {
        return new PredatorUavMessage(bytes);
    }
}
