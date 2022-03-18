/**
 * MISB EG 0104 Predator Metadata Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB EG 0104.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.eg0104 {
    requires org.jmisb.core;
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.eg0104.PredatorUavMessageFactory;

    exports org.jmisb.eg0104;
}
