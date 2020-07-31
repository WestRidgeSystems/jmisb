package org.jmisb.api.common;

import org.slf4j.Logger;

/**
 * Invalid data strategy that throws an exception.
 *
 * <p>This is a good strategy to use for production usage.
 */
public class ThrowOnInvalidDataStrategy implements IInvalidDataHandlerStrategy {

    public ThrowOnInvalidDataStrategy() {}

    @Override
    public void process(Logger logger, String message) throws KlvParseException {
        throw new KlvParseException(message);
    }
}
