package org.jmisb.api.klv.st0903.vtarget;

/**
 * Focal Plane Array Index Pack.
 *
 * <p>This data transfer object supports the ST0903 VTarget Tag 21 value.
 */
public class FpaIndexPack {

    private short fpaRow;
    private short fpaColumn;

    /**
     * Constructor.
     *
     * @param fpaRow the row number (1 base).
     * @param fpaColumn the column number (1 base).
     */
    public FpaIndexPack(short fpaRow, short fpaColumn) {
        this.fpaRow = fpaRow;
        this.fpaColumn = fpaColumn;
    }

    /**
     * Get the row number of the sensor Focal Plane Array in a two dimensional array of FPA.
     *
     * @return the row number (1 base).
     */
    public short getFpaRow() {
        return fpaRow;
    }

    /**
     * Set the row number of the sensor Focal Plane Array in a two dimensional array of FPA.
     *
     * @param fpaRow the row number (1 base).
     */
    public void setFpaRow(short fpaRow) {
        this.fpaRow = fpaRow;
    }

    /**
     * Get the column number of the sensor Focal Plane Array in a two dimensional array of FPA.
     *
     * @return the column number (1 base).
     */
    public short getFpaColumn() {
        return fpaColumn;
    }

    /**
     * Set the column number of the sensor Focal Plane Array in a two dimensional array of FPA.
     *
     * @param fpaColumn the column number (1 base).
     */
    public void setFpaColumn(short fpaColumn) {
        this.fpaColumn = fpaColumn;
    }
}
