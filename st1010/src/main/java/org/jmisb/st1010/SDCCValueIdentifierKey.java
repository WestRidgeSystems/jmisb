package org.jmisb.st1010;

import java.util.Comparator;
import org.jmisb.api.klv.IKlvKey;

/**
 * Pseudo-key item for SDCC value identification.
 *
 * <p>Each identifier corresponds to one item in the SDCC value matrix (i.e. a unique row/column
 * combination).
 */
public class SDCCValueIdentifierKey implements IKlvKey, Comparable<SDCCValueIdentifierKey> {

    private final int row;
    private final int column;
    private final int numColumns;

    /**
     * Constructor.
     *
     * @param row row identifier.
     * @param column column identifier.
     * @param numColumns
     */
    public SDCCValueIdentifierKey(final int row, final int column, final int numColumns) {
        this.row = row;
        this.column = column;
        this.numColumns = numColumns;
    }

    /**
     * Row number.
     *
     * @return row number as an integer value, zero base.
     */
    public int getRow() {
        return row;
    }

    /**
     * Column number.
     *
     * @return column number as an integer value, zero base.
     */
    public int getColumn() {
        return column;
    }

    @Override
    public int getIdentifier() {
        return row * numColumns + column;
    }

    @Override
    public int compareTo(SDCCValueIdentifierKey t) {
        return Comparator.comparingInt(SDCCValueIdentifierKey::getRow)
                .thenComparingInt(SDCCValueIdentifierKey::getColumn)
                .compare(this, t);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.row;
        hash = 67 * hash + this.column;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SDCCValueIdentifierKey other = (SDCCValueIdentifierKey) obj;
        if (this.row != other.row) {
            return false;
        }
        return this.column == other.column;
    }
}
