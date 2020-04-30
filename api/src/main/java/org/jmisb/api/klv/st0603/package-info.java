/**
 * ST 0603: MISP Time System and Timestamps.
 * <p>
 * From ST0603:
 * </p>
 * <blockquote>
 * <p>
 * An absolute, reliable time system is essential to precisely mark temporal
 * events with timestamps for collected Motion Imagery and metadata. Such
 * timestamps facilitate photogrammetric analysis, interoperability and
 * exploitation of Motion Imagery products.
 * </p>
 * <p>
 * This standard specifies the MISP Time System, which is an absolute time scale
 * from which timestamps are derived. Three types of timestamps are defined: 1)
 * a Precision Time Stamp, which represents Absolute Time specified to
 * microsecond resolution; 2) a Nano Precision Time Stamp, which represents
 * Absolute Time specified to nanosecond resolution; and, 3) a Commercial Time
 * Stamp, which represents a relative time consistent with time code used in the
 * commercial broadcast industry, specified to video frame resolution. The
 * Precision Time Stamp and the Nano Precision Time Stamp aide in correlating
 * Motion Imagery with metadata.
 * </p>
 * <p>
 * This standard also specifies a Time Status byte, which is intended to provide
 * additional information regarding the source used for timestamps
 * </p>
 * </blockquote>
 */
package org.jmisb.api.klv.st0603;