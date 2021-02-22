package org.jmisb.api.common;

import org.slf4j.Logger;

/**
 * Handler for invalid data cases.
 *
 * <p>This is a singleton implementation that allows different strategies to be applied to different
 * kinds of invalid data.
 */
public class InvalidDataHandler {

    private static final InvalidDataHandler INSTANCE = new InvalidDataHandler();
    private IInvalidDataHandlerStrategy invalidChecksumStrategy = new ThrowOnInvalidDataStrategy();
    private IInvalidDataHandlerStrategy missingChecksumStrategy = new ThrowOnInvalidDataStrategy();
    private IInvalidDataHandlerStrategy dataOverrunStrategy = new ThrowOnInvalidDataStrategy();
    private IInvalidDataHandlerStrategy invalidFieldEncodingStrategy =
            new ThrowOnInvalidDataStrategy();

    private InvalidDataHandler() {}

    /**
     * Get the Invalid Data Handler instance.
     *
     * @return the initialised invalid data handler instance.
     */
    public static InvalidDataHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Set the strategy to use in case of an invalid checksum.
     *
     * @param strategy the strategy to use.
     */
    public void setInvalidChecksumStrategy(IInvalidDataHandlerStrategy strategy) {
        invalidChecksumStrategy = strategy;
    }

    /**
     * Set the strategy to use in case of an required, but missing, checksum.
     *
     * @param strategy the strategy to use.
     */
    public void setMissingChecksumStrategy(IInvalidDataHandlerStrategy strategy) {
        missingChecksumStrategy = strategy;
    }

    /**
     * Set the strategy to use in case of a data overrun.
     *
     * @param strategy the strategy to use.
     */
    public void setOverrunStrategy(IInvalidDataHandlerStrategy strategy) {
        dataOverrunStrategy = strategy;
    }

    /**
     * Set the strategy to use in case of invalid field encoding.
     *
     * @param strategy the strategy to use.
     */
    public void setInvalidFieldEncodingStrategy(IInvalidDataHandlerStrategy strategy) {
        invalidFieldEncodingStrategy = strategy;
    }

    /**
     * Handle the case where the checksum is invalid.
     *
     * <p>Invalid in this case means that the checksum is present, but does not have the expected
     * value. This usually indicates some kind of data corruption in the message between production
     * and processing. It could mean that the producer has an incorrect checksum implementation. Its
     * possible that it indicates an error in the jMISB implementation.
     *
     * @param logger source logger, not null
     * @param message a message associated with the source event, ideally describing the nature of
     *     the invalid data
     * @throws KlvParseException if the handling strategy chooses to throw.
     */
    public void handleInvalidChecksum(Logger logger, String message) throws KlvParseException {
        this.invalidChecksumStrategy.process(logger, message);
    }

    /**
     * Handle the case where the checksum is missing.
     *
     * <p>Missing in this case means that the checksum is required, but is not present. This usually
     * means an incorrect producer implementation.
     *
     * <p>Do not use this in cases where the checksum is optional.
     *
     * @param logger source logger, not null
     * @param message a message associated with the source event, ideally describing the nature of
     *     the invalid data
     * @throws KlvParseException if the handling strategy chooses to throw.
     */
    public void handleMissingChecksum(Logger logger, String message) throws KlvParseException {
        this.missingChecksumStrategy.process(logger, message);
    }

    /**
     * Handle the case where the the parsing overruns the available data.
     *
     * <p>Overrun means that not enough data was available to complete the current parse option. In
     * KLV parsing, this could mean some the available length is less than the length specified by
     * the L part of the KLV. This usually results from some kind of data corruption, or incorrect
     * producer implementation.
     *
     * @param logger source logger, not null
     * @param message a message associated with the source event, ideally describing the nature of
     *     the invalid data
     * @throws KlvParseException if the handling strategy chooses to throw.
     */
    public void handleOverrun(Logger logger, String message) throws KlvParseException {
        this.dataOverrunStrategy.process(logger, message);
    }

    /**
     * Handle the case where the field could not be decoded correctly.
     *
     * @param logger source logger, not null
     * @param message a message associated with the source event, ideally describing the nature of
     *     the invalid data
     * @throws KlvParseException if the handling strategy chooses to throw.
     */
    public void handleInvalidFieldEncoding(Logger logger, String message) throws KlvParseException {
        this.invalidFieldEncodingStrategy.process(logger, message);
    }
}
