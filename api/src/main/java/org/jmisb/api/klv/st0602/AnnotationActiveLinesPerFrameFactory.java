package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/**
 * Factory method for AnnotationByteOrder.
 *
 * <p>The design of ST 0602 requires special handling for three "preface" items - Byte Order, Active
 * Lines per Frame, and Active Samples per Frame. These do need to occur before the Annotation
 * Universal Set (but potentially not just prior to every Annotation Universal Set - only within the
 * previous quarter second) in order to support parsing. In a parsing sense, they are peer items to
 * the Annotation Universal Set.
 *
 * <p>This factory supports parsing of the Active Lines per Frame item.
 */
public class AnnotationActiveLinesPerFrameFactory implements IMisbMessageFactory {

    @Override
    public ActiveLinesPerFrameMessage create(byte[] bytes) throws KlvParseException {
        return new ActiveLinesPerFrameMessage(bytes);
    }
}
