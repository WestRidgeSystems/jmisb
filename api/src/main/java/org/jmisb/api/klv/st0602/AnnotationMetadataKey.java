package org.jmisb.api.klv.st0602;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.UniversalLabel;

/**
 * ST 0602 key.
 *
 * <p>Each metadata element within ST0602 consists of a key represented by a instance of this
 * enumeration, and a value represented by a instance implementing {@link IAnnotationMetadataValue}.
 */
public enum AnnotationMetadataKey implements IKlvKey {
    Undefined(
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    })),
    /**
     * Byte Order.
     *
     * <p>Byte order of the metadata.
     */
    ByteOrder(AnnotationMetadataConstants.byteOrderUl),

    /** Active lines per frame. */
    LinesPerFrame(AnnotationMetadataConstants.activeLinesPerFrameUl),
    /** Active samples per line. */
    SamplesPerFrame(AnnotationMetadataConstants.activeSamplesPerLineUl),
    /**
     * Locally Unique Identifier.
     *
     * <p>A 4-byte locally unique ID.
     */
    LocallyUniqueIdentifier(AnnotationMetadataConstants.locallyUniqueIdentifierUl),

    /**
     * Event Indication.
     *
     * <p>Describes the event.
     */
    EventIndication(AnnotationMetadataConstants.eventIndicationUl),

    /**
     * Media Description.
     *
     * <p>Freeform textual description (per SMPTE ISO/IEC RP 210[8]) providing title or text
     * description of enclosed MIME data.
     */
    MediaDescription(AnnotationMetadataConstants.mediaDescriptionUl),
    /** MIME media type as defined by the IETF. */
    MIMEMediaType(AnnotationMetadataConstants.mimeMediaTypeUl),
    /**
     * MIME Data.
     *
     * <p>Notionally MIME encoded data of annotation message.
     */
    MIMEData(AnnotationMetadataConstants.mimeDataUl),
    /**
     * Modification History.
     *
     * <p>Identification of most recent significant event's author.
     */
    ModificationHistory(AnnotationMetadataConstants.modificationHistoryUl),

    /**
     * X Viewport Position in Pixels.
     *
     * <p>X position of an object within a viewed image.
     */
    XViewportPosition(AnnotationMetadataConstants.xViewPortPositionInPixelsUl),
    /**
     * Y Viewport Position in Pixels.
     *
     * <p>Y position of an object within a viewed image.
     */
    YViewportPosition(AnnotationMetadataConstants.yViewPortPositionInPixelsUl),
    /**
     * Annotation Source.
     *
     * <p>Source of the specified annotation object.
     */
    AnnotationSource(AnnotationMetadataConstants.annotationSourceUl),
    /**
     * Z-Order.
     *
     * <p>Number defining the drawing order of annotations in a frame.
     */
    ZOrder(AnnotationMetadataConstants.zOrderUl);

    private UniversalLabel ul;

    private static final Map<Integer, AnnotationMetadataKey> tagTable = new HashMap<>();
    private static final Map<UniversalLabel, AnnotationMetadataKey> ulTable = new HashMap<>();

    static {
        for (AnnotationMetadataKey key : values()) {
            ulTable.put(key.ul, key);
        }
    }

    AnnotationMetadataKey(UniversalLabel ul) {
        this.ul = ul;
    }

    @Override
    public int getIdentifier() {
        return ul.hashCode();
    }

    /**
     * Get the {@link UniversalLabel} for this metadata key.
     *
     * @return the universal label for this key.
     */
    public UniversalLabel getUl() {
        return ul;
    }

    /**
     * Look up the SecurityMetadataKey by universal label.
     *
     * @param ul the universal label.
     * @return the corresponding SecurityMetadataKey.
     */
    public static AnnotationMetadataKey getKey(UniversalLabel ul) {
        return ulTable.containsKey(ul) ? ulTable.get(ul) : Undefined;
    }
}
