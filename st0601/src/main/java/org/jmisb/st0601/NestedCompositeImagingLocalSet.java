package org.jmisb.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st1602.CompositeImagingLocalSet;
import org.jmisb.st1602.ICompositeImagingValue;

/**
 * Composite Imaging Local Set (ST 0601 Item 99).
 *
 * <p>From ST 0601:
 *
 * <blockquote>
 *
 * <p>The Composite Imaging Local Set item supports the composition of several Motion Imagery source
 * images into one composite Motion Imagery image. Such use cases include: tiled images,
 * picture-in-picture, stacked images, and blended images. The composition is destructive, where
 * background image information replaces foreground image information.
 *
 * <p>See MISB ST 1602 for generation and usage requirements.
 *
 * </blockquote>
 */
public class NestedCompositeImagingLocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final CompositeImagingLocalSet localSet;

    /**
     * Create from value.
     *
     * @param compositeImaging the Composite Imaging Local Set data
     */
    public NestedCompositeImagingLocalSet(CompositeImagingLocalSet compositeImaging) {
        this.localSet = compositeImaging;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedCompositeImagingLocalSet(byte[] bytes) throws KlvParseException {
        this.localSet = CompositeImagingLocalSet.fromNestedBytes(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes() {
        return this.localSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[Composite Imaging]";
    }

    @Override
    public String getDisplayName() {
        return "Composite Imaging";
    }

    /**
     * Get the Composite Imaging Local Set data.
     *
     * @return the Composite Imaging data as a local set
     */
    public CompositeImagingLocalSet getLocalSet() {
        return this.localSet;
    }

    @Override
    public ICompositeImagingValue getField(IKlvKey tag) {
        return this.localSet.getField(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return this.localSet.getIdentifiers();
    }
}
