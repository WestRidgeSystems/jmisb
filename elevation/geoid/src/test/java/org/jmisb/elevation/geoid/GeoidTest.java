package org.jmisb.elevation.geoid;

import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.Test;

/** Unit tests for Geoid. */
public class GeoidTest {

    private static final float NEAREST_NEIGHBOUR_TOLERANCE = 1.5f;
    private static final float BILINEAR_TOLERANCE = 0.4f;
    private static final float BICUBIC_TOLERANCE = 0.02f;

    public GeoidTest() {}

    @Test
    public void checkBaseRow90() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(90.0), 0);
    }

    @Test
    public void checkBaseColumn0() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseColumn(0.0), 0);
    }

    @Test
    public void checkBaseRowOffset90() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(90.0, 0), 0);
    }

    @Test
    public void checkBaseColumnOffset0() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseColumn(0.0, 0), 0);
    }

    @Test
    public void checkBaseRowJustBelow90() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(89.999), 0);
    }

    @Test
    public void checkBaseColumnJustAbove0() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseColumn(0.001), 0);
    }

    @Test
    public void checkBaseRowOffsetJustBelow90() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(89.999, 0), 0.004, 0.0000000001);
    }

    @Test
    public void checkBaseColumnOffsetJustAbove0() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseColumn(0.001, 0), 0.004, 0.0000000001);
    }

    @Test
    public void checkBaseRowJustIntoSecondRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(89.749999), 1);
    }

    @Test
    public void checkBaseRowOffsetJustIntoSecondRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(89.7499, 1), 0.0004, 0.00000001);
    }

    @Test
    public void checkBaseRowAlmostIntoThirdRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(89.5000001), 1);
    }

    @Test
    public void checkBaseRowOffsetAlmostIntoThirdRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(89.5001, 1), 0.9996, 0.000000001);
    }

    @Test
    public void checkBaseRowJustIntoThirdRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(89.49999999), 2);
    }

    @Test
    public void checkBaseRowOffsetJustIntoThirdRow() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(89.499, 2), 0.004, 0.0000000001);
    }

    @Test
    public void checkBaseRow0PlusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(0.001), 359);
    }

    @Test
    public void checkBaseRowOffset0PlusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(0.001, 359), 0.996, 0.0000000001);
    }

    @Test
    public void checkBaseRow0Latitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(0.00), 360);
    }

    @Test
    public void checkBaseRowOffset0Latitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(0.00, 360), 0.0, 0.0000000001);
    }

    @Test
    public void checkBaseRow0MinusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(-0.001), 360);
    }

    @Test
    public void checkBaseRowOffset0MinusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(-0.001, 360), 0.004, 0.0000000001);
    }

    @Test
    public void checkBaseRow024MinusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getBaseRow(-0.249), 360);
    }

    @Test
    public void checkBaseRowOffset024MinusLatitude() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(geoid.getOffsetFromBaseRow(-0.249, 360), 0.996, 0.0000000001);
    }

    @Test
    public void checkLookup1() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(38.6281550, 269.7791550),
                -31.628,
                NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(38.6281550, 269.7791550), -31.628, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(38.6281550, 269.7791550), -31.628, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(38.6281550, 269.7791550, 100.0), 68.372, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(38.6281550, 269.7791550, 100.0), 131.628, BICUBIC_TOLERANCE);
    }

    @Test
    public void checkLookup2() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(-14.6212170, 305.0211140),
                -2.9698,
                NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(-14.6212170, 305.0211140), -2.9698, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(-14.6212170, 305.0211140), -2.969, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(-14.6212170, 305.0211140, 100.0), 97.031, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(-14.6212170, 305.0211140, 100.0), 102.969, BICUBIC_TOLERANCE);
    }

    @Test
    public void checkLookup3() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(46.8743190, 102.4487290),
                -43.575,
                NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(46.8743190, 102.4487290), -43.575, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(46.8743190, 102.4487290), -43.575, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(46.8743190, 102.4487290, 100.0), 56.425, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(46.8743190, 102.4487290, 100.0), 143.575, BICUBIC_TOLERANCE);
    }

    @Test
    public void checkLookup4() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(-23.6174460, 133.8747120),
                15.871,
                NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(-23.6174460, 133.8747120), 15.871, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(-23.6174460, 133.8747120), 15.871, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(-23.6174460, 133.8747120, 100.0), 115.871, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(-23.6174460, 133.8747120, 100.0), 84.129, BICUBIC_TOLERANCE);
    }

    @Test
    public void checkLookup5() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(38.6254730, 359.9995000),
                50.066,
                NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(38.6254730, 359.9995000), 50.066, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(38.6254730, 359.9995000), 50.066, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(38.6254730, 359.9995000, 100.0), 150.066, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(38.6254730, 359.9995000, 100.0), 49.934, BICUBIC_TOLERANCE);
    }

    @Test
    public void checkLookup6() throws IOException {
        Geoid geoid = new Geoid();
        assertEquals(
                geoid.getValueNearest(-0.4667440, 0.0023000), 17.329, NEAREST_NEIGHBOUR_TOLERANCE);
        assertEquals(geoid.getValueBilinear(-0.4667440, 0.0023000), 17.329, BILINEAR_TOLERANCE);
        assertEquals(geoid.getValueBicubic(-0.4667440, 0.0023000), 17.329, BICUBIC_TOLERANCE);
        assertEquals(geoid.toHAE(-0.4667440, 0.0023000, 100.0), 117.329, BICUBIC_TOLERANCE);
        assertEquals(geoid.toMSL(-0.4667440, 0.0023000, 100.0), 82.671, BICUBIC_TOLERANCE);
    }
}
