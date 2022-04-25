package org.jmisb.examples.parserplugin.timemessage;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

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

    @Override
    public UniversalLabel getUniversalLabel() {
        return TimeMessageConstants.TIME_STAMP_UL;
    }
}
