package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;

/** Dynamically create {@link IAnnotationMetadataValue}s from {@link AnnotationMetadataKey}s. */
public class UniversalSetFactory {
    private UniversalSetFactory() {}

    /**
     * Create a {@link IAnnotationMetadataValue} instance from encoded bytes.
     *
     * @param key identifier defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if input is invalid
     */
    public static IAnnotationMetadataValue createValue(AnnotationMetadataKey key, byte[] bytes)
            throws KlvParseException {
        // Keep the case statements in enum ordinal order so we can keep track of what is
        // implemented. Mark all unimplemented tags with TODO.
        switch (key) {
            case LocallyUniqueIdentifier:
                return new LocallyUniqueIdentifier(bytes);
            case EventIndication:
                return new EventIndication(bytes);
            case MediaDescription:
                return new MediaDescription(bytes);
            case MIMEMediaType:
                return new MIMEMediaType(bytes);
            case MIMEData:
                return new MIMEData(bytes);
            case ModificationHistory:
                return new ModificationHistory(bytes);
            case XViewportPosition:
                return new XViewportPosition(bytes);
            case YViewportPosition:
                return new YViewportPosition(bytes);
            case AnnotationSource:
                return new AnnotationSource(bytes);
            case ZOrder:
                return new ZOrder(bytes);
            default:
                throw new IllegalArgumentException("Unrecognized identifier: " + key);
        }
    }
}
