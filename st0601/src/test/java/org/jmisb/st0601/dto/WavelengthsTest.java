package org.jmisb.st0601.dto;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Wavelengths DTO. */
public class WavelengthsTest {
    @Test
    public void setId() {
        Wavelengths uut = new Wavelengths();
        uut.setId(21);
        assertEquals(uut.getId(), 21);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void tooLowId() {
        Wavelengths uut = new Wavelengths();
        uut.setId(20);
    }
}
