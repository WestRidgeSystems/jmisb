package org.jmisb.examples.parserplugin.timemessage;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.IMisbMessageFactory;

/**
 * Factory for TimeMessage example.
 *
 * <p>The concept is that the main KLV parser looks up a factory instance based on the Universal
 * Label in the message.
 */
public class TimeMessageFactory implements IMisbMessageFactory {

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        return new TimeMessage(bytes);
    }
}
