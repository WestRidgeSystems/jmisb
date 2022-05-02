/**
 * MISB ST 1601 Geo-Registration Local Set.
 *
 * <p>This standard defines metadata to support the identification of a geo-registration algorithm
 * and various outputs from a geo-registration process. The intent is to use this standard’s Local
 * Set in conjunction with the KLV construct Amend Local Set as defined in MISB ST 1607. Only
 * include the Geo-Registration Local Set within an existing Local Set that contains items
 * describing a sensor model.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1601 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st1601.GeoRegistrationLocalSetFactory;

    exports org.jmisb.st1601;
}
