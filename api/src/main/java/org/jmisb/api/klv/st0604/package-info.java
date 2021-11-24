/**
 * ST 0604: Timestamps for Class 1/Class 2 Motion Imagery.
 *
 * <p>From ST 0604.6:
 *
 * <blockquote>
 *
 * <p>The MISP mandates that a timestamp based on Absolute Time be inserted into Class 0/1/2 Motion
 * Imagery. This standard provides guidance and specifies requirements for inserting a Precision
 * Time Stamp into H.262/MPEG-2, H.264/AVC and H.265/HEVC Class 1 and Class 2 Motion Imagery
 * (compressed imagery). This standard also provides guidance and specific requirements for
 * inserting a Nano Precision Time Stamp into H.265/HEVC Class 1 and Class 2 Motion Imagery.
 *
 * <p>These compression standards, approved by the MISP for use in Class1/Class 2 Motion Imagery,
 * allocate user-defined data fields. This standard specifies the format, encoding and mapping of
 * timestamp information into these user-defined data fields.
 *
 * <p>Finally, this standard provides guidance for inserting an optional Commercial Time Stamp in
 * reserved fields as identified in the respective compression standard.
 *
 * <p>This standard does not address timestamp information within Class 2 Motion Imagery JPEG2000.
 *
 * </blockquote>
 */
package org.jmisb.api.klv.st0604;
