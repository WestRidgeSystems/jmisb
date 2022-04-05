/**
 * MISB ST 1902 - ST 1908 MIMD implementation for jmisb.
 *
 * <p>This module provides the implementation of the MISB Motion Imagery Metadata (MIMD) standards.
 */
module org.jmisb.mimd {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.mimd.st1902.MimdLocalSetFactory;

    exports org.jmisb.mimd.st1902;
    exports org.jmisb.mimd.st1903;
    exports org.jmisb.mimd.st1904;
    exports org.jmisb.mimd.st1905;
    exports org.jmisb.mimd.st1906;
    exports org.jmisb.mimd.st1907;
    exports org.jmisb.mimd.st1908;
}
