package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0601.dto.Location;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Airbase Locations (ST 0601 Item 130).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * The Airbase Locations item is a Variable Length Pack (VLP) describing either the take-off
 * location, the recovery location or both within a Location Defined Length Pack (DLP).
 *
 * <p>Both the take-off and recovery locations are coordinates with WGS84 Latitude, Longitude and
 * Height Above Ellipsoid (HAE). Each location is described in a DLP containing IMAPB values for
 * latitude, longitude and HAE. The latitude and longitude are each four (4) bytes and the HAE is
 * three (3) bytes.
 *
 * </blockquote>
 *
 * <p>See the Location data transfer object documentation for description of the components within a
 * Location.
 */
public class AirbaseLocations implements IUasDatalinkValue {
    private Location takeoffLocation;
    private Location recoveryLocation;
    private boolean takeoffLocationIsUnknown;
    private boolean recoveryLocationIsUnknown;

    private final FpEncoder latDecoder = new FpEncoder(-90, 90, 4);
    private final FpEncoder lonDecoder = new FpEncoder(-180, 180, 4);
    private final FpEncoder haeDecoder = new FpEncoder(-900, 9000, 3);

    /**
     * Create from values.
     *
     * @param takeoff the takeoff location (latitude/longitude, optional height)
     * @param recovery the recovery location (latitude/longitude, optional height)
     */
    public AirbaseLocations(Location takeoff, Location recovery) {
        takeoffLocation = takeoff;
        takeoffLocationIsUnknown = false;
        recoveryLocation = recovery;
        recoveryLocationIsUnknown = false;
    }

    // Used by the static methods only. At least one of takeoff or recovery must
    // be present.
    private AirbaseLocations() {}

    /**
     * Create from value with a known takeoff location but unknown recovery location.
     *
     * @param takeoff the takeoff location (latitude/longitude, optional height)
     * @return the new object instance
     */
    static AirbaseLocations withUnknownRecovery(Location takeoff) {
        AirbaseLocations airbaseLocations = new AirbaseLocations();
        airbaseLocations.takeoffLocation = takeoff;
        airbaseLocations.takeoffLocationIsUnknown = false;
        airbaseLocations.recoveryLocation = null;
        airbaseLocations.recoveryLocationIsUnknown = true;
        return airbaseLocations;
    }

    /**
     * Create from value with a known recovery location but unknown takeoff location.
     *
     * @param recovery the recovery location (latitude/longitude, optional height)
     * @return the new object instance
     */
    static AirbaseLocations withUnknownTakeoff(Location recovery) {
        AirbaseLocations airbaseLocations = new AirbaseLocations();
        airbaseLocations.takeoffLocation = null;
        airbaseLocations.takeoffLocationIsUnknown = true;
        airbaseLocations.recoveryLocation = recovery;
        airbaseLocations.recoveryLocationIsUnknown = false;
        return airbaseLocations;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes encoded value
     */
    public AirbaseLocations(byte[] bytes) {
        int idx = 0;
        BerField takeoffLenField = BerDecoder.decode(bytes, idx, false);
        idx += takeoffLenField.getLength();
        switch (takeoffLenField.getValue()) {
            case 11:
                takeoffLocationIsUnknown = false;
                takeoffLocation = new Location();
                takeoffLocation.setLatitude(latDecoder.decode(bytes, idx));
                idx += 4;
                takeoffLocation.setLongitude(lonDecoder.decode(bytes, idx));
                idx += 4;
                double hae = haeDecoder.decode(bytes, idx);
                takeoffLocation.setHAE(hae);
                idx += 3;
                break;
            case 8:
                takeoffLocationIsUnknown = false;
                takeoffLocation = new Location();
                takeoffLocation.setLatitude(latDecoder.decode(bytes, idx));
                idx += 4;
                takeoffLocation.setLongitude(lonDecoder.decode(bytes, idx));
                idx += 4;
                takeoffLocation.setHAE(-1000.0);
                break;
            case 0:
                takeoffLocation = null;
                takeoffLocationIsUnknown = true;
                break;
            default:
                throw new IllegalArgumentException(
                        this.getDisplayName() + " has unsupported length.");
        }
        if (idx == bytes.length) {
            // If we're out of length here, the pack is truncated.
            // That means recovery location == takeoff location.
            recoveryLocation = new Location();
            if (takeoffLocation == null) {
                // This isn't valid, but see https://github.com/WestRidgeSystems/jmisb/issues/220
                recoveryLocation = null;
                recoveryLocationIsUnknown = true;
            } else {
                recoveryLocation.setLatitude(takeoffLocation.getLatitude());
                recoveryLocation.setLongitude(takeoffLocation.getLongitude());
                recoveryLocation.setHAE(takeoffLocation.getHAE());
            }
        } else {
            BerField recoveryLenField = BerDecoder.decode(bytes, idx, false);
            idx += recoveryLenField.getLength();
            switch (recoveryLenField.getValue()) {
                case 11:
                    recoveryLocationIsUnknown = false;
                    recoveryLocation = new Location();
                    recoveryLocation.setLatitude(latDecoder.decode(bytes, idx));
                    idx += 4;
                    recoveryLocation.setLongitude(lonDecoder.decode(bytes, idx));
                    idx += 4;
                    double hae = haeDecoder.decode(bytes, idx);
                    recoveryLocation.setHAE(hae);
                    idx += 3;
                    break;
                case 8:
                    recoveryLocationIsUnknown = false;
                    recoveryLocation = new Location();
                    recoveryLocation.setLatitude(latDecoder.decode(bytes, idx));
                    idx += 4;
                    recoveryLocation.setLongitude(lonDecoder.decode(bytes, idx));
                    idx += 4;
                    recoveryLocation.setHAE(-1000.0);
                    break;
                case 0:
                    recoveryLocation = null;
                    recoveryLocationIsUnknown = true;
                    break;
                default:
                    throw new IllegalArgumentException(
                            this.getDisplayName() + " has unsupported length.");
            }
        }
    }

    @Override
    public byte[] getBytes() {
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        if (takeoffLocationIsUnknown) {
            byte[] takeoffLenBytes = BerEncoder.encode(0);
            chunks.add(takeoffLenBytes);
            totalLength += takeoffLenBytes.length;
        } else {
            int takeoffLen = 0;
            byte[] latBytes = latDecoder.encode(takeoffLocation.getLatitude());
            takeoffLen += latBytes.length;
            byte[] lonBytes = lonDecoder.encode(takeoffLocation.getLongitude());
            takeoffLen += lonBytes.length;
            byte[] haeBytes = new byte[] {};
            if (!(takeoffLocation.getHAE() < -900.0)) {
                haeBytes = haeDecoder.encode(takeoffLocation.getHAE());
                takeoffLen += haeBytes.length;
            }
            byte[] takeoffLenBytes = BerEncoder.encode(takeoffLen);
            chunks.add(takeoffLenBytes);
            chunks.add(latBytes);
            chunks.add(lonBytes);
            chunks.add(haeBytes);
            totalLength += takeoffLen;
            totalLength += takeoffLenBytes.length;
        }
        if (recoveryLocationIsUnknown) {
            byte[] recoveryLenBytes = BerEncoder.encode(0);
            chunks.add(recoveryLenBytes);
            totalLength += recoveryLenBytes.length;
        } else if (takeoffAndRecoveryLocationsAreTheSame()) {
            // Nothing - its just omitted
        } else {
            int recoveryLen = 0;
            byte[] latBytes = latDecoder.encode(recoveryLocation.getLatitude());
            recoveryLen += latBytes.length;
            byte[] lonBytes = lonDecoder.encode(recoveryLocation.getLongitude());
            recoveryLen += lonBytes.length;
            byte[] haeBytes = new byte[] {};
            if (!(recoveryLocation.getHAE() < -900.0)) {
                haeBytes = haeDecoder.encode(recoveryLocation.getHAE());
                recoveryLen += haeBytes.length;
            }
            byte[] recoveryLenBytes = BerEncoder.encode(recoveryLen);
            chunks.add(recoveryLenBytes);
            chunks.add(latBytes);
            chunks.add(lonBytes);
            chunks.add(haeBytes);
            totalLength += recoveryLen;
            totalLength += recoveryLenBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, totalLength);
    }

    /**
     * Get the Takeoff Location.
     *
     * <p>
     *
     * @return the location, or null if it is not known.
     */
    public Location getTakeoffLocation() {
        return takeoffLocation;
    }

    /**
     * Get the Recovery Location.
     *
     * <p>
     *
     * @return the location, or null if it is not known.
     */
    public Location getRecoveryLocation() {
        return recoveryLocation;
    }

    /**
     * Whether the takeoff location is unknown.
     *
     * <p>
     *
     * @return true if its unknown, false if its known
     */
    public boolean isTakeoffLocationUnknown() {
        return takeoffLocationIsUnknown;
    }

    /**
     * Whether the recovery location is unknown.
     *
     * <p>
     *
     * @return true if its unknown, false if its known
     */
    public boolean isRecoveryLocationUnknown() {
        return recoveryLocationIsUnknown;
    }

    @Override
    public String getDisplayableValue() {
        return "[Airbase Locations]";
    }

    @Override
    public final String getDisplayName() {
        return "Airbase Locations";
    }

    private boolean takeoffAndRecoveryLocationsAreTheSame() {
        return (takeoffLocation != null) && (takeoffLocation.equals(recoveryLocation));
    }
}
