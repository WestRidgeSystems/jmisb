package org.jmisb.st0601.dto;

/**
 * Wavelengths description.
 *
 * <p>This class supports the ST0601 WavelengthsList implementation.
 */
public class Wavelengths {
    private int id;
    private double min;
    private double max;
    private String name;

    /**
     * Get the numeric identifier for this wavelengths range.
     *
     * @return the identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Set the numeric identifier for this wavelengths range.
     *
     * @param id the identifier, which must be 21 or higher
     */
    public void setId(int id) {
        if (id < 21) {
            throw new IllegalArgumentException("Minimum wavelength id is 21");
        }
        this.id = id;
    }

    /**
     * The minimum wavelength in the range.
     *
     * @return the wavelength in nanometers (nm)
     */
    public double getMin() {
        return min;
    }

    /**
     * Set the minimum wavelength.
     *
     * @param min the wavelength in nanometers (nm)
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * The maximum wavelength in the range.
     *
     * @return the wavelength in nanometers (nm)
     */
    public double getMax() {
        return max;
    }

    /**
     * Set the maximum wavelength.
     *
     * @param max the wavelength in nanometers (nm).
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Short name for this range.
     *
     * @return String containing the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the short name for this range.
     *
     * @param name the name of the range
     */
    public void setName(String name) {
        this.name = name;
    }
}
