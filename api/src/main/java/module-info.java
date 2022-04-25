/**
 * Primary API for jmisb.
 *
 * <p>This module provides the implementation of the MISB standards.
 */
module org.jmisb.api {
    requires transitive org.jmisb.core;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    exports org.jmisb.api.common;
    exports org.jmisb.api.klv;
    exports org.jmisb.api.klv.st0107;
    exports org.jmisb.api.klv.st0603;
    exports org.jmisb.api.klv.st0604;
    exports org.jmisb.api.klv.st1201;
    exports org.jmisb.api.klv.st1204;
    exports org.jmisb.api.klv.st1303;
}
