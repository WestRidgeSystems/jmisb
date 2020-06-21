package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_DEBUG;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_ERROR;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_FATAL;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_INFO;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_PANIC;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_TRACE;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_VERBOSE;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_WARNING;

import org.bytedeco.ffmpeg.avutil.LogCallback;
import org.bytedeco.javacpp.BytePointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Singleton class to redirect FFmpeg's logging to SLF4J */
class FfmpegLog extends LogCallback {
    private static final Logger logger = LoggerFactory.getLogger(FfmpegLog.class);
    static final FfmpegLog INSTANCE = new FfmpegLog();

    private FfmpegLog() {}

    @Override
    public void call(int level, BytePointer msg) {
        String message = msg.getString();
        int adjustedLevel = adjust(level, message);
        switch (adjustedLevel) {
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
                if (logger.isDebugEnabled()) logger.debug(message);
        }
    }

    /**
     * Adjust the FFmpeg logging level based on heuristics
     *
     * @param origLevel The original FFmpeg log level
     * @param message The message string
     * @return The adjusted log level
     */
    private int adjust(int origLevel, String message) {
        int adjusted = origLevel;

        // H.264 codec is ridiculously verbose
        if (message.startsWith("[h264") || message.startsWith("[libx264")) {
            adjusted = AV_LOG_DEBUG;
        }
        return adjusted;
    }
}
