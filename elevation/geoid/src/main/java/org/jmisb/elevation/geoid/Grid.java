package org.jmisb.elevation.geoid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** Interpolation Grid. */
public final class Grid {
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float yResolution;
    private float xResolution;

    private float[][] values;

    /**
     * Create a new Grid from the built-in EGM 96 values.
     *
     * @return instantiated Grid
     * @throws IOException if the grid could not be read.
     */
    static Grid fromEGM96Grid() throws IOException {
        Grid grid = new Grid();
        InputStream is = grid.getClass().getResourceAsStream("/egm96.dat");
        DataInputStream dis = new DataInputStream(is);
        grid.readHeaderFrom(dis);
        grid.values = new float[grid.getNumRows()][grid.getNumColumns()];
        grid.readValuesFrom(dis);
        return grid;
    }

    /**
     * Set the left (west-most) extent of the grid.
     *
     * @param leftExtent the left extent of the grid, in degrees.
     */
    public void setLeft(final float leftExtent) {
        this.left = leftExtent;
    }

    /**
     * Set the top (north-most) extent of the grid.
     *
     * @param topExtent the top extent of the grid, in degrees.
     */
    public void setTop(final float topExtent) {
        this.top = topExtent;
    }

    /**
     * Get the top (north-most) extent of the grid.
     *
     * @return the top extent, in degrees.
     */
    public float getTop() {
        return top;
    }

    /**
     * Set the right (east-most) extent of the grid.
     *
     * @param rightExtent the right extent of the grid, in degrees.
     */
    public void setRight(final float rightExtent) {
        this.right = rightExtent;
    }

    /**
     * Set the bottom (south-most) extent of the grid.
     *
     * @param bottomExtent the bottom extent of the grid, in degrees.
     */
    public void setBottom(final float bottomExtent) {
        this.bottom = bottomExtent;
    }

    /**
     * Get the Y-resolution of the grid.
     *
     * <p>This can be considered the vertical (latitude direction) spacing of the rows.
     *
     * @return Y direction grid spacing, in degrees.
     */
    public float getyResolution() {
        return yResolution;
    }

    /**
     * Set the Y-resolution of the grid.
     *
     * @param resolution resolution in degrees
     */
    public void setyResolution(final float resolution) {
        this.yResolution = resolution;
    }

    /**
     * Get the X-resolution of the grid.
     *
     * <p>This can be considered the horizontal (longitude direction) spacing of the rows.
     *
     * @return X direction grid spacing, in degrees.
     */
    public float getxResolution() {
        return xResolution;
    }

    /**
     * Set the X-resolution of the grid.
     *
     * @param resolution resolution in degrees.
     */
    public void setxResolution(final float resolution) {
        this.xResolution = resolution;
    }

    /**
     * Write the standard header values to the provided {@code DataOutputStream}.
     *
     * @param dos the stream to write to
     * @throws IOException if the stream cannot be written to.
     */
    public void writeHeaderTo(final DataOutputStream dos) throws IOException {
        dos.writeFloat(bottom);
        dos.writeFloat(top);
        dos.writeFloat(left);
        dos.writeFloat(right);
        dos.writeFloat(yResolution);
        dos.writeFloat(xResolution);
    }

    /**
     * Read the standard header values from the provided {@code DataInputStream}.
     *
     * @param dis the stream to read from
     * @throws IOException if the stream cannot be read from.
     */
    private void readHeaderFrom(final DataInputStream dis) throws IOException {
        bottom = dis.readFloat();
        top = dis.readFloat();
        left = dis.readFloat();
        right = dis.readFloat();
        yResolution = dis.readFloat();
        xResolution = dis.readFloat();
    }

    /**
     * Write the grid values to the provided {@code DataOutputStream}.
     *
     * @param dos the stream to write to
     * @throws IOException if the stream cannot be written to.
     */
    public void writeValuesTo(final DataOutputStream dos) throws IOException {
        for (float[] value : values) {
            for (int c = 0; c < value.length; c++) {
                dos.writeFloat(value[c]);
            }
        }
    }

    /**
     * Read the grid values from the provided {@code DataInputStream}.
     *
     * <p>The values array must be initialised prior to reading.
     *
     * @param dis the stream to read from.
     * @throws IOException if the stream cannot be read from
     */
    private void readValuesFrom(final DataInputStream dis) throws IOException {
        for (float[] value : values) {
            for (int c = 0; c < value.length; c++) {
                value[c] = dis.readFloat();
            }
        }
    }

    /**
     * Get the number of rows.
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return 1 + (int) Math.ceil((top - bottom) / yResolution);
    }

    /**
     * Get the number of columns.
     *
     * @return the number of columns
     */
    public int getNumColumns() {
        return 1 + (int) Math.ceil((right - left) / xResolution);
    }

    /**
     * Set a specific row/column value.
     *
     * @param row the row number
     * @param column the column number
     * @param value the value to set at {@code row, column}
     */
    public void setValue(final int row, final int column, final float value) {
        values[row][column] = value;
    }

    float findValue(final int baseRow, final int baseColumn) {
        int rowNumber = baseRow;
        int columnNumber = baseColumn;
        if (rowNumber < 0) {
            rowNumber = 0;
        }
        if (rowNumber >= values.length) {
            rowNumber = values.length - 1;
        }
        if (columnNumber < 0) {
            columnNumber += values[rowNumber].length - 1;
        }
        if (columnNumber >= values[rowNumber].length) {
            columnNumber = columnNumber - (values[rowNumber].length - 1);
        }
        return values[rowNumber][columnNumber];
    }
}
