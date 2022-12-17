/**
 * MISB ST 1902 - ST 1908 MIMD Version 2 implementation for jmisb.
 *
 * <p>This module provides the implementation of the MISB Motion Imagery Metadata (MIMD) standards.
 * This corresponds to:
 *
 * <ul>
 *   <li>ST 1902.2
 *   <li>ST 1903.2
 *   <li>ST 1904.2
 *   <li>ST 1905.2
 *   <li>ST 1906.2
 *   <li>ST 1907.2
 *   <li>ST 1908.2
 * </ul>
 */
@SuppressWarnings("module") // That is not a software version number - its a document set number.
module org.jmisb.mimd2 {
    requires org.jmisb.api;
    requires org.jmisb.mimdbase;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.mimd.v2.st1902.MimdLocalSetFactory;

    exports org.jmisb.mimd.v2.st1902;
    exports org.jmisb.mimd.v2.st1903;
    exports org.jmisb.mimd.v2.st1904;
    exports org.jmisb.mimd.v2.st1905;
    exports org.jmisb.mimd.v2.st1906;
    exports org.jmisb.mimd.v2.st1907;
    exports org.jmisb.mimd.v2.st1908;
}
