package org.jmisb.api.klv.st1201;

/**
 * Container class for the result of an ST1201 decode operation.
 *
 * <p>This holds the kind of value mapping, and the results, which is either a double value for a
 * normal mapped value, or an unsigned long identifier for the special cases.
 */
public class DecodeResult {
    private ValueMappingKind kind;
    private long identifier;
    private double value;

    /** Constructor. */
    public DecodeResult() {}

    public ValueMappingKind getKind() {
        return kind;
    }

    public void setKind(ValueMappingKind kind) {
        this.kind = kind;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
