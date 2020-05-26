package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vchip.VChipLS;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VChip Image Chip Series (ST0903 VTarget Pack Tag 106).
 * <p>
 * From ST0903:
 * <blockquote>
 * A Series of one or more VChip LS associated with a specific target. Each LS
 * contains image chip information. When using the VChip LS within a
 * VChipSeries, omit the VChip LS Tag 105 because all values are of the same
 * type. Just specify the Length and Value for each VChip LS represented.
 * Indicating multiple image chips could; for example, an image chip from the
 * source sensor and another image chip from a different sensor, say, one of
 * higher resolution or of a different modality (e.g., IR).
 * </blockquote>
 */
public class VChipSeries implements IVmtiMetadataValue
{
    private final List<VChipLS> chips = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param vchips the VChip Local Sets to add.
     */
    public VChipSeries(List<VChipLS> vchips)
    {
        chips.addAll(vchips);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VChipSeries
     * @param parseOptions the parsing options to use in the event of error
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VChipSeries(byte[] bytes, EnumSet<ParseOptions> parseOptions) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1)
        {
            BerField lengthField = BerDecoder.decode(bytes, index, true);
            index += lengthField.getLength();
            byte[] vChipBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            VChipLS chip = new VChipLS(vChipBytes, parseOptions);
            chips.add(chip);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VChipLS chip: getChips())
        {
            byte[] localSetBytes = chip.getBytes();
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Chip Series]";
    }

    @Override
    public String getDisplayName()
    {
        return "Image Chips";
    }

    /**
     * Get the VChipLS structure.
     *
     * @return the list of chip local sets.
     */
    public List<VChipLS> getChips()
    {
        return chips;
    }
}
