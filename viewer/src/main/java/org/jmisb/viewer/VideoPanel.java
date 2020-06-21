package org.jmisb.viewer;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.jmisb.api.video.IVideoListener;
import org.jmisb.api.video.VideoFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Simple JPanel to display video content */
public class VideoPanel extends JPanel implements IVideoListener, ComponentListener {
    private static Logger logger = LoggerFactory.getLogger(VideoPanel.class);
    private BufferedImage bufferedImage;
    private int x, y, width, height;
    private boolean resized = false;

    VideoPanel() {
        if (logger.isDebugEnabled()) logger.debug("Creating video panel");

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

        // Need to repaint on the EDT
        SwingUtilities.invokeLater(() -> repaint(0, 0, getWidth(), getHeight()));
    }

    public void clear() {
        bufferedImage = null;
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
}
