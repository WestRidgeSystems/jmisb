package org.jmisb.api.common;

import static org.testng.Assert.*;

import org.jmisb.api.klv.LoggerChecks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/** Unit tests for InvalidDataHandler. */
public class InvalidDataHandlerTest extends LoggerChecks {

    private static Logger LOGGER = LoggerFactory.getLogger(InvalidDataHandlerTest.class);

    public InvalidDataHandlerTest() {
        super(InvalidDataHandler.class);
    }

    class TestLoggingHandlerStrategy extends LogOnInvalidDataStrategy {

        int wasCalled = 0;

        @Override
        public void process(Logger logger, String message) throws KlvParseException {
            super.process(logger, message);
            wasCalled++;
        }
    }

    @Test
    public void checkInstance() {
        InvalidDataHandler instance = InvalidDataHandler.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void checkBadChecksumThrow() throws KlvParseException {
        try {
            InvalidDataHandler.getInstance().handleInvalidChecksum(LOGGER, "Test Message");
        } catch (KlvParseException ex) {
            assertEquals(ex.getMessage(), "Test Message");
            return;
        }
        fail();
    }

    @Test
    public void checkMissingChecksumThrow() throws KlvParseException {
        try {
            InvalidDataHandler.getInstance().handleMissingChecksum(LOGGER, "Test Message");
        } catch (KlvParseException ex) {
            assertEquals(ex.getMessage(), "Test Message");
            return;
        }
        fail();
    }

    @Test
    public void checkOverrunThrow() throws KlvParseException {
        try {
            InvalidDataHandler.getInstance().handleOverrun(LOGGER, "Test Message");
        } catch (KlvParseException ex) {
            assertEquals(ex.getMessage(), "Test Message");
            return;
        }
        fail();
    }

    @Test
    public void checkInvalidFieldEncodingThrow() {
        try {
            InvalidDataHandler.getInstance().handleInvalidFieldEncoding(LOGGER, "Test Message");
        } catch (KlvParseException ex) {
            assertEquals(ex.getMessage(), "Test Message");
            return;
        }
        fail();
    }

    @Test
    public void checkMissingChecksumLog() throws KlvParseException {
        TestLoggingHandlerStrategy strategy = new TestLoggingHandlerStrategy();
        InvalidDataHandler.getInstance().setMissingChecksumStrategy(strategy);
        InvalidDataHandler.getInstance().handleMissingChecksum(LOGGER, "Test Message");
        assertEquals(strategy.wasCalled, 1);
        InvalidDataHandler.getInstance()
                .setMissingChecksumStrategy(new ThrowOnInvalidDataStrategy());
    }

    @Test
    public void checkInvalidChecksumLog() throws KlvParseException {
        TestLoggingHandlerStrategy strategy = new TestLoggingHandlerStrategy();
        InvalidDataHandler.getInstance().setInvalidChecksumStrategy(strategy);
        InvalidDataHandler.getInstance().handleInvalidChecksum(LOGGER, "Test Message");
        assertEquals(strategy.wasCalled, 1);
        InvalidDataHandler.getInstance()
                .setInvalidChecksumStrategy(new ThrowOnInvalidDataStrategy());
    }

    @Test
    public void checkInvalidFieldEncodingLog() throws KlvParseException {
        TestLoggingHandlerStrategy strategy = new TestLoggingHandlerStrategy();
        InvalidDataHandler.getInstance().setInvalidFieldEncodingStrategy(strategy);
        InvalidDataHandler.getInstance().handleInvalidFieldEncoding(LOGGER, "Test Message");
        assertEquals(strategy.wasCalled, 1);
        InvalidDataHandler.getInstance()
                .setInvalidFieldEncodingStrategy(new ThrowOnInvalidDataStrategy());
    }

    @Test
    public void checkDataOverrun() throws KlvParseException {
        TestLoggingHandlerStrategy strategy = new TestLoggingHandlerStrategy();
        InvalidDataHandler.getInstance().setOverrunStrategy(strategy);
        InvalidDataHandler.getInstance().handleOverrun(LOGGER, "Test Message");
        assertEquals(strategy.wasCalled, 1);
        InvalidDataHandler.getInstance().setOverrunStrategy(new ThrowOnInvalidDataStrategy());
    }
}
