package org.jmisb.st0808;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for AncillaryTextLocalSet. */
public class AncillaryTextLocalSetFactory implements IMisbMessageFactory {

    @Override
    public AncillaryTextLocalSet create(byte[] bytes) throws KlvParseException {
        return new AncillaryTextLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AncillaryTextLocalSet.AncillaryTextLocalSetUl;
    }
}
