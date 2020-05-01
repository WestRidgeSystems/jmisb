package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import org.jmisb.api.klv.LoggerChecks;

public class VideoFileInputTest extends LoggerChecks
{
    public VideoFileInputTest()
    {
        super(VideoFileInput.class);
    }

    @Test
    public void testCreate()
    {
        try (IVideoFileInput fileInput = new VideoFileInput())
        {
            Assert.assertFalse(fileInput.isOpen());
            Assert.assertFalse(fileInput.isPlaying());
            Assert.assertEquals(fileInput.getDuration(), 0.0);
            Assert.assertEquals(fileInput.getPosition(), 0.0);
        }
        catch (IOException e)
        {
            Assert.fail("Exception auto-closing file");
        }
    }

    @Test(expectedExceptions = IOException.class)
    public void checkOpenExceptions() throws IOException
    {
        try (IVideoFileInput fileInput = new VideoFileInput())
        {
            fileInput.open("This file does not exist");
        }
    }
}
