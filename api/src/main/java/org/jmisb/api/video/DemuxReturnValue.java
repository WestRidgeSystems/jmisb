package org.jmisb.api.video;

/** Return value enumeration for demux operation. */
enum DemuxReturnValue {
    /** Operation succeeded. */
    SUCCESS,
    /** End of file. */
    EOF,
    /** Operation needs to be tried again. */
    EAGAIN,
    /** Some other failure. */
    ERROR
}
