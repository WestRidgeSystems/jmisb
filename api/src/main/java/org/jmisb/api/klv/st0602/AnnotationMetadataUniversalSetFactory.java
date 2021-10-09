package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for AnnotationMetadataUniversalSet. */
public class AnnotationMetadataUniversalSetFactory implements IMisbMessageFactory {

    @Override
    public AnnotationMetadataUniversalSet create(byte[] bytes) throws KlvParseException {
        return new AnnotationMetadataUniversalSet(bytes);
    }
}
