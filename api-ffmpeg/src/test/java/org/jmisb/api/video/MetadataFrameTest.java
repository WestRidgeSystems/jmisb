package org.jmisb.api.video;

import java.util.TreeMap;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.st0601.IUasDatalinkValue;
import org.jmisb.st0601.UasDatalinkMessage;
import org.jmisb.st0601.UasDatalinkTag;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MetadataFrameTest {
    @Test
    public void testBasic() {
        IMisbMessage message1 =
                new UasDatalinkMessage(new TreeMap<UasDatalinkTag, IUasDatalinkValue>());
        IMisbMessage message2 =
                new UasDatalinkMessage(new TreeMap<UasDatalinkTag, IUasDatalinkValue>());

        MetadataFrame frame1 = new MetadataFrame(message1, 0.0);
        MetadataFrame frame2 = new MetadataFrame(message2, 0.033);

        Assert.assertEquals(frame1.getPts(), 0.0);
        Assert.assertEquals(frame2.getPts(), 0.033);

        Assert.assertEquals(frame1.getMisbMessage(), message1);
        Assert.assertEquals(frame2.getMisbMessage(), message2);
    }
}
