package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for SecurityMetadataLocalSet. */
public class SecurityMetadataLocalSetFactory implements IMisbMessageFactory {

    @Override
    public SecurityMetadataLocalSet create(byte[] bytes) throws KlvParseException {
        return new SecurityMetadataLocalSet(bytes, true);
    }
}
