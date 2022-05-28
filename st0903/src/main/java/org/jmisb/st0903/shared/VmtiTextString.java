package org.jmisb.st0903.shared;

import org.jmisb.st0903.IVmtiMetadataValue;

/** Represents a string value in ST 0903. */
public class VmtiTextString extends VmtiUtf8
        implements IVmtiMetadataValue, IVTrackMetadataValue, IVTrackItemMetadataValue {
    /**
     * VMTI LS Tag 3 - VMTI System Name. The name or description of the VMTI system producing the
     * VMTI targets. The field is free text.
     */
    public static final String SYSTEM_NAME = "System Name/Description";

    /** VMTI LS Tag 10 - VMTI Source Sensor. Free text identifier of the image source sensor. */
    public static final String SOURCE_SENSOR = "Source Sensor";

    /**
     * VTarget LS Tag 6 - Algorithm. Unique name or description of the algorithm or method used to
     * create or maintain object movement reports or intervening predictions of such movement.
     */
    public static final String ALGORITHM = "Algorithm";

    /**
     * VChip LS Tag 1 - Image Type.
     *
     * <p>A string of UTF-8 characters that correspond to an IANA media image subtype. Only the IANA
     * media image subtypes “jpeg” and “png” are allowed; these are common formats for compressing a
     * still image. JPEG is a lossy compression method, but quality is adjustable. PNG is lossless
     * and provides RGB bit depths up to 48 bits per pixel (16 bits per color component). Thus, it
     * preserves “raw” pixel values.
     *
     * <p>Tag 1 Image Type is a required item in the VChip LS. However, for bandwidth efficiency,
     * Image Type is not necessary in every VChip LS (although it may) but needs to be present
     * periodically. Once specified Image Type, Image URI and Embedded Image items in subsequent
     * VChip LSs needs to be consistent with the specified image type.
     *
     * <p>Valid Values: A string of UTF-8 characters that correspond to an IANA media image subtype.
     */
    public static final String IMAGE_TYPE = "Image Type";

    /**
     * VObject LS Tag 2 - Ontology class.
     *
     * <p>A value representing a target class or type, as defined by the VObject Ontology Tag 1. For
     * bandwidth efficiency, it is desirable that the Ontology specify a mapping between compact
     * values (perhaps BER-OID encoded) for use in OntologyClass and more descriptive names for use
     * by systems that present the information to human observers.
     */
    public static final String ONTOLOGY_CLASS = "Ontology Class";

    /**
     * Algorithm LS Tag 2 - Name.
     *
     * <p>The Name is the name assigned to the algorithm by the data producer.
     *
     * <p>Valid Values: Any alphanumeric value in UTF8.
     */
    public static final String ALGORITHM_NAME = "Algorithm Name";

    /**
     * Algorithm LS Tag 3 - Version.
     *
     * <p>The Version is the version of the algorithm.
     *
     * <p>Valid Values: Any alphanumeric value in UTF8.
     */
    public static final String ALGORITHM_VERSION = "Algorithm Version";

    /**
     * Algorithm LS Tag 4 - Class.
     *
     * <p>The Class is the type of algorithm.
     *
     * <p>Valid Values: Any alphanumeric value in UTF8.
     */
    public static final String ALGORITHM_CLASS = "Algorithm Class";

    /**
     * VFeature LS Tag 2 - Schema Feature.
     *
     * <p>A Geographic Markup Language (GML) document structured according to the schema specified
     * by VFeature LS Tag 1 Schema. It may contain one or more values observed for a feature of
     * interest.
     *
     * <p>Valid Values: Any OGC GML document that validates against the schema specified in Tag 1:
     * Schema.
     */
    public static final String VFEATURE_SCHEMA_FEATURE = "Schema Feature";

    /**
     * VTrack Pack Item 24 - motion imagery URL.
     *
     * <p>A Uniform Resource Locator (URL) for the Motion Imagery essence.
     *
     * <p>Valid Values: A URL conformant to IETF RFC 3986.
     */
    public static final String MOTION_IMAGERY_URL = "Motion Imagery URL";

    /**
     * Create from value.
     *
     * @param name The display name for the string
     * @param value The string value
     */
    public VmtiTextString(String name, String value) {
        super(name, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public VmtiTextString(String name, byte[] bytes) {
        super(name, bytes);
    }
}
