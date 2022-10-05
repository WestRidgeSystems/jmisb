package org.jmisb.api.awt.st0602;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.st0602.AnnotationMetadataKey;
import org.jmisb.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.st0602.EventIndication;
import org.jmisb.st0602.EventIndicationKind;
import org.jmisb.st0602.IAnnotationMetadataValue;
import org.jmisb.st0602.LocallyUniqueIdentifier;
import org.jmisb.st0602.MIMEMediaType;
import org.jmisb.st0602.XViewportPosition;
import org.jmisb.st0602.YViewportPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Support for ST 0602 style annotations.
 *
 * <p>This class takes BufferedImages and renders annotations according to MISB ST 0602 "Annotation
 * Metadata Set". It supports PNG, BMP, JPEG and SVG annotations. There is currently no support for
 * rendering CGM annotations.
 *
 * <p>The concept is that the {@code AnnotationsRenderer} class is instantiated, and then as {@link
 * AnnotationMetadataUniversalSet} instances are received in the KLV stream, they are pushed into
 * the Annotations state using {@link updateSet}. Meanwhile, as video frames are being displayed,
 * the {@link render} method is called to render annotations over the top of the {@link
 * BufferedImage}. When the annotations are no longer relevant (for example, at the end of a file),
 * the consumer can call {@link clear()} to clear the state before starting a new file.
 */
public class AnnotationsRenderer {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationsRenderer.class);

    private final Map<Long, AnnotationMetadataUniversalSet> sets = new ConcurrentHashMap<>();

    /**
     * Update the Annotation state with an AnnotationMetadataUniversalSet.
     *
     * <p>This handles the various annotation event types (e.g. a DELETE event will remove the
     * annotation from the current set).
     *
     * @param set the annotation as a UniversalSet.
     */
    public void updateSet(AnnotationMetadataUniversalSet set) {
        if (set.getIdentifiers().contains(AnnotationMetadataKey.EventIndication)
                && set.getIdentifiers().contains(AnnotationMetadataKey.LocallyUniqueIdentifier)) {
            LocallyUniqueIdentifier id =
                    (LocallyUniqueIdentifier)
                            set.getField(AnnotationMetadataKey.LocallyUniqueIdentifier);
            EventIndication eventIndication =
                    (EventIndication) set.getField(AnnotationMetadataKey.EventIndication);
            EventIndicationKind eventKind = eventIndication.getEventIndicationKind();
            switch (eventKind) {
                case NEW:
                    sets.put(id.getIdentifier(), set);
                    break;
                case DELETE:
                    sets.remove(id.getIdentifier());
                    break;
                case MOVE:
                case MODIFY:
                case STATUS:
                    if (sets.containsKey(id.getIdentifier())) {
                        sets.get(id.getIdentifier()).mergeAndUpdate(set);
                    } else {
                        sets.put(id.getIdentifier(), set);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Render annotations on top of a frame.
     *
     * <p>This renders each of the annotations (in Z-Order) on a given frame, using the specified
     * graphics context.
     *
     * @param g2 the graphics context for the rendering.
     */
    public void render(Graphics2D g2) {
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        PriorityQueue<AnnotationMetadataUniversalSet> annotations =
                new PriorityQueue<>(Comparator.comparingInt(v -> v.getZOrderValue()));
        annotations.addAll(sets.values());
        for (AnnotationMetadataUniversalSet annotation : annotations) {
            try {
                IKlvValue mimeMediaType = annotation.getField(AnnotationMetadataKey.MIMEMediaType);
                if (mimeMediaType != null && (mimeMediaType instanceof MIMEMediaType)) {
                    renderAnnotation(mimeMediaType, annotation, g2);
                } else {
                    throw new IOException("Bad annotation set");
                }
            } catch (IOException ex) {
                LOG.error("Failed to render annotation: " + ex.toString());
            }
        }
    }

    private void renderAnnotation(
            IKlvValue mimeMediaType, AnnotationMetadataUniversalSet annotation, Graphics2D g2)
            throws IOException, IllegalArgumentException {
        MIMEMediaType mimeType = (MIMEMediaType) mimeMediaType;
        XViewportPosition xValue =
                (XViewportPosition) annotation.getField(AnnotationMetadataKey.XViewportPosition);
        YViewportPosition yValue =
                (YViewportPosition) annotation.getField(AnnotationMetadataKey.YViewportPosition);
        if (mimeType.isPNG() || mimeType.isJPEG() || mimeType.isBMP()) {
            renderRaster(annotation, g2, xValue, yValue);
        } else if (mimeType.isSVG()) {
            renderSVG(annotation, g2, xValue, yValue);
        }
    }

    private void renderRaster(
            AnnotationMetadataUniversalSet annotation,
            Graphics2D g2,
            XViewportPosition xValue,
            YViewportPosition yValue)
            throws IOException {
        IAnnotationMetadataValue mimeData = annotation.getField(AnnotationMetadataKey.MIMEData);
        if (mimeData != null) {
            byte[] imageBytes = mimeData.getBytes();
            BufferedImage annotationImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

            g2.drawImage(annotationImage, xValue.getPosition(), yValue.getPosition(), null);
        }
    }

    private void renderSVG(
            AnnotationMetadataUniversalSet annotation,
            Graphics2D g2,
            XViewportPosition xValue,
            YViewportPosition yValue)
            throws IOException {
        IAnnotationMetadataValue mimeData = annotation.getField(AnnotationMetadataKey.MIMEData);
        if (mimeData != null) {
            byte[] svgBytes = mimeData.getBytes();
            String localId =
                    annotation
                            .getField(AnnotationMetadataKey.LocallyUniqueIdentifier)
                            .getDisplayableValue();
            SVGUniverse universe = new SVGUniverse();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(svgBytes)) {
                URI svgUri = universe.loadSVG(bais, localId, true);
                if (svgUri != null) {
                    SVGDiagram svg = universe.getDiagram(svgUri);
                    AffineTransform savedTransform = g2.getTransform();
                    g2.translate(xValue.getPosition(), yValue.getPosition());
                    svg.render(g2);
                    g2.setTransform(savedTransform);
                }
            } catch (SVGException ex) {
                throw new IOException("Unsupported SVG set");
            }
        }
    }

    /**
     * Clear the Annotation state.
     *
     * <p>This is intended to reset the renderer when annotations are no longer relevant (e.g. when
     * the file has ended).
     */
    public void clear() {
        sets.clear();
    }
}
