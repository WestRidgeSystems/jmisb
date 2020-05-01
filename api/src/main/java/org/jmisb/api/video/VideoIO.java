package org.jmisb.api.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.ffmpeg.global.avutil.setLogCallback;

/**
 * Abstract base class from which all video I/O classes inherit.
 */
public abstract class VideoIO {

    private static final Logger logger = LoggerFactory.getLogger(VideoIO.class);

    static
    {
        try
        {
            // Redirect ffmpeg's log to slf4j
            setLogCallback(FfmpegLog.INSTANCE);
        }
        catch (Exception ex)
        {
            logger.error(ex.getLocalizedMessage());
        }
    }
}
