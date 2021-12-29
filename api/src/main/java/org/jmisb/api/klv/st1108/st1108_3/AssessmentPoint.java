package org.jmisb.api.klv.st1108.st1108_3;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Assessment Point.
 *
 * <p>An Assessment Point is a physical location along the imaging workflow. The Assessment Point
 * item documents the calculation of one or more metrics at this location. The Assessment Point is
 * an enumerated value. This Local Set item is mandatory.
 */
public enum AssessmentPoint implements IInterpretabilityQualityMetadataValue {

    /**
     * Undefined.
     *
     * <p>This is not a valid assessment point value, and should not be created.
     */
    Undefined(0),
    /**
     * Sensor.
     *
     * <p>Output directly from sensor.
     */
    Sensor(1),
    /**
     * Sensor Encoder.
     *
     * <p>Output from Motion Imagery encoder (if applicable).
     */
    SensorEncoder(2),
    /**
     * GCS Received.
     *
     * <p>Platform to ground station reception point.
     */
    GCSReceived(3),
    /**
     * GCS Transmit.
     *
     * <p>GCS transmission point.
     */
    GCSTransmit(4),
    /**
     * Library/Archive.
     *
     * <p>Receipt at archival location.
     */
    LibraryArchive(5);

    static AssessmentPoint fromBytes(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException("Assessment Point encoding is a one-byte unsigned int");
        }
        return lookup.getOrDefault(bytes[0], Undefined);
    }

    private static final Map<Byte, AssessmentPoint> lookup = new HashMap<>();

    static {
        for (AssessmentPoint key : values()) {
            lookup.put(key.value, key);
        }
    }

    private byte value;

    @Override
    public String getDisplayName() {
        return "Assessment Point";
    }

    @Override
    public String getDisplayableValue() {
        switch (this) {
            case Sensor:
                return "Sensor";
            case SensorEncoder:
                return "Sensor Encoder";
            case GCSReceived:
                return "GCS Received";
            case GCSTransmit:
                return "GCS Transmit";
            case LibraryArchive:
                return "Library/Archive";
            default:
                return "Unknown(" + this.value + ")";
        }
    }

    private AssessmentPoint(int value) {
        this.value = (byte) value;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.AssessmentPoint.getIdentifier());
        byte[] valueBytes = new byte[] {(byte) value};
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
