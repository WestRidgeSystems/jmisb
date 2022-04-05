/**
 * ST 1909 Metadata Overlay for Visualization.
 *
 * <p>This standard defines a graphical, non-destructive, metadata overlay for Motion Imagery
 * visualization. The content display locations scale according to a display’s native spatial
 * density.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1909 {
    requires org.jmisb.api;

    exports org.jmisb.st1909;
}