package org.jmisb.st0903.ontology;

import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.VmtiV3Value;

/**
 * Ontology ID (Ontology LS Tag 1).
 *
 * <p>When the VMTI LS contains a Series of Ontology Local Sets each element in the Series needs a
 * unique identifier. The Id is an integer which identifies a single Ontology LS in the Series and
 * is unique among all elements in the list. Systems may reuse Ids from VMTI LS to VMTI LS (i.e.,
 * two sequential VMTI packets) so receivers should not assume an identifier value is static for a
 * whole VMTI stream. The Id does not need to start at a value of one nor do the Ids need to be in
 * any specific order in the Ontology Series.
 */
public class OntologyId extends VmtiV3Value implements IVmtiMetadataValue {
    /**
     * Create from value.
     *
     * @param id the ontology identifier.
     */
    public OntologyId(int id) {
        super(id);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes identifier, encoded as a variable length unsigned int (max 3 bytes)
     */
    public OntologyId(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Ontology Id";
    }
}
