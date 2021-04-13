/**
 * ST 1906 Motion Imagery Metadata (MIMD): Staging System.
 *
 * <p>ST 1901, ST 1902, ST 1903, ST 1904, ST 1905, ST 1906, ST 1907 and ST 1908 collectively define
 * Motion Imagery Metadata (MIMD). The Motion Imagery Metadata (MIMD) Model includes locations,
 * orientations, and kinematics (velocity, acceleration, etc.) of platforms, gimbals, sensors,
 * sensor elements, geospatial and other points. The locations and orientations are either absolute
 * references to a well-known frame of reference (e.g., WGS84) or relative references to other
 * locations and orientations. Each location and orientation pairing define a “stage” that has the
 * potential to be the frame of reference for another location and orientation. Linking stages
 * together forms the Staging System. The Staging System then defines an ability to describe, in
 * metadata, the physical make-up and configuration of a system and the time varying physical
 * relationships of the system and its sub-system components.
 *
 * <p>Implementation note: Most of the implementation of this document is generated from the Motion
 * Imagery Modeling Language (MIML) representation.
 */
package org.jmisb.api.klv.st1906;
