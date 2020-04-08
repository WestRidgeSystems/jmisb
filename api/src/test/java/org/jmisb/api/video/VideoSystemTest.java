package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import org.jmisb.api.klv.LoggerChecks;

public class VideoSystemTest extends LoggerChecks
{
    public VideoSystemTest()
    {
        super(VideoSystem.class);
    }

    @Test
    public void createStream()
    {
        try (IVideoStreamInput stream = VideoSystem.createInputStream())
        {
            Assert.assertFalse(stream.isOpen());
        }
        catch (IOException e)
        {
            Assert.fail("Exception auto-closing stream");
        }
    }
}
