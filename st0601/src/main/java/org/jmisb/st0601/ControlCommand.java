package org.jmisb.st0601;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Control Command (ST 0601 Item 115).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Record of command from GCS to Aircraft.
 *
 * <p>A copy of the command and control values used to request platform/sensor to perform an action.
 *
 * <p>Item 116 uses the Command ID to signal validation.
 *
 * <p>Command is a "string" format defined by platform vendor.
 *
 * <p>Control Command Verification (Item 116) shows acknowledgment of the command
 *
 * <p>The purpose of the Control Command (Item 115) and Command Acknowledgement (Item 116) items are
 * to report the commands issued to the platform/sensor and the acknowledgment of those commands.
 * The Control Command defines a command ID and the command string which describes the command or
 * action to perform. At some later time, the command is acknowledged by the platform and Item 116
 * records the acknowledgment, by just restating the Command ID.
 *
 * </blockquote>
 */
public class ControlCommand implements IUasDatalinkValue {
    private final int id;
    private final String commandText;
    private long timestamp;
    private boolean timestampIsValid = false;

    /**
     * Create from values.
     *
     * @param commandId the value to track the command. This is an increasing and unique number
     *     assigned to each command as it is issued.
     * @param command utf8 value which describes the command. This string has a maximum length of
     *     127 characters. The format and content of the string is vendor defined.
     * @param pts the Precision Time Stamp when first issuing the command to the platform.
     */
    public ControlCommand(int commandId, String command, long pts) {
        if (command.length() > 127) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " command is 127 characters maximum");
        }
        this.id = commandId;
        this.commandText = command;
        this.timestamp = pts;
        this.timestampIsValid = true;
    }

    /**
     * Create from values.
     *
     * <p>This version of the constructor omits the PTS, which defaults to the timestamp of the
     * parent packet.
     *
     * @param commandId the value to track the command. This is an increasing and unique number
     *     assigned to each command as it is issued.
     * @param command utf8 value which describes the command. This string has a maximum length of
     *     127 characters. The format and content of the string is vendor defined.
     */
    public ControlCommand(int commandId, String command) {
        if (command.length() > 127) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " command is 127 characters maximum");
        }
        this.id = commandId;
        this.commandText = command;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes encoded value
     * @throws KlvParseException if there is a problem parsing the encoded data
     */
    public ControlCommand(byte[] bytes) throws KlvParseException {
        int idx = 0;
        BerField idField = BerDecoder.decode(bytes, idx, true);
        idx += idField.getLength();
        id = idField.getValue();
        BerField commandLengthField = BerDecoder.decode(bytes, idx, false);
        idx += commandLengthField.getLength();
        int stringLength = commandLengthField.getValue();
        if (idx + stringLength > bytes.length) {
            throw new KlvParseException("Insufficient bytes available for specified string length");
        }
        commandText = new String(bytes, idx, stringLength, StandardCharsets.UTF_8);
        idx += commandLengthField.getValue();
        if (bytes.length > idx) {
            timestamp = PrimitiveConverter.toInt64(bytes, idx);
            timestampIsValid = true;
        }
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.appendAsOID(id);
        byte[] commandBytes = commandText.getBytes(StandardCharsets.UTF_8);
        builder.appendAsBerLength(commandBytes.length);
        builder.append(commandBytes);
        if (timestampIsValid) {
            byte[] timestampBytes = PrimitiveConverter.int64ToBytes(timestamp);
            builder.append(timestampBytes);
        }
        return builder.toBytes();
    }

    /**
     * Get the command id value.
     *
     * @return the id as an integer.
     */
    public int getCommandId() {
        return id;
    }

    /**
     * Get the command text.
     *
     * @return the command (vendor specific) text.
     */
    public String getCommand() {
        return commandText;
    }

    /**
     * Get the timestamp associated with this command.
     *
     * <p>This can be invalid - check the timestampIsValid() to tell.
     *
     * @return timestamp, or 0 if not valid.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Check if the timestamp is a valid value.
     *
     * @return true if it is valid, otherwise false.
     */
    public boolean timestampIsValid() {
        return timestampIsValid;
    }

    @Override
    public String getDisplayableValue() {
        return "" + id + ", " + commandText;
    }

    @Override
    public final String getDisplayName() {
        return "Control Command";
    }
}
