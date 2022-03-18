package org.jmisb.st0602;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.UniversalLabel;

/**
 * Active Samples per Line prefix message.
 *
 * <p>This message conceptually wraps an {@link ActiveSamplesPerLine} instance so it can appear as a
 * top level entity in a KLV stream.
 */
public class ActiveSamplesPerLineMessage implements IMisbMessage {

    /**
     * Universal label for Active Samples per Line preface item for Annotation Universal Metadata
     * Set.
     */
    public static final UniversalLabel AnnotationActiveSamplesPerLineUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01,
                        0x02, 0x00, 0x00, 0x00
                    });

    private final ActiveSamplesPerLine activeSamplesPerLine;
    private static final int UNIVERSAL_LABEL_BYTE_OFFSET = 0;
    private static final int UNIVERSAL_LABEL_LEN =
            AnnotationActiveSamplesPerLineUl.getBytes().length;
    private static final int LEN_BYTE_OFFSET = UNIVERSAL_LABEL_BYTE_OFFSET + UNIVERSAL_LABEL_LEN;
    private static final int LEN_REQUIRED_LEN = 1;
    private static final int VALUE_BYTE_OFFSET = LEN_BYTE_OFFSET + LEN_REQUIRED_LEN;
    private static final int VALUE_REQUIRED_LEN = 2;
    private static final int TOTAL_REQUIRED_LEN =
            UNIVERSAL_LABEL_LEN + LEN_REQUIRED_LEN + VALUE_REQUIRED_LEN;

    /**
     * Construct an {@code ActiveSamplesPerLineMessage} by wrapping an {@link ActiveSamplesPerLine}
     * instance.
     *
     * @param samplesPerLine the wrapped object
     */
    public ActiveSamplesPerLineMessage(ActiveSamplesPerLine samplesPerLine) {
        this.activeSamplesPerLine = samplesPerLine;
    }

    /**
     * Construct an {@code ActiveSamplesPerLineMessage} from a serialised representation.
     *
     * <p>The serialised representation is the universal label, the length, and the two byte value.
     *
     * @param bytes byte array containing the serialised representation.
     * @throws KlvParseException if the label or length are not as expected.
     */
    public ActiveSamplesPerLineMessage(byte[] bytes) throws KlvParseException {
        verifyLength(bytes);
        verifyUniversalLabel(bytes);

        activeSamplesPerLine =
                new ActiveSamplesPerLine(
                        Arrays.copyOfRange(
                                bytes, VALUE_BYTE_OFFSET, VALUE_BYTE_OFFSET + VALUE_REQUIRED_LEN));
    }

    private void verifyUniversalLabel(byte[] bytes) throws KlvParseException {
        if (!Arrays.equals(
                bytes,
                0,
                UNIVERSAL_LABEL_LEN,
                AnnotationActiveSamplesPerLineUl.getBytes(),
                0,
                UNIVERSAL_LABEL_LEN)) {
            throw new KlvParseException(
                    "Unexpected Annotation Active Samples per Line Universal Label passed in");
        }
    }

    private void verifyLength(byte[] bytes) throws KlvParseException {
        if (bytes.length != TOTAL_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Active Samples per Line Universal Label must have length 19");
        }
        if (bytes[LEN_BYTE_OFFSET] != VALUE_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Active Samples per Line Universal Label must be specified as length 2");
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AnnotationActiveSamplesPerLineUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 0602 Active Samples per Line cannot be nested");
        }
        byte[] linesBytes = activeSamplesPerLine.getBytes();
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(AnnotationActiveSamplesPerLineUl);
        arrayBuilder.appendAsBerLength(linesBytes.length);
        arrayBuilder.append(linesBytes);
        return arrayBuilder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "ST 0602, Active Samples per Line";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        if (tag.equals(AnnotationMetadataKey.ActiveSamplesPerLine)) {
            return this.activeSamplesPerLine;
        } else {
            return null;
        }
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        if (activeSamplesPerLine != null) {
            return Collections.singleton(AnnotationMetadataKey.ActiveSamplesPerLine);
        } else {
            return Collections.emptySet();
        }
    }
}
