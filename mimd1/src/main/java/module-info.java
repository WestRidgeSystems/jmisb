/**
 * MISB ST 1902 - ST 1908 MIMD Version 1 implementation for jmisb.
 *
 * <p>This module provides the implementation of the MISB Motion Imagery Metadata (MIMD) standards.
 * This corresponds to:
 *
 * <ul>
 *   <li>ST 1902.1
 *   <li>ST 1903.1
 *   <li>ST 1904.1
 *   <li>ST 1905.1
 *   <li>ST 1906.1
 *   <li>ST 1907.1
 *   <li>ST 1908.1
 * </ul>
 */
@SuppressWarnings("module") // That is not a software version number - its a document set number.
module org.jmisb.mimd1 {
    requires org.jmisb.api;
    requires org.jmisb.mimdbase;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.mimd.v1.st1902.MimdLocalSetFactory;

    exports org.jmisb.mimd.v1.st1902;
    exports org.jmisb.mimd.v1.st1903;
    exports org.jmisb.mimd.v1.st1904;
    exports org.jmisb.mimd.v1.st1905;
    exports org.jmisb.mimd.v1.st1906;
    exports org.jmisb.mimd.v1.st1907;
    exports org.jmisb.mimd.v1.st1908;
}
