package org.jmisb.api.klv.st0903.vobject;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * Ontology ID (VObject LS Tag 3).
 *
 * <p>The OntologyId is an alternative method for specifying the information in Tag 1 (Ontology) and
 * Tag 2 (OntologyClass) when the VMTI LS includes an Ontology Series in Tag 103. The OntologyId is
 * a reference to one of the elements in the VMTI LS Ontology Series. The 103_ontologySeries
 * attribute lists all the ontologies the VMTI LS will use. Each ontology in the series includes an
 * identifier (Id), the OntologyId value equals one of the Id values in the Ontology Series. Using
 * the OntologyId replaces the need to use Tag 1 and Tag 2 in VObject LS saving bandwidth by not
 * duplicating the same information for different VObjects.
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
