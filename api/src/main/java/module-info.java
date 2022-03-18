/**
 * Primary API for jmisb.
 *
 * <p>This module provides the implementation of the MISB standards.
 */
module org.jmisb.api {
    requires org.jmisb.core;
    requires org.slf4j;

    uses org.jmisb.api.klv.IMisbMessageFactory;

    // If this is updated, ensure src/main/resources/META-INF/services is updated
    provides org.jmisb.api.klv.IMisbMessageFactory with
            org.jmisb.api.klv.st0102.universalset.SecurityMetadataUniversalSetFactory,
            org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSetFactory,
            org.jmisb.api.klv.st0601.UasDatalinkMessageFactory,
            org.jmisb.api.klv.st0903.vtrack.VTrackLocalSetFactory,
            org.jmisb.api.klv.st1301.MiisLocalSetFactory,
            org.jmisb.api.klv.st1603.localset.TimeTransferLocalSetFactory,
            org.jmisb.api.klv.st1603.nanopack.NanoTimeTransferPackFactory;

    exports org.jmisb.api.common;
    exports org.jmisb.api.klv;
    exports org.jmisb.api.klv.st0102;
    exports org.jmisb.api.klv.st0102.localset;
    exports org.jmisb.api.klv.st0102.universalset;
    exports org.jmisb.api.klv.st0107;
    exports org.jmisb.api.klv.st0601;
    exports org.jmisb.api.klv.st0601.dto;
    exports org.jmisb.api.klv.st0603;
    exports org.jmisb.api.klv.st0604;
    exports org.jmisb.api.klv.st0805;
    exports org.jmisb.api.klv.st0806;
    exports org.jmisb.api.klv.st0806.poiaoi;
    exports org.jmisb.api.klv.st0806.userdefined;
    exports org.jmisb.api.klv.st0903;
    exports org.jmisb.api.klv.st0903.algorithm;
    exports org.jmisb.api.klv.st0903.ontology;
    exports org.jmisb.api.klv.st0903.shared;
    exports org.jmisb.api.klv.st0903.vchip;
    exports org.jmisb.api.klv.st0903.vfeature;
    exports org.jmisb.api.klv.st0903.vmask;
    exports org.jmisb.api.klv.st0903.vobject;
    exports org.jmisb.api.klv.st0903.vtarget;
    exports org.jmisb.api.klv.st0903.vtrack;
    exports org.jmisb.api.klv.st0903.vtracker;
    exports org.jmisb.api.klv.st1201;
    exports org.jmisb.api.klv.st1204;
    exports org.jmisb.api.klv.st1206;
    exports org.jmisb.api.klv.st1301;
    exports org.jmisb.api.klv.st1303;
    exports org.jmisb.api.klv.st1403;
    exports org.jmisb.api.klv.st1603.localset;
    exports org.jmisb.api.klv.st1603.nanopack;
    exports org.jmisb.api.klv.st1909;
}
