package org.jmisb.st1601;

import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.UuidUtils;

/**
 * Algorithm Configuration Identifier (ST 1601 Tag 7).
 *
 * <p>The Algorithm Configuration Identifier item is a generic identification system based on a
 * Universal Unique Identifier (UUID) to differentiate configurations or set of parameters for a
 * specific Algorithm Name and Algorithm Version. It is up to the algorithm vendor to identify the
 * set of variable configuration parameters and values for each algorithm to form the basis of the
 * UUID.
 *
 * <p>Multiple techniques to generate a UUID are available as discussed in ISO 9834-8 and RFC 4122.
 * NGA Recommended Practice NGA.RP.0001_1.0.0 discusses which UUID versions may be used.
 *
 * <p>This Standard recommends constructing UUIDs from unique configuration information through UUID
 * Version 4 leveraging random numbers or UUID Version 5 leveraging Secure Hash Algorithm (SHA)
 * coding process. For example, combine minimization thresholds, minimum number of tie points,
 * correlation settings, etc. and use either truly random numbers or the SHA hash function to
 * generate the UUID.
 */
public class AlgorithmConfigurationIdentifier implements IGeoRegistrationValue {
    private final UUID id;

    /**
     * Create from value.
     *
     * @param id configuration identifier value
     */
    public AlgorithmConfigurationIdentifier(UUID id) {
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the AlgorithmConfigurationIdentifier could not be parsed.
     */
    public AlgorithmConfigurationIdentifier(byte[] bytes) throws KlvParseException {
        try {
            id = UuidUtils.arrayToUuid(bytes, 0);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    @Override
    public byte[] getBytes() {
        return UuidUtils.uuidToArray(id);
    }

    @Override
    public String getDisplayableValue() {
        return id.toString();
    }

    @Override
    public String getDisplayName() {
        return "Algorithm Configuration Identifier";
    }

    /**
     * Get UUID.
     *
     * @return the UUID for this algorithm configuration.
     */
    public UUID getUUID() {
        return id;
    }
}
