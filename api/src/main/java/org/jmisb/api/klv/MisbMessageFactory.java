package org.jmisb.api.klv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import org.jmisb.api.common.KlvParseException;

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
        ServiceLoader<IMisbMessageFactory> loader = ServiceLoader.load(IMisbMessageFactory.class);
        for (IMisbMessageFactory factory : loader) {
            MESSAGE_HANDLERS.put(factory.getUniversalLabel(), factory);
        }
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
     * @deprecated Use the service loader instead.
     */
    @Deprecated
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
        var ul = new UniversalLabel(Arrays.copyOfRange(messageData, 0, UniversalLabel.LENGTH));
        if (MESSAGE_HANDLERS.containsKey(ul)) {
            var factory = MESSAGE_HANDLERS.get(ul);
            return factory.create(messageData);
        }
        return new RawMisbMessage(ul, messageData);
    }

    private static class MisbFactoryHolder {
        private static final MisbMessageFactory INSTANCE = new MisbMessageFactory();
    }
}
