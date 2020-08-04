package org.jmisb.api.video;

import java.io.IOException;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VideoSystemTest extends LoggerChecks {
    @SuppressWarnings("deprecation")
    public VideoSystemTest() {
        super(VideoSystem.class);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void createStream() {
        try (IVideoStreamInput stream = VideoSystem.createInputStream()) {
            Assert.assertFalse(stream.isOpen());
        } catch (IOException e) {
            Assert.fail("Exception auto-closing stream");
        }
    }
}
