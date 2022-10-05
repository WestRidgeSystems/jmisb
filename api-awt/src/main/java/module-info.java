/**
 * AWT integration support.
 *
 * <p>This module provides AWT (part of the {@code java.desktop} module) integration for jmisb. This
 * currently includes rendering of annotations and metadata overlays on video frames.
 */
module org.jmisb.api.awt {
    requires org.jmisb.api;
    requires transitive org.jmisb.st0602;
    requires transitive org.jmisb.st1909;
    requires java.desktop;
    requires com.kitfox.svg;
    requires org.slf4j;

    exports org.jmisb.api.awt.st0602;
    exports org.jmisb.api.awt.st1909;
}
