package org.jmisb.st0806;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.MisbMessageFactory;
import org.testng.annotations.Test;

/** Unit tests for RvtLocalSetFactory. */
public class RvtLocalSetFactoryTest {
    private static final byte[] BYTES =
            new byte[] {
                0x06,
                0x0E,
                0x2B,
                0x34,
                0x02,
                0x0B,
                0x01,
                0x01,
                0x0E,
                0x01,
                0x03,
                0x01,
                0x02,
                0x00,
                0x00,
                0x00,
                0x09,
                0x08,
                0x01,
                0x04,
                0x01,
                0x04,
                (byte) 0x82,
                0x35,
                0x32,
                0x10
            };

    @Test
    public void checkUniversalLabel() {
        RvtLocalSetFactory factory = new RvtLocalSetFactory();
        assertEquals(
                factory.getUniversalLabel().getBytes(),
                new byte[] {
                    (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00
                });
    }

    @Test
    public void parseTag8() throws KlvParseException {

        RvtLocalSetFactory factory = new RvtLocalSetFactory();
        RvtLocalSet localSet = factory.create(BYTES);
        checkLocalSetResultsForTag8(localSet);
    }

    @Test
    public void checkFromBytesService() throws KlvParseException {
        MisbMessageFactory messageFactory = MisbMessageFactory.getInstance();
        IMisbMessage message = messageFactory.handleMessage(BYTES);
        assertTrue(message instanceof RvtLocalSet);
        RvtLocalSet localSet = (RvtLocalSet) message;
        checkLocalSetResultsForTag8(localSet);
    }

    private void checkLocalSetResultsForTag8(RvtLocalSet localSet) {
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 1);
        assertTrue(
                localSet.getIdentifiers()
                        .contains(
                                new RvtMetadataIdentifier(
                                        RvtMetadataKind.PLAIN,
                                        RvtMetadataKey.UASLSVersionNumber.getIdentifier())));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.UASLSVersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), "ST0806.4");
    }
}
