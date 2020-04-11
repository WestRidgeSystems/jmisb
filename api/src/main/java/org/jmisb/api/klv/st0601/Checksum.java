package org.jmisb.api.klv.st0601;

import java.nio.ByteBuffer;

/**
 * Compute checksum for ST 0601 packet
 */
public class Checksum
{
    private Checksum() {}

    /**
     * Compute checksum
     * <p>
     * The checksum is computed by summing the full message buffer up to and including the checksum's length field. The
     * reference algorithm as listed in the ST takes two bytes at a time and treats them as uint16 values. It sums them
     * into another uint16 register, so overflow is normal and ignored.
     *
     * @param fullMessage Byte array of the full message packet
     * @param insert True to insert the computed checksum into {@code fullMessage}
     * @return 2-byte checksum
     */
    public static byte[] compute(byte[] fullMessage, boolean insert)
    {
        int sum = 0;
        final int lastShortIndex = fullMessage.length - 4;

        // TODO: optimize
        for (int i = 0; i <= lastShortIndex; i += 2)
        {
            sum += Short.toUnsignedInt(ByteBuffer.wrap(fullMessage, i, 2).getShort());
        }

        // Add final byte if there's an odd number of bytes
        if (fullMessage.length % 2 == 1)
        {
            int last = Short.toUnsignedInt(fullMessage[fullMessage.length-3]);
            sum += (last << 8);
        }

        if (insert)
        {
            fullMessage[fullMessage.length-2] = (byte)(sum >>> 8);
            fullMessage[fullMessage.length-1] = (byte)sum;
        }

        return new byte[]{(byte)(sum >>> 8), (byte)sum};
    }
}
