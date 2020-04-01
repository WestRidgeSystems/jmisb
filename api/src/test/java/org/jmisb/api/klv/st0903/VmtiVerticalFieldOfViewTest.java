package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VMTI Vertical FoV (Tag 12)
 */
public class VmtiVerticalFieldOfViewTest {
    @Test
    public void testConstructFromValue()
    {
        VmtiVerticalFieldOfView fov = new VmtiVerticalFieldOfView(10.0);
        assertEquals(fov.getBytes(), new byte[]{(byte)0x05, (byte)0x00});
        assertEquals(fov.getDisplayName(), "Vertical Field of View");
        assertEquals(fov.getDisplayableValue(), "10.0\u00B0");
        assertEquals(fov.getFieldOfView(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        VmtiVerticalFieldOfView fov = new VmtiVerticalFieldOfView(new byte[]{(byte)0x05, (byte)0x00});
        assertEquals(fov.getBytes(), new byte[]{(byte)0x05, (byte)0x00});
        assertEquals(fov.getDisplayName(), "Vertical Field of View");
        assertEquals(fov.getDisplayableValue(), "10.0\u00B0");
        assertEquals(fov.getFieldOfView(), 10.0);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiLocalSet.createValue(VmtiMetadataKey.VerticalFieldOfView, new byte[]{(byte)0x05, (byte)0x00});
        assertTrue(value instanceof VmtiVerticalFieldOfView);
        VmtiVerticalFieldOfView fov = (VmtiVerticalFieldOfView)value;
        assertEquals(fov.getBytes(), new byte[]{(byte)0x05, (byte)0x00});
        assertEquals(fov.getDisplayName(), "Vertical Field of View");
        assertEquals(fov.getDisplayableValue(), "10.0\u00B0");
        assertEquals(fov.getFieldOfView(), 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new VmtiVerticalFieldOfView(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new VmtiVerticalFieldOfView(180.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new VmtiVerticalFieldOfView(new byte[]{0x01});
    }
}
