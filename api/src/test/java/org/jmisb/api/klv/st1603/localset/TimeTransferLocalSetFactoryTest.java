package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Unit tests for Time Transfer Local Set Factory implementation. */
public class TimeTransferLocalSetFactoryTest extends LoggerChecks {

    public TimeTransferLocalSetFactoryTest() {
        super(TimeTransferLocalSet.class);
    }

    @Test
    public void checkFactory() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                };
        TimeTransferLocalSetFactory factory = new TimeTransferLocalSetFactory();
        TimeTransferLocalSet localSet = factory.create(bytes);
        verifyNoLoggerMessages();
        assertEquals(localSet.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(localSet.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(localSet.getIdentifiers().size(), 1);
        assertTrue(localSet.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(
                localSet.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(),
                "ST 1603.2");
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(localSet.frameMessage(true), new byte[] {0x01, 0x01, 0x02});
    }
}
