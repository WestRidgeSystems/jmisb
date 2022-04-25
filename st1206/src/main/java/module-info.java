/**
 * ST 1206: SAR Motion Imagery Metadata.
 *
 * <p>This standard defines the content and Local Set Key-Length-Value (KLV) representation of
 * metadata necessary to exploit both sequential synthetic aperture radar (SAR) imagery and
 * sequential SAR coherent change products as motion imagery.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1206 {
    requires org.jmisb.api;
    requires org.slf4j;

    exports org.jmisb.st1206;
}
