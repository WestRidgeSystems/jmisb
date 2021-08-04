package org.jmisb.api.klv.st1201;

/**
 * Out-of-range behaviour options for ST 1201 IMAP encoding.
 *
 * <p>In earlier versions of ST 1201, there was not description of what happened in the event that
 * the input value was beyond the range of the IMAP definition. For example, given {@code
 * IMAPB(-50.0, 5000.0, 3)}, what the code should do if the value to be encoded is -100.0, or
 * 6000.0. Earlier jMISB implementations assumed that was an error, and throw an Exception.
 *
 * <p>As of ST 1201.5, there are special flag values for out-of-range indications, and a description
 * that the default value is the clamp the values (i.e. if the input is below the IMAPB range, the
 * lower limit value is encoded; and if the input is above the IMAPB range, the upper limit value is
 * encoded). However it is also permitted that an invoking specification can require the special
 * values to be sent instead (i.e. flagging an error condition to the consumer).
 *
 * <p>This enumeration is used to modify the encoding to reflect the required outcome.
 */
public enum OutOfRangeBehaviour {
    /**
     * Clamp values.
     *
     * <p>Inputs that are beyond the valid range are clamped at the lower or upper limits of the
     * range.
     *
     * <p>This is the default specified in ST 1201.5, and is usually what you want.
     */
    Clamp,
    /**
     * Flag out-of-range.
     *
     * <p>Inputs that are beyond the valid range are flagged with ST 1201.5 special case values.
     *
     * <p>This should be used when the invoking specification requires sending {@code
     * IMAP_ABOVE_MAXIMUM} or {@code IMAP_BELOW_MINIMUM} special values.
     */
    Flag,
    /**
     * Throw exception.
     *
     * <p>Inputs that are beyond the valid range will result in a Java level Exception, as noted in
     * the API.
     *
     * <p>This is almost never what you want. It is retained for backwards behavioural
     * compatibility.
     */
    Throw;
}
