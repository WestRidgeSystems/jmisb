package org.jmisb.st0601;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.st0601.dto.Location;

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

    private final FpEncoder latDecoder = new FpEncoder(-90, 90, 4, OutOfRangeBehaviour.Default);
    private final FpEncoder lonDecoder = new FpEncoder(-180, 180, 4, OutOfRangeBehaviour.Default);
    private final FpEncoder haeDecoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);

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
                    break;
                case 8:
                    recoveryLocationIsUnknown = false;
                    recoveryLocation = new Location();
                    recoveryLocation.setLatitude(latDecoder.decode(bytes, idx));
                    idx += 4;
                    recoveryLocation.setLongitude(lonDecoder.decode(bytes, idx));
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
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        if (takeoffLocationIsUnknown) {
            arrayBuilder.appendAsBerLength(0);
        } else {
            addLocationBytes(arrayBuilder, takeoffLocation);
        }
        if (recoveryLocationIsUnknown) {
            arrayBuilder.appendAsBerLength(0);
        } else if (!takeoffAndRecoveryLocationsAreTheSame()) {
            addLocationBytes(arrayBuilder, recoveryLocation);
        }
        return arrayBuilder.toBytes();
    }

    private void addLocationBytes(ArrayBuilder arrayBuilder, Location location) {
        byte[] latBytes = latDecoder.encode(location.getLatitude());
        byte[] lonBytes = lonDecoder.encode(location.getLongitude());
        byte[] haeBytes = new byte[] {};
        if (!(location.getHAE() < -900.0)) {
            haeBytes = haeDecoder.encode(location.getHAE());
        }
        int locationByteCount = latBytes.length + lonBytes.length + haeBytes.length;
        arrayBuilder.appendAsBerLength(locationByteCount);
        arrayBuilder.append(latBytes);
        arrayBuilder.append(lonBytes);
        arrayBuilder.append(haeBytes);
    }

    /**
     * Get the Takeoff Location.
     *
     * @return the location, or null if it is not known.
     */
    public Location getTakeoffLocation() {
        return takeoffLocation;
    }

    /**
     * Get the Recovery Location.
     *
     * @return the location, or null if it is not known.
     */
    public Location getRecoveryLocation() {
        return recoveryLocation;
    }

    /**
     * Whether the takeoff location is unknown.
     *
     * @return true if its unknown, false if its known
     */
    public boolean isTakeoffLocationUnknown() {
        return takeoffLocationIsUnknown;
    }

    /**
     * Whether the recovery location is unknown.
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
