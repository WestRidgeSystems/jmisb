package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmLS;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VMTI LS Algorithm Series (ST 0903 VMTI LS Tag 102).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * AlgorithmSeries is a Series type which contains one or more Algorithm Local Sets. Within the VMTI
 * LS the Tag for the algorithmSeries is Tag 102. The Length field for the pack is the sum of all
 * the data in the algorithmSeries Value field. The Value field is comprised of one or more
 * Algorithm LS, each of which can be of a different size (thereby including different information)
 * parsed according to the Length provided for each LS.
 *
 * <p>The algorithmSeries enables assigning an algorithm to a detected target or a generated track,
 * whereas the algorithm item in the VTracker LS only assigns an algorithm to a track. The
 * recommendation is to use algorithmSeries because 1) it affords specifying a multitude of
 * algorithms as needed, and 2) definition at the parent VMTI LS level is more bandwidth efficient
 * when an algorithm applies to more than one detection.
 *
 * </blockquote>
 */
public class AlgorithmSeries implements IVmtiMetadataValue {
    private final List<AlgorithmLS> localSets = new ArrayList<>();

    /**
     * Create the message from a list of Algorithm Local Sets
     *
     * @param values the local sets to include in the series.
     */
    public AlgorithmSeries(List<AlgorithmLS> values) {
        localSets.addAll(values);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if there is a parsing error on the byte array.
     */
    public AlgorithmSeries(byte[] bytes) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            AlgorithmLS algorithmLS = new AlgorithmLS(bytes, index, lengthField.getValue());
            localSets.add(algorithmLS);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (AlgorithmLS localSet : localSets) {
            byte[] localSetBytes = localSet.getBytes();
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
        return "[Algorithms]";
    }

    @Override
    public String getDisplayName() {
        return "Algorithm Series";
    }

    /**
     * Get the Algorithm LS in the series.
     *
     * @return the algorithm information as a List
     */
    public List<AlgorithmLS> getAlgorithms() {
        return localSets;
    }
}
