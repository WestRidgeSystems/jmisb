package org.jmisb.st0602.layer;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.st0602.AnnotationMetadataUniversalSet;

/**
 * Annotation layers for an image source.
 *
 * <p>This is a collection of the annotation layers, which in turn are sets of annotations at a
 * common Z-Order.
 */
public class AnnotationLayers {

    private final List<AnnotationLayer> annotationLayers = new ArrayList<>();
    private final int activeLines;
    private final int activeSamples;
    private String defaultAuthor;

    /**
     * Constructor.
     *
     * <p>The layers are defined in the context of a parent image, which corresponds to a frame of
     * the video stream that is being annotated. The annotation metadata requires specifying the
     * image size in terms of the Active Lines per Frame, and the Active Samples per Line.
     * Clarification from the MISB is that this is the number of lines (equivalently, the image
     * height in pixels or number of rows) and the number of columns (equivalently, the image width
     * in pixels) in the "as viewed" image including any letterboxing or other padding.
     *
     * @param imageWidth the image width
     * @param imageHeight the image height
     */
    public AnnotationLayers(int imageWidth, int imageHeight) {
        this.activeSamples = imageWidth;
        this.activeLines = imageHeight;
    }

    /**
     * Add a new layer to the annotation layers.
     *
     * <p>This version uses the default Z-Order ({@code 50}).
     *
     * @return the layer that has been added.
     */
    public AnnotationLayer addLayer() {
        AnnotationLayer annotationLayer = new AnnotationLayer(activeSamples, activeLines);
        if (defaultAuthor != null) {
            annotationLayer.setAuthor(defaultAuthor);
        }
        annotationLayers.add(annotationLayer);
        return annotationLayer;
    }

    /**
     * Add a new layer to the annotation layers.
     *
     * <p>This version allows the Z-Order to be specified. Layers with lower Z-Order values are
     * rendered first, and layers with higher values will be rendered above (on top).
     *
     * @param zOrder the Z-Order value.
     * @return the layer that has been added.
     */
    public AnnotationLayer addLayer(int zOrder) {
        AnnotationLayer annotationLayer = new AnnotationLayer(activeSamples, activeLines, zOrder);
        if (defaultAuthor != null) {
            annotationLayer.setAuthor(defaultAuthor);
        }
        annotationLayers.add(annotationLayer);
        return annotationLayer;
    }

    /**
     * Get the Universal Set structures corresponding to the layers.
     *
     * <p>This function should be called periodically in order to serialise out the annotations -
     * ideally when anything has changed, but at least every 5 seconds.
     *
     * @return the list of AnnotationMetadataUniversalSets, ready to be serialised out.
     */
    public List<AnnotationMetadataUniversalSet> getUniversalSets() {
        List<AnnotationMetadataUniversalSet> sets = new ArrayList();
        annotationLayers.forEach(layer -> sets.addAll(layer.getUniversalSets()));
        return sets;
    }

    /**
     * The image width.
     *
     * <p>This is the width (X-direction extent) of the image on which annotations are being
     * created.
     *
     * @return the image width in pixels
     */
    public int getImageWidth() {
        return activeSamples;
    }

    /**
     * The image height.
     *
     * <p>This is the height (Y-direction extent) of the image on which annotations are being
     * created.
     *
     * @return the image height in pixels
     */
    public int getImageHeight() {
        return activeLines;
    }

    /**
     * The default author.
     *
     * <p>This will be used for newly created layers if non-null.
     *
     * @return the default author value, which can be null.
     */
    public String getDefaultAuthor() {
        return defaultAuthor;
    }

    /**
     * The default author.
     *
     * <p>This will be used for newly created layers if non-null.
     *
     * <p>Individual layers can have different authors - use {@code setAuthor} on the layer.
     *
     * @param defaultAuthor the default author value, which can be null.
     */
    public void setDefaultAuthor(String defaultAuthor) {
        this.defaultAuthor = defaultAuthor;
    }
}
