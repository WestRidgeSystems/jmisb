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
package org.jmisb.st1602;
