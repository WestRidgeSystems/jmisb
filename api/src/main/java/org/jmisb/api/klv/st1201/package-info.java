/**
 * ST 1201: Floating Point to Integer Mapping.
 *
 * <p>This standard describes the method for mapping floating-point values to integer values and the
 * reverse, mapping integer values back to their original floating-point value to within an
 * acceptable precision. There are many ways of optimizing the transmission of floating-point values
 * from one system to another; the purpose of this standard is to provide a single method for use by
 * all MISB metadata standards. This standard supports all floating-point ranges and valid
 * precisions. This standard provides a method for a forward and reverse linear mapping of a
 * specified range of floating-point values to a specified integer range of values based on the
 * number of bytes desired for the integer value. Additionally, it provides a set of special values
 * to transmit non-numerical “signals” to a receiving system. This standard is dependent on context
 * from an invoking standard (or another document), called a Parent Document.
 */
package org.jmisb.api.klv.st1201;
