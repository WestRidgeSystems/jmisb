package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avutil.av_dict_count;
import static org.testng.Assert.assertEquals;

import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.testng.annotations.Test;

public class CodecConfigurationTest {
    @Test
    public void constructor() {
        CodecConfiguration codecConfiguration =
                new CodecConfiguration("myname", "Some Label", CodecIdentifier.H264);
        assertEquals(codecConfiguration.getName(), "myname");
        assertEquals(codecConfiguration.getLabel(), "Some Label");
        assertEquals(codecConfiguration.getIdentifier(), CodecIdentifier.H264);
    }

    @Test
    public void constructorH265() {
        CodecConfiguration codecConfiguration =
                new CodecConfiguration("myname", "Some Label", CodecIdentifier.H265);
        assertEquals(codecConfiguration.getName(), "myname");
        assertEquals(codecConfiguration.getLabel(), "Some Label");
        assertEquals(codecConfiguration.getIdentifier(), CodecIdentifier.H265);
    }

    @Test
    public void options() {
        CodecConfiguration codecConfiguration =
                new CodecConfiguration("myname", "Some Label", CodecIdentifier.H265);
        codecConfiguration.addCodecOption("key1", "value1");
        codecConfiguration.addCodecOption("key2", "value2");
        AVDictionary dict = codecConfiguration.getOptions();
        assertEquals(av_dict_count(dict), 2);
    }
}
