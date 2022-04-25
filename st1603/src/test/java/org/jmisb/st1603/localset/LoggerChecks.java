package org.jmisb.st1603.localset;

import java.util.List;
import org.slf4j.helpers.MessageFormatter;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

/**
 * Superclass for logging checks in a test case.
 *
 * <p>The concept is that there is a test logger that should always contain no log messages after a
 * test has been run. That gets verified in the AfterMethod so if there is a message, the logger
 * needs to be checked and clear()ed before the test method returns.
 *
 * <p>Only ERROR, WARN and INFO levels are checked. DEBUG and lower are implementation detail.
 *
 * <p>The subclass is responsible for initialising the LOGGER correctly by calling the super
 * constructor with the class that is creating the log messages.
 */
public abstract class LoggerChecks {
    protected TestLogger LOGGER;

    public LoggerChecks(Class T) {
        LOGGER = TestLoggerFactory.getTestLogger(T);
        LOGGER.setEnabledLevelsForAllThreads(Level.ERROR, Level.WARN, Level.INFO);
    }

    @BeforeMethod
    public void clearLogger() {
        LOGGER.clear();
    }

    @AfterMethod
    public void checkLogger() {
        verifyNoLoggerMessages();
    }

    protected void verifyNoLoggerMessages() {
        if (LOGGER.getLoggingEvents().size() > 0) {
            List<LoggingEvent> events = LOGGER.getLoggingEvents();
            for (LoggingEvent event : events) {
                System.out.println(event.getLevel().name() + ": " + event.getMessage().toString());
            }
        }
        Assert.assertEquals(LOGGER.getLoggingEvents().size(), 0);
    }

    protected void verifySingleLoggerMessage(final String expectedMessage) {
        Assert.assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        Assert.assertEquals(event.getArguments().size(), 1);
        String message =
                MessageFormatter.format(event.getMessage(), event.getArguments().get(0))
                        .getMessage();
        Assert.assertEquals(message, expectedMessage);
        LOGGER.clear();
    }
}
