package org.jmisb.st1010;

import org.jmisb.api.klv.IKlvValue;

/**
 * Wrapper for an item in an SDCC matrix.
 *
 * <p>This acts as an adapter (facade) around the double value, to present it as an IKlvValue.
 */
public class SDCCValueWrap implements IKlvValue {

    private final int row;
    private final int column;
    private final double value;

    /**
     * Constructor.
     *
     * @param row the row number
     * @param column the column number
     * @param value the value
     */
    public SDCCValueWrap(final int row, final int column, final double value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public String getDisplayName() {
        return String.format("[%d][%d]", row, column);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f", value);
    }

    /**
     * Row index.
     *
     * @return the row index of the value
     */
    public int getRow() {
        return row;
    }

    /**
     * Column index.
     *
     * @return the column index of the value
     */
    public int getColumn() {
        return column;
    }

    /**
     * Value.
     *
     * @return the wrapped value, as a double.
     */
    public double getValue() {
        return value;
    }
}
