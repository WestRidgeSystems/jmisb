package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class UasDatalinkConstantsTest {
    @Test
    public void checkCurrentVersion() {
        assertEquals(UasDatalinkConstants.ST_VERSION_NUMBER, 16);
    }
}
