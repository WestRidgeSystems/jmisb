package org.jmisb.api.video;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class CodecConfigurationsTest {
    @Test
    public void instance() {
        CodecConfigurations codecConfigurations = CodecConfigurations.getInstance();
        assertNotNull(codecConfigurations);
        assertEquals(codecConfigurations.getCodecs(CodecIdentifier.H264).size(), 4);
        assertEquals(codecConfigurations.getCodecs(CodecIdentifier.H265).size(), 2);
    }
}
