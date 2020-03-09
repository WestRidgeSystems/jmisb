package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Shared implementation of variable length pixel
 *
 * @author bradh
 */
public abstract class PixelNumber implements IVmtiMetadataValue {

    private long pixelNumber;

    public PixelNumber(long num) {
        pixelNumber = num;
    }

    public PixelNumber(byte[] bytes) {
        if (bytes.length > 7) {
            throw new IllegalArgumentException("Pixel number encoding is up to 6 bytes");
        }
        pixelNumber = 0;
        for (int i = 0; i < bytes.length; ++i) {
            pixelNumber = pixelNumber << 8;
            pixelNumber += ((int) bytes[i] & 0xFF);
        }
    }

    @Override
    public byte[] getBytes() {
        // TODO: implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%l", pixelNumber);
    }

}
