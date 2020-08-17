package org.jmisb.api.klv.st0903.vtrack;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

/** Unit tests for VTrackItemSeries. */
public class VTrackItemSeriesTest {

    public VTrackItemSeriesTest() {}

    @Test
    public void checkFromListEmpty() {
        List<VTrackItem> items = new ArrayList<>();
        VTrackItemSeries trackItemSeries = new VTrackItemSeries(items);
        assertNotNull(trackItemSeries);
        assertEquals(trackItemSeries.getIdentifiers().size(), 0);
    }

    @Test
    public void checkFromListTwoItems() {
        List<VTrackItem> items = new ArrayList<>();
        items.add(new VTrackItem(2, new HashMap<>()));
        items.add(new VTrackItem(3, new HashMap<>()));
        VTrackItemSeries trackItemSeries = new VTrackItemSeries(items);
        assertNotNull(trackItemSeries);
        assertEquals(trackItemSeries.getIdentifiers().size(), 2);
        assertNotNull(trackItemSeries.getField(() -> 2));
        assertNotNull(trackItemSeries.getField(() -> 3));
    }
}
