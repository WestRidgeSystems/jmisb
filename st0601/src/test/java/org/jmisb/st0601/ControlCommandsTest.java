package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvValue;
import org.testng.annotations.Test;

public class ControlCommandsTest {
    private final byte[] ST_EXAMPLE_BYTES =
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

    private final byte[] ST_EXAMPLE_BYTES_IN_601 =
            new byte[] {
                0x06,
                0x0e,
                0x2b,
                0x34,
                0x02,
                0x0b,
                0x01,
                0x01,
                0x0e,
                0x01,
                0x03,
                0x01,
                0x01,
                0x00,
                0x00,
                0x00,
                0x19,
                0x73,
                0x13,
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
                0x01,
                0x02,
                (byte) 0x4f,
                (byte) 0xfc
            };

    private final byte[] TWO_COMMANDS_BYTES_IN_601 =
            new byte[] {
                0x06,
                0x0e,
                0x2b,
                0x34,
                0x02,
                0x0b,
                0x01,
                0x01,
                0x0e,
                0x01,
                0x03,
                0x01,
                0x01,
                0x00,
                0x00,
                0x00,
                0x2E,
                0x73,
                0x13,
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
                0x73,
                0x13,
                (byte) 0x04,
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
                (byte) 0x33,
                0x01,
                0x02,
                (byte) 0x13,
                (byte) 0xe7
            };

    @Test
    public void testConstructFromValue() {
        ControlCommands controlCommands =
                new ControlCommands(
                        new ControlCommand(
                                ControlCommandTest.ST_EXAMPLE_ID,
                                ControlCommandTest.ST_EXAMPLE_COMMAND));
        validateControlCommands(controlCommands);
    }

    private void validateControlCommands(ControlCommands controlCommands) {
        assertNotNull(controlCommands);
        assertEquals(controlCommands.getDisplayName(), "Control Commands");
        assertEquals(controlCommands.getDisplayableValue(), "[Control Commands]");
        assertEquals(controlCommands.getIdentifiers().size(), 1);
        controlCommands
                .getIdentifiers()
                .forEach(
                        identifier -> {
                            assertTrue(identifier instanceof ControlCommandsKey);
                            IKlvValue value = controlCommands.getField(identifier);
                            assertTrue(value instanceof ControlCommand);
                            ControlCommand controlCommand = (ControlCommand) value;
                            ControlCommandTest.checkValuesForExample(controlCommand);
                        });
        SortedMap<UasDatalinkTag, IUasDatalinkValue> messageValues = new TreeMap<>();
        messageValues.put(UasDatalinkTag.ControlCommand, controlCommands);
        UasDatalinkMessage msg = new UasDatalinkMessage(messageValues);
        assertEquals(msg.frameMessage(false), ST_EXAMPLE_BYTES_IN_601);
    }

    @Test
    public void testFactory() throws KlvParseException {
        UasDatalinkMessage msg = new UasDatalinkMessage(ST_EXAMPLE_BYTES_IN_601);
        assertTrue(msg.getIdentifiers().contains(UasDatalinkTag.ControlCommand));
        IUasDatalinkValue v = msg.getField(UasDatalinkTag.ControlCommand);
        assertTrue(v instanceof ControlCommands);
        ControlCommands controlCommands = (ControlCommands) v;
        validateControlCommands(controlCommands);
        ControlCommandsKey noSuchKey = new ControlCommandsKey(0);
        assertNull(controlCommands.getField(noSuchKey));
    }

    @Test
    public void testFactoryTwoMessages() throws KlvParseException {
        UasDatalinkMessage msg = new UasDatalinkMessage(TWO_COMMANDS_BYTES_IN_601);
        assertTrue(msg.getIdentifiers().contains(UasDatalinkTag.ControlCommand));
        IUasDatalinkValue v = msg.getField(UasDatalinkTag.ControlCommand);
        assertTrue(v instanceof ControlCommands);
        ControlCommands controlCommands = (ControlCommands) v;
        validateTwoControlCommands(controlCommands);
    }

    private void validateTwoControlCommands(ControlCommands controlCommands) {
        assertNotNull(controlCommands);
        assertEquals(controlCommands.getDisplayName(), "Control Commands");
        assertEquals(controlCommands.getDisplayableValue(), "[Control Commands]");
        assertEquals(controlCommands.getIdentifiers().size(), 2);
        assertTrue(controlCommands.getIdentifiers().contains(new ControlCommandsKey(5)));
        ControlCommand controlCommand5 =
                (ControlCommand) controlCommands.getField(new ControlCommandsKey(5));
        ControlCommandTest.checkValuesForExample(controlCommand5);
        assertTrue(controlCommands.getIdentifiers().contains(new ControlCommandsKey(4)));
        ControlCommand controlCommand4 =
                (ControlCommand) controlCommands.getField(new ControlCommandsKey(4));
        assertEquals(controlCommand4.getCommandId(), 4);
        assertEquals(controlCommand4.getCommand(), "Fly to Waypoint 3");
        SortedMap<UasDatalinkTag, IUasDatalinkValue> messageValues = new TreeMap<>();
        messageValues.put(UasDatalinkTag.ControlCommand, controlCommands);
        UasDatalinkMessage msg = new UasDatalinkMessage(messageValues);
        assertEquals(msg.frameMessage(false), TWO_COMMANDS_BYTES_IN_601);
    }

    @Test
    public void fromValues() {
        List<ControlCommand> commands = new ArrayList<>();
        ControlCommand controlCommand = new ControlCommand(5, "Fly to Waypoint 1");
        commands.add(controlCommand);
        ControlCommand secondControlCommand = new ControlCommand(4, "Fly to Waypoint 3");
        commands.add(secondControlCommand);
        ControlCommands controlCommands = new ControlCommands(commands);
        validateTwoControlCommands(controlCommands);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testBadGetBytes() throws KlvParseException {
        UasDatalinkMessage msg = new UasDatalinkMessage(ST_EXAMPLE_BYTES_IN_601);
        List<ControlCommand> commands = new ArrayList<>();
        ControlCommand controlCommand = new ControlCommand(5, "Fly to Waypoint 1");
        commands.add(controlCommand);
        ControlCommands controlCommands = new ControlCommands(commands);
        controlCommands.getBytes();
    }
}
