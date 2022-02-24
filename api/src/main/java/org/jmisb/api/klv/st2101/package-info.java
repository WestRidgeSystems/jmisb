/**
 * ST 2101 Core Identifier for Class 1/Class 2 Motion Imagery.
 *
 * <p>This standard (ST) provides guidance and requirements for how to insert a Motion Imagery
 * Identification System (MIIS) Core Identifier (ID) into a Supplemental Enhancement Information
 * (SEI) message of a MPEG compressed Class1/Class 2 Motion Imagery elementary stream.
 *
 * <p>MISB ST 1204 provides guidance in how to create a Binary Value Core ID as required here.
 *
 * <p>This standard recommends inserting the Core ID into a MPEG compressed Motion Imagery
 * elementary stream when accompanied by associated metadata. This is critical in the event the
 * Motion Imagery and its metadata become separated during post processing.
 *
 * <p>This standard does not apply to the MPEG-2 video format which does not support SEI messages. .
 */
package org.jmisb.api.klv.st2101;
