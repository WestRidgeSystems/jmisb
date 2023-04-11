package org.jmisb.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st1002.IRangeImageMetadataValue;
import org.jmisb.st1002.RangeImageLocalSet;

/**
 * Range Image Local Set (ST 0601 Item 97).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * <p>The Range Image Local Set item allows users to include the Range Image LS (MISB ST 1002)
 * within MISB ST 0601. Range Motion Imagery is a temporal sequence of range images. Each range
 * image is a collection of range measurements from a sensor to target scene. A range measurement is
 * the distance (e.g., meters) from an object (or area) in the scene to the sensor. The KLV
 * structures of this standard are intended to allow for flexibility, efficient packing, and future
 * extensions. Range Motion Imagery can be used standalone, or in collaboration with other Motion
 * Imagery.
 *
 * <p>See MISB ST 1002 for generation and usage requirements.
 *
 * </blockquote>
 */
public class NestedRangeImageLocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final RangeImageLocalSet rangeImageLocalSet;

    /**
     * Create from value.
     *
     * @param rangeImage the Range Image data as a local set
     */
    public NestedRangeImageLocalSet(RangeImageLocalSet rangeImage) {
        this.rangeImageLocalSet = rangeImage;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedRangeImageLocalSet(byte[] bytes) throws KlvParseException {
        this.rangeImageLocalSet = RangeImageLocalSet.fromNestedBytes(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes() {
        return this.rangeImageLocalSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[Range Image]";
    }

    @Override
    public String getDisplayName() {
        return "Range Image";
    }

    /**
     * Get the Range Image data.
     *
     * @return the Range Image data as a local set
     */
    public RangeImageLocalSet getRangeImage() {
        return this.rangeImageLocalSet;
    }

    @Override
    public IRangeImageMetadataValue getField(IKlvKey tag) {
        return this.rangeImageLocalSet.getField(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return this.rangeImageLocalSet.getIdentifiers();
    }
}
