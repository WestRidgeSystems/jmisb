package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MiisCoreIdentifierTest {
    
    private static final byte[] ST_EXAMPLE_BYTES = new byte[] {(byte)0x01, (byte)0x70, (byte)0xF5, (byte)0x92, (byte)0xF0, (byte)0x23, (byte)0x73, (byte)0x36, (byte)0x4A, (byte)0xF8, (byte)0xAA, (byte)0x91, (byte)0x62, (byte)0xC0, (byte)0x0F, (byte)0x2E, (byte)0xB2, (byte)0xDA, (byte)0x16, (byte)0xB7, (byte)0x43, (byte)0x41, (byte)0x00, (byte)0x08, (byte)0x41, (byte)0xA0, (byte)0xBE, (byte)0x36, (byte)0x5B, (byte)0x5A, (byte)0xB9, (byte)0x6A, (byte)0x36, (byte)0x45};
    private static final String ST_EXAMPLE_TEXT = "0170:F592-F023-7336-4AF8-AA91-62C0-0F2E-B2DA/16B7-4341-0008-41A0-BE36-5B5A-B96A-3645:D3";
        
    public MiisCoreIdentifierTest() {
    }

    @Test
    public void verifyFromText() {
        // ST0601 example
        CoreIdentifier coreIdentifier = CoreIdentifier.fromString(ST_EXAMPLE_TEXT);
        MiisCoreIdentifier identifier = new MiisCoreIdentifier(coreIdentifier);
        assertEquals(identifier.getDisplayableValue(), ST_EXAMPLE_TEXT);
        assertEquals(identifier.getDisplayName(), "MIIS Core Identifier");
        assertEquals(identifier.getBytes(), ST_EXAMPLE_BYTES);
        assertNotNull(identifier.getCoreIdentifier());
        assertEquals(identifier.getCoreIdentifier().getVersion(), 1);
        assertTrue(identifier.getCoreIdentifier().hasValidCheckValue());
    }
    
    @Test
    public void verifyFromBytes() {
        // ST0601 example
        MiisCoreIdentifier identifier = new MiisCoreIdentifier(ST_EXAMPLE_BYTES);
        assertEquals(identifier.getDisplayableValue(), ST_EXAMPLE_TEXT);
        assertEquals(identifier.getDisplayName(), "MIIS Core Identifier");
        assertEquals(identifier.getBytes(), ST_EXAMPLE_BYTES);
        assertNotNull(identifier.getCoreIdentifier());
        assertEquals(identifier.getCoreIdentifier().getVersion(), 1);
        assertTrue(identifier.getCoreIdentifier().hasValidCheckValue());
    }
    
    @Test
    public void verifyFromFactory() throws KlvParseException {
        // ST0601 example
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.MiisCoreIdentifier, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof MiisCoreIdentifier);
        MiisCoreIdentifier identifier = (MiisCoreIdentifier) v;
        assertEquals(identifier.getDisplayableValue(), ST_EXAMPLE_TEXT);
        assertEquals(identifier.getDisplayName(), "MIIS Core Identifier");
        assertEquals(identifier.getBytes(), ST_EXAMPLE_BYTES);
        assertNotNull(identifier.getCoreIdentifier());
        assertEquals(identifier.getCoreIdentifier().getVersion(), 1);
        assertTrue(identifier.getCoreIdentifier().hasValidCheckValue());
    }
}
