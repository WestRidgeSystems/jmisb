package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for CompositeImagingLocalSet. */
public class CompositeImagingLocalSetFactory implements IMisbMessageFactory {

    @Override
    public CompositeImagingLocalSet create(byte[] bytes) throws KlvParseException {
        return new CompositeImagingLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return CompositeImagingLocalSet.CompositeImagingLocalSetUl;
    }
}
