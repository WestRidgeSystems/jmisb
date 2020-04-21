package org.jmisb.api.klv.st0601;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0601.dto.Payload;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Payload List (Tag 138).
 * <p>
 * From ST0601:
 * <blockquote>
 * The Payload List provides type and name of all relevant payloads on the
 * platform. The Payload List may contain optical sensors and non-optical
 * payload packages such as SIGINT, LIDAR, or RADAR systems. Some of the items
 * in the Payload List will have further wavelength information provided in the
 * Wavelengths List when they become active. This list does not contain any
 * weapons, see Tag 140 for listing platform weapons. The Payload List is a
 * Floating Length Pack (FLP) which contains a Payload Record. A Payload Record
 * consists of four elements: Payload Identifier, Payload Type, Name Length and
 * Payload Name. The Payload Identifier is a unique BER-OID integer sequentially
 * assigned starting with the number zero (0). The Active Payload (Tag 139) uses
 * the Payload Identifier to specify which payloads are active. The Name Length
 * encodes the length of the Payload Name in BER short or long form. The Payload
 * Name is a descriptive name of the payload defined by the metadata encoder.
 * </blockquote>
 * Also:
 * <blockquote>
 * Sending all Payload Records in one UAS Datalink LS is unnecessary and could
 * contribute to bandwidth compromises. Sending Payload Records using multiple
 * UAS Datalink LS’s distributes the metadata and reduces these issues. In each
 * Payload List, the Payload Count is constant and contains the total number of
 * payloads on-board the platform.
 * </blockquote>
 */
// TODO: this is a candidate for nested metadata
public class PayloadList implements IUasDatalinkValue
{
    private final List<Payload> payloadList = new ArrayList<>();

    /**
     * Create from value.
     * @param payloads the list of Payload objects.
     */
    public PayloadList(List<Payload> payloads)
    {
        this.payloadList.addAll(payloads);
    }

    /**
     * Create from encoded bytes.
     * @param bytes The byte array containing the variable length pack.
     */
    public PayloadList(byte[] bytes)
    {
        int offset = 0;
        BerField payloadCountField = BerDecoder.decode(bytes, offset, true);
        offset += payloadCountField.getLength();
        while (offset < bytes.length)
        {
            BerField payloadLengthField = BerDecoder.decode(bytes, offset, false);
            offset += payloadLengthField.getLength();
            BerField idField = BerDecoder.decode(bytes, offset, true);
            offset += idField.getLength();
            BerField typeField = BerDecoder.decode(bytes, offset, true);
            offset += typeField.getLength();
            BerField nameLengthField = BerDecoder.decode(bytes, offset, false);
            offset += nameLengthField.getLength();
            String name = new String(bytes, offset, nameLengthField.getValue());
            Payload payload = new Payload(idField.getValue(), typeField.getValue(), name);
            offset += nameLengthField.getValue();
            payloadList.add(payload);
        }
    }

    /**
     * Get the list of payloads.
     * <p>
     * This gets the live list, so it can also be used to add an entry, or to clear the list.
     * <p>
     * @return the payloads, as a list.
     */
    public List<Payload> getPayloadList()
    {
        return payloadList;
    }

    @Override
    public byte[] getBytes()
    {
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        byte[] countBytes = BerEncoder.encode(payloadList.size(), Ber.OID);
        chunks.add(countBytes);
        totalLength += countBytes.length;
        for (Payload payload: getPayloadList())
        {
            int lengthForPayload = 0;
            byte[] idBytes = BerEncoder.encode(payload.getIdentifier(), Ber.OID);
            lengthForPayload += idBytes.length;
            byte[] typeBytes = BerEncoder.encode(payload.getType(), Ber.OID);
            lengthForPayload += typeBytes.length;
            byte[] nameBytes = payload.getName().getBytes(Charset.forName("UTF-8"));
            byte[] nameLengthBytes = BerEncoder.encode(nameBytes.length);
            lengthForPayload += nameLengthBytes.length;
            lengthForPayload += nameBytes.length;
            byte[] lengthForPayloadBytes = BerEncoder.encode(lengthForPayload, Ber.OID);
            chunks.add(lengthForPayloadBytes);
            chunks.add(idBytes);
            chunks.add(typeBytes);
            chunks.add(nameLengthBytes);
            chunks.add(nameBytes);
            totalLength += lengthForPayloadBytes.length;
            totalLength += lengthForPayload;
        }
        return ArrayUtils.arrayFromChunks(chunks, totalLength);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Payloads]";
    }

    @Override
    public String getDisplayName()
    {
        return "Payload List";
    }
}
