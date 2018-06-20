package org.jmisb.api.video;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.avutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.avutil.AV_LOG_DEBUG;
import static org.bytedeco.javacpp.avutil.AV_LOG_TRACE;

/**
 * Singleton class to redirect FFmpeg's logging to SLF4J
 */
class FfmpegLog extends avutil.LogCallback
{
    private static final Logger logger = LoggerFactory.getLogger(FfmpegLog.class);
    static final FfmpegLog INSTANCE = new FfmpegLog();
    private FfmpegLog() {}

    @Override
    public void call(int level, BytePointer msg)
    {
        String message = msg.getString();
        int adjustedLevel = adjust(level, message);
        switch (adjustedLevel)
        {
            case AV_LOG_PANIC:
            case AV_LOG_FATAL:
            case AV_LOG_ERROR:
                logger.error(message);
                break;
            case AV_LOG_WARNING:
                logger.warn(message);
                break;
            case AV_LOG_INFO:
                logger.info(message);
                break;
            case AV_LOG_VERBOSE:
            case AV_LOG_DEBUG:
            case AV_LOG_TRACE:
            default:
                if (logger.isDebugEnabled())
                    logger.debug(message);
        }
    }

    /**
     * Adjust the FFmpeg logging level based on heuristics
     *
     * @param origLevel The original FFmpeg log level
     * @param message The message string
     *
     * @return The adjusted log level
     */
    private int adjust(int origLevel, String message)
    {
        int adjusted = origLevel;

        // H.264 codec is ridiculously verbose
        if (message.startsWith("[h264") || message.startsWith("[libx264"))
        {
            adjusted = AV_LOG_DEBUG;
        }
        return adjusted;
    }
}
