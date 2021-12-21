package org.jmisb.st1002;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st1202.GeneralizedTransformationLocalSet;

/**
 * Generalized Transformation (ST 1002 Local Set Item 19).
 *
 * <p>The Generalized Transformation is a mathematical transformation used to project information,
 * points, or lines from one image plane into a second image plane. The use of the Generalized
 * Transformation Local Set, MISB ST 1202, is for specific cases when aligning a Range Image to a
 * Collaborative Sensors Image. In this case, the Range Image is said to be a child of the
 * Collaborative Image (i.e., the parent image), so the Child-Parent Transformation (CPT)
 * enumeration 2 defined in Table 1 of MISB ST 1202, is used.
 *
 * <p>The boresighted imaging case is a hardware solution, where multiple focal plane arrays are
 * simultaneously imaging through a single aperture. The hardware limits the variation in the
 * perspective centres and the principal axis of the system to be coincident. Therefore, the
 * three-dimensional scene is imaged on two, dependent, focal plane arrays, where the dependency is
 * in the geometry. These focal planes do not necessarily have the same orientation or identical
 * pixel sizes. Also, the magnification of the two optical paths can be different causing different
 * image scales. These effects can be sufficiently modelled using the Generalized Transformation.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/coboresightedsensors.png" alt="Co-Boresighted
 * sensors">
 *
 * <p>When the Range Imagery does not meet the requirements for being co-boresighted, the Range
 * Imagery is called non-boresighted, and a transformation is needed to align the Range Imagery with
 * the Collaborative Imagery.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/nonboresightedsensors.png"
 * alt="Non-Boresighted sensors">
 */
public class GeneralizedTransformation implements IRangeImageMetadataValue, INestedKlvValue {

    private final GeneralizedTransformationLocalSet localSet;

    /**
     * Create from encoded bytes.
     *
     * @param localset value part of the nested local set, without UniversalLabel or overall length
     */
    public GeneralizedTransformation(final GeneralizedTransformationLocalSet localset) {
        this.localSet = localset;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array for the nested local set.
     * @throws KlvParseException if parsing fails
     */
    public GeneralizedTransformation(byte[] bytes) throws KlvParseException {
        this.localSet = new GeneralizedTransformationLocalSet(bytes);
    }

    @Override
    public byte[] getBytes() {
        return localSet.frameMessage(true);
    }

    @Override
    public String getDisplayName() {
        return "Generalized Transformation Local Set";
    }

    @Override
    public String getDisplayableValue() {
        return localSet.displayHeader();
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return localSet.getField(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return localSet.getIdentifiers();
    }
}
