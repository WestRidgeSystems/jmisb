/**
 * ST 0903: VTrack Local Set.
 *
 * <p>Whereas the VMTI LS describes detections of object movement associated within a single frame,
 * the VTrack Local Set (LS) describes a set of detections that may span many frames presumed to be
 * associated with a single, identified object. With few exceptions, the data items of the VTrack
 * Local Set are the same as those defined for the VMTI Local Set. The VTrack Local Set adopts a
 * "track-centric" view of the detection data, whereas the VMTI Local Set takes a "frame-centric"
 * view. The data describing a detection are the same, regardless of the view taken, but their
 * organization is different.
 *
 * <p>This module provides an implementation of the VTrack part of the MISB ST 0903 Standard.
 */
module org.jmisb.st0903vtrack {
    requires org.jmisb.api;
    requires org.jmisb.st0903;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0903vtrack.VTrackLocalSetFactory;

    exports org.jmisb.st0903vtrack;
}
