package org.jmisb.api.awt.st1909;

import static org.testng.Assert.*;

import java.awt.Color;
import java.awt.Font;
import org.testng.annotations.Test;

/** Unit tests for OverlayRendererConfiguration class. */
public class OverlayRendererConfigurationTest {

    public OverlayRendererConfigurationTest() {}

    @Test
    public void checkFontGetter() {
        OverlayRendererConfiguration overlayRendererConfiguration =
                new OverlayRendererConfiguration();
        assertEquals(overlayRendererConfiguration.getFont().getSize(), 20);
    }

    @Test
    public void checkFontSetter() {
        OverlayRendererConfiguration overlayRendererConfiguration =
                new OverlayRendererConfiguration();
        Font newFont = new Font("Monospaced", Font.PLAIN, 10);
        overlayRendererConfiguration.setFont(newFont);
        assertEquals(overlayRendererConfiguration.getFont().getSize(), 10);
    }

    @Test
    public void checkCircleSize() {
        OverlayRendererConfiguration overlayRendererConfiguration =
                new OverlayRendererConfiguration();
        assertEquals(overlayRendererConfiguration.getNorthCircleSize(), 40);
        overlayRendererConfiguration.setNorthCircleSize(100);
        assertEquals(overlayRendererConfiguration.getNorthCircleSize(), 100);
    }

    @Test
    public void checkColour() {
        OverlayRendererConfiguration overlayRendererConfiguration =
                new OverlayRendererConfiguration();
        assertEquals(overlayRendererConfiguration.getFillColor(), Color.WHITE);
        overlayRendererConfiguration.setFillColor(Color.BLUE);
        assertEquals(overlayRendererConfiguration.getFillColor(), Color.BLUE);
    }
}
