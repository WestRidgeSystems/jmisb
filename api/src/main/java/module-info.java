module org.jmisb.api {
    requires org.jmisb.core;
    requires org.slf4j;
    // TODO: refactor to avoid needing this
    requires java.desktop;

    exports org.jmisb.api.common;
    exports org.jmisb.api.klv;
    exports org.jmisb.api.klv.eg0104;
    exports org.jmisb.api.klv.st0102;
    exports org.jmisb.api.klv.st0102.localset;
    exports org.jmisb.api.klv.st0102.universalset;
    exports org.jmisb.api.klv.st0601;
    exports org.jmisb.api.klv.st0601.dto;
    exports org.jmisb.api.klv.st0602;
    exports org.jmisb.api.klv.st0603;
    exports org.jmisb.api.klv.st0604;
    exports org.jmisb.api.klv.st0805;
    exports org.jmisb.api.klv.st0806;
    exports org.jmisb.api.klv.st0806.poiaoi;
    exports org.jmisb.api.klv.st0806.userdefined;
    exports org.jmisb.api.klv.st0808;
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
    exports org.jmisb.api.klv.st1108;
    exports org.jmisb.api.klv.st1201;
    exports org.jmisb.api.klv.st1204;
    exports org.jmisb.api.klv.st1206;
    exports org.jmisb.api.klv.st1303;
    exports org.jmisb.api.klv.st1403;
    exports org.jmisb.api.klv.st1902;
    exports org.jmisb.api.klv.st1903;
    exports org.jmisb.api.klv.st1904;
    exports org.jmisb.api.klv.st1905;
    exports org.jmisb.api.klv.st1906;
    exports org.jmisb.api.klv.st1907;
    exports org.jmisb.api.klv.st1908;
    exports org.jmisb.api.klv.st1909;
}
