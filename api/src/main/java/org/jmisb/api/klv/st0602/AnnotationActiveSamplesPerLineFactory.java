package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/**
 * Factory method for AnnotationByteOrder.
 *
 * <p>The design of ST 0602 requires special handling for three "preface" items - Byte Order, Active
 * Lines per Frame, and Active Samples per Frame. These do need to occur before the Annotation
 * Universal Set (but potentially not just prior to every Annotation Universal Set - only within the
 * previous quarter second) in order to support parsing. In a parsing sense, they are peer items to
 * the Annotation Universal Set.
 *
 * <p>This factory supports parsing of the Active Samples per Line item.
 */
public class AnnotationActiveSamplesPerLineFactory implements IMisbMessageFactory {

    @Override
    public ActiveSamplesPerLineMessage create(byte[] bytes) throws KlvParseException {
        return new ActiveSamplesPerLineMessage(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return ActiveSamplesPerLineMessage.AnnotationActiveSamplesPerLineUl;
    }
}
