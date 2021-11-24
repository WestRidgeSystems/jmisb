module org.jmisb.api.ffmpeg {
    requires org.jmisb.api;
    requires org.jmisb.core;
    requires org.bytedeco.javacpp;
    requires org.bytedeco.ffmpeg;
    requires org.slf4j;
    // TODO: refactor to not require this
    requires java.desktop;
    // TODO: possibly a different namespace?
    exports org.jmisb.api.video;
}
