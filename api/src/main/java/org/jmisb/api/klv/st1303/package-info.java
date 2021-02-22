/**
 * ST 1303: Multi-Dimensional Array Pack.
 *
 * <p>This standard describes a method for formatting multi-dimensional arrays of data in KLV (Key
 * Length Value). Multi-dimensional arrays store and organize related data. Applications may process
 * the array as a unit of data (i.e. matrices, etc.) or use them to organize a group of information.
 * This standard defines a KLV Pack construct to format a multi-dimensional array and the array
 * support information. The Multi-Dimensional Array Pack (MDAP) minimizes the bytes needed to
 * represent the data. Supporting information includes options for specifying methods of array
 * packing and element processing, such as using MISB ST 1201 (Floating Point to Integer Mapping)
 * for compression and Run-Length Encoding for unsigned integers.
 *
 * <p>In application, the Multi-Dimensional Array Pack defined in this standard requires further
 * context from an invoking document; that is, it is not a standalone construct. To provide
 * consistency, this standard defines a syntax for invoking this standard within MISB documents.
 */
package org.jmisb.api.klv.st1303;
