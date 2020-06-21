package org.jmisb.api.video;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Simple tests for DemuxReturnValue. */
public class DemuxReturnValueTest {
    public DemuxReturnValueTest() {}

    @Test
    public void checkValues() {
        DemuxReturnValue v1 = DemuxReturnValue.SUCCESS;
        DemuxReturnValue v2 = DemuxReturnValue.ERROR;
        DemuxReturnValue v3 = DemuxReturnValue.EOF;
        DemuxReturnValue v4 = DemuxReturnValue.EAGAIN;
        assertTrue(v1 == v1);
        assertTrue(v1 != v2);
        assertTrue(v2 != v3);
        assertTrue(v3 != v4);
        assertTrue(v4 != v1);
    }
}
