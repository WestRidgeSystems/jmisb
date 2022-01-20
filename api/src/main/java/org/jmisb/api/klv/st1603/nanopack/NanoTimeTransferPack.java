package org.jmisb.api.klv.st1603.nanopack;

import java.util.EnumSet;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st0603.NanoPrecisionTimeStamp;
import org.jmisb.api.klv.st1603.localset.TimeTransferLocalSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ST 1603 Nano Time Transfer Pack.
 *
 * <p>The Nano Time Transfer Pack is a KLV construct composed of a two-element truncation pack. The
 * first element is the Nano Precision Time Stamp as defined in MISB ST 0603, which is a 64- bit
 * unsigned integer representing time measured from the MISP Epoch in nanoseconds. The second
 * element is the value portion of the Time Transfer Local Set. A truncation pack saves bandwidth
 * and co-joins the time transfer metadata to be directly associated with its parent time within the
 * same KLV group.
 */
public class NanoTimeTransferPack implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTransferLocalSet.class);

    private final NanoPrecisionTimeStamp timeStamp;
    private final TimeTransferLocalSet timeTransferLocalSet;

    /**
     * Construct from values.
     *
     * @param timeStamp the nano precision time stamp value.
     * @param timeTransferLocalSet the local set providing time transfer metadata.
     */
    public NanoTimeTransferPack(
            NanoPrecisionTimeStamp timeStamp, TimeTransferLocalSet timeTransferLocalSet) {
        this.timeStamp = timeStamp;
        this.timeTransferLocalSet = timeTransferLocalSet;
    }

    /**
     * Construct from encoded bytes.
     *
     * <p>This assumes the pack starts with the universal label and length.
     *
     * @param bytes the byte array providing the encoded pack values.
     */
    NanoTimeTransferPack(byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField lengthField = BerDecoder.decode(bytes, offset, false);
        offset += lengthField.getLength();
        int valueLength = lengthField.getValue();
        if (valueLength < NanoPrecisionTimeStamp.BYTES) {
            throw new KlvParseException(
                    "Insufficient bytes to parse ST 1603 Nano Time Transfer Pack");
        }
        if (offset + NanoPrecisionTimeStamp.BYTES > bytes.length) {
            throw new KlvParseException(
                    "Too few bytes available to parse ST 1603 Nano Time Transfer Pack");
        }
        timeStamp = new NanoPrecisionTimeStamp(bytes, offset);
        offset += NanoPrecisionTimeStamp.BYTES;
        timeTransferLocalSet = TimeTransferLocalSet.fromNestedBytes(bytes, offset, valueLength - 8);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.NanoTimeTransferPackUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        builder.append(timeStamp.getBytes());
        builder.append(timeTransferLocalSet.frameMessage(true));
        if (!isNested) {
            builder.prependLength();
            builder.prepend(KlvConstants.NanoTimeTransferPackUl);
        }
        return builder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "Nano Time Transfer Pack";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        NanoTimeTransferPackKey key = (NanoTimeTransferPackKey) tag;
        switch (key) {
            case NanoPrecisionTimeStamp:
                return timeStamp;
            case TimeTransferLocalSetValue:
                return timeTransferLocalSet;
            default:
                throw new AssertionError(key.name());
        }
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return EnumSet.allOf(NanoTimeTransferPackKey.class);
    }

    /**
     * Get the nanosecond time stamp.
     *
     * @return time stamp structure
     */
    public NanoPrecisionTimeStamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get the time transfer properties.
     *
     * @return time transfer properties as an ST 1603 Local Set.
     */
    public TimeTransferLocalSet getTimeTransferLocalSet() {
        return timeTransferLocalSet;
    }
}
