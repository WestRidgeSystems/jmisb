/**
 * FFmpeg integration for jmisb.
 *
 * <p>This module provides FFmpeg-specific support, including parsing.
 */
module org.jmisb.api.ffmpeg {
    requires org.jmisb.api;
    requires org.jmisb.st0601;
    requires org.bytedeco.javacpp;
    requires org.bytedeco.ffmpeg;
    requires org.slf4j;
    // TODO: refactor to not require this
    requires java.desktop;
    // TODO: possibly a different namespace?
    exports org.jmisb.api.video;
}
