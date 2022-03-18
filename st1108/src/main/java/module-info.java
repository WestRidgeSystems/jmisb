/**
 * MISB ST 1108 Interpretability and Quality Local Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 1108.2 and ST 1108.3 standards.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1108 {
    requires org.jmisb.core;
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st1108.InterpretabilityQualityLocalSetFactory;

    exports org.jmisb.st1108;
    exports org.jmisb.st1108.st1108_2;
    exports org.jmisb.st1108.st1108_3;
    exports org.jmisb.st1108.st1108_3.metric;
}
