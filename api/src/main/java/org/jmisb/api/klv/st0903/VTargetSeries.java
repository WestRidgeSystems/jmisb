package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.vtarget.VTargetPack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IRepeatingNestedKlvValue;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VMTI LS VTarget Series (ST 0903 VMTI LS Tag 101).
 * <p>
 * From ST0903:
 * <blockquote>
 * VTargetSeries is a Series type which contains VTarget Packs only. The Length
 * field for the series is the sum of all the data in the VTargetSeries Value
 * field. The Value field is comprised of one or more VTarget Packs, each of
 * which can be of a different size (thereby including different information)
 * parsed according to the Length provided for each VTarget Pack.
 * </blockquote>
 */
public class VTargetSeries implements IVmtiMetadataValue, IRepeatingNestedKlvValue
{
    private final List<VTargetPack> targetPacks = new ArrayList<>();

    /**
     * Create the message from a list of VTargetPacks
     *
     * @param values the target packs to include in the series.
     */
    public VTargetSeries(List<VTargetPack> values)
    {
        targetPacks.addAll(values);
    }
    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if there is a parsing error on the byte array.
     */
    public VTargetSeries(byte[] bytes) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1)
        {
            BerField lengthField = BerDecoder.decode(bytes, index, true);
            index += lengthField.getLength();
            byte[] targetPackBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            VTargetPack targetPack = new VTargetPack(targetPackBytes);
            targetPacks.add(targetPack);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VTargetPack vtargetPack : targetPacks)
        {
            byte[] localSetBytes = vtargetPack.getBytes();
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
        return "[Targets]";
    }

    @Override
    public String getDisplayName()
    {
        return "Target Series";
    }

    /**
     * Get the VTargets in the series.
     *
     * @return the VTargets as a List
     */
    public List<VTargetPack> getVTargets()
    {
        return targetPacks;
    }

    @Override
    public int getNumberOfEntries()
    {
        return targetPacks.size();
    }

    @Override
    public VTargetPack getNestedValue(int i)
    {
        return targetPacks.get(i);
    }
}
