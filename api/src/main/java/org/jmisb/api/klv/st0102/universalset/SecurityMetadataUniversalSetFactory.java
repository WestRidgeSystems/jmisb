package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for SecurityMetadataUniversalSet. */
public class SecurityMetadataUniversalSetFactory implements IMisbMessageFactory {

    @Override
    public SecurityMetadataUniversalSet create(byte[] bytes) throws KlvParseException {
        return new SecurityMetadataUniversalSet(bytes);
    }
}
