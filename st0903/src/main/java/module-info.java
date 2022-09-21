/**
 * ST 0903: VMTI Local Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 0903 VMTI Standard.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0903 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    // provides org.jmisb.api.klv.IMisbMessageFactory with
    //        org.jmisb.st0601.UasDatalinkMessageFactory;

    exports org.jmisb.st0903;
    exports org.jmisb.st0903.algorithm;
    exports org.jmisb.st0903.ontology;
    exports org.jmisb.st0903.shared;
    exports org.jmisb.st0903.vchip;
    exports org.jmisb.st0903.vfeature;
    exports org.jmisb.st0903.vmask;
    exports org.jmisb.st0903.vobject;
    exports org.jmisb.st0903.vtarget;
    exports org.jmisb.st0903.vtracker;
}