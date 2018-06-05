package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class VideoSystemTest
{
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
