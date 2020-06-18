package org.jmisb.api.klv.st1909;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for MetadataItems class. */
public class MetadataItemsTest {

    public MetadataItemsTest() {}

    @Test
    public void lifecycle() {
        MetadataItems metadataItems = new MetadataItems();
        assertFalse(metadataItems.isValid());
        assertEquals(metadataItems.getItemKeys().size(), 0);
        metadataItems.addItem(MetadataKey.AzAngle, "X");
        assertTrue(metadataItems.isValid());
        assertEquals(metadataItems.getItemKeys().size(), 1);
        assertTrue(metadataItems.getItemKeys().contains(MetadataKey.AzAngle));
        assertEquals(metadataItems.getValue(MetadataKey.AzAngle), "X");
        metadataItems.clear();
        assertFalse(metadataItems.isValid());
        assertEquals(metadataItems.getItemKeys().size(), 0);
    }
}
