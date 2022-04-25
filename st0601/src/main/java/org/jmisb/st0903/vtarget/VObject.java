package org.jmisb.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.st0903.vobject.VObjectLS;

/**
 * VObject Ontology (ST0903 VTarget Pack Item 102 and VTrackItem Pack Item 102).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * The VObject Local Set describes the class or type of a target (aircraft, watercraft, car, truck,
 * train, dismount, etc.) to an arbitrary level of detail. For example, it might be useful to expand
 * the notion of a “dismount” to include combatant, noncombatant, male, female, etc. This standard
 * mandates the use of the Web Ontology Language (OWL) to define the VObject ontology.
 *
 * </blockquote>
 */
public class VObject implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private final VObjectLS value;

    /**
     * Create from value.
     *
     * @param vObject the VObject Local Set.
     */
    public VObject(VObjectLS vObject) {
        value = vObject;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VObject LS
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VObject(byte[] bytes) throws KlvParseException {
        value = new VObjectLS(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes() {
        return value.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[VObject]";
    }

    @Override
    public final String getDisplayName() {
        return "VObject Ontology";
    }

    /**
     * Get the VObjectLS.
     *
     * @return the chip local set.
     */
    public VObjectLS getVObject() {
        return value;
    }
}
