package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.vtarget.TargetIdentifierKey;
import org.jmisb.api.klv.st0903.vtarget.VTargetPack;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VMTI LS VTarget Series (ST 0903 VMTI LS Tag 101).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * VTargetSeries is a Series type which contains VTarget Packs only. The Length field for the series
 * is the sum of all the data in the VTargetSeries Value field. The Value field is comprised of one
 * or more VTarget Packs, each of which can be of a different size (thereby including different
 * information) parsed according to the Length provided for each VTarget Pack.
 *
 * </blockquote>
 */
public class VTargetSeries implements IVmtiMetadataValue, INestedKlvValue {
    private final Map<Integer, VTargetPack> targetPacks = new HashMap<>();

    /**
     * Create the message from a list of VTargetPacks.
     *
     * @param values the target packs to include in the series.
     */
    public VTargetSeries(List<VTargetPack> values) {
        values.forEach(
                (targetPack) -> {
                    targetPacks.put(targetPack.getTargetIdentifier(), targetPack);
                });
    }
    /**
     * Create from encoded bytes.
     *
     * <p>This constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if there is a parsing error on the byte array.
     * @deprecated use {@link #VTargetSeries(byte[], EncodingMode)} instead
     */
    @Deprecated
    public VTargetSeries(byte[] bytes) throws org.jmisb.api.common.KlvParseException {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>This constructor allows selection of which encoding rules (according to the ST0903
     * version) to apply.
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     * @throws KlvParseException if there is a parsing error on the byte array.
     */
    public VTargetSeries(byte[] bytes, EncodingMode encodingMode) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            VTargetPack targetPack =
                    new VTargetPack(bytes, index, lengthField.getValue(), encodingMode);
            targetPacks.put(targetPack.getTargetIdentifier(), targetPack);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VTargetPack vtargetPack : targetPacks.values()) {
            byte[] localSetBytes = vtargetPack.getBytes();
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            ;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue() {
        return "[Targets]";
    }

    @Override
    public String getDisplayName() {
        return "Target Series";
    }

    /**
     * Get the VTargets in the series.
     *
     * @return the VTargets as a List
     */
    public List<VTargetPack> getVTargets() {
        List<VTargetPack> vtargets = new ArrayList<>();
        vtargets.addAll(targetPacks.values());
        return vtargets;
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        int identifier = tag.getIdentifier();
        return targetPacks.get(identifier);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<TargetIdentifierKey> identifiers = new TreeSet<>();
        targetPacks
                .keySet()
                .forEach(
                        (Integer ident) -> {
                            identifiers.add(new TargetIdentifierKey(ident));
                        });
        return identifiers;
    }
}
