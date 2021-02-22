package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.vobject.VObjectLS;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VObject Ontology Series (ST0903 VTarget Pack Item 107 and VTrackItem Pack Item 107).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * A Series of one or more VObject LS associated with a specific target. Each LS contains ontology
 * information. When using the VObject LS within a VObjectSeries, omit the VObject LS Tag 102
 * because all values are of the same type. Just specify the Length and Value for each VObject LS
 * represented.
 *
 * </blockquote>
 */
public class VObjectSeries implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private final List<VObjectLS> vobjects = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param objects the VObject Local Sets to add.
     */
    public VObjectSeries(List<VObjectLS> objects) {
        vobjects.addAll(objects);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VObjectSeries
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VObjectSeries(byte[] bytes) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            VObjectLS objectLocalSet = new VObjectLS(bytes, index, lengthField.getValue());
            vobjects.add(objectLocalSet);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VObjectLS vobject : getVObjects()) {
            byte[] localSetBytes = vobject.getBytes();
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
        return "[VObject Series]";
    }

    @Override
    public String getDisplayName() {
        return "Ontologies";
    }

    /**
     * Get the VObjectLS structure.
     *
     * @return the list of VObject local sets.
     */
    public List<VObjectLS> getVObjects() {
        return vobjects;
    }
}
