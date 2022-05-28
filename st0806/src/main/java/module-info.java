/**
 * ST 0806: Remote Video Terminal Metadata.
 *
 * <p>This module provides an implementation of the MISB ST 0806 Remote Video Terminal (RVT)
 * Metadata Standard.
 *
 * <p>Users of the ROVER (Remotely Operated Video Enhanced Receiver) require additional metadata
 * elements unique to its mission needs than are specified in MISB ST 0601. The purpose of ST 0806
 * is to formalize a method of communicating with a Remote Video Terminal (ROVER or other related
 * programs, such as the One System Remote Video Terminal (OSRVT)), and create a
 * configuration-managed metadata standard to meet its needs with minimal impacts to ST 0601 users.
 * The RVT Local Set (LS) defined in ST 0806 can stand as an independent local set, or be embedded
 * within other metadata sets.
 *
 * <p>This provides the ability for system designers to produce or receive one, or a mix of metadata
 * elements from other sets based upon program requirements.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0806 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0806.RvtLocalSetFactory;

    exports org.jmisb.st0806;
    exports org.jmisb.st0806.poiaoi;
    exports org.jmisb.st0806.userdefined;
}
