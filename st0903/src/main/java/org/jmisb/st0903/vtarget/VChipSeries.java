package org.jmisb.st0903.vtarget;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.st0903.vchip.VChipLS;

/**
 * VChip Image Chip Series (ST0903 VTarget Pack Item 106 and VTrackItem Pack Item 106).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * A Series of one or more VChip LS associated with a specific target. Each LS contains image chip
 * information. When using the VChip LS within a VChipSeries, omit the VChip LS Tag 105 because all
 * values are of the same type. Just specify the Length and Value for each VChip LS represented.
 * Indicating multiple image chips could; for example, an image chip from the source sensor and
 * another image chip from a different sensor, say, one of higher resolution or of a different
 * modality (e.g., IR).
 *
 * </blockquote>
 */
public class VChipSeries implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private final List<VChipLS> chips = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param vchips the VChip Local Sets to add.
     */
    public VChipSeries(List<VChipLS> vchips) {
        chips.addAll(vchips);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VChipSeries
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VChipSeries(byte[] bytes) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            VChipLS chip = new VChipLS(bytes, index, lengthField.getValue());
            chips.add(chip);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (VChipLS chip : getChips()) {
            byte[] chipBytes = chip.getBytes();
            arrayBuilder.appendAsBerLength(chipBytes.length);
            arrayBuilder.append(chipBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[Chip Series]";
    }

    @Override
    public String getDisplayName() {
        return "Image Chips";
    }

    /**
     * Get the VChipLS structure.
     *
     * @return the list of chip local sets.
     */
    public List<VChipLS> getChips() {
        return chips;
    }
}
