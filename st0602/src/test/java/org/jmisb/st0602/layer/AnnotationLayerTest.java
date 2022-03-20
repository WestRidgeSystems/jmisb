package org.jmisb.st0602.layer;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for AnnotationLayer. */
public class AnnotationLayerTest {

    public AnnotationLayerTest() {}

    @Test
    public void checkLayerDefaults() {
        AnnotationLayer layer = new AnnotationLayer(1920, 1080);
        assertEquals(layer.getAuthor(), "jmisb");
        assertEquals(layer.getImageWidth(), 1920);
        assertEquals(layer.getImageHeight(), 1080);
        assertEquals(layer.getZOrder(), 50);
    }

    @Test
    public void checkLayerSetters() {
        AnnotationLayer layer = new AnnotationLayer(1920, 1080);
        layer.setAuthor("bradh");
        layer.setZOrder(20);
        assertEquals(layer.getAuthor(), "bradh");
        assertEquals(layer.getZOrder(), 20);
    }
}
