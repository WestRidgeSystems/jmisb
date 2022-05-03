/**
 * ST 1602: Composite Imaging Local Set.
 *
 * <p>This standard defines a KLV metadata Local Set which enables a unique assignment of image-
 * attribute metadata to several images composited into one image frame.
 *
 * <p>The standard supports image compositing such as: tiling of views from different sensors,
 * blending of images, a stacked series of images, and picture-in-picture.
 *
 * <p>The standard applies to rectangular geometric images only; that is, rectangles-on-rectangles.
 *
 * <p>The standard assumes reuse of the Segment Local Set (see MISB ST 1607) within an instantiating
 * metadata set.
 *
 * <p>The standard does not support dynamic overlays under direction of a client receiver
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1602 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st1602.CompositeImagingLocalSetFactory;

    exports org.jmisb.st1602;
}
