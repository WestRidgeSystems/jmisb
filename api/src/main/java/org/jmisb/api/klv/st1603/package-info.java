/**
 * ST 1603: Time Transfer Pack.
 *
 * <p>The Time Transfer Pack is the generic name for a KLV Pack structure, which supports a specific
 * representation of the MISP Time System as defined in MISB ST 0603 co-joined with the Time
 * Transfer Local Set.
 *
 * <p>This standard defines metadata elements of the Time Transfer Local Set to validate and correct
 * timestamps. In addition, a leap second offset is included to adjust an International Atomic Time
 * (TAI) timestamp to Universal Coordinated Time (UTC).
 *
 * <p>Currently, one Time Transfer Pack is defined: The Nano Time Transfer Pack, which is a KLV pack
 * consisting of a Nano Precision Time Stamp, as defined in MISB ST 0603, and the Time Transfer
 * Local Set.
 */
package org.jmisb.api.klv.st1603;
