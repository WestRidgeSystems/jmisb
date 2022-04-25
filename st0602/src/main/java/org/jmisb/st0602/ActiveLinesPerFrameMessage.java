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
 * Active Lines Per Frame prefix message.
 *
 * <p>This message conceptually wraps an {@link ActiveLinesPerFrame} instance so it can appear as a
 * top level entity in a KLV stream.
 */
public class ActiveLinesPerFrameMessage implements IMisbMessage {

    /**
     * Universal label for Active Lines per Frame preface item for Annotation Universal Metadata
     * Set.
     */
    public static final UniversalLabel AnnotationActiveLinesPerFrameUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02,
                        0x02, 0x00, 0x00, 0x00
                    });

    private final ActiveLinesPerFrame activeLinesPerFrame;
    private static final int UNIVERSAL_LABEL_BYTE_OFFSET = 0;
    private static final int UNIVERSAL_LABEL_LEN =
            AnnotationActiveLinesPerFrameUl.getBytes().length;
    private static final int LEN_BYTE_OFFSET = UNIVERSAL_LABEL_BYTE_OFFSET + UNIVERSAL_LABEL_LEN;
    private static final int LEN_REQUIRED_LEN = 1;
    private static final int VALUE_BYTE_OFFSET = LEN_BYTE_OFFSET + LEN_REQUIRED_LEN;
    private static final int VALUE_REQUIRED_LEN = 2;
    private static final int TOTAL_REQUIRED_LEN =
            UNIVERSAL_LABEL_LEN + LEN_REQUIRED_LEN + VALUE_REQUIRED_LEN;

    /**
     * Construct an {@code ActiveLinesPerFrameMessage} by wrapping an {@link ActiveLinesPerFrame}
     * instance.
     *
     * @param linesPerFrame the wrapped object
     */
    public ActiveLinesPerFrameMessage(ActiveLinesPerFrame linesPerFrame) {
        this.activeLinesPerFrame = linesPerFrame;
    }

    /**
     * Construct an {@code ActiveLinesPerFrameMessage} from a serialised representation.
     *
     * <p>The serialised representation is the universal label, the length, and the two byte value.
     *
     * @param bytes byte array containing the serialised representation.
     * @throws KlvParseException if the label or length are not as expected.
     */
    public ActiveLinesPerFrameMessage(byte[] bytes) throws KlvParseException {
        verifyLength(bytes);
        verifyUniversalLabel(bytes);
        activeLinesPerFrame =
                new ActiveLinesPerFrame(
                        Arrays.copyOfRange(
                                bytes, VALUE_BYTE_OFFSET, VALUE_BYTE_OFFSET + VALUE_REQUIRED_LEN));
    }

    private void verifyUniversalLabel(byte[] bytes) throws KlvParseException {
        if (!Arrays.equals(
                bytes,
                0,
                UNIVERSAL_LABEL_LEN,
                AnnotationActiveLinesPerFrameUl.getBytes(),
                0,
                UNIVERSAL_LABEL_LEN)) {
            throw new KlvParseException(
                    "Unexpected Annotation Active Lines per Frame Universal Label passed in");
        }
    }

    private void verifyLength(byte[] bytes) throws KlvParseException {
        if (bytes.length != TOTAL_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Active Lines per Frame Universal Label must have length 19");
        }
        if (bytes[LEN_BYTE_OFFSET] != VALUE_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Active Lines per Frame Universal Label must be specified as length 2");
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AnnotationActiveLinesPerFrameUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 0602 active lines per frame cannot be nested");
        }
        byte[] linesBytes = activeLinesPerFrame.getBytes();
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(AnnotationActiveLinesPerFrameUl);
        arrayBuilder.appendAsBerLength(linesBytes.length);
        arrayBuilder.append(linesBytes);
        return arrayBuilder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "ST 0602, Active Lines per Frame";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        if (tag.equals(AnnotationMetadataKey.ActiveLinesPerFrame)) {
            return this.activeLinesPerFrame;
        } else {
            return null;
        }
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        if (activeLinesPerFrame != null) {
            return Collections.singleton(AnnotationMetadataKey.ActiveLinesPerFrame);
        } else {
            return Collections.emptySet();
        }
    }
}
