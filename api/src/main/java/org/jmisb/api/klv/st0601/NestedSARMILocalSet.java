package org.jmisb.api.klv.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st1206.ISARMIMetadataValue;
import org.jmisb.api.klv.st1206.SARMILocalSet;

/**
 * SARMI Local Set (ST 0601 Item 95).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * <p>The SAR Motion Imagery Local Set item allows users to include the SAR Motion Imagery Metadata
 * (MISB ST 1206) within MISB ST 0601. The SARMI metadata set allows users to exploit both
 * sequential synthetic aperture radar (SAR) imagery and sequential SAR coherent change products as
 * Motion Imagery.
 *
 * <p>See MISB ST 1206 for generation and usage requirements.
 *
 * </blockquote>
 */
public class NestedSARMILocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final SARMILocalSet sarmiLocalSet;

    /**
     * Create from value.
     *
     * @param sarmi the SARMI data
     */
    public NestedSARMILocalSet(SARMILocalSet sarmi) {
        this.sarmiLocalSet = sarmi;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedSARMILocalSet(byte[] bytes) throws KlvParseException {
        this.sarmiLocalSet = new SARMILocalSet(bytes);
    }

    @Override
    public byte[] getBytes() {
        return this.sarmiLocalSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[SARMI]";
    }

    @Override
    public String getDisplayName() {
        return "SAR Motion Imagery";
    }

    /**
     * Get the SAR Motion Imagery data.
     *
     * @return the SARMI data as a local set
     */
    public SARMILocalSet getSARMI() {
        return this.sarmiLocalSet;
    }

    @Override
    public ISARMIMetadataValue getField(IKlvKey tag) {
        return this.sarmiLocalSet.getField(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return this.sarmiLocalSet.getIdentifiers();
    }
}
