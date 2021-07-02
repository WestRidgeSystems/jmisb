package org.jmisb.api.klv.st1108.st1108_3.metric;

import org.jmisb.api.klv.IKlvValue;

public interface IMetricLocalSetValue extends IKlvValue {

    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}
