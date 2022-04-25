package org.jmisb.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for AnnotationMetadataUniversalSet. */
public class AnnotationMetadataUniversalSetFactory implements IMisbMessageFactory {

    @Override
    public AnnotationMetadataUniversalSet create(byte[] bytes) throws KlvParseException {
        return new AnnotationMetadataUniversalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AnnotationMetadataUniversalSet.AnnotationUniversalSetUl;
    }
}
