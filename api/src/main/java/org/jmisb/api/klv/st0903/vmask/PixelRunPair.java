package org.jmisb.api.klv.st0903.vmask;

/**
 * Pixel Number plus pixel run.
 *
 * This data transfer object supports the ST0903 VMask Tag 2 value.
 */
public class PixelRunPair
{
    private long pixelNumber;
    private int run;

    /**
     * Constructor.
     *
     * @param pixelNumber the pixel number.
     * @param run the run length in pixels.
     */
    public PixelRunPair(long pixelNumber, int run) {
        this.pixelNumber = pixelNumber;
        this.run = run;
    }

    /**
     * Get the starting point pixel number.
     *
     * @return the pixel number.
     */
    public long getPixelNumber() {
        return pixelNumber;
    }

    /**
     * Set the starting point pixel number.
     *
     * @param pixelNumber the pixel number.
     */
    public void setPixelNumber(long pixelNumber) {
        this.pixelNumber = pixelNumber;
    }

    /**
     * Get the run length.
     *
     * @return the run length in pixels.
     */
    public int getRun() {
        return run;
    }

    /**
     * Set the run length.
     * @param run the run length in pixels.
     */
    public void setRun(int run) {
        this.run = run;
    }

}
