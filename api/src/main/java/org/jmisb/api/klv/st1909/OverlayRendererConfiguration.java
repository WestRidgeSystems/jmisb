package org.jmisb.api.klv.st1909;

import java.awt.Color;
import java.awt.Font;

/** Configuration Options for the ST1909 OverlayRenderer. */
public class OverlayRendererConfiguration {
    private Color fillColor = Color.WHITE;
    private Font font = new Font("Monospaced", Font.PLAIN, 20);
    private int northCircleSize = 40;

    public OverlayRendererConfiguration() {}

    /**
     * Get the current fill colour.
     *
     * @return the fill colour
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Set the current fill colour.
     *
     * @param fillColor the fill colour
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Get the current font.
     *
     * @return the current font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Set the current font.
     *
     * @param font the current font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * The size of the north circle.
     *
     * @return the radius of the north circle, in pixels.
     */
    public int getNorthCircleSize() {
        return northCircleSize;
    }

    /**
     * Set the size of the north circle.
     *
     * @param northCircleSize the north circle radius, in pixels
     */
    public void setNorthCircleSize(int northCircleSize) {
        this.northCircleSize = northCircleSize;
    }
}
