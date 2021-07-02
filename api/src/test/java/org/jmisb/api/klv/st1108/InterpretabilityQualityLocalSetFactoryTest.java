package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1108.st1108_2.LegacyIQLocalSet;
import org.jmisb.api.klv.st1108.st1108_2.LegacyIQLocalSetTest;
import org.jmisb.api.klv.st1108.st1108_3.IQLocalSet;
import org.jmisb.api.klv.st1108.st1108_3.IQLocalSetTest;
import org.testng.annotations.Test;

/** Tests for the ST 1108 Interpretability and Quality Local Set Factory. */
public class InterpretabilityQualityLocalSetFactoryTest extends LoggerChecks {
    InterpretabilityQualityLocalSetFactoryTest() {
        super(IQLocalSet.class);
    }

    @Test
    public void parseTagSimple() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x07,
                    0x01,
                    0x01,
                    0x04,
                    0x0B,
                    0x02,
                    (byte) 0xf5,
                    (byte) 0xca
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        IMisbMessage message = factory.create(bytes);
        assertNotNull(message);
        assertTrue(message instanceof IQLocalSet);
        IQLocalSet localSet = (IQLocalSet) message;
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        IQLocalSetTest.checkAssessmentPoint(localSet);

        assertEquals(localSet.frameMessage(false), bytes);
    }

    @Test
    public void parseTagSimpleLegacy() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x0a,
                    0x01,
                    0x08,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x09,
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        IMisbMessage message = factory.create(bytes);
        assertNotNull(message);
        assertTrue(message instanceof LegacyIQLocalSet);
        LegacyIQLocalSet localSet = (LegacyIQLocalSet) message;
        assertEquals(localSet.displayHeader(), "ST 1108 Legacy Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        LegacyIQLocalSetTest.checkMostRecentFrameTime(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    @Test
    public void parseTagSimpleUnknown() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x05,
                    0x01,
                    0x03,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        IMisbMessage message = factory.create(bytes);
        assertNull(message);
    }

    @Test
    public void parseTagSimpleUnknownNoTag1() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x04,
                    0x02,
                    0x02,
                    (byte) 0x01,
                    (byte) 0x02,
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        IMisbMessage message = factory.create(bytes);
        assertNull(message);
    }
}
