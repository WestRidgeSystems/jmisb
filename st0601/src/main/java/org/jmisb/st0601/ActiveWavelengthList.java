package org.jmisb.st0601;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Active Wavelength List (Item 121).
 *
 * <p>From ST0601:
 *
 * <blockquote>
 *
 * List of wavelengths in Motion Imagery.
 *
 * <p>Used with Wavelengths List (Item 128).
 *
 * <p>The Active Wavelength List provides a list of wavelengths used by the sensor to generate the
 * Motion Imagery. This value updates when the sensor changes and the new sensor has a different
 * wavelength than the last sensor used. For example, the sensor changes from a visible light to an
 * infrared sensor. Multiple wavelengths identifiers support multi-band sensors or sensors which
 * fuse multiple wavelength bands.
 *
 * </blockquote>
 */
public class ActiveWavelengthList implements IUasDatalinkValue {
    private final List<Integer> wavelengthIdentifiers = new ArrayList<>();

    /**
     * Create from value.
     *
     * <p>This valid identifiers should be from the defined set (1-7), or 21 and higher as defined
     * by Item 128.
     *
     * @param wavelengthIdentifiers the list of identifiers.
     */
    public ActiveWavelengthList(List<Integer> wavelengthIdentifiers) {
        this.wavelengthIdentifiers.addAll(wavelengthIdentifiers);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array containing the defined length pack.
     */
    public ActiveWavelengthList(byte[] bytes) {
        int offset = 0;
        while (offset < bytes.length) {
            BerField wavelengthIdentifierField = BerDecoder.decode(bytes, offset, true);
            offset += wavelengthIdentifierField.getLength();
            int wavelengthIdentifier = wavelengthIdentifierField.getValue();
            wavelengthIdentifiers.add(wavelengthIdentifier);
        }
    }

    /**
     * Get the list of wavelength identifiers.
     *
     * <p>This gets the live list, so it can also be used to add an entry, or to clear the list.
     *
     * @return the wavelength identifiers, as a list.
     */
    public List<Integer> getWavelengthIdentifiers() {
        return wavelengthIdentifiers;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int wavelengthIdentifier : wavelengthIdentifiers) {
            byte[] identifierBytes = BerEncoder.encode(wavelengthIdentifier);
            baos.write(identifierBytes, 0, identifierBytes.length);
        }
        return baos.toByteArray();
    }

    @Override
    public String getDisplayableValue() {
        List<String> identifiersAsStrings = new ArrayList<>();
        wavelengthIdentifiers.forEach(
                (wavelengthIdentifier) ->
                        identifiersAsStrings.add(wavelengthIdentifier.toString()));
        return String.join(",", identifiersAsStrings);
    }

    @Override
    public String getDisplayName() {
        return "Active Wavelength List";
    }
}
