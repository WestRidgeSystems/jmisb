package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.dto.Location;
import org.testng.annotations.Test;

public class AirbaseLocationsTest {
    private final byte[] ST_EXAMPLE_BYTES =
            new byte[] {
                (byte) 0x0B,
                (byte) 0x40,
                (byte) 0x6B,
                (byte) 0xC2,
                (byte) 0x09,
                (byte) 0x19,
                (byte) 0xBD,
                (byte) 0xA5,
                (byte) 0x54,
                (byte) 0x07,
                (byte) 0x0E,
                (byte) 0x00,
                (byte) 0x0B,
                (byte) 0x40,
                (byte) 0x78,
                (byte) 0x3C,
                (byte) 0xB8,
                (byte) 0x19,
                (byte) 0xA2,
                (byte) 0x92,
                (byte) 0x74,
                (byte) 0x07,
                (byte) 0xC6,
                (byte) 0x00
            };
    private final byte[] ST_EXAMPLE_BYTES_NO_HAE =
            new byte[] {
                (byte) 0x08,
                (byte) 0x40,
                (byte) 0x6B,
                (byte) 0xC2,
                (byte) 0x09,
                (byte) 0x19,
                (byte) 0xBD,
                (byte) 0xA5,
                (byte) 0x54,
                (byte) 0x08,
                (byte) 0x40,
                (byte) 0x78,
                (byte) 0x3C,
                (byte) 0xB8,
                (byte) 0x19,
                (byte) 0xA2,
                (byte) 0x92,
                (byte) 0x74
            };
    private final byte[] ST_EXAMPLE_BYTES_UNKNOWN_TAKEOFF =
            new byte[] {
                (byte) 0x00,
                (byte) 0x0B,
                (byte) 0x40,
                (byte) 0x78,
                (byte) 0x3C,
                (byte) 0xB8,
                (byte) 0x19,
                (byte) 0xA2,
                (byte) 0x92,
                (byte) 0x74,
                (byte) 0x07,
                (byte) 0xC6,
                (byte) 0x00
            };
    private final byte[] ST_EXAMPLE_BYTES_UNKNOWN_RECOVERY =
            new byte[] {
                (byte) 0x0B,
                (byte) 0x40,
                (byte) 0x6B,
                (byte) 0xC2,
                (byte) 0x09,
                (byte) 0x19,
                (byte) 0xBD,
                (byte) 0xA5,
                (byte) 0x54,
                (byte) 0x07,
                (byte) 0x0E,
                (byte) 0x00,
                (byte) 0x00
            };
    private final byte[] ST_EXAMPLE_BYTES_OMIT_RECOVERY =
            new byte[] {
                (byte) 0x0B,
                (byte) 0x40,
                (byte) 0x6B,
                (byte) 0xC2,
                (byte) 0x09,
                (byte) 0x19,
                (byte) 0xBD,
                (byte) 0xA5,
                (byte) 0x54,
                (byte) 0x07,
                (byte) 0x0E,
                (byte) 0x00
            };

    private final double TAKEOFF_LAT = 38.841859;
    private final double TAKEOFF_LON = -77.036784;
    private final double TAKEOFF_HAE = 3;
    private final double RECOVERY_LAT = 38.939353;
    private final double RECOVERY_LON = -77.459811;
    private final double RECOVERY_HAE = 95;

    @Test
    public void testConstructFromValue() {
        // From ST:
        Location takeoffLocation = new Location();
        takeoffLocation.setLatitude(TAKEOFF_LAT);
        takeoffLocation.setLongitude(TAKEOFF_LON);
        takeoffLocation.setHAE(TAKEOFF_HAE);
        Location recoveryLocation = new Location();
        recoveryLocation.setLatitude(RECOVERY_LAT);
        recoveryLocation.setLongitude(RECOVERY_LON);
        recoveryLocation.setHAE(RECOVERY_HAE);
        AirbaseLocations airbaseLocations = new AirbaseLocations(takeoffLocation, recoveryLocation);
        checkValuesForExample(airbaseLocations);
    }

    @Test
    public void testConstructFromValueUnknownTakeoff() {
        Location recoveryLocation = new Location();
        recoveryLocation.setLatitude(RECOVERY_LAT);
        recoveryLocation.setLongitude(RECOVERY_LON);
        recoveryLocation.setHAE(RECOVERY_HAE);
        AirbaseLocations airbaseLocations = AirbaseLocations.withUnknownTakeoff(recoveryLocation);
        checkValuesForExampleUnknownTakeoff(airbaseLocations);
    }

    @Test
    public void testConstructFromValueUnknownRecovery() {
        Location takeoffLocation = new Location();
        takeoffLocation.setLatitude(TAKEOFF_LAT);
        takeoffLocation.setLongitude(TAKEOFF_LON);
        takeoffLocation.setHAE(TAKEOFF_HAE);
        AirbaseLocations airbaseLocations = AirbaseLocations.withUnknownRecovery(takeoffLocation);
        checkValuesForExampleUnknownRecovery(airbaseLocations);
    }

    @Test
    public void testConstructFromEncoded() {
        AirbaseLocations airbaseLocations = new AirbaseLocations(ST_EXAMPLE_BYTES);
        checkValuesForExample(airbaseLocations);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.AirbaseLocations, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof AirbaseLocations);
        AirbaseLocations airbaseLocations = (AirbaseLocations) v;
        checkValuesForExample(airbaseLocations);
    }

    @Test
    public void testFactoryNoHAE() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.AirbaseLocations, ST_EXAMPLE_BYTES_NO_HAE);
        assertTrue(v instanceof AirbaseLocations);
        AirbaseLocations airbaseLocations = (AirbaseLocations) v;
        checkValuesForExampleNoHAE(airbaseLocations);
    }

    @Test
    public void testFactoryUnknownTakeoffLocation() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.AirbaseLocations, ST_EXAMPLE_BYTES_UNKNOWN_TAKEOFF);
        assertTrue(v instanceof AirbaseLocations);
        AirbaseLocations airbaseLocations = (AirbaseLocations) v;
        checkValuesForExampleUnknownTakeoff(airbaseLocations);
    }

    @Test
    public void testFactoryUnknownRecoveryLocation() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.AirbaseLocations, ST_EXAMPLE_BYTES_UNKNOWN_RECOVERY);
        assertTrue(v instanceof AirbaseLocations);
        AirbaseLocations airbaseLocations = (AirbaseLocations) v;
        checkValuesForExampleUnknownRecovery(airbaseLocations);
    }

    @Test
    public void testFactoryOmittedRecoveryLocation() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.AirbaseLocations, ST_EXAMPLE_BYTES_OMIT_RECOVERY);
        assertTrue(v instanceof AirbaseLocations);
        AirbaseLocations airbaseLocations = (AirbaseLocations) v;
        checkValuesForExampleOmitRecovery(airbaseLocations);
    }

    private void checkValuesForExample(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getBytes(), ST_EXAMPLE_BYTES);
        checkDisplayValues(airbaseLocations);
        checkTakeoffLocationST(airbaseLocations);
        checkRecoveryLocationST(airbaseLocations);
    }

    private void checkValuesForExampleNoHAE(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getBytes(), ST_EXAMPLE_BYTES_NO_HAE);
        checkDisplayValues(airbaseLocations);
        checkTakeoffLocationLatLon(airbaseLocations);
        assertEquals(airbaseLocations.getTakeoffLocation().getHAE(), -1000, 0.00001);
        checkRecoveryLocationLatLon(airbaseLocations);
        assertEquals(airbaseLocations.getRecoveryLocation().getHAE(), -1000, 0.00001);
    }

    private void checkValuesForExampleUnknownRecovery(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getBytes(), ST_EXAMPLE_BYTES_UNKNOWN_RECOVERY);
        checkDisplayValues(airbaseLocations);
        checkTakeoffLocationST(airbaseLocations);
        assertTrue(airbaseLocations.isRecoveryLocationUnknown());
        assertNull(airbaseLocations.getRecoveryLocation());
    }

    private void checkValuesForExampleUnknownTakeoff(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getBytes(), ST_EXAMPLE_BYTES_UNKNOWN_TAKEOFF);
        checkDisplayValues(airbaseLocations);
        assertTrue(airbaseLocations.isTakeoffLocationUnknown());
        assertNull(airbaseLocations.getTakeoffLocation());
        checkRecoveryLocationST(airbaseLocations);
    }

    private void checkValuesForExampleOmitRecovery(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getBytes(), ST_EXAMPLE_BYTES_OMIT_RECOVERY);
        checkDisplayValues(airbaseLocations);
        checkTakeoffLocationST(airbaseLocations);
        assertFalse(airbaseLocations.isRecoveryLocationUnknown());
        // In this case, the takeoff and recovery locations really are meant to be the same
        assertEquals(airbaseLocations.getRecoveryLocation().getLatitude(), TAKEOFF_LAT, 0.00001);
        assertEquals(airbaseLocations.getRecoveryLocation().getLongitude(), TAKEOFF_LON, 0.00001);
        assertEquals(airbaseLocations.getRecoveryLocation().getHAE(), TAKEOFF_HAE, 0.00001);
    }

    protected void checkDisplayValues(AirbaseLocations airbaseLocations) {
        assertEquals(airbaseLocations.getDisplayableValue(), "[Airbase Locations]");
        assertEquals(airbaseLocations.getDisplayName(), "Airbase Locations");
    }

    protected void checkTakeoffLocationST(AirbaseLocations airbaseLocations) {
        checkTakeoffLocationLatLon(airbaseLocations);
        assertEquals(airbaseLocations.getTakeoffLocation().getHAE(), TAKEOFF_HAE, 0.00001);
    }

    protected void checkRecoveryLocationST(AirbaseLocations airbaseLocations) {
        checkRecoveryLocationLatLon(airbaseLocations);
        assertEquals(airbaseLocations.getRecoveryLocation().getHAE(), RECOVERY_HAE, 0.00001);
    }

    protected void checkRecoveryLocationLatLon(AirbaseLocations airbaseLocations) {
        assertFalse(airbaseLocations.isRecoveryLocationUnknown());
        assertEquals(airbaseLocations.getRecoveryLocation().getLatitude(), RECOVERY_LAT, 0.00001);
        assertEquals(airbaseLocations.getRecoveryLocation().getLongitude(), RECOVERY_LON, 0.00001);
    }

    protected void checkTakeoffLocationLatLon(AirbaseLocations airbaseLocations) {
        assertFalse(airbaseLocations.isTakeoffLocationUnknown());
        assertEquals(airbaseLocations.getTakeoffLocation().getLatitude(), TAKEOFF_LAT, 0.00001);
        assertEquals(airbaseLocations.getTakeoffLocation().getLongitude(), TAKEOFF_LON, 0.00001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthTakeoff() {
        byte[] takeoffByteArrayLength1 = new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00};
        new AirbaseLocations(takeoffByteArrayLength1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthRecovery() {
        byte[] recoveryByteArrayLength1 =
                new byte[] {
                    (byte) 0x0B,
                    (byte) 0x40,
                    (byte) 0x6B,
                    (byte) 0xC2,
                    (byte) 0x09,
                    (byte) 0x19,
                    (byte) 0xBD,
                    (byte) 0xA5,
                    (byte) 0x54,
                    (byte) 0x07,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00
                };
        new AirbaseLocations(recoveryByteArrayLength1);
    }
}
