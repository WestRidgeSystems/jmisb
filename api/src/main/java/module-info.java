/**
 * Primary API for jmisb.
 *
 * <p>This module provides the implementation of the MISB standards.
 */
module org.jmisb.api {
    requires transitive org.jmisb.core;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.api.klv.st0102.universalset.SecurityMetadataUniversalSetFactory,
            org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSetFactory,
            org.jmisb.api.klv.st1301.MiisLocalSetFactory;

    exports org.jmisb.api.common;
    exports org.jmisb.api.klv;
    exports org.jmisb.api.klv.st0102;
    exports org.jmisb.api.klv.st0102.localset;
    exports org.jmisb.api.klv.st0102.universalset;
    exports org.jmisb.api.klv.st0107;
    exports org.jmisb.api.klv.st0603;
    exports org.jmisb.api.klv.st0604;
    exports org.jmisb.api.klv.st1201;
    exports org.jmisb.api.klv.st1204;
    exports org.jmisb.api.klv.st1206;
    exports org.jmisb.api.klv.st1301;
    exports org.jmisb.api.klv.st1303;
}
