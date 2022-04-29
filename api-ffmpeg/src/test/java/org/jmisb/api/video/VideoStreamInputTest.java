package org.jmisb.api.video;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VideoStreamInputTest extends LoggerChecks {

    public VideoStreamInputTest() {
        super(VideoStreamInput.class);
    }

    @Test
    public void createInputStream() {
        try (IVideoStreamInput stream = new VideoStreamInput()) {
            Assert.assertFalse(stream.isOpen());
        } catch (IOException e) {
            Assert.fail("Exception auto-closing stream");
        }
    }

    @Test(expectedExceptions = IOException.class)
    public void checkOpenExceptions() throws IOException {
        try (IVideoStreamInput stream = new VideoStreamInput()) {
            stream.open("This url does not exist");
        }
    }
}
