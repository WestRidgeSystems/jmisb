package org.jmisb.api.klv;

import static org.testng.Assert.*;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import uk.org.lidalia.slf4jext.Level;

/** Unit tests for KlvParser. */
public class KlvParserTest {
    private final TestLogger testLogger = TestLoggerFactory.getTestLogger(KlvParser.class);

    @Test
    public void checkParse() throws KlvParseException {
        testLogger.setEnabledLevels(Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG);
        doParse();
        assertEquals(testLogger.getAllLoggingEvents().size(), 2);
        assertEquals(testLogger.getLoggingEvents().get(0).getLevel(), Level.DEBUG);
        assertEquals(testLogger.getLoggingEvents().get(0).getMessage(), "len: 26");
        assertEquals(testLogger.getLoggingEvents().get(1).getLevel(), Level.DEBUG);
        assertEquals(testLogger.getLoggingEvents().get(1).getMessage(), "Parsed as Unknown");
    }

    @Test
    public void checkParseNoDebug() throws KlvParseException {
        testLogger.setEnabledLevels(Level.ERROR, Level.WARN, Level.INFO);
        doParse();
        assertEquals(testLogger.getAllLoggingEvents().size(), 0);
    }

    private void doParse() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3a
                };
        List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
        assertNotNull(messages);
        assertEquals(messages.size(), 1);
        IMisbMessage message = messages.get(0);
        assertNotNull(message);
        assertEquals(
                message.getUniversalLabel(),
                new UniversalLabel(
                        new byte[] {
                            0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                            0x02, 0x00, 0x00, 0x00
                        }));
        assertEquals(message.displayHeader(), "Unknown");
        assertTrue(message instanceof RawMisbMessage);
        RawMisbMessage rawMessage = (RawMisbMessage) message;
        assertEquals(
                rawMessage.getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3a
                });
        assertTrue(rawMessage.getIdentifiers().isEmpty());
    }

    @Test
    public void checkParseStream() throws KlvParseException {
        doParseStream();
    }

    private void doParseStream() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3a,
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3b
                };
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        List<IMisbMessage> messages = new ArrayList<>();
        List<KlvParseException> errors = new ArrayList<>();
        KlvParser.parseStream(bais, messages::add, errors::add);
        assertEquals(0, errors.size());
        if (!errors.isEmpty()) {
            throw errors.get(0);
        }

        assertNotNull(messages);
        assertEquals(messages.size(), 2);
        for (int i = 0; i < messages.size(); i++) {
            IMisbMessage message = messages.get(i);
            assertNotNull(message);
            assertEquals(
                    message.getUniversalLabel(),
                    new UniversalLabel(
                            new byte[] {
                                0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03,
                                0x01, 0x02, 0x00, 0x00, 0x00
                            }));
            assertEquals(message.displayHeader(), "Unknown");
            assertTrue(message instanceof RawMisbMessage);
            RawMisbMessage rawMessage = (RawMisbMessage) message;
            assertEquals(
                    rawMessage.getBytes(),
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                        0x02, 0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e,
                        0x2f, (byte) (0x3a + i)
                    });
            assertTrue(rawMessage.getIdentifiers().isEmpty());
        }
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLength() throws KlvParseException {
        KlvParser.parseBytes(
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x01
                });
    }

    @Test
    public void parseTwoMessagesWithExtraBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3a,
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x06, 0x01, 0x04
                };
        List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
        assertNotNull(messages);
        assertEquals(messages.size(), 2);
        IMisbMessage message0 = messages.get(0);
        assertNotNull(message0);
        assertEquals(
                message0.getUniversalLabel(),
                new UniversalLabel(
                        new byte[] {
                            0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                            0x02, 0x00, 0x00, 0x00
                        }));
        assertEquals(message0.displayHeader(), "Unknown");
        assertTrue(message0 instanceof RawMisbMessage);
        RawMisbMessage rawMessage0 = (RawMisbMessage) message0;
        assertEquals(
                rawMessage0.getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x04, 0x02, 0x00, 0x4f, 0x01, 0x03, 0x1e, 0x2f, 0x3a
                });
        IMisbMessage message1 = messages.get(1);
        assertNotNull(message1);
        assertEquals(
                message1.getUniversalLabel(),
                new UniversalLabel(
                        new byte[] {
                            0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                            0x02, 0x00, 0x00, 0x00
                        }));
        assertEquals(message1.displayHeader(), "Unknown");
        assertTrue(message1 instanceof RawMisbMessage);
        RawMisbMessage rawMessage1 = (RawMisbMessage) message1;
        assertEquals(
                rawMessage1.getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x06, 0x01, 0x04
                });
    }

    @AfterMethod
    public void cleanup() {
        testLogger.clearAll();
    }
}
