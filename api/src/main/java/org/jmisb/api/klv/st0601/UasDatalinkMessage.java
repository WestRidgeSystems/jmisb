package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;

import java.util.*;

import static org.jmisb.api.klv.KlvConstants.UasDatalinkLocalUl;
import static org.jmisb.core.klv.ArrayUtils.arrayFromChunks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UAS Datalink Local Set message packet as defined by ST 0601.
 * <p>
 * For guidance on which items are mandatory to include in ST 0601 messages, refer to ST 0902 for a list of the
 * minimum set of UAS Datalink LS metadata items.
 */
public class UasDatalinkMessage implements IMisbMessage
{
    private static Logger logger = LoggerFactory.getLogger(UasDatalinkMessage.class);

    // TODO: should we make this class immutable? May have benefits for stability in multi-threaded environments.

    /** Map containing all data elements in the message (except, normally, the checksum) */
    private SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the message
     */
    public UasDatalinkMessage(SortedMap<UasDatalinkTag, IUasDatalinkValue> values)
    {
        this.map = values;
    }

    /**
     * Create the message by parsing the given byte array.
     *
     * This will use the default parsing options (basically, no flags set).
     *
     * @param bytes Byte array containing a UAS Datalink message
     *
     * @throws KlvParseException if a parsing error occurs, or checksum is missing/invalid
     */
    private UasDatalinkMessage(byte[] bytes) throws org.jmisb.api.common.KlvParseException
    {
        this(bytes, EnumSet.noneOf(ParseOptions.class));
    }

    /**
     * Create the message by parsing the given byte array.
     *
     * @param bytes Byte array containing a UAS Datalink message
     * @param parseOptions any special parse options
     *
     * @throws KlvParseException if a parsing error occurs, or checksum is missing/invalid (depending on parse options)
     */
    public UasDatalinkMessage(byte[] bytes, EnumSet<ParseOptions> parseOptions) throws KlvParseException
    {
        // Parse the length field
        BerField lengthField = BerDecoder.decode(bytes, UniversalLabel.LENGTH, false);
        int lengthLength = lengthField.getLength();
        int valueLength = lengthField.getValue();

        // Parse fields out of the array
        List<LdsField> fields = LdsParser.parseFields(bytes, UniversalLabel.LENGTH + lengthLength, valueLength, parseOptions);

        boolean checksumFound = false;
        for (LdsField field : fields)
        {
            UasDatalinkTag tag = UasDatalinkTag.getTag(field.getTag());
            if (tag == UasDatalinkTag.Undefined)
            {
                logger.info("Unknown UAS Datalink tag: " + field.getTag());
            }
            else if (tag == UasDatalinkTag.Checksum)
            {
                checksumFound = true;
                byte[] expected = Checksum.compute(bytes, false);
                byte[] actual = Arrays.copyOfRange(bytes, bytes.length-2, bytes.length);
                if (!Arrays.equals(expected, actual))
                {
                    if (parseOptions.contains(ParseOptions.LOG_ON_CHECKSUM_FAIL)) 
                    {
                        logger.warn("Bad checksum");
                    }
                    else
                    {
                        throw new KlvParseException("Bad checksum");
                    }
                }
            }
            else
            {
                try
                {
                    IUasDatalinkValue value = UasDatalinkFactory.createValue(tag, field.getData(), parseOptions);
                    setField(tag, value);
                }
                catch (KlvParseException | IllegalArgumentException ex)
                {
                    if (parseOptions.contains(ParseOptions.LOG_ON_INVALID_FIELD_ENCODING)) 
                    {
                        logger.warn(ex.getMessage());
                    }
                    else
                    {
                        throw ex;
                    }
                }
            }
        }

        // Throw if checksum is missing
        if (!checksumFound)
        {
            if (parseOptions.contains(ParseOptions.LOG_ON_CHECKSUM_MISSING)) 
            {
                logger.warn("Missing checksum");
            }
            else
            {
                throw new KlvParseException("Missing checksum");
            }
        }
    }

    private void setField(UasDatalinkTag tag, IUasDatalinkValue value)
    {
        map.put(tag, value);
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IUasDatalinkValue getField(UasDatalinkTag tag)
    {
        return map.get(tag);
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Collection<UasDatalinkTag> getTags()
    {
        return map.keySet();
    }

    @Override
    public UniversalLabel getUniversalLabel()
    {
        return UasDatalinkLocalUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested)
    {
        // List representing all tags and values as primitive byte arrays. Avoids boxing/unboxing
        // individual bytes for efficiency.
        List<byte[]> chunks = new ArrayList<>();

        // Note: we will insert key/length fields into chunks after all value fields have been added

        // Add all values from map
        for (Map.Entry<UasDatalinkTag, IUasDatalinkValue> entry : map.entrySet())
        {
            UasDatalinkTag tag = entry.getKey();

            // Ignore checksum if present in the map (should not be), it will be calculated and appended at the very end
            if (tag == UasDatalinkTag.Checksum)
            {
                continue;
            }

            IUasDatalinkValue value = entry.getValue();
            byte[] bytes = value.getBytes();
            if (bytes != null && bytes.length > 0)
            {
                // Add key, length, value to chunks
                chunks.add(BerEncoder.encode(tag.getCode(), Ber.OID));
                chunks.add(BerEncoder.encode(bytes.length));
                chunks.add(bytes.clone());
            }
        }

        // Add Key and Length of checksum with placeholder for value - Checksum must be final element
        byte[] checksum = new byte[2];
        chunks.add(new byte[]{(byte)UasDatalinkTag.Checksum.getCode()});
        chunks.add(BerEncoder.encode(checksum.length, Ber.SHORT_FORM));
        chunks.add(checksum);

        // Figure out value length
        final int keyLength = UniversalLabel.LENGTH;
        int valueLength = 0;
        for (byte[] chunk : chunks)
        {
            valueLength += chunk.length;
        }

        // Determine total length
        int totalLength;
        if (isNested)
        {
            // NOTE: nesting ST 0601 seems unlikely, but we'll support it anyway
            totalLength = valueLength;
        }
        else
        {
            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend key field (UL) into front of the list
            chunks.add(0, UasDatalinkLocalUl.getBytes());

            // Compute full message length
            totalLength = keyLength + lengthField.length + valueLength;
        }

        // Allocate array and write all chunks
        byte[] array = arrayFromChunks(chunks, totalLength);

        // Compute the checksum and replace the last two bytes of array
        Checksum.compute(array, true);

        return array;
    }

    @Override
    public String displayHeader() {
        return "ST 0601";
    }
}
