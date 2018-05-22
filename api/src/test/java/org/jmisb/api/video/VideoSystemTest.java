package org.jmisb.api.video;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by dan on 5/10/17.
 */
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
