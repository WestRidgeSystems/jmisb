package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VideoFrameTest {
    @Test
    public void testBasic() {
        BufferedImage image1 = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage image2 = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);

        VideoFrame frame1 = new VideoFrame(image1, 0.0);
        ST0603TimeStamp timestamp = new ST0603TimeStamp(1547483647000000L);
        frame1.setTimeStamp(timestamp);
        VideoFrame frame2 = new VideoFrame(image2, 0.033);
        frame2.setTimeStatus(new TimeStatus());

        Assert.assertEquals(frame1.getPts(), 0.0);
        Assert.assertEquals(frame2.getPts(), 0.033);

        Assert.assertEquals(frame1.getImage(), image1);
        Assert.assertEquals(frame1.getTimeStamp().getDisplayableValue(), "1547483647000000");
        Assert.assertEquals(frame2.getImage(), image2);
        Assert.assertFalse(frame2.getTimeStatus().isDiscontinuity());
        Assert.assertFalse(frame2.getTimeStatus().isReverse());
        Assert.assertFalse(frame2.getTimeStatus().isLocked());
    }
}