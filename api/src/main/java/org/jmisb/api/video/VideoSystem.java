package org.jmisb.api.video;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.avcodec.avcodec_register_all;
import static org.bytedeco.javacpp.avdevice.avdevice_register_all;
import static org.bytedeco.javacpp.avformat.av_register_all;
import static org.bytedeco.javacpp.avformat.avformat_network_init;

/**
 * Central class used to instantiate all streams
 * <p>
 * When possible, it is recommended that <i>try-with-resources</i> blocks be used when creating instances
 * using any of the {@code create*} methods of this class to ensure that the instance's close() method will
 * be called and its resources released. For example,
 * <pre>
 * {@code
 * try (IVideoFileInput input = VideoSystem.createInputFile())
 * {
 *     input.open("file.ts");
 *     // ... do something with input
 * }
 * catch (IOException ex)
 * {
 *     // handle file open failure
 * }
 * }
 * </pre>
 */
public class VideoSystem
{
    private static Logger logger = LoggerFactory.getLogger(VideoSystem.class);

    private VideoSystem() {}

    static
    {
        try
        {
            // TODO: needed?
            Loader.load(org.bytedeco.javacpp.avutil.class);
            Loader.load(org.bytedeco.javacpp.swresample.class);
            Loader.load(org.bytedeco.javacpp.avcodec.class);
            Loader.load(org.bytedeco.javacpp.avformat.class);
            Loader.load(org.bytedeco.javacpp.swscale.class);

            // Redirect ffmpeg's log to slf4j
            avutil.setLogCallback(FfmpegLog.INSTANCE);

            // Register all formats and codecs
            avcodec_register_all();
            av_register_all();
            avformat_network_init();

            // TODO: needed?
            Loader.load(org.bytedeco.javacpp.avdevice.class);

            avdevice_register_all();
        }
        catch (Exception ex)
        {
            logger.error(ex.getLocalizedMessage());
        }
    }

    /**
     * Create a VideoStreamInput
     *
     * @return The created stream
     */
    public static IVideoStreamInput createInputStream()
    {
        VideoStreamInputOptions options = new VideoStreamInputOptions();
        return new VideoStreamInput(options);
    }

    /**
     * Create a VideoStreamInput
     *
     * @param options Input stream options
     * @return The created instance
     */
    public static IVideoStreamInput createInputStream(VideoStreamInputOptions options)
    {
        return new VideoStreamInput(options);
    }

    /**
     * Create a VideoFileInput
     *
     * @return The created instance
     */
    public static IVideoFileInput createInputFile()
    {
        VideoFileInputOptions options = new VideoFileInputOptions();
        return new VideoFileInput(options);
    }

    /**
     * Create a VideoFileInput
     *
     * @param options Input file options
     * @return The created instance
     */
    public static IVideoFileInput createInputFile(VideoFileInputOptions options)
    {
        return new VideoFileInput(options);
    }

    /**
     * Create a VideoFileOutput
     *
     * @param options Output options
     * @return The created instance
     */
    public static IVideoFileOutput createOutputFile(VideoOutputOptions options)
    {
        return new VideoFileOutput(options);
    }

    /**
     * Create a VideoStreamOutput
     *
     * @param options Output options
     * @return The created instance
     */
    public static IVideoStreamOutput createOutputStream(VideoOutputOptions options)
    {
        return new VideoStreamOutput(options);
    }
}

