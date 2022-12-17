/**
 * MISB ST 1902 - ST 1908 Shared MIMD implementation for jmisb.
 *
 * <p>This module provides the implementation of the MISB Motion Imagery Metadata (MIMD) standards.
 */
module org.jmisb.mimdbase {
    requires org.jmisb.api;
    requires org.slf4j;

    exports org.jmisb.mimd;
}
