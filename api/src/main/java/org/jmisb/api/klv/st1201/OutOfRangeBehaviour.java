package org.jmisb.api.klv.st1201;

/**
 * Out-of-range behavior options for ST 1201 IMAP encoding.
 *
 * <p>In earlier versions of ST 1201, there was no description of what happened in the event that
 * the input value was beyond the range of the IMAP definition. For example, given {@code
 * IMAPB(-50.0, 5000.0, 3)}, what the code should do if the value to be encoded is -100.0, or
 * 6000.0. Earlier jMISB implementations assumed that was an error, and throw an Exception.
 *
 * <p>As of ST 1201.5, there are special flag values for out-of-range indications. Those will be
 * used if the {@code Default} behavior is selected.
 *
 * <p>This enumeration is used to modify the encoding to reflect the required outcome.
 */
public enum OutOfRangeBehaviour {
    /**
     * Use default handling of out-of-range.
     *
     * <p>Inputs that are beyond the valid range are flagged with ST 1201.5 special case values.
     * Decode of those values will be clamped to the IMAP range (i.e. IMAP_ABOVE_MAXIMUM will be
     * decoded as the maximum valid value, IMAP_BELOW_MINIMUM will be decoded as the minimum valid
     * value).
     */
    Default,
    /**
     * Throw exception.
     *
     * <p>Inputs that are beyond the valid range will result in a Java level Exception, as noted in
     * the API.
     *
     * <p>This is almost never what you want. It is retained for backwards behavioral compatibility.
     */
    Throw;
}
