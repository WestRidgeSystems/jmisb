package org.jmisb.api.klv;

import static org.testng.Assert.*;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import java.util.List;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.common.LogOnInvalidDataStrategy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import uk.org.lidalia.slf4jext.Level;

/** Unit tests for LdsParser. */
public class LdsParserTest {

    private final TestLogger testLogger = TestLoggerFactory.getTestLogger(LdsParser.class);

    @Test
    public void checkSingleParse() throws KlvParseException {
        byte[] bytes = new byte[] {0x07, 0x03, 0x04, 0x03, 0x02};
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(fields.size(), 1);
        LdsField field = fields.get(0);
        assertEquals(field.getTag(), 7);
        assertEquals(field.getData(), new byte[] {0x04, 0x03, 0x02});
        assertEquals(testLogger.getLoggingEvents().size(), 1);
        assertEquals(testLogger.getLoggingEvents().get(0).getMessage(), "Tags: 7 ");
    }

    @Test
    public void checkSingleParseNoDebug() throws KlvParseException {
        byte[] bytes = new byte[] {0x07, 0x03, 0x04, 0x03, 0x02};
        testLogger.setEnabledLevels(Level.OFF);
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(fields.size(), 1);
        LdsField field = fields.get(0);
        assertEquals(field.getTag(), 7);
        assertEquals(field.getData(), new byte[] {0x04, 0x03, 0x02});
        assertEquals(testLogger.getLoggingEvents().size(), 0);
    }

    @Test
    public void checkMultiParse() throws KlvParseException {
        byte[] bytes = new byte[] {0x07, 0x03, 0x04, 0x03, 0x02, 0x02, 0x01, 0x0f};
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(fields.size(), 2);
        LdsField field0 = fields.get(0);
        assertEquals(field0.getTag(), 7);
        assertEquals(field0.getData(), new byte[] {0x04, 0x03, 0x02});
        LdsField field1 = fields.get(1);
        assertEquals(field1.getTag(), 2);
        assertEquals(field1.getData(), new byte[] {0x0f});
        assertEquals(testLogger.getLoggingEvents().size(), 1);
        assertEquals(testLogger.getLoggingEvents().get(0).getMessage(), "Tags: 7 2 ");
    }

    @Test
    public void checkParseOverrun() throws KlvParseException {
        byte[] bytes = new byte[] {0x07, 0x03, 0x04, 0x03};
        InvalidDataHandler.getInstance().setOverrunStrategy(new LogOnInvalidDataStrategy());
        LdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(testLogger.getLoggingEvents().size(), 2);
        assertEquals(
                testLogger.getLoggingEvents().get(0).getMessage(),
                "Overrun encountered while parsing LDS fields");
        assertEquals(testLogger.getLoggingEvents().get(1).getMessage(), "Tags: 7 ");
    }

    @AfterMethod
    public void cleanup() {
        testLogger.clearAll();
    }
}
