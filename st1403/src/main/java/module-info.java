/**
 * ST 1403 SARMI Threshold Metadata Sets.
 *
 * <p>This standard defines the Synthetic Aperture Radar Motion Imagery (SARMI) threshold metadata
 * which enable the basic capabilities of Situational Awareness, Discovery and Retrieval, and
 * Cross-Domain Dissemination. This metadata is intended to convey collection information related to
 * the SAR image formation and SAR coherent change product processes used to create the SARMI data
 * in addition to mission and classification information. SAR Motion Imagery Metadata.
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1403 {
    requires org.jmisb.api;
    requires org.slf4j;

    exports org.jmisb.st1403;
}