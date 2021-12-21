/**
 * MISB ST 1002 Range Motion Imagery implementation for jmisb.
 *
 * <p>This module provides an implementation of the MISB ST 1002 standard, which describes Range
 * Motion Imagery, its format and supporting metadata.
 *
 * <p>Range Motion Imagery is a temporal sequence of Range Images. Each Range Image is a collection
 * of Range Measurements from a sensor to target scene. A Range Measurement is the distance (e.g.,
 * meters) from an object (or area) in the scene to the sensor. The KLV structures of this Standard
 * are intended to allow for flexibility, efficient packing, and future extensions. Range Motion
 * Imagery can be used standalone or in collaboration with other Motion Imagery. MISB ST 1107 Metric
 * Geopositioning Metadata Set provides the basis for collaborating with other Motion Imagery types.
 *
 * <p>This standard describes the:
 *
 * <ul>
 *   <li>Perspective Range Motion Imagery and Depth Range Motion Imagery;
 *   <li>the collection methods of Range Motion Imagery;
 *   <li>the formats used for storing or transmitting Range Motion Imagery;
 *   <li>the supporting metadata needed for Range Motion Imagery including:
 *       <ul>
 *         <li>temporal
 *         <li>uncertainty, and
 *         <li>compression parameters;
 *       </ul>
 *       and
 *   <li>the alignment to Collaborative Imagery.
 * </ul>
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1002 {
    requires org.jmisb.api;
    requires org.jmisb.st1202;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.st1002.RangeImageLocalSetFactory;

    exports org.jmisb.st1002;
}
