package org.jmisb.examples.parserplugin.timemessage;

import org.jmisb.api.klv.IKlvValue;

/** Interface for a value in a Time Message. */
public interface ITimeMessageValue extends IKlvValue {
    /**
     * Get the encoded bytes associated with a value.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}
