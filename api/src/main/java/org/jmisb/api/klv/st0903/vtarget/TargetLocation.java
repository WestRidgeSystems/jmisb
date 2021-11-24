package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.LocVelAccPackKey;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Location (ST0903 VTarget Pack Item 17 and VTrackItem Pack Item 13).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Provides detailed geo-positioning information for a target, optionally including the standard
 * deviation and correlation coefficients. This item is of type Location which is a Defined-Length
 * Truncation Pack. To specify the geographic coordinates for a target when the VMTI LS is not
 * embedded within a MISB ST 0601 LS, Target Location VTarget Pack Tag 17 must be used in lieu of
 * Target Location Latitude Offset VTarget Pack Tag 10 and Target Location Longitude Offset VTarget
 * Pack Tag 11, since ST 0601 Target Latitude and Target Longitude (Frame Center Coordinates) will
 * not be specified. However, even embedding the VMTI LS within a ST 0601 LS, Target Location may
 * still be used
 *
 * </blockquote>
 */
public class TargetLocation
        implements IVmtiMetadataValue, IVTrackItemMetadataValue, INestedKlvValue {

    private final LocationPack value;
    private static final int COORDINATES_GROUP_LEN = 10;
    private static final int STANDARD_DEVIATIONS_GROUP_LEN = 6;
    private static final int CORRELATION_GROUP_LEN = 6;
    private static final double MIN_SIGMA_VAL = 0.0;
    private static final double MAX_SIGMA_VAL = 650.0;
    private static final double MIN_RHO_VAL = -1.0;
    private static final double MAX_RHO_VAL = 1.0;
    private static final double MIN_LAT_VAL = -90.0;
    private static final double MAX_LAT_VAL = 90.0;
    private static final double MIN_LON_VAL = -180.0;
    private static final double MAX_LON_VAL = 180.0;
    protected static final double MIN_HAE_VAL = -900;
    protected static final double MAX_HAE_VAL = 19000;
    private static final int LEGACY_INT_RANGE = 65535;
    private static final long MAX_LAT_LON_INT_RANGE = 4294967295L;
    private static final int NUM_BYTES = 2;
    private static final int NUM_BYTES_LAT_LON = 4;
    private static final FpEncoder LatEncoder =
            new FpEncoder(MIN_LAT_VAL, MAX_LAT_VAL, NUM_BYTES_LAT_LON, OutOfRangeBehaviour.Default);
    private static final FpEncoder LonEncoder =
            new FpEncoder(MIN_LON_VAL, MAX_LON_VAL, NUM_BYTES_LAT_LON, OutOfRangeBehaviour.Default);
    private static final FpEncoder HaeEncoder =
            new FpEncoder(MIN_HAE_VAL, MAX_HAE_VAL, NUM_BYTES, OutOfRangeBehaviour.Default);
    private static final FpEncoder SigmaEncoder =
            new FpEncoder(MIN_SIGMA_VAL, MAX_SIGMA_VAL, NUM_BYTES, OutOfRangeBehaviour.Default);
    private static final FpEncoder RhoEncoder =
            new FpEncoder(MIN_RHO_VAL, MAX_RHO_VAL, NUM_BYTES, OutOfRangeBehaviour.Default);

    /**
     * Create from value.
     *
     * @param targetLocation the packed target location structure.
     */
    public TargetLocation(LocationPack targetLocation) {
        value = targetLocation;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note: this constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array.
     * @deprecated use {@link #TargetLocation(byte[], EncodingMode)} to specify the encoding used in
     *     {@code bytes} array.
     */
    @Deprecated
    public TargetLocation(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding for each element to 2-byte IMAPB (4-byte IMAPB for Latitude /
     * Longitude) in ST0903.4. Earlier versions used a set of unsigned integer encoding that was
     * then mapped into the same ranges that the IMAPB encoding uses. Which formatting applies can
     * only be determined from the ST0903 version in the parent {@link
     * org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code encodingMode} parameter determines whether
     * to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array.
     * @param encodingMode the encoding that is used for the floating point values in the {@code
     *     bytes} array.
     */
    public TargetLocation(byte[] bytes, EncodingMode encodingMode) {
        if ((bytes.length == COORDINATES_GROUP_LEN)
                || (bytes.length == COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN)
                || (bytes.length
                        == COORDINATES_GROUP_LEN
                                + STANDARD_DEVIATIONS_GROUP_LEN
                                + CORRELATION_GROUP_LEN)) {
            value = targetLocationPackFromBytes(bytes, encodingMode);
        } else {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " length must match one of 10, 16 or 22");
        }
    }

    /**
     * Parse a LocationPack from a byte array.
     *
     * <p>This static utility method is provided to support Series types that embed the LocationPack
     * within other structures.
     *
     * <p>ST0903 changed the encoding for each element to 2-byte IMAPB (4-byte IMAPB for Latitude /
     * Longitude) in ST0903.4. Earlier versions used a set of unsigned integer encoding that was
     * then mapped into the same ranges that the IMAPB encoding uses. Which formatting applies can
     * only be determined from the ST0903 version in this {@link
     * org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code encodingMode} parameter determines whether
     * to parse using the legacy encoding or current encoding.
     *
     * @param bytes the byte array to parse, length 10, 16 or 22
     * @param encodingMode the encoding that is used for the floating point values in the {@code
     *     bytes} array.
     * @return LocationPack data from the {@code bytes} array.
     */
    public static LocationPack targetLocationPackFromBytes(
            byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            return parseAsLegacy(bytes);
        } else {
            return parseAsIMAPB(bytes);
        }
    }

    private static LocationPack parseAsLegacy(byte[] bytes) {
        switch (bytes.length) {
            case COORDINATES_GROUP_LEN:
                {
                    double lat = legacyLatitudeDecoder(bytes, 0);
                    double lon = legacyLongitudeDecoder(bytes, 4);
                    double hae = legacyHaeDecoder(bytes, 8);
                    return new LocationPack(lat, lon, hae);
                }
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double lat = legacyLatitudeDecoder(bytes, 0);
                    double lon = legacyLongitudeDecoder(bytes, 4);
                    double hae = legacyHaeDecoder(bytes, 8);
                    double sigEast = legacySigmaDecoder(bytes, 10);
                    double sigNorth = legacySigmaDecoder(bytes, 12);
                    double sigUp = legacySigmaDecoder(bytes, 14);
                    return new LocationPack(lat, lon, hae, sigEast, sigNorth, sigUp);
                }
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double lat = legacyLatitudeDecoder(bytes, 0);
                    double lon = legacyLongitudeDecoder(bytes, 4);
                    double hae = legacyHaeDecoder(bytes, 8);
                    double sigEast = legacySigmaDecoder(bytes, 10);
                    double sigNorth = legacySigmaDecoder(bytes, 12);
                    double sigUp = legacySigmaDecoder(bytes, 14);
                    double rhoEastNorth = legacyRhoDecoder(bytes, 16);
                    double rhoEastUp = legacyRhoDecoder(bytes, 18);
                    double rhoNorthUp = legacyRhoDecoder(bytes, 20);
                    return new LocationPack(
                            lat,
                            lon,
                            hae,
                            sigEast,
                            sigNorth,
                            sigUp,
                            rhoEastNorth,
                            rhoEastUp,
                            rhoNorthUp);
                }
            default:
                throw new IllegalArgumentException(
                        "Target Location Pack length must match one of 10, 16 or 22");
        }
    }

    private static double legacyLatitudeDecoder(byte[] bytes, int offset) {
        byte[] byteSubset =
                new byte[] {bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]};
        long longVal = PrimitiveConverter.toUint32(byteSubset);
        return MIN_LAT_VAL + (longVal * (MAX_LAT_VAL - MIN_LAT_VAL) / MAX_LAT_LON_INT_RANGE);
    }

    private static double legacyLongitudeDecoder(byte[] bytes, int offset) {
        byte[] byteSubset =
                new byte[] {bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]};
        long longVal = PrimitiveConverter.toUint32(byteSubset);
        return MIN_LON_VAL + (longVal * (MAX_LON_VAL - MIN_LON_VAL) / MAX_LAT_LON_INT_RANGE);
    }

    private static double legacyHaeDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.toUint16(byteSubset);
        return MIN_HAE_VAL + (v * (MAX_HAE_VAL - MIN_HAE_VAL) / LEGACY_INT_RANGE);
    }

    private static double legacySigmaDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.toUint16(byteSubset);
        return MIN_SIGMA_VAL + (v * (MAX_SIGMA_VAL - MIN_SIGMA_VAL) / LEGACY_INT_RANGE);
    }

    private static double legacyRhoDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.toUint16(byteSubset);
        return MIN_RHO_VAL + (v * (MAX_RHO_VAL - MIN_RHO_VAL) / LEGACY_INT_RANGE);
    }

    private static LocationPack parseAsIMAPB(byte[] bytes) throws IllegalArgumentException {
        switch (bytes.length) {
            case COORDINATES_GROUP_LEN:
                {
                    double lat = LatEncoder.decode(bytes, 0);
                    double lon = LonEncoder.decode(bytes, 4);
                    double hae = HaeEncoder.decode(bytes, 8);
                    return new LocationPack(lat, lon, hae);
                }
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double lat = LatEncoder.decode(bytes, 0);
                    double lon = LonEncoder.decode(bytes, 4);
                    double hae = HaeEncoder.decode(bytes, 8);
                    double sigEast = SigmaEncoder.decode(bytes, 10);
                    double sigNorth = SigmaEncoder.decode(bytes, 12);
                    double sigUp = SigmaEncoder.decode(bytes, 14);
                    return new LocationPack(lat, lon, hae, sigEast, sigNorth, sigUp);
                }
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double lat = LatEncoder.decode(bytes, 0);
                    double lon = LonEncoder.decode(bytes, 4);
                    double hae = HaeEncoder.decode(bytes, 8);
                    double sigEast = SigmaEncoder.decode(bytes, 10);
                    double sigNorth = SigmaEncoder.decode(bytes, 12);
                    double sigUp = SigmaEncoder.decode(bytes, 14);
                    double rhoEastNorth = RhoEncoder.decode(bytes, 16);
                    double rhoEastUp = RhoEncoder.decode(bytes, 18);
                    double rhoNorthUp = RhoEncoder.decode(bytes, 20);
                    return new LocationPack(
                            lat,
                            lon,
                            hae,
                            sigEast,
                            sigNorth,
                            sigUp,
                            rhoEastNorth,
                            rhoEastUp,
                            rhoNorthUp);
                }
            default:
                throw new IllegalArgumentException(
                        "Target Location Pack length must match one of 10, 16 or 22");
        }
    }

    @Override
    public byte[] getBytes() {
        return serialiseLocationPack(value);
    }

    /**
     * Serialise a LocationPack to a byte array.
     *
     * <p>This static utility method is provided to support Series types that embed the LocationPack
     * within other structures.
     *
     * @param targetLocationPack the LocationPack to serialise
     * @return the byte array containing the serialised LocationPack.
     */
    public static byte[] serialiseLocationPack(LocationPack targetLocationPack) {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        if (hasRequiredValues(targetLocationPack)) {
            chunks.add(LatEncoder.encode(targetLocationPack.getLat()));
            chunks.add(LonEncoder.encode(targetLocationPack.getLon()));
            chunks.add(HaeEncoder.encode(targetLocationPack.getHae()));
            len += COORDINATES_GROUP_LEN;
            if (hasStandardDeviations(targetLocationPack)) {
                chunks.add(SigmaEncoder.encode(targetLocationPack.getSigEast()));
                chunks.add(SigmaEncoder.encode(targetLocationPack.getSigNorth()));
                chunks.add(SigmaEncoder.encode(targetLocationPack.getSigUp()));
                len += STANDARD_DEVIATIONS_GROUP_LEN;
                if (hasCorrelations(targetLocationPack)) {
                    chunks.add(RhoEncoder.encode(targetLocationPack.getRhoEastNorth()));
                    chunks.add(RhoEncoder.encode(targetLocationPack.getRhoEastUp()));
                    chunks.add(RhoEncoder.encode(targetLocationPack.getRhoNorthUp()));
                    len += CORRELATION_GROUP_LEN;
                }
            }
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue() {
        return "[Location]";
    }

    @Override
    public final String getDisplayName() {
        return "Target Location";
    }

    /**
     * Get the Target Location.
     *
     * @return the target location as a packed structure.
     */
    public LocationPack getTargetLocation() {
        return value;
    }

    private static boolean hasRequiredValues(LocationPack value) {
        return (value.getLat() != null) && (value.getLon() != null) && (value.getHae() != null);
    }

    private static boolean hasStandardDeviations(LocationPack value) {
        return (value.getSigEast() != null)
                && (value.getSigNorth() != null)
                && (value.getSigUp() != null);
    }

    private static boolean hasCorrelations(LocationPack value) {
        return (value.getRhoEastNorth() != null)
                && (value.getRhoEastUp() != null)
                && (value.getRhoNorthUp() != null);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<LocVelAccPackKey> keys = EnumSet.noneOf(LocVelAccPackKey.class);
        if (hasRequiredValues(value)) {
            keys.add(LocVelAccPackKey.east);
            keys.add(LocVelAccPackKey.north);
            keys.add(LocVelAccPackKey.up);
            if (hasStandardDeviations(value)) {
                keys.add(LocVelAccPackKey.sigEast);
                keys.add(LocVelAccPackKey.sigNorth);
                keys.add(LocVelAccPackKey.sigUp);
                if (hasCorrelations(value)) {
                    keys.add(LocVelAccPackKey.rhoEastNorth);
                    keys.add(LocVelAccPackKey.rhoEastUp);
                    keys.add(LocVelAccPackKey.rhoNorthUp);
                }
            }
        }
        return keys;
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        switch ((LocVelAccPackKey) tag) {
            case east:
                return new VmtiTextString("Latitude", String.format("%.4f°", value.getLat()));
            case north:
                return new VmtiTextString("Longitude", String.format("%.4f°", value.getLon()));
            case up:
                return new VmtiTextString("HAE", String.format("%.1fm", value.getHae()));
            case sigEast:
                return new VmtiTextString(
                        "Standard Deviation East", String.format("%.1fm", value.getSigEast()));
            case sigNorth:
                return new VmtiTextString(
                        "Standard Deviation North", String.format("%.1fm", value.getSigNorth()));
            case sigUp:
                return new VmtiTextString(
                        "Standard Deviation Up", String.format("%.1fm", value.getSigUp()));
            case rhoEastNorth:
                return new VmtiTextString(
                        "Cross Correlation East North",
                        String.format("%.2f", value.getRhoEastNorth()));
            case rhoEastUp:
                return new VmtiTextString(
                        "Cross Correlation East Up", String.format("%.2f", value.getRhoEastUp()));
            case rhoNorthUp:
                return new VmtiTextString(
                        "Cross Correlation North Up", String.format("%.2f", value.getRhoNorthUp()));
        }
        return null;
    }
}
