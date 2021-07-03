package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class ControlCommandTest {
    private static final byte[] ST_EXAMPLE_BYTES =
            new byte[] {
                (byte) 0x05,
                (byte) 0x11,
                (byte) 0x46,
                (byte) 0x6C,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x74,
                (byte) 0x6F,
                (byte) 0x20,
                (byte) 0x57,
                (byte) 0x61,
                (byte) 0x79,
                (byte) 0x70,
                (byte) 0x6F,
                (byte) 0x69,
                (byte) 0x6E,
                (byte) 0x74,
                (byte) 0x20,
                (byte) 0x31
            };
    public static final int ST_EXAMPLE_ID = 5;
    public static final String ST_EXAMPLE_COMMAND = "Fly to Waypoint 1";
    private final long EXAMPLE_TIMESTAMP = 1587194489117472L;

    private final byte[] ST_EXAMPLE_BYTES_PLUS_TIMESTAMP =
            new byte[] {
                (byte) 0x05,
                (byte) 0x11,
                (byte) 0x46,
                (byte) 0x6C,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x74,
                (byte) 0x6F,
                (byte) 0x20,
                (byte) 0x57,
                (byte) 0x61,
                (byte) 0x79,
                (byte) 0x70,
                (byte) 0x6F,
                (byte) 0x69,
                (byte) 0x6E,
                (byte) 0x74,
                (byte) 0x20,
                (byte) 0x31,
                (byte) 0x00,
                (byte) 0x05,
                (byte) 0xA3,
                (byte) 0x8B,
                (byte) 0x83,
                (byte) 0xB6,
                (byte) 0x9B,
                (byte) 0x20
            };

    @Test
    public void testConstructFromValue() {
        // From ST:
        ControlCommand controlCommand = new ControlCommand(ST_EXAMPLE_ID, ST_EXAMPLE_COMMAND);
        checkValuesForExample(controlCommand);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        ControlCommand controlCommand = new ControlCommand(ST_EXAMPLE_BYTES);
        checkValuesForExample(controlCommand);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ControlCommand, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof ControlCommand);
        ControlCommand controlCommand = (ControlCommand) v;
        checkValuesForExample(controlCommand);
    }

    @Test
    public void testFactoryWithPTS() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.ControlCommand, ST_EXAMPLE_BYTES_PLUS_TIMESTAMP);
        assertTrue(v instanceof ControlCommand);
        ControlCommand controlCommand = (ControlCommand) v;
        checkValuesForExamplePlusPTS(controlCommand);
    }

    @Test
    public void testConstructFromValueWithPTS() {
        // From ST:
        ControlCommand controlCommand =
                new ControlCommand(ST_EXAMPLE_ID, ST_EXAMPLE_COMMAND, EXAMPLE_TIMESTAMP);
        checkValuesForExamplePlusPTS(controlCommand);
    }

    public static void checkValuesForExample(ControlCommand controlCommand) {
        assertEquals(controlCommand.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(controlCommand.getCommandId(), ST_EXAMPLE_ID);
        assertEquals(controlCommand.getCommand(), ST_EXAMPLE_COMMAND);
        assertFalse(controlCommand.timestampIsValid());
        assertEquals(controlCommand.getDisplayableValue(), "5, Fly to Waypoint 1");
        assertEquals(controlCommand.getDisplayName(), "Control Command");
    }

    private void checkValuesForExamplePlusPTS(ControlCommand controlCommand) {
        assertEquals(controlCommand.getBytes(), ST_EXAMPLE_BYTES_PLUS_TIMESTAMP);
        assertEquals(controlCommand.getCommandId(), ST_EXAMPLE_ID);
        assertEquals(controlCommand.getCommand(), ST_EXAMPLE_COMMAND);
        assertTrue(controlCommand.timestampIsValid());
        assertEquals(controlCommand.getTimestamp(), EXAMPLE_TIMESTAMP);
        assertEquals(controlCommand.getDisplayableValue(), "5, Fly to Waypoint 1");
        assertEquals(controlCommand.getDisplayName(), "Control Command");
    }

    public void testLengthStringOK() {
        String command127 =
                "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "012345678901234567890123456";

        ControlCommand controlCommand = new ControlCommand(1, command127);
        assertNotNull(controlCommand);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testLengthString128() {
        String command128 =
                "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "0123456789012345678901234567";

        new ControlCommand(1, command128);
    }

    public void testLengthStringOKpts() {
        String command127 =
                "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "012345678901234567890123456";

        ControlCommand controlCommand = new ControlCommand(1, command127, 2);
        assertNotNull(controlCommand);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testLengthString128pts() {
        String command128 =
                "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "0123456789012345678901234567";

        new ControlCommand(1, command128, 2);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        new ControlCommand(
                new byte[] {
                    (byte) 0xde,
                    0x35,
                    0x5c,
                    0x51,
                    (byte) 0xb7,
                    0x33,
                    (byte) 0x97,
                    (byte) 0xe3,
                    (byte) 0xf5,
                    (byte) 0xb1,
                    0x40,
                    0x26,
                    (byte) 0x97,
                    0x26,
                    (byte) 0xa7,
                    0x59,
                    (byte) 0xfe,
                    (byte) 0xbf,
                    (byte) 0xdf,
                    0x1e,
                    (byte) 0xf3,
                    (byte) 0x9d
                });
    }
}
