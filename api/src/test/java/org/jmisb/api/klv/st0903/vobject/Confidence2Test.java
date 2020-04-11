package org.jmisb.api.klv.st0903.vobject;

import org.testng.annotations.Test;

/**
 * Tests for Confidence2.
 */
public class Confidence2Test
{
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new Confidence2(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new Confidence2(100.01);
    }
}
