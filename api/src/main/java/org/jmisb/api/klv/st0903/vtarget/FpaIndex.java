package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;

/**
 * Focal Plane Array (FPA) Index (ST0903 VTarget Pack Item 21 and VTrackItem Pack Item 17).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Specifies the Focal Plane Array (FPA) in which detection of the target occurs with FPA Row and
 * FPA Column, in that order, in a two-dimensional array of FPAs. The purpose of the FPA Index is to
 * support sensors constructed using multiple FPAs, such as Large Volume Motion Imagery (LVMI).
 * Numbering for Rows is from 1, top to bottom, starting from the top of the array. Numbering for
 * Columns is from 1, left to right, starting from the left of the array.
 *
 * </blockquote>
 */
public class FpaIndex implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private FpaIndexPack value;
    private static final int NUM_BYTES = 2;

    /**
     * Create from value.
     *
     * @param fpaIndexPack the packed FPA index structure.
     */
    public FpaIndex(FpaIndexPack fpaIndexPack) {
        if ((fpaIndexPack.getFpaRow() < 1) || (fpaIndexPack.getFpaRow() > 255)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " row must be in range [1,255]");
        }
        if ((fpaIndexPack.getFpaColumn() < 1) || (fpaIndexPack.getFpaColumn() > 255)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " column must be in range [1,255]");
        }
        value = fpaIndexPack;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, two bytes length.
     */
    public FpaIndex(byte[] bytes) {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte pack");
        }
        short row = (short) (bytes[0] & 0xFF);
        short col = (short) (bytes[1] & 0xFF);
        value = new FpaIndexPack(row, col);
    }

    @Override
    public byte[] getBytes() {
        return new byte[] {(byte) value.getFpaRow(), (byte) value.getFpaColumn()};
    }

    @Override
    public String getDisplayableValue() {
        return "Row " + value.getFpaRow() + ", Col " + value.getFpaColumn();
    }

    @Override
    public final String getDisplayName() {
        return "FPA Index";
    }

    /**
     * Get the FPA Index Pack structure.
     *
     * @return the pack structure (row and column)
     */
    public FpaIndexPack getFpaIndexPack() {
        return value;
    }
}
