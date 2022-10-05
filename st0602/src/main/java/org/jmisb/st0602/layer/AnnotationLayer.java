package org.jmisb.st0602.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0602.ActiveLinesPerFrame;
import org.jmisb.st0602.ActiveSamplesPerLine;
import org.jmisb.st0602.AnnotationMetadataKey;
import org.jmisb.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.st0602.AnnotationSource;
import org.jmisb.st0602.EventIndication;
import org.jmisb.st0602.EventIndicationKind;
import org.jmisb.st0602.IAnnotationMetadataValue;
import org.jmisb.st0602.LocallyUniqueIdentifier;
import org.jmisb.st0602.MIMEData;
import org.jmisb.st0602.MIMEMediaType;
import org.jmisb.st0602.ModificationHistory;
import org.jmisb.st0602.XViewportPosition;
import org.jmisb.st0602.YViewportPosition;
import org.jmisb.st0602.ZOrder;

/**
 * Annotation layer.
 *
 * <p>An annotation layer is essentially a collection of {@link Annotation}s that share common data.
 */
public class AnnotationLayer {

    private final Map<Integer, Annotation> annotations = new HashMap<>();
    private final int activeLines;
    private final int activeSamples;
    private String author = "jmisb";
    private int zOrder = 50;

    /**
     * Constructor.
     *
     * <p>The layer is defined in the context of a parent image, which corresponds to a frame of the
     * video stream that is being annotated. The annotation metadata requires specifying the image
     * size in terms of the Active Lines per Frame, and the Active Samples per Line. Clarification
     * from the MISB is that this is the number of lines (equivalently, the image height in pixels
     * or number of rows) and the number of columns (equivalently, the image width in pixels) in the
     * "as viewed" image including any letterboxing or other padding.
     *
     * <p>This sets a default zOrder of 50.
     *
     * @param imageWidth the image width
     * @param imageHeight the image height
     */
    AnnotationLayer(int imageWidth, int imageHeight) {
        this.activeSamples = imageWidth;
        this.activeLines = imageHeight;
    }

    /**
     * Constructor.
     *
     * <p>The layer is defined in the context of a parent image, which corresponds to a frame of the
     * video stream that is being annotated. The annotation metadata requires specifying the image
     * size in terms of the Active Lines per Frame, and the Active Samples per Line. Clarification
     * from the MISB is that this is the number of lines (equivalently, the image height in pixels
     * or number of rows) and the number of columns (equivalently, the image width in pixels) in the
     * "as viewed" image including any letterboxing or other padding.
     *
     * @param imageWidth the image width
     * @param imageHeight the image height
     * @param zOrder the zOrder
     */
    AnnotationLayer(int imageWidth, int imageHeight, int zOrder) {
        this.activeSamples = imageWidth;
        this.activeLines = imageHeight;
        this.zOrder = zOrder;
    }

    /**
     * Add an annotation entry to the layer.
     *
     * @param id the locally unique identifier for the annotation
     * @param annotation the annotation to add
     */
    public void addAnnotationEntry(int id, Annotation annotation) {
        annotations.put(id, annotation);
    }

    /**
     * Move an annotation to a different location on the screen.
     *
     * <p>This keeps all of the annotation information the same. The annotation is translated to a
     * new location.
     *
     * <p>The location corresponds to the the X and Y Viewport Position in Pixels values from ST
     * 0602, and is the location of the media reference point, typically the image origin, but is
     * defined independently for each data type. For PNG, JPEG and SVG, this is the top left corner.
     * For BMP, this is the lower left corner. For CGM, this is the VDC origin. The X and Y position
     * is referenced based upon a (0, 0) origin in the upper-left corner of the original essence
     * data image.
     *
     * @param id the locally unique identifier for the annotation to move.
     * @param x the new X (column) location.
     * @param y the new Y (row) location.
     */
    public void moveAnnotation(int id, int x, int y) {
        annotations.get(id).moveTo(x, y);
    }

    /**
     * Modify an annotation that is on the layer.
     *
     * <p>This only allows changing the data (e.g. the annotation shape), not the location. However
     * a previous or subsequent call to {@link moveAnnotation} can be used to translate the new
     * shape.
     *
     * @param id the locally unique identifier for the annotation to move.
     * @param mediaType the media type (e.g. {@code image/png} for the data
     * @param data the annotation as a byte array, in the format specified by the mediaType.
     */
    public void modifyAnnotation(int id, String mediaType, byte[] data) {
        annotations.get(id).setMediaType(mediaType);
        annotations.get(id).setMediaData(data);
    }

    /**
     * Delete an annotation entry.
     *
     * <p>Before remove from the layer structure, it first needs to be output in the KLV stream.
     * Only after a successful call to {@link getUniversalSets} will the annotation be removed.
     *
     * @param id the locally unique identifier for the annotation to delete
     */
    public void deleteAnnotationEntry(int id) {
        annotations.put(id, Annotation.getDeleteAnnotation());
    }

    /**
     * Get the Universal Set structures corresponding to the layer.
     *
     * @return the list of AnnotationMetadataUniversalSets, ready to be serialised out.
     */
    List<AnnotationMetadataUniversalSet> getUniversalSets() {
        List<AnnotationMetadataUniversalSet> sets = new ArrayList<>();
        List<Integer> toDelete = new ArrayList<>();
        boolean isFirst = true;
        for (Entry<Integer, Annotation> entry : annotations.entrySet()) {
            AnnotationMetadataUniversalSet set = generateUniversalSet(entry, isFirst);
            if (set != null) {
                sets.add(set);
                if (entry.getValue().getState().equals(EventIndicationKind.DELETE)) {
                    toDelete.add(entry.getKey());
                } else {
                    entry.getValue().setState(EventIndicationKind.STATUS);
                }
                isFirst = false;
            }
        }
        toDelete.forEach(id -> annotations.remove(id));
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
     * The author of the most recent significant event.
     *
     * <p>This corresponds to "Modification History" in ST 0602, which is defined as "Identification
     * of most recent significant event’s author".
     *
     * @return the author as a String.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the most recent significant event.
     *
     * <p>This corresponds to "Modification History" in ST 0602, which is defined as "Identification
     * of most recent significant event’s author". If not set, this will default to {@code jmisb}.
     *
     * <p>The allowed encoding for this value is ISO/IEC 646:1991, which is treated as US-ASCII for
     * implementation purposes. For maximum compatibility, avoid using characters in the variant set
     * (primarily punctuation, a notable inclusion being &amp;).
     *
     * @param author the author as a String.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * The Z-Order for the layer.
     *
     * <p>Lower Z-Order values are rendered first, with higher values rendered above (on top).
     *
     * @return the Z-Order as an integer value.
     */
    public int getZOrder() {
        return zOrder;
    }

    /**
     * Set the Z-Order for this layer.
     *
     * <p>Lower Z-Order values are rendered first, with higher values rendered above (on top).
     *
     * @param zOrder the Z-Order as an integer value.
     */
    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    /** Clear the annotations from this layer. */
    public void clear() {
        annotations.clear();
    }

    private AnnotationMetadataUniversalSet generateUniversalSet(
            Entry<Integer, Annotation> annotation, boolean first) {
        SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> values = new TreeMap<>();
        if (annotation.getValue() != null) {
            try {
                if (first) {
                    values.put(
                            AnnotationMetadataKey.ActiveLinesPerFrame,
                            new ActiveLinesPerFrame(activeLines));
                    values.put(
                            AnnotationMetadataKey.ActiveSamplesPerLine,
                            new ActiveSamplesPerLine(activeSamples));
                }
                values.put(
                        AnnotationMetadataKey.LocallyUniqueIdentifier,
                        new LocallyUniqueIdentifier(annotation.getKey()));
                EventIndicationKind state = annotation.getValue().getState();
                // ST 0602.4-09
                values.put(AnnotationMetadataKey.EventIndication, new EventIndication(state));
                if (state != EventIndicationKind.MOVE) {
                    // NEW, MODIFY, STATUS - ST 0604.4-12
                    // DELETE - ST 0602.4-14
                    values.put(
                            AnnotationMetadataKey.ModificationHistory,
                            new ModificationHistory(author));
                }
                if (state != EventIndicationKind.DELETE) {
                    // NEW, MODIFY, STATUS - ST 0604.4-12
                    // MOVE - ST 0602.4-13
                    values.put(
                            AnnotationMetadataKey.XViewportPosition,
                            new XViewportPosition(annotation.getValue().getXPosition()));
                    values.put(
                            AnnotationMetadataKey.YViewportPosition,
                            new YViewportPosition(annotation.getValue().getYPosition()));
                    values.put(AnnotationMetadataKey.ZOrder, new ZOrder(zOrder));
                }
                if ((state == EventIndicationKind.NEW)
                        || (state == EventIndicationKind.MODIFY)
                        || (state == EventIndicationKind.STATUS)) {
                    // ST 0604.4-12
                    values.put(
                            AnnotationMetadataKey.MIMEMediaType,
                            new MIMEMediaType(annotation.getValue().getMediaType()));
                    values.put(
                            AnnotationMetadataKey.MIMEData,
                            new MIMEData(annotation.getValue().getMediaData()));
                }
                if ((state == EventIndicationKind.NEW) || (state == EventIndicationKind.STATUS)) {
                    // NEW - ST 0604.4-15
                    // STATUS - ST 0604.4-16
                    values.put(
                            AnnotationMetadataKey.AnnotationSource,
                            new AnnotationSource(annotation.getValue().getSource()));
                }
                return new AnnotationMetadataUniversalSet(values);
            } catch (KlvParseException ex) {
                // TODO: log
                return null;
            }
        }
        return null;
    }
}
