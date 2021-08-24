package org.jmisb.examples.rawklv;

import org.jmisb.api.common.IInvalidDataHandlerStrategy;
import org.jmisb.api.common.KlvParseException;
import org.slf4j.Logger;

/** Invalid data strategy implementation that just dumps to System.out. */
public class PrintOnInvalidDataStrategy implements IInvalidDataHandlerStrategy {

    @Override
    public void process(Logger logger, String message) throws KlvParseException {
        System.out.println(message);
    }
}
