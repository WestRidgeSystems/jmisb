/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.api.klv.st0903.shared;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared implementation of VTarget and VTracker confidence levels.
 */
public abstract class Confidence implements IVmtiMetadataValue {
    
    protected static final int MIN_VALUE = 0;
    protected static final int MAX_VALUE = 100;
    protected final short confidence;

    /**
     * Create from value.
     *
     * @param confidence the confidence as a percentage (0 lowest, 100 highest)
     */
    public Confidence(short confidence)
    {
        if (confidence < MIN_VALUE || confidence > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " value must be in range [0,100]");
        }
        this.confidence = confidence; 
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public Confidence(byte[] bytes)
    {
        if (bytes.length > 1)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is maximum one byte unsigned integer");
        }
        confidence = (short)PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes(confidence);
    }

    @Override
    public String getDisplayableValue() {
        return "" + confidence + "%";
    }

    /**
     * Get the confidence level.
     *
     * @return the confidence as a percentage (0 lowest, 100 highest).
     */
    public short getConfidence() {
        return this.confidence;
    }
    
}
