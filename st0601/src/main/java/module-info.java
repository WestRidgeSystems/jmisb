/**
 * ST 0601: UAS Datalink Local Set implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 0601 UAS Datalink Standard.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0601 {
    requires org.jmisb.api;
    requires org.jmisb.st0102;
    requires org.jmisb.st0806;
    requires transitive org.jmisb.st0903;
    requires org.jmisb.st1206;
    requires org.jmisb.st1601;
    requires org.jmisb.st1602;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0601.UasDatalinkMessageFactory;

    exports org.jmisb.st0601;
    exports org.jmisb.st0601.dto;
}
