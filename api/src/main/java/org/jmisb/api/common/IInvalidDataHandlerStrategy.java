package org.jmisb.api.common;

import org.slf4j.Logger;

/** Strategy to handle invalid data. */
public interface IInvalidDataHandlerStrategy {

    /**
     * Process the invalid data event according to the implementing strategy.
     *
     * @param logger a source logger, for use in strategies that log errors
     * @param message a message associated with the source event, ideally describing the nature of
     *     the invalid data
     * @throws org.jmisb.api.common.KlvParseException if the strategy throws
     */
    public void process(Logger logger, String message) throws KlvParseException;
}
