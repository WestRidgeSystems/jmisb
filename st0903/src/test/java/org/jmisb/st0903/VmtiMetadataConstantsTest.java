package org.jmisb.st0903;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for VmtiMetadataConstants. */
public class VmtiMetadataConstantsTest {
    @Test
    public void checkVersion() {
        assertEquals(VmtiMetadataConstants.ST_VERSION_NUMBER, 5);
    }
}
