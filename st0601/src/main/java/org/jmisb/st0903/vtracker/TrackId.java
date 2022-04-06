package org.jmisb.st0903.vtracker;

import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.UuidUtils;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackMetadataValue;

/**
 * Track ID (VTracker LS Tag 1).
 *
 * <p>Uniquely identifies a track, using a 128-bit (16-byte) Universal Unique Identification (UUID)
 * as standardized by the Open Software Foundation according to ISO/IEC 9834-8.
 */
public class TrackId implements IVmtiMetadataValue, IVTrackMetadataValue {
    private final UUID id;

    /**
     * Create from value.
     *
     * @param id Microseconds since the epoch
     */
    public TrackId(UUID id) {
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the TrackId could not be parsed.
     */
    public TrackId(byte[] bytes) throws KlvParseException {
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
        return UuidUtils.formatUUID(id);
    }

    @Override
    public String getDisplayName() {
        return "Track ID";
    }

    /**
     * Get UUID.
     *
     * @return the UUID for this track.
     */
    public UUID getUUID() {
        return id;
    }
}
