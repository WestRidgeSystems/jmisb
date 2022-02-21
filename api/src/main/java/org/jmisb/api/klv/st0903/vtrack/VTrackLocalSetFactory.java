package org.jmisb.api.klv.st0903.vtrack;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for VTrackLocalSet. */
public class VTrackLocalSetFactory implements IMisbMessageFactory {

    @Override
    public VTrackLocalSet create(byte[] bytes) throws KlvParseException {
        return new VTrackLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return VTrackLocalSet.VTrackLocalSetUl;
    }
}
