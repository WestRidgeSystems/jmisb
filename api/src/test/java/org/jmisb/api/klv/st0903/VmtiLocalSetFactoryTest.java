package org.jmisb.api.klv.st0903;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;
import org.testng.annotations.Test;

/** Unit tests for VmtiLocalSetFactoryTest. */
public class VmtiLocalSetFactoryTest {

    private final byte[] raw0903;
    private final VmtiLocalSetFactory factory;

    // the outermost identifiers in test encoding
    private final Set<IKlvKey> outerIdentifiers =
            new HashSet<>(
                    Arrays.asList(
                            VmtiMetadataKey.PrecisionTimeStamp,
                            VmtiMetadataKey.SystemName,
                            VmtiMetadataKey.VersionNumber,
                            VmtiMetadataKey.FrameNumber,
                            VmtiMetadataKey.FrameWidth,
                            VmtiMetadataKey.FrameHeight,
                            VmtiMetadataKey.SourceSensor,
                            VmtiMetadataKey.VTargetSeries,
                            VmtiMetadataKey.AlgorithmSeries,
                            VmtiMetadataKey.OntologySeries));

    public VmtiLocalSetFactoryTest() throws IOException {
        raw0903 = Files.readAllBytes(new File("src/test/resources/0903.bin").toPath());
        factory = new VmtiLocalSetFactory();
    }

    @Test
    public void testNumberMessages() throws KlvParseException {
        int offset = 0, messageCount = 0;
        while (offset < raw0903.length) {
            byte[] message = getNextMessage(raw0903, offset);
            messageCount++;
            offset += message.length;
        }
        assertEquals(messageCount, 5);
    }

    @Test
    public void testCreateUl() throws KlvParseException {
        int offset = 0;
        while (offset < raw0903.length) {
            byte[] message = getNextMessage(raw0903, offset);
            VmtiLocalSet vmtiSet = factory.create(message);
            assertEquals(vmtiSet.getIdentifiers(), outerIdentifiers);
            offset += message.length;
        }
    }

    @Test
    public void testCreateNoUl() throws KlvParseException {
        int offset = 0;
        while (offset < raw0903.length) {
            byte[] message = getNextMessage(raw0903, offset);
            VmtiLocalSet vmtiSet = factory.create(removeUl(message));
            assertEquals(vmtiSet.getIdentifiers(), outerIdentifiers);
            offset += message.length;
        }
    }

    private static byte[] removeUl(byte[] message) throws KlvParseException {
        int offset = 0;
        int setLength = message.length;

        if (setLength > UniversalLabel.LENGTH) {
            byte[] ul = Arrays.copyOfRange(message, offset, UniversalLabel.LENGTH);
            if (Arrays.equals(KlvConstants.VmtiLocalSetUl.getBytes(), ul)) {
                BerField lengthField = BerDecoder.decode(message, UniversalLabel.LENGTH, false);

                int labelLength = UniversalLabel.LENGTH + lengthField.getLength();
                if (labelLength + lengthField.getValue() > message.length)
                    throw new KlvParseException("VMTI BER length is greater than provided bytes");
                // also removes checksum
                return Arrays.copyOfRange(message, labelLength, message.length - 4);
            }
        }
        return message;
    }

    private static byte[] getNextMessage(byte[] bytes, int pos) throws KlvParseException {
        // Length of the key field (UL)
        final int keyLength = UniversalLabel.LENGTH;
        BerField lengthField = BerDecoder.decode(bytes, pos + keyLength, false);
        final int totalLength = keyLength + lengthField.getLength() + lengthField.getValue();

        if (pos + totalLength > bytes.length) {
            throw new KlvParseException("Length exceeds available bytes");
        }
        // If the lengths are equal, just return the original array; otherwise copy a subrange.
        if ((pos == 0) && (totalLength == bytes.length)) {
            return bytes;
        } else {
            return Arrays.copyOfRange(bytes, pos, pos + totalLength);
        }
    }
}
