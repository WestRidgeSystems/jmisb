/**
 * ST 1010: Generalized Standard Deviation and Correlation Coefficient Metadata.
 *
 * <p>This standard (ST) defines a bit-efficient method for transmitting standard deviation and
 * correlation coefficient data.
 *
 * <p>In support of this method, a Standard Deviation and Correlation Coefficient Floating Length
 * Pack (SDCC-FLP) construct is defined. The construct leverages the symmetry of the
 * variance-covariance matrix and the fixed data range of the correlation coefficients to reduce the
 * number of bytes transmitted, in effect compressing the data. This method is, therefore, not
 * extendable to more generic matrix cases.
 *
 * <p>This ST is dependent on context from an invoking standard (or other document), called a Parent
 * Document, which provides a list of values (random variables) that can have corresponding standard
 * deviation and correlation coefficients; this list of random variables is called the Source List.
 */
package org.jmisb.st1010;
