package org.jmisb.api.klv.st0602;

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
 * Byte Order prefix message.
 *
 * <p>This message is the fixed-content Byte Order prefix value, presented as a top level entity in
 * a KLV stream.
 */
public class AnnotationByteOrderMessage implements IMisbMessage {

    /** Universal label for Byte Order preface item for Annotation Universal Metadata Set. */
    public static final UniversalLabel AnnotationByteOrderUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01,
                        0x02, 0x00, 0x00, 0x00
                    });

    /**
     * Construct an empty ByteOrderMessage.
     *
     * <p>This is not normally needed, since the serialisation code will generate one if needed.
     */
    public AnnotationByteOrderMessage() {}

    private static final int UNIVERSAL_LABEL_BYTE_OFFSET = 0;
    private static final int UNIVERSAL_LABEL_LEN = AnnotationByteOrderUl.getBytes().length;
    private static final int LEN_BYTE_OFFSET = UNIVERSAL_LABEL_BYTE_OFFSET + UNIVERSAL_LABEL_LEN;
    private static final int LEN_REQUIRED_LEN = 1;
    private static final int VALUE_BYTE_OFFSET = LEN_BYTE_OFFSET + LEN_REQUIRED_LEN;
    private static final int VALUE_REQUIRED_LEN = 2;
    private static final int TOTAL_REQUIRED_LEN =
            UNIVERSAL_LABEL_LEN + LEN_REQUIRED_LEN + VALUE_REQUIRED_LEN;

    /**
     * Construct a {@code ByteOrderMessage} from a serialised representation.
     *
     * <p>The serialised representation is the universal label, the length, and the two byte value.
     *
     * @param bytes byte array containing the serialised representation.
     * @throws KlvParseException if any of the parts are not as expected.
     */
    public AnnotationByteOrderMessage(byte[] bytes) throws KlvParseException {
        verifyLength(bytes);
        verifyUniversalLabel(bytes);
        verifyValues(bytes);
    }

    private void verifyUniversalLabel(byte[] bytes) throws KlvParseException {
        if (!Arrays.equals(
                bytes,
                0,
                UNIVERSAL_LABEL_LEN,
                AnnotationByteOrderUl.getBytes(),
                0,
                UNIVERSAL_LABEL_LEN)) {
            throw new KlvParseException(
                    "Unexpected Annotation Byte Order Universal Label passed in");
        }
    }

    private void verifyLength(byte[] bytes) throws KlvParseException {
        if (bytes.length != TOTAL_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Byte Order Universal Label must have length 19");
        }
        if (bytes[LEN_BYTE_OFFSET] != VALUE_REQUIRED_LEN) {
            throw new KlvParseException(
                    "Annotation Byte Order Universal Label must be specified as length 2");
        }
    }

    private void verifyValues(byte[] bytes) throws KlvParseException {
        if ((bytes[VALUE_BYTE_OFFSET] != 0x4D) || (bytes[VALUE_BYTE_OFFSET + 1] != 0x4D)) {
            throw new KlvParseException("Annotation Byte Order value must be 0x4D4D");
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AnnotationByteOrderUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 0602 byte order cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        byte[] byteOrderValue = new byte[] {0x4D, 0x4D};
        arrayBuilder.append(AnnotationByteOrderUl);
        arrayBuilder.appendAsBerLength(byteOrderValue.length);
        arrayBuilder.append(byteOrderValue);
        return arrayBuilder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "ST 0602, Byte Order";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return new IKlvValue() {
            @Override
            public String getDisplayName() {
                return "Byte Order";
            }

            @Override
            public String getDisplayableValue() {
                return "Big Endian (MM)";
            }
        };
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return Collections.singleton(AnnotationMetadataKey.ByteOrder);
    }
}
