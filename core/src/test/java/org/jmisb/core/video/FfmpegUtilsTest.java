package org.jmisb.core.video;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FfmpegUtilsTest
{
    @Test
    public void testFourCC()
    {
        String fourCc = FfmpegUtils.tagToFourCc(1096174667);
        Assert.assertEquals(fourCc, "KLVA");

        int tag = FfmpegUtils.fourCcToTag("KLVA");
        Assert.assertEquals(tag, 1096174667);
    }
}
