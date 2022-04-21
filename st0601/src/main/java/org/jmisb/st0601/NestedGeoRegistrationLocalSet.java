package org.jmisb.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st1601.GeoRegistrationLocalSet;
import org.jmisb.st1601.IGeoRegistrationValue;

/**
 * Geo-Registration Local Set (ST 0601 Item 98).
 *
 * <p>From ST 0601:
 *
 * <blockquote>
 *
 * <p>The Geo-Registration Local Set item allows users to include the Geo-Registration Local Set
 * (MISB ST 1601) within the UAS Datalink LS. MISB ST 1601 supports the identification of a
 * geo-registration algorithm and standard deviations and correlation coefficients output from a
 * geo-registration process.
 *
 * <p>See MISB ST 1601 for generation and usage requirements.
 *
 * </blockquote>
 */
public class NestedGeoRegistrationLocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final GeoRegistrationLocalSet localSet;

    /**
     * Create from value.
     *
     * @param localSet the ST 1601 Geo-Registration local set data
     */
    public NestedGeoRegistrationLocalSet(GeoRegistrationLocalSet localSet) {
        this.localSet = localSet;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedGeoRegistrationLocalSet(byte[] bytes) throws KlvParseException {
        this.localSet = GeoRegistrationLocalSet.fromNestedBytes(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes() {
        return this.localSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[Geo-Registration]";
    }

    @Override
    public String getDisplayName() {
        return "Geo-Registration";
    }

    /**
     * Get the Geo-Registration data.
     *
     * @return the Geo-Registration data as a local set
     */
    public GeoRegistrationLocalSet getLocalSet() {
        return this.localSet;
    }

    @Override
    public IGeoRegistrationValue getField(IKlvKey tag) {
        return this.localSet.getField(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return this.localSet.getIdentifiers();
    }
}
