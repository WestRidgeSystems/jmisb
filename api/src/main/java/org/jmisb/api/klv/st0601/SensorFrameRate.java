package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Sensor Frame Rate (ST 0601 tag 127).
 * <p>
 * From ST:
 * <blockquote>
 * The Sensor Frame Rate Pack consists of two unsigned integers used to compute
 * the frame rate. The ratio of the two integers provides the capability to
 * compute both integer and drop-frame frame rates. For example, typical integer
 * frame rates of 30, 60 are the ratio of 30/1 and 60/1, respectively. While
 * drop-frame rates of 29.97 and 59.94 are the ratio of 30000/1001 and
 * 60000/1001, respectively.
 * <p>
 * The Sensor Frame Rate Pack is a two-element truncation pack where the first
 * element is the numerator in BER-OID format, and the second element is the
 * denominator in BER-OID format.
 * </blockquote>
 */
public class SensorFrameRate implements IUasDatalinkValue
{
    private final int numerator;
    private final int denominator;

    /**
     * Create from values.
     *
     * @param numerator the numerator part of the rate.
     * @param denominator the denominator part of the rate.
     */
    public SensorFrameRate(int numerator, int denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Create from value.
     *
     * @param numerator the numerator part of the rate.
     */
    public SensorFrameRate(int numerator)
    {
        this.numerator = numerator;
        this.denominator = 1;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes encoded value
     */
    public SensorFrameRate(byte[] bytes)
    {
        BerField numeratorField = BerDecoder.decode(bytes, 0, true);
        numerator = numeratorField.getValue();
        if (bytes.length > numeratorField.getLength())
        {
            BerField denominatorField = BerDecoder.decode(bytes, numeratorField.getLength(), true);
            denominator = denominatorField.getValue();
        }
        else
        {
            denominator = 1;
        }
    }

    @Override
    public byte[] getBytes()
    {
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        byte[] numeratorBytes = BerEncoder.encode(numerator, Ber.OID);
        chunks.add(numeratorBytes);
        totalLength += numeratorBytes.length;
        if (denominator != 1)
        {
            byte[] denominatorBytes = BerEncoder.encode(denominator, Ber.OID);
            chunks.add(denominatorBytes);
            totalLength += denominatorBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, totalLength);
    }

    /**
     * Get the resulting frame rate.
     *
     * @return Frame rate as a floating point value.
     */
    public double getFrameRate()
    {
        return (double)numerator / denominator;
    }

    /**
     * Get the numerator part of the frame rate.
     *
     * @return frame rate ratio numerator
     */
    public int getNumerator()
    {
        return numerator;
    }

    /**
     * Get the denominator part of the frame rate.
     *
     * @return frame rate ratio denominator
     */
    public int getDenominator()
    {
        return denominator;
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.2f fps", (float)numerator / denominator);
    }

    @Override
    public final String getDisplayName()
    {
        return "Sensor Frame Rate";
    }
}
