package org.jmisb.api.klv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.eg0104.PredatorUavMessageFactory;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSetFactory;
import org.jmisb.api.klv.st0102.universalset.SecurityMetadataUniversalSetFactory;
import org.jmisb.api.klv.st0601.UasDatalinkMessageFactory;
import org.jmisb.api.klv.st0808.AncillaryTextLocalSetFactory;
import org.jmisb.api.klv.st0903.vtrack.VTrackLocalSetFactory;
import org.jmisb.api.klv.st1108.InterpretabilityQualityLocalSetFactory;
import org.jmisb.api.klv.st1902.MimdLocalSetFactory;

/**
 * Factory class for {@link IMisbMessage} instances.
 *
 * <p>This singleton provides the ability to get an IMisbMessage class instance corresponding to a
 * provided {@link UniversalLabel}. Built-in implementations will be registered automatically.
 * Additional implementations (e.g. for proprietary extensions) can be registered as required.
 */
public class MisbMessageFactory {
    private static final Map<UniversalLabel, IMisbMessageFactory> MESSAGE_HANDLERS =
            new HashMap<>();

    private MisbMessageFactory() {
        registerHandler(KlvConstants.UasDatalinkLocalUl, new UasDatalinkMessageFactory());
        registerHandler(KlvConstants.AncillaryTextLocalSetUl, new AncillaryTextLocalSetFactory());
        registerHandler(
                KlvConstants.SecurityMetadataUniversalSetUl,
                new SecurityMetadataUniversalSetFactory());
        registerHandler(
                KlvConstants.SecurityMetadataLocalSetUl, new SecurityMetadataLocalSetFactory());
        registerHandler(KlvConstants.PredatorMetadataLocalSetUl, new PredatorUavMessageFactory());
        registerHandler(KlvConstants.VTrackLocalSetUl, new VTrackLocalSetFactory());
        registerHandler(KlvConstants.MIMDLocalSetUl, new MimdLocalSetFactory());
        registerHandler(
                KlvConstants.InterpretabilityQualityLocalSetUl,
                new InterpretabilityQualityLocalSetFactory());
    }

    /**
     * Get the singleton instance.
     *
     * @return the message factory instance.
     */
    public static MisbMessageFactory getInstance() {
        return MisbFactoryHolder.INSTANCE;
    }

    /**
     * Register a handler for a given {@link UniversalLabel}.
     *
     * @param universalLabel the universal label
     * @param factory the corresponding factory to use
     */
    public final void registerHandler(UniversalLabel universalLabel, IMisbMessageFactory factory) {
        MESSAGE_HANDLERS.put(universalLabel, factory);
    }

    /**
     * Lookup the appropriate message handler for this message, and process it.
     *
     * <p>If no matching message handler is available, the data will be returned as a {@link
     * RawMisbMessage}.
     *
     * @param messageData the message data (starting at the universal label)
     * @return the message instance
     * @throws KlvParseException if the message handler throws.
     */
    public IMisbMessage handleMessage(byte[] messageData) throws KlvParseException {
        UniversalLabel ul =
                new UniversalLabel(Arrays.copyOfRange(messageData, 0, UniversalLabel.LENGTH));
        if (MESSAGE_HANDLERS.containsKey(ul)) {
            IMisbMessageFactory factory = MESSAGE_HANDLERS.get(ul);
            return factory.create(messageData);
        }
        return new RawMisbMessage(ul, messageData);
    }

    private static class MisbFactoryHolder {
        private static final MisbMessageFactory INSTANCE = new MisbMessageFactory();
    }
}
