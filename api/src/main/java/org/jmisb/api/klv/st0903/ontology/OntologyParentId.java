package org.jmisb.api.klv.st0903.ontology;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.*;


/**
 * Ontology Parent Id (Ontology LS Tag 2).
 * <p>
 * When detecting or tracking objects there may be several related
 * classifications made for an object. For example, a blob of pixels moving with
 * a high velocity may classify as both a vehicle (a generalization) with a
 * confidence level of 90%, a car with a confidence level of 70%, and a
 * motorcycle with a confidence level of 50%. Vehicle is a generalization of
 * both car and motorcycle; this relationship between car and vehicle, and
 * motorcycle and vehicle is indicated with the parentId
 * <p>
 * When an OntologySeries has two related LS in the Series, the ParentId defines
 * the link by having the child define its parentId equal to the parent ontology
 * object’s OntologyId. For example, an OntologySeries having three elements:
 * Vehicle with OntologyId 10, Car with OntologyId 17, and Motorcycle with
 * OntologyId 3. Since Car and Motorcycle are both “children” of the Vehicle,
 * those two LS define their ParentId’s equal to 10.
 */
public class OntologyParentId extends VmtiV3Value implements IVmtiMetadataValue
{
    /**
     * Create from value
     *
     * @param id the ontology identifier.
     */
    public OntologyParentId(int id)
    {
        super(id);
    }

    /**
     * Create from encoded bytes
     * @param bytes identifier, encoded as a variable length unsigned int (max 3 bytes)
     */
    public OntologyParentId(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Ontology Parent Id";
    }

}
