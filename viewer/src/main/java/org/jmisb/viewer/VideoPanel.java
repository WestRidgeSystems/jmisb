package org.jmisb.viewer;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.jmisb.api.awt.st1909.OverlayRenderer;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.api.klv.st1909.MetadataItems;
import org.jmisb.api.klv.st1909.ST0601Converter;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.IVideoListener;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Simple JPanel to display video content */
public class VideoPanel extends JPanel
        implements IVideoListener, IMetadataListener, ComponentListener {

    private static Logger logger = LoggerFactory.getLogger(VideoPanel.class);
    private BufferedImage bufferedImage;
    private int x, y, width, height;
    private boolean resized = false;
    private final OverlayRenderer overlayRenderer = new OverlayRenderer();
    private final MetadataItems metadata = new MetadataItems();
    private boolean metadataOverlayEnabled = false;
    private boolean annotationOverlayEnabled = false;
    private final Annotations annotations = new Annotations();

    VideoPanel() {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating video panel");
        }

        addComponentListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // First clear away drawing surface if anything has been resized
        if (resized) {
            g2d.clearRect(0, 0, getWidth(), getHeight());
        }

        if (bufferedImage != null) {
            // Recompute the letter box dimensions, if anything has been resized
            if (resized) {
                updateLetterBox(
                        bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        getWidth(),
                        getHeight());
                resized = false;
            }

            // Draw the video frame
            g2d.drawImage(bufferedImage, x, y, width, height, null);
        } else {
            g2d.clearRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void onVideoReceived(VideoFrame frame) {
        // If the new frame is not the same as the old one, recompute letter boxing next time we
        // draw
        if (bufferedImage == null
                || frame.getImage().getWidth() != bufferedImage.getWidth()
                || frame.getImage().getHeight() != bufferedImage.getHeight()
                || resized) {
            resized = true;
        }

        bufferedImage = frame.getImage();
        if (metadataOverlayEnabled && metadata.isValid()) {
            overlayRenderer.render(bufferedImage, metadata, frame.getTimeStamp());
        }
        if (annotationOverlayEnabled && (annotations != null)) {
            annotations.render(bufferedImage.createGraphics());
        }

        // Need to repaint on the EDT
        SwingUtilities.invokeLater(() -> repaint(0, 0, getWidth(), getHeight()));
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        if (metadataOverlayEnabled) {
            if (metadataFrame.getMisbMessage() instanceof UasDatalinkMessage) {
                UasDatalinkMessage message = (UasDatalinkMessage) metadataFrame.getMisbMessage();
                ST0601Converter.convertST0601(message, this.metadata);
            }
        }
        if (annotationOverlayEnabled) {
            if (metadataFrame.getMisbMessage() instanceof AnnotationMetadataUniversalSet) {
                AnnotationMetadataUniversalSet message =
                        (AnnotationMetadataUniversalSet) metadataFrame.getMisbMessage();
                annotations.updateSet(message);
            }
        }
    }

    public void clear() {
        bufferedImage = null;
        this.metadata.clear();
        this.annotations.clear();
        repaint(0, 0, getWidth(), getHeight());
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // If the panel has been resized, we should immediately redraw to account for letter boxing
        resized = true;
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    /**
     * Recompute letter boxing based on the current video frame and panel dimensions
     *
     * @param imageWidth Video frame width
     * @param imageHeight Video frame height
     * @param panelWidth Panel width
     * @param panelHeight Panel height
     */
    private void updateLetterBox(int imageWidth, int imageHeight, int panelWidth, int panelHeight) {
        double imageAspect = imageWidth / (double) imageHeight;
        double panelAspect = panelWidth / (double) panelHeight;

        width =
                (panelAspect >= imageAspect)
                        ? (int) Math.floor(imageAspect * panelHeight)
                        : panelWidth;
        height =
                (panelAspect >= imageAspect)
                        ? panelHeight
                        : (int) Math.floor(panelWidth / imageAspect);
        x = (panelWidth - width) / 2;
        y = (panelHeight - height) / 2;
    }

    void setMetadataOverlayState(boolean state) {
        this.metadataOverlayEnabled = state;
    }

    void setAnnotationOverlayState(boolean state) {
        this.annotationOverlayEnabled = state;
    }
}
