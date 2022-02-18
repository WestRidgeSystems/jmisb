/**
 * jmisb utility methods.
 *
 * <p>This package provides shared implementation of methods that are commonly needed in KLV, video
 * and other metadata processing, such as operations on byte arrays.
 */
module org.jmisb.core {
    requires org.slf4j;

    exports org.jmisb.core.klv;
    exports org.jmisb.core.video;
}
