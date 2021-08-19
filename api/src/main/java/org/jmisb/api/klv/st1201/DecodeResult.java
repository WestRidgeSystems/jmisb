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

    /**
     * The kind of result.
     *
     * <p>This can be thought of as a namespace, and indicates how to interpret the rest of the
     * result. For {@link ValueMappingKind#NormalMappedValue}, the result value is provided by
     * {@link #getValue()}. For other kinds of results, the result value is provided by {@link
     * #getIdentifier()} if applicable.
     *
     * @return the kind of result
     */
    public ValueMappingKind getKind() {
        return kind;
    }

    /**
     * Set the kind of result.
     *
     * <p>This can be thought of as a namespace, and indicates how to interpret the rest of the
     * result. For {@link ValueMappingKind#NormalMappedValue}, the result value is set by {@link
     * #setValue}. For other kinds of results, the result value is set by {@link #setIdentifier} if
     * applicable.
     *
     * @param kind the kind of result
     */
    public void setKind(ValueMappingKind kind) {
        this.kind = kind;
    }

    /**
     * Get the special-case identifier value.
     *
     * <p>This is the value associated with one of the special-case kinds of value (that is,
     * something other than {@link ValueMappingKind#NormalMappedValue}. Some kinds of values (for
     * example, {@link ValueMappingKind#PositiveInfinity} only ever have a value of 0, but others
     * can have signaling identifiers included. For example, {@link ValueMappingKind#UserDefined}
     * allows use of identifiers to indicate which specific user defined identifier is meant.
     *
     * @return the identifier for the special case kind.
     */
    public long getIdentifier() {
        return identifier;
    }

    /**
     * Set the special-case identifier value.
     *
     * <p>This is the value associated with one of the special-case kinds of value (that is,
     * something other than {@link ValueMappingKind#NormalMappedValue}. Some kinds of values (for
     * example, {@link ValueMappingKind#PositiveInfinity} only ever have a value of 0, but others
     * can have signaling identifiers included. For example, {@link ValueMappingKind#UserDefined}
     * allows use of identifiers to indicate which specific user defined identifier is meant.
     *
     * @param identifier the identifier for the special case kind.
     */
    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    /**
     * Get the normal mapped value.
     *
     * <p>This is the value associated with the {@link ValueMappingKind#NormalMappedValue} decode
     * result, which is the common-case for ST1201 decoding.
     *
     * @return the decoded value as a double.
     */
    public double getValue() {
        return value;
    }

    /**
     * Set the normal mapped value.
     *
     * <p>This is the value associated with the {@link ValueMappingKind#NormalMappedValue} decode
     * result, which is the common-case for ST1201 decoding.
     *
     * @param value the decoded value as a double.
     */
    public void setValue(double value) {
        this.value = value;
    }
}
