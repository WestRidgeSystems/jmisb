/**
 * ST 1204: Motion Imagery Identification System (MIIS) - Core Identifier.
 *
 * From ST1204.2 Section 1:
 * <blockquote>
 * Many different sensors produce Motion Imagery Data distributed across many
 * different networks and received by many different users and systems.
 * Assigning a consistent name, label,or identity to each Motion Imagery source
 * helps to coordinate analysis, manage assets,and avoid confusion.
 * <p>
 * TheMotion Imagery Identification System (MIIS)-Core Identifier addresses this
 * issue. Specifically, this standard defines:(1) required identifiers for a
 * Motion Imagery stream or file; (2) points to insert identifiers as the Motion
 * Imagery Data flows from source to user; and (3) methods and formats for
 * inserting identifiers into a Motion Imagery stream or file. This standard
 * also provides guidance on extracting Universal Unique Identifiers (UUID) for
 * use as enterprise identifiers of Motion Imagery systems.
 * <p>
 * MIIS-Augmentation Identifiers provide a framework for including
 * human-readable supplemental information to Motion Imagery Data to better
 * manage and exploit Motion Imagery. MISB ST1301 defines and documents
 * Augmentation Identifiers.
 * <p>
 * The MIIS solves four problems: (1) Determining whether two (or more) Motion
 * Imagery streams or files are from the same source (sensor/platform). (2)
 * Determining whether two (or more) Motion Imagery streams or files are from
 * two different sources. (3) Basis for pedigree information about the Motion
 * Imagery (i.e.,tracking all Motion Imagery manipulations from source through
 * receiver). (4) Linking useful identifying information about the Motion Imagery
 * stream or file to the Motion Imagery source. When fully implemented, the MIIS
 * fulfills the need to provide a consistent and unique identifier for all
 * sensors and platforms.
 * </blockquote>
 *
 * MIIS identifiers can be formatted as text, XML or byte array. This package
 * supports parsing and generation of text and byte array representations. There
 * is no direct support for the XML representation, although there is indirect
 * support since the value of the `MiisCoreId` element is the same format as the
 * text representation.
 */
package org.jmisb.api.klv.st1204;
