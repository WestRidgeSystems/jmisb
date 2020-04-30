package org.jmisb.api.klv.st0601;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.CountryCodingMethodUtilities;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Country Codes (ST 0601 tag 122).
 * <p>
 * From ST:
 * <blockquote>
 * Country codes which are associated with the platform and its operation.
 * <p>
 * The Country Codes item provides country related information about the
 * platform and its operation. The country which own and fly aircraft, along
 * with where the platform is flying, and the country observed in the Motion
 * Imagery scene are all needed information. For example, Country A is flying
 * Country B’s UAV over Country C while imaging Country D and Country E performs
 * analysis and classification of the Motion Imagery. There are five country
 * codes of interest: Operator Country, Manufacture Country, Overflight Country,
 * Object Country (Motion Imagery Scene) and Classifying Country. For the
 * example above:
 * <ul>
 * <li>Operator = Country A</li>
 * <li>Manufacture = Country B</li>
 * <li>Overflight Country = Country C</li>
 * <li>Object Country = Country D</li>
 * <li>Classifying country = Country E</li>
 * </ul>
 * <p>
 * The following lists the definitions for each of the different Country Code
 * types:
 * <ul>
 * <li>Overflight Country: The country the platform is operating or flying over.
 * This may be different than the country within the scene of the Motion
 * Imagery.</li>
 * <li>Operator Country: Country where the operator is located. For example, a
 * GCS operator.</li>
 * <li>Country of Manufacture: The Country where the platform was
 * manufactured.</li>
 * <li>Object Country: The country within the Motion Imagery scene or the
 * “Object” of the Motion Imagery. Note: This value is an item in MISB ST 0102
 * and is not included in this item’s country codes list.</li>
 * <li>Classifying Country: The country which initially analyzes or classified
 * the Motion Imagery. Note: this value is an item in MISB ST 0102 and is not
 * included in this item’s country codes list.</li>
 * </ul>
 * </blockquote>
 */
// TODO: candidate for nested metadata
public class CountryCodes implements IUasDatalinkValue
{
    private final CountryCodingMethod codingMethod;
    private final String overflightCountry;
    private final String operatorCountry;
    private final String countryOfManufacture;

    /**
     * Create from values
     *
     * @param countryCodingMethod the coding method (country code namespace)
     * @param overflightCountryCode the code for the country being overflown, or an empty string if unknown.
     * @param operatorCountryCode the code for the country where the operator is located, of an empty string if unknown.
     * @param countryOfManufactureCode the code for the country where the platform was manufactured, or an empty string if unknown.
     */
    public CountryCodes(final CountryCodingMethod countryCodingMethod, final String overflightCountryCode, final String operatorCountryCode, final String countryOfManufactureCode)
    {
        codingMethod = countryCodingMethod;
        overflightCountry = overflightCountryCode;
        operatorCountry = operatorCountryCode;
        countryOfManufacture = countryOfManufactureCode;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes encoded value
     */
    public CountryCodes(byte[] bytes)
    {
        int idx = 0;

        BerField codingMethodLengthField = BerDecoder.decode(bytes, idx, false);
        idx += codingMethodLengthField.getLength();
        codingMethod = CountryCodingMethodUtilities.getMethodForValue(bytes[idx]); // This assumes codingMethod is the next byte
        idx += codingMethodLengthField.getValue();

        BerField overflightCountryLengthField = BerDecoder.decode(bytes, idx, false);
        idx += overflightCountryLengthField.getLength();
        overflightCountry = new String(bytes, idx, overflightCountryLengthField.getValue(), StandardCharsets.UTF_8);
        idx += overflightCountryLengthField.getValue();

        if (idx == bytes.length)
        {
            operatorCountry = "";
            countryOfManufacture = "";
            return;
        }

        BerField operatorCountryLengthField = BerDecoder.decode(bytes, idx, false);
        idx += operatorCountryLengthField.getLength();
        operatorCountry = new String(bytes, idx, operatorCountryLengthField.getValue(), StandardCharsets.UTF_8);
        idx += operatorCountryLengthField.getValue();

        if (idx == bytes.length)
        {
            countryOfManufacture = "";
            return;
        }

        BerField countryOfManufactureLengthField = BerDecoder.decode(bytes, idx, false);
        idx += countryOfManufactureLengthField.getLength();
        countryOfManufacture = new String(bytes, idx, countryOfManufactureLengthField.getValue(), StandardCharsets.UTF_8);
        idx += countryOfManufactureLengthField.getValue();
    }

    @Override
    public byte[] getBytes()
    {
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        byte[] codingMethodBytes = new byte[]{CountryCodingMethodUtilities.getValueForCodingMethod(codingMethod)};
        byte[] codingMethodLengthBytes = BerEncoder.encode(codingMethodBytes.length);
        chunks.add(codingMethodLengthBytes);
        totalLength += codingMethodLengthBytes.length;
        chunks.add(codingMethodBytes);
        totalLength += codingMethodBytes.length;

        byte[] overflightCountryBytes = overflightCountry.getBytes(StandardCharsets.UTF_8);
        byte[] overflightCountryLengthBytes = BerEncoder.encode(overflightCountryBytes.length);
        chunks.add(overflightCountryLengthBytes);
        totalLength += overflightCountryLengthBytes.length;
        chunks.add(overflightCountryBytes);
        totalLength += overflightCountryBytes.length;

        byte[] operatorCountryBytes = operatorCountry.getBytes(StandardCharsets.UTF_8);
        byte[] countryOfManufactureBytes = countryOfManufacture.getBytes(StandardCharsets.UTF_8);

        if ((operatorCountryBytes.length == 0) && (countryOfManufactureBytes.length == 0))
        {
            // truncate here
            return ArrayUtils.arrayFromChunks(chunks, totalLength);
        }
        byte[] operatorCountryLengthBytes = BerEncoder.encode(operatorCountryBytes.length);
        chunks.add(operatorCountryLengthBytes);
        totalLength += operatorCountryLengthBytes.length;
        chunks.add(operatorCountryBytes);
        totalLength += operatorCountryBytes.length;

        if (countryOfManufactureBytes.length == 0)
        {
            // truncate here
            return ArrayUtils.arrayFromChunks(chunks, totalLength);
        }
        byte[] countryOfManufactureLengthBytes = BerEncoder.encode(countryOfManufactureBytes.length);
        chunks.add(countryOfManufactureLengthBytes);
        totalLength += countryOfManufactureLengthBytes.length;
        chunks.add(countryOfManufactureBytes);
        totalLength += countryOfManufactureBytes.length;

        return ArrayUtils.arrayFromChunks(chunks, totalLength);
    }

    /**
     * Get the coding method used for these country codes.
     *
     * @return the coding method as enumerated value.
     */
    public CountryCodingMethod getCodingMethod()
    {
        return codingMethod;
    }

    /**
     * Get the overflight country.
     * <p>
     * The country the platform is operating or flying over. This may be different to the country within the scene of the Motion Imagery.
     * </p>
     * @return the overflight country (to be interpreted using the coding method for this object), or an empty string if not known.
     */
    public String getOverflightCountry()
    {
        return overflightCountry;
    }

    /**
     * Get the operating country.
     * <p>
     * Country where the operator is located. For example, a GCS operator.
     * </p>
     * @return the operating country (to be interpreted using the coding method for this object), or an empty string if not known.
     */
    public String getOperatorCountry()
    {
        return operatorCountry;
    }

    /**
     * Get the country of manufacture.
     * <p>
     * The Country where the platform was manufactured.
     * </p>
     * @return the country of manufacture (to be interpreted using the coding method for this object), or an empty string if not known.
     */
    public String getCountryOfManufacture()
    {
        return countryOfManufacture;
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Country Codes]";
    }

    @Override
    public final String getDisplayName()
    {
        return "Country Codes";
    }
}
