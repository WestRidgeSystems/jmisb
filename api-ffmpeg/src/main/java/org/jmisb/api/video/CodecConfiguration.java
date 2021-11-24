package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avutil.av_dict_set;

import java.util.HashMap;
import java.util.Map;
import org.bytedeco.ffmpeg.avutil.AVDictionary;

/**
 * Codec-specific configuration information.
 *
 * <p>This is oriented towards encoder options.
 */
public class CodecConfiguration {
    private final String name;
    private final String label;
    private final CodecIdentifier identifier;
    private final Map<String, String> codecOptions = new HashMap<>();

    /**
     * Create a new configuration.
     *
     * <p>The configuration has no codec options set.
     *
     * @param name the name of the codec, which must match what the codec calls itself
     * @param label a display label (human readable) for the codec.
     * @param identifier the kind of codec that this configuration is for
     */
    public CodecConfiguration(String name, String label, CodecIdentifier identifier) {
        this.name = name;
        this.label = label;
        this.identifier = identifier;
    }

    /**
     * The name of the codec.
     *
     * @return the name of the codec as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * The display label for the codec.
     *
     * @return the human readable display label for the codec, as a string.
     */
    public String getLabel() {
        return label;
    }

    /**
     * The codec identifier.
     *
     * @return the codec identifier as an enumerated value.
     */
    public CodecIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Add a new codec option.
     *
     * @param key the key for the option
     * @param value the value for the option
     * @return this instance, to support chaining.
     */
    public CodecConfiguration addCodecOption(String key, String value) {
        this.codecOptions.put(key, value);
        return this;
    }

    /**
     * Get the options for a given codec.
     *
     * @return the options as a dictionary
     */
    public AVDictionary getOptions() {
        AVDictionary optionsDictionary = new AVDictionary(null);
        codecOptions.forEach((key, value) -> av_dict_set(optionsDictionary, key, value, 0));
        return optionsDictionary;
    }
}
