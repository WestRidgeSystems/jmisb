package org.jmisb.api.klv.st0601;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0601.dto.Wavelengths;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Wavelengths List (Item 128).
 *
 * <p>From ST0601:
 *
 * <blockquote>
 *
 * List of wavelength bands provided by sensor(s).
 *
 * <p>The Wavelengths List is a list of information used by the on-board sensors which collect
 * Motion Imagery. This item is a companion to Active Wavelength List (Item 121).
 *
 * <p>Table 14 shows predefined sensor records which support a set of common wavelengths used by
 * sensors. The Active Wavelength List (Item 121) can use these predefined wavelength bands if they
 * are sufficient for the given platform’s sensors. If a platform/sensor requires more specific or
 * customized wavelength records, this item enables their definition. Any custom Wavelengths List
 * records are sent at a minimum of once every 30 seconds. If the predefined wavelengths are
 * sufficient for the platforms sensors there is no need to send a Wavelengths List item.
 *
 * <table border="1">
 * <caption>Table 14: Predefined Wavelength Information Records</caption>
 * <tr><th>ID</th><th>Min (nm)</th><th>Max (nm)</th><th>Name</th><th>Description</th></tr>
 * <tr><td>1</td><td>380</td><td>750</td><td>VIS</td><td>Visible light</td></tr>
 * <tr><td>2</td><td>750</td><td>100,000</td><td>IR</td><td>Infrared</td></tr>
 * <tr><td>3</td><td>750</td><td>3000</td><td>NIR</td><td>Near/Short Wave Infrared</td></tr>
 * <tr><td>4</td><td>3000</td><td>8000</td><td>MIR</td><td>Mid-wave Infrared</td></tr>
 * <tr><td>5</td><td>8000</td><td>14000</td><td>LIR</td><td>Long-wave Infrared</td></tr>
 * <tr><td>6</td><td>14000</td><td>100,000</td><td>FIR</td><td>Far-Infrared</td></tr>
 * </table>
 *
 * <p>A sensor wavelength record contains a numeric identifier (ID), min/max wavelengths, and a
 * unique name for display on remote terminals, etc. The ID is a unique number for the wavelength
 * record. Custom wavelength records begin at ID 21 and increment as needed. A custom wavelength
 * record persists only for a given flight. The “Min” and “Max” wavelengths define the range of the
 * band. The “Name” is a unique string describing the band. The sensor wavelength record does not
 * include the “Description,” it is only in the table for informational purposes.
 *
 * <p>See the Motion Imagery Handbook Section 3.1 for information on these wavelengths and
 * descriptions.
 *
 * </blockquote>
 */
public class WavelengthsList implements IUasDatalinkValue {

    private final List<Wavelengths> wavelengthsList = new ArrayList<>();
    private static final double MIN_VAL = 0.0;
    private static final double MAX_VAL = 1e9;
    private static final int IMAPB_BYTES = 4;
    private static final FpEncoder decoder =
            new FpEncoder(MIN_VAL, MAX_VAL, IMAPB_BYTES, OutOfRangeBehaviour.Default);

    /**
     * Create from value.
     *
     * @param wavelengths the list of wavelengths objects.
     */
    public WavelengthsList(List<Wavelengths> wavelengths) {
        this.wavelengthsList.addAll(wavelengths);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array containing the variable length pack.
     * @throws KlvParseException if there is a problem during parsing
     */
    public WavelengthsList(byte[] bytes) throws KlvParseException {
        int offset = 0;
        while (offset < bytes.length) {
            Wavelengths wavelengths = new Wavelengths();
            BerField packLengthField = BerDecoder.decode(bytes, offset, false);
            offset += packLengthField.getLength();
            int packLength = packLengthField.getValue();
            BerField idField = BerDecoder.decode(bytes, offset, true);
            offset += idField.getLength();
            wavelengths.setId(idField.getValue());
            double min = decoder.decode(bytes, offset);
            offset += IMAPB_BYTES;
            wavelengths.setMin(min);
            double max = decoder.decode(bytes, offset);
            offset += IMAPB_BYTES;
            wavelengths.setMax(max);
            int nameLength = packLength - (2 * IMAPB_BYTES + idField.getLength());
            if (nameLength < 0) {
                throw new KlvParseException("Wavelengths Name length cannot be negative");
            }
            if ((offset + nameLength) > bytes.length) {
                throw new KlvParseException(
                        "Insufficient bytes available for specified string length");
            }
            String name = new String(bytes, offset, nameLength, StandardCharsets.UTF_8);
            wavelengths.setName(name);
            offset += nameLength;
            wavelengthsList.add(wavelengths);
        }
    }

    /**
     * Get the list of wavelengths.
     *
     * <p>This gets the live list, so it can also be used to add an entry, or to clear the list.
     *
     * @return the known wavelengths, as a list.
     */
    public List<Wavelengths> getWavelengthsList() {
        return wavelengthsList;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (Wavelengths wavelengths : wavelengthsList) {
            int packLength = 0;
            byte[] idBytes = BerEncoder.encode(wavelengths.getId());
            packLength += idBytes.length;
            byte[] minBytes = decoder.encode(wavelengths.getMin());
            packLength += minBytes.length;
            byte[] maxBytes = decoder.encode(wavelengths.getMax());
            packLength += maxBytes.length;
            byte[] nameBytes = wavelengths.getName().getBytes(StandardCharsets.UTF_8);
            packLength += nameBytes.length;
            byte[] packLengthBytes = BerEncoder.encode(packLength);
            baos.write(packLengthBytes, 0, packLengthBytes.length);
            baos.write(idBytes, 0, idBytes.length);
            baos.write(minBytes, 0, minBytes.length);
            baos.write(maxBytes, 0, maxBytes.length);
            baos.write(nameBytes, 0, nameBytes.length);
        }
        return baos.toByteArray();
    }

    @Override
    public String getDisplayableValue() {
        return "[Wavelengths]";
    }

    @Override
    public String getDisplayName() {
        return "Wavelengths List";
    }
}
