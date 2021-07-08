package org.jmisb.elevation.geoid;

import static org.testng.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.testng.annotations.Test;

/** Unit tests for the Geoid Grid implementation. */
public class GridTest {

    public GridTest() {}

    @Test
    public void checkHeaderSetters() {
        Grid grid = new Grid();
        grid.setBottom(-90.0f);
        grid.setLeft(0.0f);
        grid.setRight(360.0f);
        grid.setTop(90.0f);
        grid.setxResolution(1.0f);
        grid.setyResolution(2.0f);
        assertEquals(grid.getNumColumns(), 361);
        assertEquals(grid.getNumRows(), 91);
    }

    @Test
    public void writeHeader() throws IOException {
        Grid grid = Grid.fromEGM96Grid();
        assertEquals(grid.getNumColumns(), 1441);
        assertEquals(grid.getNumRows(), 721);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)) {
            grid.writeHeaderTo(dos);
            byte[] bytes = baos.toByteArray();
            assertEquals(
                    bytes,
                    new byte[] {
                        (byte) 0xC2,
                        (byte) 0xB4,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x42,
                        (byte) 0xB4,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x43,
                        (byte) 0xB4,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x3E,
                        (byte) 0x80,
                        (byte) 0x00,
                        (byte) 0x00,
                        (byte) 0x3E,
                        (byte) 0x80,
                        (byte) 0x00,
                        (byte) 0x00
                    });
        }
    }

    @Test
    public void writeBody() throws IOException {
        Grid grid = Grid.fromEGM96Grid();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)) {
            grid.writeValuesTo(dos);
            byte[] bytes = baos.toByteArray();
            assertEquals(bytes.length, 1441 * 721 * 4);
        }
    }

    @Test
    public void values() throws IOException {
        Grid grid = Grid.fromEGM96Grid();
        assertEquals(grid.findValue(0, 0), 13.606f, 0.000001);
        assertEquals(grid.findValue(0, -1), 13.606f, 0.000001);
        assertEquals(grid.findValue(25, 0), 27.615, 0.000001);
        assertEquals(grid.findValue(25, 1), 27.604, 0.000001);
        assertEquals(grid.findValue(25, 720), 5.741f, 0.000001);
        assertEquals(grid.findValue(25, 1439), 27.624f, 0.000001);
        assertEquals(grid.findValue(25, 1440), 27.615f, 0.000001);
        assertEquals(grid.findValue(25, 1441), 27.604f, 0.000001);
        assertEquals(grid.findValue(25, -1), 27.624f, 0.000001);
        assertEquals(grid.findValue(720, 0), -29.534f, 0.000001);
        assertEquals(grid.findValue(720, 1440), -29.534f, 0.000001);
        assertEquals(grid.findValue(722, 1440), -29.534f, 0.000001);
        assertEquals(grid.findValue(-1, 0), 13.606f, 0.000001);
        grid.setValue(0, 0, 14.224f);
        assertEquals(grid.findValue(0, 0), 14.224f, 0.000001);
    }
}
