package org.jmisb.st0601;

import java.util.*;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UAS Datalink Local Set message packet as defined by ST 0601.
 *
 * <p>For guidance on which items are mandatory to include in ST 0601 messages, refer to ST 0902 for
 * a list of the minimum set of UAS Datalink LS metadata items.
 */
public class UasDatalinkMessage implements IMisbMessage {

    /** Universal label for UAS Datalink Local Metadata Set. */
    public static final UniversalLabel UasDatalinkLocalUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x01,
                        0x01, 0x00, 0x00, 0x00
                    });

    private static final Logger logger = LoggerFactory.getLogger(UasDatalinkMessage.class);

    // TODO: should we make this class immutable? May have benefits for stability in multi-threaded
    // environments.
    /** Map containing all data elements in the message (except, normally, the checksum). */
    private SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the message
     */
    public UasDatalinkMessage(SortedMap<UasDatalinkTag, IUasDatalinkValue> values) {
        this.map = values;
    }

    /**
     * Create the message by parsing the given byte array.
     *
     * @param bytes Byte array containing a UAS Datalink message
     * @throws KlvParseException if a parsing error occurs, or checksum is missing/invalid
     */
    public UasDatalinkMessage(byte[] bytes) throws KlvParseException {
        // Parse the length field
        BerField lengthField = BerDecoder.decode(bytes, UniversalLabel.LENGTH, false);
        int lengthLength = lengthField.getLength();
        int valueLength = lengthField.getValue();

        // Parse fields out of the array
        List<LdsField> fields =
                LdsParser.parseFields(bytes, UniversalLabel.LENGTH + lengthLength, valueLength);

        boolean checksumFound = false;
        for (LdsField field : fields) {
            UasDatalinkTag tag = UasDatalinkTag.getTag(field.getTag());
            if (tag == UasDatalinkTag.Undefined) {
                logger.info("Unknown UAS Datalink tag: " + field.getTag());
            } else if (tag == UasDatalinkTag.ControlCommand) {
                processControlCommand(tag, field.getData());
            } else if (tag == UasDatalinkTag.Checksum) {
                checksumFound = true;
                byte[] expected = Checksum.compute(bytes, false);
                byte[] actual = Arrays.copyOfRange(bytes, bytes.length - 2, bytes.length);
                if (!Arrays.equals(expected, actual)) {
                    InvalidDataHandler.getInstance().handleInvalidChecksum(logger, "Bad checksum");
                }
            } else {
                try {
                    IUasDatalinkValue value = UasDatalinkFactory.createValue(tag, field.getData());
                    setField(tag, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(logger, ex.getMessage());
                }
            }
        }

        // Handle the case where the mandatory checksum is missing
        if (!checksumFound) {
            InvalidDataHandler.getInstance().handleMissingChecksum(logger, "Missing checksum");
        }
    }

    private void processControlCommand(UasDatalinkTag tag, byte[] fieldBytes)
            throws KlvParseException {
        ControlCommand controlCommand =
                (ControlCommand) UasDatalinkFactory.createValue(tag, fieldBytes);
        if (map.containsKey(UasDatalinkTag.ControlCommand)) {
            ControlCommands controlCommands =
                    (ControlCommands) map.get(UasDatalinkTag.ControlCommand);
            controlCommands.add(controlCommand);
        } else {
            setField(UasDatalinkTag.ControlCommand, new ControlCommands(controlCommand));
        }
    }

    private void setField(UasDatalinkTag tag, IUasDatalinkValue value) {
        map.put(tag, value);
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IUasDatalinkValue getField(UasDatalinkTag tag) {
        return map.get(tag);
    }

    @Override
    public IUasDatalinkValue getField(IKlvKey tag) {
        return this.getField((UasDatalinkTag) tag);
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Collection<UasDatalinkTag> getTags() {
        return this.getIdentifiers();
    }

    @Override
    public Set<UasDatalinkTag> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return UasDatalinkLocalUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (Map.Entry<UasDatalinkTag, IUasDatalinkValue> entry : map.entrySet()) {
            UasDatalinkTag tag = entry.getKey();

            // Ignore checksum if present in the map (should not be), it will be calculated and
            // appended at the very end
            if (tag == UasDatalinkTag.Checksum) {
                continue;
            }

            IUasDatalinkValue value = entry.getValue();
            if (value instanceof ISpecialFraming) {
                ISpecialFraming specialFramingEntry = (ISpecialFraming) value;
                arrayBuilder.append(specialFramingEntry.getEncodedValue());
            } else {
                byte[] bytes = value.getBytes();
                if (bytes != null && bytes.length > 0) {
                    // Add key, length, value to chunks
                    arrayBuilder.appendAsOID(tag.getCode());
                    arrayBuilder.appendAsBerLength(bytes.length);
                    arrayBuilder.append(bytes.clone());
                }
            }
        }
        // Add Key and Length of checksum with placeholder for value - Checksum must be final
        // element
        byte[] checksum = new byte[2];
        arrayBuilder.appendByte((byte) UasDatalinkTag.Checksum.getCode());
        arrayBuilder.appendAsBerLength(checksum.length);
        arrayBuilder.append(checksum);
        if (isNested) {
            return arrayBuilder.toBytes();
        } else {
            arrayBuilder.prependLength();
            arrayBuilder.prepend(UasDatalinkLocalUl);
            ;
            byte[] array = arrayBuilder.toBytes();
            // Compute the checksum and replace the last two bytes of array
            Checksum.compute(array, true);
            return array;
        }
    }

    @Override
    public String displayHeader() {
        return "ST 0601";
    }
}
