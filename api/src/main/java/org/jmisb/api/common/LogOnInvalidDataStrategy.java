package org.jmisb.api.common;

import org.slf4j.Logger;

/**
 * Invalid data strategy that logs a warning message on invalid data.
 *
 * <p>This is a good strategy to use for debugging and when handling data that is known to be
 * non-compliant.
 */
public class LogOnInvalidDataStrategy implements IInvalidDataHandlerStrategy {

    /** Constructor. */
    public LogOnInvalidDataStrategy() {}

    @Override
    public void process(Logger logger, String message) throws KlvParseException {
        logger.error(message);
    }
}
