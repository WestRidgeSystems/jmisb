package org.jmisb.api.klv;

/**
 * Decode data encoded using Basic Encoding Rules (BER)
 */
public class BerDecoder
{
    private BerDecoder() {}

    /**
     * Decode a KLV length field from a byte array
     *
     * @param data holding the BER-encoded field
     * @param offset of the first byte of the field
     * @param isOid true if the data is encoded using BER-OID
     *
     * @return decoded Length field
     *
     * @throws IllegalArgumentException if the encoded data is invalid
     * @throws UnsupportedOperationException if the BER encoding type is not short form or long form
     */
    public static LengthField decodeLengthField(byte[] data, int offset, boolean isOid) throws IllegalArgumentException
    {
        final int fullBerSize, length;

        // logger.debug("First byte of BER: " + String.format("%02X ", data[offset]));

        if (!isOid)
        {
            if ((data[offset] & 0x80) == 0)
            {
                // BER Short Form. If the first bit of the BER is 0 then the BER is 1-byte and the length is
                // encoded directly in that byte. This means the short form encodes values from 0 to 127.
                //
                fullBerSize = 1;
                length = data[offset] & 0x7f;
            } else
            {
                // BER Long Form (variable length). If the first bit of the BER is 1 then the rest of the
                // first byte encodes the length of the BER in bytes and the length is read from that number
                // of bytes immediately following. Theoretically the long form encodes values 128 to
                // 2^(8*127), but for our purposes we handle 128 to 2^32, which should be plenty for a length field.
                //
                int berLength = data[offset] & 0x7f;
                if (data.length < offset + 1 + berLength)
                {
                    throw new IllegalArgumentException("BER long form: BER length overruns packet size");
                }

                if (berLength > 4)
                {
                    throw new IllegalArgumentException("BER long form: BER length is >5 bytes; data is probably corrupt");
                }
                int len = 0;
                for (int i = 0; i < berLength; ++i)
                {
                    int b = 0x00FF & data[offset + i + 1];
                    len = (len << 8) | b;
                }
                fullBerSize = berLength + 1;
                length = len;
            }
        }
        else
        {
            throw new UnsupportedOperationException("BER-OID not yet supported");
        }

        if (fullBerSize < 1 || length < 0)
        {
            throw new IllegalArgumentException("BER: error decoding length");
        }

        return new LengthField(fullBerSize, length);
    }
}
