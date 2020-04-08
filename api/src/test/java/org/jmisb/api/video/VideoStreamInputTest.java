package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import org.jmisb.api.klv.LoggerChecks;

public class VideoStreamInputTest extends LoggerChecks
{
    public VideoStreamInputTest()
    {
        super(VideoStreamInput.class);
    }

    @Test
    public void createInputStream()
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

    @Test(expectedExceptions = IOException.class)
    public void checkOpenExceptions() throws IOException
    {
        this.verifyNoLoggerMessages();
        try (IVideoStreamInput stream = VideoSystem.createInputStream())
        {
            stream.open("This url does not exist");
        }
    }
}
