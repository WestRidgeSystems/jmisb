package org.jmisb.st1202;

import org.jmisb.api.common.KlvParseException;

/** Transformation type enumeration. */
public enum TransformationEnumeration implements IGeneralizedTransformationMetadataValue {
    /**
     * Unknown value.
     *
     * <p>This is not a valid enumeration value, and typically indicates a problem with decoding
     * (e.g. bad data or implementation bug).
     */
    UNKNOWN(-1, "UNKNOWN", ""),

    /**
     * Other – No Defined Transformation.
     *
     * <p>An enumeration value equal to {@code 0} implies the transformation type is not defined;
     * however, this does not prevent the user from exploiting the information contained within the
     * Generalized Transformation LS.
     */
    OTHER(0, "Other - No Defined Transformation (NDT)", ""),

    /**
     * Chipping Transformation.
     *
     * <p>An enumeration value equal to {@code 1} signifies the transmitted image is a chip (or
     * sub-region) from a larger image. Examples of a chipped image are: 1) a sub-region of an image
     * that may be digitally enlarged (zoom); 2) a sub-region of an image selected to reduce
     * bandwidth, or to provide higher quality within the sub-region.
     */
    CHIPPING(1, "Chipping Transformation (CT)", "px"),

    /**
     * Child-Parent Transformation.
     *
     * <p>An enumeration value equal to {@code 2} indicates the transformation of a child focal
     * plane array (FPA) to its parent FPA (e.g. example defined in MISB ST 1002). This CPT is a
     * plane-to-plane transformation used to transform between FPA's in image space.
     */
    CHILD_PARENT(2, "Child-Parent Transformation (CPT)", "mm"),

    /**
     * Default Pixel-Space to Image-Space Transformation.
     *
     * <p>An enumeration value equal to {@code 3} is the default pixel-space to image-space
     * transformation.
     */
    DPIT(3, "Default Pixel-Space to Image-Space Transformation (DPIT)", "mm"),

    /**
     * Optical Transformation.
     *
     * <p>An enumeration value equal to {@code 4} indicates the pixel data of an image is a
     * translation, rotation, scale or skew from the originating FPA to final optical focal plane.
     * This may occur when the originating FPA is a subset of an entire optical focal plane. An
     * example is a Combined Composite Focal Plane Array (CCFPA) sensor, where multiple focal plane
     * array detectors combine to image a single optical focal plane. This optical transformation is
     * a plane-to-plane transformation to transform from FPA to the optical image plane. In addition
     * to providing a transformation from FPA to CCFPA, the optical transformation may also support
     * the effects of Coudé paths or Fast Steering Mirrors (FSM). Coudé path and FSM effects may
     * mimic that of the transformation between FPA and CCFPA. They may also differ, however, by
     * translating, rotating, scaling or skewing the optical image plane.
     */
    OPTICAL(4, "Optical Transformation (OT)", "mm");

    /**
     * Get the enumeration value for a byte array.
     *
     * @param bytes the byte array, length 1
     * @return the corresponding enumeration value, or UNKNOWN if there is no matching enumeration
     *     value.
     * @throws KlvParseException if the parsing fails (e.g. wrong length byte array)
     */
    static TransformationEnumeration fromBytes(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException(
                    "Transformation Enumeration should be encoded as a 1 byte array");
        }
        int value = bytes[0];
        return TransformationEnumeration.lookup(value);
    }

    /**
     * Look up enumeration value by encoded value.
     *
     * @param value the encoded (integer) value
     * @return the corresponding enumeration value, or UNKNOWN if the value did not match any entry
     */
    public static TransformationEnumeration lookup(int value) {
        for (TransformationEnumeration transformationType : values()) {
            if (transformationType.getEncodedValue() == value) {
                return transformationType;
            }
        }
        return UNKNOWN;
    }

    private final int encodedValue;
    private final String description;
    private final String units;

    private TransformationEnumeration(
            final int encodedValue, final String description, final String units) {
        this.encodedValue = encodedValue;
        this.description = description;
        this.units = units;
    }

    @Override
    public byte[] getBytes() {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException(
                    "Cannot serialise UNKNOWN Transformation Enumeration");
        }
        return new byte[] {(byte) encodedValue};
    }

    @Override
    public String getDisplayName() {
        return "Transformation Enumeration";
    }

    @Override
    public String getDisplayableValue() {
        return description;
    }

    /**
     * Encoded value.
     *
     * @return integer encoded value for the enumeration
     */
    public int getEncodedValue() {
        return encodedValue;
    }

    /**
     * Text description.
     *
     * @return String containing human-readable enumeration description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Transformation units.
     *
     * <p>The transformation units as provided in ST 1202.2 Table 1.
     *
     * @return units as a text string (blank if "None").
     */
    public String getUnits() {
        return units;
    }
}
