package org.jmisb.api.klv.st0903.shared;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Represents a string value in ST 0903.
 */
public class VmtiTextString extends VmtiUtf8 implements IVmtiMetadataValue
{
    /**
     * VMTI LS Tag 3 - VMTI System Name.
     * The name or description of the VMTI system producing the VMTI targets.
     * The field is free text.
     */
    public final static String SYSTEM_NAME = "System Name/Description";

    /**
     * VMTI LS Tag 10 - VMTI Source Sensor.
     * Free text identifier of the image source sensor.
     */
    public final static String SOURCE_SENSOR = "Source Sensor";

    /**
     * VTarget LS Tag 6 - Algorithm.
     * Unique name or description of the algorithm
     * or method used to create or maintain object movement reports or
     * intervening predictions of such movement.
     */
    public final static String ALGORITHM = "Algorithm";

    /**
     * VChip LS Tag 1 - Image Type.
     * <p>
     * A string of UTF-8 characters that correspond to an IANA media image
     * subtype. Only the IANA media image subtypes “jpeg” and “png” are allowed;
     * these are common formats for compressing a still image. JPEG is a lossy
     * compression method, but quality is adjustable. PNG is lossless and
     * provides RGB bit depths up to 48 bits per pixel (16 bits per color
     * component). Thus, it preserves “raw” pixel values.
     * <p>
     * Tag 1 Image Type is a required item in the VChip LS. However, for
     * bandwidth efficiency, Image Type is not necessary in every VChip LS
     * (although it may) but needs to be present periodically. Once specified
     * Image Type, Image URI and Embedded Image items in subsequent VChip LSs
     * needs to be consistent with the specified image type.
     * <p>
     * Valid Values: A string of UTF-8 characters that correspond to an IANA
     * media image subtype.
     */
    public final static String IMAGE_TYPE = "Image Type";

    /**
     * VObject LS Tag 2 - Ontology class.
     * <p>
     * A value representing a target class or type, as defined by the VObject
     * Ontology Tag 1. For bandwidth efficiency, it is desirable that the
     * Ontology specify a mapping between compact values (perhaps BER-OID
     * encoded) for use in OntologyClass and more descriptive names for use by
     * systems that present the information to human observers.
     */
    public final static String ONTOLOGY_CLASS = "Ontology Class";

    /**
     * Algorithm LS Tag 2 - Name.
     * <p>
     * The Name is the name assigned to the algorithm by the data producer.
     * <p>
     * Valid Values: Any alphanumeric value in UTF8.
     */
    public static String ALGORITHM_NAME = "Algorithm Name";

    /**
     * Algorithm LS Tag 3 - Version.
     * <p>
     * The Version is the version of the algorithm.
     * <p>
     * Valid Values: Any alphanumeric value in UTF8.
     */
    public static String ALGORITHM_VERSION = "Algorithm Version";

    /**
     * Algorithm LS Tag 4 - Class.
     * <p>
     * The Class is the type of algorithm.
     * <p>
     * Valid Values: Any alphanumeric value in UTF8.
     */
    public static String ALGORITHM_CLASS = "Algorithm Class";

    /**
     * Create from value
     * @param name The display name for the string
     * @param value The string value
     */
    public VmtiTextString(String name, String value)
    {
        super(name, value);
    }

    /**
     * Create from encoded bytes
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public VmtiTextString(String name, byte[] bytes)
    {
        super(name, bytes);
    }
}
