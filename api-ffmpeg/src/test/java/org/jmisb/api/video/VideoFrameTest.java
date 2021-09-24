package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VideoFrameTest {
    @Test
    public void testBasic() {
        BufferedImage image1 = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage image2 = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);

        VideoFrame frame1 = new VideoFrame(image1, 0.0);
        VideoFrame frame2 = new VideoFrame(image2, 0.033);

        Assert.assertEquals(frame1.getPts(), 0.0);
        Assert.assertEquals(frame2.getPts(), 0.033);

        Assert.assertEquals(frame1.getImage(), image1);
        Assert.assertEquals(frame2.getImage(), image2);
    }
}
