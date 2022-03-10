package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for TimeTransferLocalSet. */
public class TimeTransferLocalSetFactory implements IMisbMessageFactory {

    @Override
    public TimeTransferLocalSet create(byte[] bytes) throws KlvParseException {
        return new TimeTransferLocalSet(bytes);
    }
}