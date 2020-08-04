package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.testng.annotations.Test;

/** Tests for Target Boundary Series */
public class TargetBoundarySeriesTest {
    final byte[] bytesTwoLocations =
            new byte[] {
                10, // Location 1 length
                (byte) 0x27,
                (byte) 0xba,
                (byte) 0x90,
                (byte) 0xab,
                (byte) 0x34,
                (byte) 0x4a,
                (byte) 0x1a,
                (byte) 0xdf,
                (byte) 0x10,
                (byte) 0x14,
                10, // Location 2 length
                (byte) 0x27,
                (byte) 0xba,
                (byte) 0x93,
                (byte) 0x01,
                (byte) 0x34,
                (byte) 0x4a,
                (byte) 0x1b,
                (byte) 0x00,
                (byte) 0x10,
                (byte) 0x14
            };

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() throws KlvParseException {
        TargetBoundarySeries boundarySeries = new TargetBoundarySeries(bytesTwoLocations);
        verifyTwoLocations(boundarySeries);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingIMAPB() throws KlvParseException {
        TargetBoundarySeries boundarySeries =
                new TargetBoundarySeries(bytesTwoLocations, EncodingMode.IMAPB);
        verifyTwoLocations(boundarySeries);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(VTargetMetadataKey.TargetBoundarySeries, bytesTwoLocations);
        assertTrue(value instanceof TargetBoundarySeries);
        TargetBoundarySeries targetBoundarySeries = (TargetBoundarySeries) value;
        verifyTwoLocations(targetBoundarySeries);
    }

    @Test
    public void testFactoryEncodedBytesExplicitEncodingIMAPB() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetBoundarySeries,
                        bytesTwoLocations,
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetBoundarySeries);
        TargetBoundarySeries targetBoundarySeries = (TargetBoundarySeries) value;
        verifyTwoLocations(targetBoundarySeries);
    }

    private void verifyTwoLocations(TargetBoundarySeries boundarySeries) {
        assertEquals(boundarySeries.getBytes(), bytesTwoLocations);
        assertEquals(boundarySeries.getDisplayName(), "Target Boundary");
        assertEquals(boundarySeries.getDisplayableValue(), "[Location Series]");
        assertEquals(boundarySeries.getLocations().size(), 2);
        LocationPack location1 = boundarySeries.getLocations().get(0);
        assertEquals(location1.getLat(), -10.54246008396, 0.000001);
        assertEquals(location1.getLon(), 29.15789008141, 0.01);
        assertEquals(location1.getHae(), 3216.0, 0.01);
        LocationPack location2 = boundarySeries.getLocations().get(1);
        assertEquals(location2.getLat(), -10.54238867760, 0.000001);
        assertEquals(location2.getLon(), 29.15789818763, 0.01);
        assertEquals(location2.getHae(), 3216.0, 0.01);
    }

    @Test
    public void constructFromValue() throws KlvParseException, URISyntaxException {
        List<LocationPack> packs = new ArrayList<>();
        packs.add(new LocationPack(-10.54246008396, 29.15789008141, 3216.0));
        packs.add(new LocationPack(-10.54238867760, 29.15789818763, 3216.0));
        TargetBoundarySeries boundarySeries = new TargetBoundarySeries(packs);
        verifyTwoLocations(boundarySeries);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() throws KlvParseException {
        new TargetBoundarySeries(new byte[] {0x01, 0x02, 0x03}, EncodingMode.IMAPB);
    }
}
