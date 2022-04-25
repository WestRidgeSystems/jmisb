package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for {@link MeteorologicalMetadataLocalSet}. */
public class MeteorologicalMetadataLocalSetFactory implements IMisbMessageFactory {

    @Override
    public MeteorologicalMetadataLocalSet create(byte[] bytes) throws KlvParseException {
        return new MeteorologicalMetadataLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return MeteorologicalMetadataLocalSet.MeteorologicalMetadataLocalSetUl;
    }
}
