package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.ontology.OntologyLS;
import org.jmisb.core.klv.ArrayUtils;

/**
 * VMTI LS Ontology Series (ST 0903 VMTI LS Tag 103).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * OntologySeries is a Series type which contains one or more Ontology Local Sets. Within the VMTI
 * LS the Tag for the ontologySeries is Tag 103. The Length field for the pack is the sum of all the
 * data in the ontologySeries Value field. The Value field is comprised of one or more Ontology LS,
 * each of which can be of a different size (thereby including different information) parsed
 * according to the Length provided for each LS.
 *
 * <p>The ontologySeries enables assigning multiple ontologies to a detected target, whereas the
 * ontology items in the VObject LS referenced by the VTarget LS only allow a single ontology per
 * detected target. The recommendation is to use ontologySeries because 1) it affords specifying a
 * multitude of ontologies as needed, and 2) definition at the parent VMTI LS level is more
 * bandwidth efficient when an ontology applies to more than one target.
 *
 * </blockquote>
 */
public class OntologySeries implements IVmtiMetadataValue {
    private final List<OntologyLS> localSets = new ArrayList<>();

    /**
     * Create the message from a list of VObject Local Sets.
     *
     * @param values the local sets to include in the series.
     */
    public OntologySeries(List<OntologyLS> values) {
        localSets.addAll(values);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if there is a parsing error on the byte array.
     */
    public OntologySeries(byte[] bytes) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            OntologyLS vobjectLS = new OntologyLS(bytes, index, lengthField.getValue());
            localSets.add(vobjectLS);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (OntologyLS localSet : localSets) {
            byte[] bytes = localSet.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            ;
            chunks.add(bytes);
            len += bytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue() {
        return "[Ontologies]";
    }

    @Override
    public String getDisplayName() {
        return "Ontology Series";
    }

    /**
     * Get the Ontology LS in the series.
     *
     * @return the ontology information as a List
     */
    public List<OntologyLS> getOntologies() {
        return localSets;
    }
}
