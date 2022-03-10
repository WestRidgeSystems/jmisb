package org.jmisb.api.klv.st0602;

import org.jmisb.api.klv.UniversalLabel;

/** Constants used by ST 0602. */
public class AnnotationMetadataConstants {

    private AnnotationMetadataConstants() {}

    /** Locally Unique Identifier Universal Label. */
    public static final UniversalLabel locallyUniqueIdentifierUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x01, 0x03, 0x03, 0x01,
                        0x00, 0x00, 0x00, 0x00
                    });

    /** Event indication Universal Label. */
    public static final UniversalLabel eventIndicationUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x05, 0x01, 0x01, 0x02,
                        0x00, 0x00, 0x00, 0x00
                    });

    /** Media Description Universal Label. */
    public static final UniversalLabel mediaDescriptionUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x02, 0x01, 0x06,
                        0x03, 0x00, 0x00, 0x00
                    });

    /** MIME Media Type Universal Label. */
    public static final UniversalLabel mimeMediaTypeUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07, 0x04, 0x09, 0x02, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    });

    /** MIME data Universal Label. */
    public static final UniversalLabel mimeDataUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x02, 0x05,
                        0x01, 0x00, 0x00, 0x00
                    });

    /** Modification History Universal Label. */
    public static final UniversalLabel modificationHistoryUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x02, 0x05,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** X view port position Universal Label. */
    public static final UniversalLabel xViewPortPositionInPixelsUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x07, 0x01, 0x02, 0x03,
                        0x01, 0x00, 0x00, 0x00
                    });
    /** Y view port position Universal Label. */
    public static final UniversalLabel yViewPortPositionInPixelsUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x07, 0x01, 0x02, 0x03,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Annotation Source Universal Label. */
    public static final UniversalLabel annotationSourceUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x02, 0x05,
                        0x03, 0x00, 0x00, 0x00
                    });

    /** Z-Order Universal Label. */
    public static final UniversalLabel zOrderUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x02, 0x05,
                        0x06, 0x00, 0x00, 0x00
                    });
}
