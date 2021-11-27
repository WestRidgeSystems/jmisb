package org.jmisb.api.klv.st0808;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for AncillaryTextLocalSet. */
public class AncillaryTextLocalSetFactory implements IMisbMessageFactory {

    @Override
    public AncillaryTextLocalSet create(byte[] bytes) throws KlvParseException {
        return new AncillaryTextLocalSet(bytes);
    }
}
