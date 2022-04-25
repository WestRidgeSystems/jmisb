module org.jmisb.examples.parserplugin {
    requires org.jmisb.api;
    requires org.jmisb.core;
    requires org.jmisb.api.ffmpeg;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.examples.parserplugin.timemessage.TimeMessageFactory;
}
