package org.jmisb.api.video;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VideoOutputOptions. */
public class VideoOutputOptionsTest {

    public VideoOutputOptionsTest() {}

    @Test
    public void checkConstructor1() {
        VideoOutputOptions uut = new VideoOutputOptions(1920, 1280);
        assertEquals(uut.getWidth(), 1920);
        assertEquals(uut.getHeight(), 1280);
        assertEquals(uut.getBitRate(), 1500000);
        assertEquals(uut.getFrameRate(), 30.0, 0.001);
        assertEquals(uut.getGopSize(), 30);
        assertEquals(uut.getMultiplexingMethod(), KlvFormat.Asynchronous);
        assertTrue(uut.hasKlvStream());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void checkConstructor2() {
        VideoOutputOptions uut = new VideoOutputOptions(1920, 1280, 1200000, 25.0, 32, false);
        assertEquals(uut.getWidth(), 1920);
        assertEquals(uut.getHeight(), 1280);
        assertEquals(uut.getBitRate(), 1200000);
        assertEquals(uut.getFrameRate(), 25.0, 0.001);
        assertEquals(uut.getGopSize(), 32);
        assertEquals(uut.getMultiplexingMethod(), KlvFormat.NoKlv);
        assertFalse(uut.hasKlvStream());
    }

    @Test
    public void checkConstructor3() {
        VideoOutputOptions uut =
                new VideoOutputOptions(640, 480, 1200000, 29.98, 10, KlvFormat.Synchronous);
        assertEquals(uut.getWidth(), 640);
        assertEquals(uut.getHeight(), 480);
        assertEquals(uut.getBitRate(), 1200000);
        assertEquals(uut.getFrameRate(), 29.98, 0.001);
        assertEquals(uut.getGopSize(), 10);
        assertEquals(uut.getMultiplexingMethod(), KlvFormat.Synchronous);
        assertTrue(uut.hasKlvStream());
    }
}
