/**
 * MISB ST 1202.2 Generalized Transformation Parameters.
 *
 * <p>This standard describes a generalized method of transforming two-dimensional data (or points)
 * from one coordinate system into a second two-dimensional coordinate system. This Generalized
 * Transformation may be used for various image-to-image transformations such as an affine
 * transformation by simply equating some parameters to be equal to zero. In addition, this
 * Generalized Transformation may describe some homographic-like transformations.
 *
 * <p>This standard defines three items:
 *
 * <ul>
 *   <li>The different methods of implementation and constraints that need to be enforced to
 *       maintain certain transformation relationships.
 *   <li>The mandatory method of uncertainty propagation to be implemented on systems where
 *       uncertainty information is needed.
 *   <li>The KLV Local Set (LS) that represents all the parameters for the Generalized
 *       Transformation.
 * </ul>
 */
@SuppressWarnings("module") // That is not a version number - its a document number.
module org.jmisb.st1202 {
    requires org.jmisb.api;
    requires org.jmisb.st1010;
    requires org.slf4j;

    exports org.jmisb.st1202;
}
