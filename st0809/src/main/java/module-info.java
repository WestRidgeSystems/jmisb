/**
 * MISB ST 0809 Meteorological Metadata Local Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 0809 standard.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0809 {
    requires org.jmisb.core;
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0809.MeteorologicalMetadataLocalSetFactory;

    exports org.jmisb.st0809;
}
