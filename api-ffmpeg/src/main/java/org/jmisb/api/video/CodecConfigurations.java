package org.jmisb.api.video;

import java.util.LinkedList;
import java.util.List;

/**
 * Configurations for Codecs.
 *
 * <p>This is essentially a collection of CodecConfigurations.
 */
public class CodecConfigurations {
    private final List<CodecConfiguration> codecs = new LinkedList<>();

    private CodecConfigurations() {
        // HW first, then fallback to best software options.
        codecs.add(
                new CodecConfiguration("hevc_nvenc", "NVIDIA", CodecIdentifier.H265)
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "ll"));
        codecs.add(
                new CodecConfiguration("libx265", "x265", CodecIdentifier.H265)
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "ultrafast"));
        codecs.add(
                new CodecConfiguration("h264_nvenc", "NVIDIA", CodecIdentifier.H264)
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "fast"));
        codecs.add(
                new CodecConfiguration("h264_qsv", "Intel QuickSync", CodecIdentifier.H264)
                        // Untested options
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "ultrafast"));
        codecs.add(
                new CodecConfiguration("h264_vaapi", "VAAPI", CodecIdentifier.H264)
                        // Untested options
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "ultrafast"));
        codecs.add(
                new CodecConfiguration("libx264", "x264", CodecIdentifier.H264)
                        .addCodecOption("tune", "zerolatency")
                        .addCodecOption("preset", "ultrafast"));
    }

    /**
     * Get the codecs for a given kind of codec identifier.
     *
     * @param codecIdentifier the codec identifier (e.g. H.264)
     * @return a list of codec configuration that match the given identifier, in priority order.
     */
    public List<CodecConfiguration> getCodecs(CodecIdentifier codecIdentifier) {
        List<CodecConfiguration> filteredCodecs = new LinkedList<>();
        codecs.stream()
                .filter(c -> (c.getIdentifier().equals(codecIdentifier)))
                .forEachOrdered(filteredCodecs::add);
        return filteredCodecs;
    }

    /**
     * Get an instance of this singleton object.
     *
     * @return the configurations
     */
    public static CodecConfigurations getInstance() {
        return CodecConfigurationsHolder.INSTANCE;
    }

    private static class CodecConfigurationsHolder {
        private static final CodecConfigurations INSTANCE = new CodecConfigurations();
    }
}
