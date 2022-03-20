/**
 * MISB ST 0602 Annotation Metadata Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 0602 annotation standard.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0602 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0602.AnnotationActiveLinesPerFrameFactory,
            org.jmisb.st0602.AnnotationActiveSamplesPerLineFactory,
            org.jmisb.st0602.AnnotationByteOrderFactory,
            org.jmisb.st0602.AnnotationMetadataUniversalSetFactory;

    exports org.jmisb.st0602;
    exports org.jmisb.st0602.layer;
}
