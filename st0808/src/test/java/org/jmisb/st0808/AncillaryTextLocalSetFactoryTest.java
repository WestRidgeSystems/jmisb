package org.jmisb.st0808;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.testng.annotations.Test;

/** Tests for the ST0808 Ancillary Text Local Set Factory. */
public class AncillaryTextLocalSetFactoryTest extends LoggerChecks {
    public AncillaryTextLocalSetFactoryTest() {
        super(AncillaryTextLocalSet.class);
    }

    @Test
    public void parseTags() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x05,
                    0x02,
                    0x00,
                    0x00,
                    0x00,
                    0x39,
                    0x01,
                    0x08,
                    0x41,
                    0x75,
                    0x74,
                    0x68,
                    0x6f,
                    0x72,
                    0x20,
                    0x31,
                    0x02,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c,
                    0x03,
                    0x09,
                    0x4d,
                    0x65,
                    0x73,
                    0x73,
                    0x61,
                    0x67,
                    0x65,
                    0x20,
                    0x31,
                    0x04,
                    0x0e,
                    0x54,
                    0x65,
                    0x73,
                    0x74,
                    0x20,
                    0x43,
                    0x68,
                    0x61,
                    0x74,
                    0x20,
                    0x52,
                    0x6f,
                    0x6f,
                    0x6d,
                    0x05,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c
                };
        AncillaryTextLocalSetFactory factory = new AncillaryTextLocalSetFactory();
        AncillaryTextLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST0808 Ancillary Text");
        assertEquals(localSet.getUniversalLabel(), AncillaryTextLocalSet.AncillaryTextLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 5);
        checkOriginatorExample(localSet);
        checkPrecisionTimeStampExample(localSet);
        checkMessageBodyExample(localSet);
        checkSourceExample(localSet);
        checkMessageCreationTimeExample(localSet);
        assertEquals(
                localSet.frameMessage(false),
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x05,
                    0x02,
                    0x00,
                    0x00,
                    0x00,
                    0x39,
                    0x01,
                    0x08,
                    0x41,
                    0x75,
                    0x74,
                    0x68,
                    0x6f,
                    0x72,
                    0x20,
                    0x31,
                    0x02,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c,
                    0x03,
                    0x09,
                    0x4d,
                    0x65,
                    0x73,
                    0x73,
                    0x61,
                    0x67,
                    0x65,
                    0x20,
                    0x31,
                    0x04,
                    0x0e,
                    0x54,
                    0x65,
                    0x73,
                    0x74,
                    0x20,
                    0x43,
                    0x68,
                    0x61,
                    0x74,
                    0x20,
                    0x52,
                    0x6f,
                    0x6f,
                    0x6d,
                    0x05,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c
                });
        assertEquals(
                localSet.frameMessage(true),
                new byte[] {
                    0x01,
                    0x08,
                    0x41,
                    0x75,
                    0x74,
                    0x68,
                    0x6f,
                    0x72,
                    0x20,
                    0x31,
                    0x02,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c,
                    0x03,
                    0x09,
                    0x4d,
                    0x65,
                    0x73,
                    0x73,
                    0x61,
                    0x67,
                    0x65,
                    0x20,
                    0x31,
                    0x04,
                    0x0e,
                    0x54,
                    0x65,
                    0x73,
                    0x74,
                    0x20,
                    0x43,
                    0x68,
                    0x61,
                    0x74,
                    0x20,
                    0x52,
                    0x6f,
                    0x6f,
                    0x6d,
                    0x05,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5f,
                    0x58,
                    0x59,
                    0x6c
                });
    }

    private void checkOriginatorExample(AncillaryTextLocalSet localSet) {
        final String stringVal = "Author 1";
        assertTrue(localSet.getIdentifiers().contains(AncillaryTextMetadataKey.Originator));
        IAncillaryTextMetadataValue v = localSet.getField(AncillaryTextMetadataKey.Originator);
        assertEquals(v.getDisplayName(), "Originator");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof NaturalText);
        NaturalText text = (NaturalText) localSet.getField(AncillaryTextMetadataKey.Originator);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkPrecisionTimeStampExample(AncillaryTextLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(AncillaryTextMetadataKey.PrecisionTimeStamp));
        IAncillaryTextMetadataValue v =
                localSet.getField(AncillaryTextMetadataKey.PrecisionTimeStamp);
        assertEquals(v.getDisplayName(), "Precision Time Stamp");
        assertEquals(v.getDisplayableValue(), "1112376654453100");
        assertTrue(v instanceof ST0603TimeStamp);
        ST0603TimeStamp timestamp =
                (ST0603TimeStamp) localSet.getField(AncillaryTextMetadataKey.PrecisionTimeStamp);
        assertEquals(timestamp.getDisplayableValueDateTime(), "2005-04-01T17:30:54.4531");
        assertEquals(timestamp.getMicroseconds(), 1112376654453100L);
    }

    private void checkMessageBodyExample(AncillaryTextLocalSet localSet) {
        final String stringVal = "Message 1";
        assertTrue(localSet.getIdentifiers().contains(AncillaryTextMetadataKey.MessageBody));
        IAncillaryTextMetadataValue v = localSet.getField(AncillaryTextMetadataKey.MessageBody);
        assertEquals(v.getDisplayName(), "Message Body");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof NaturalText);
        NaturalText text = (NaturalText) localSet.getField(AncillaryTextMetadataKey.MessageBody);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkSourceExample(AncillaryTextLocalSet localSet) {
        final String stringVal = "Test Chat Room";
        assertTrue(localSet.getIdentifiers().contains(AncillaryTextMetadataKey.Source));
        IAncillaryTextMetadataValue v = localSet.getField(AncillaryTextMetadataKey.Source);
        assertEquals(v.getDisplayName(), "Source");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof NaturalText);
        NaturalText text = (NaturalText) localSet.getField(AncillaryTextMetadataKey.Source);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkMessageCreationTimeExample(AncillaryTextLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(AncillaryTextMetadataKey.MessageCreationTime));
        IAncillaryTextMetadataValue v =
                localSet.getField(AncillaryTextMetadataKey.MessageCreationTime);
        assertEquals(v.getDisplayName(), "Message Creation Time");
        assertEquals(v.getDisplayableValue(), "2005-04-01T17:30:54.4531");
        assertTrue(v instanceof ST0603TimeStamp);
        ST0603TimeStamp timestamp =
                (ST0603TimeStamp) localSet.getField(AncillaryTextMetadataKey.MessageCreationTime);
        assertEquals(timestamp.getDisplayableValueDateTime(), "2005-04-01T17:30:54.4531");
        assertEquals(timestamp.getMicroseconds(), 1112376654453100L);
    }
}
