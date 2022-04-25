/**
 * ST 0102: Security Metadata Universal and Local Sets.
 *
 * <p>This standard defines a Security Metadata Universal Set and a Security Metadata Local Set
 * encoded as KLV (Key-Length-Value) elements for marking Motion Imagery Data with security
 * classification information.
 *
 * <p>The methods used to gather security information, mark Motion Imagery Data with security
 * information, and display security information are the responsibility of application system
 * developers in concert with appropriate security officials. Originators and users of Motion
 * Imagery Data are responsible for the proper handling, and ultimately, for the use and disposition
 * of classified information. This standard is not a security manual or instruction on when or how
 * to use security markings or caveats. Use of this standard does not ensure a Motion Imagery System
 * will be accredited by security officials.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st0102 {
    requires org.jmisb.api;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st0102.universalset.SecurityMetadataUniversalSetFactory,
            org.jmisb.st0102.localset.SecurityMetadataLocalSetFactory;

    exports org.jmisb.st0102;
    exports org.jmisb.st0102.localset;
    exports org.jmisb.st0102.universalset;
}
