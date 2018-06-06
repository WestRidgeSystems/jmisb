package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class VideoStreamInputTest
{
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
        try (IVideoStreamInput stream = VideoSystem.createInputStream())
        {
            stream.open("This url does not exist");
        }
    }
}
