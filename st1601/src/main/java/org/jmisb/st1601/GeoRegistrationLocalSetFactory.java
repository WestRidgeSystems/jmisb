package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for GeoRegistrationLocalSet. */
public class GeoRegistrationLocalSetFactory implements IMisbMessageFactory {

    @Override
    public GeoRegistrationLocalSet create(byte[] bytes) throws KlvParseException {
        return new GeoRegistrationLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return GeoRegistrationLocalSet.GeoRegistrationLocalSetUl;
    }
}
