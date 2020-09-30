package org.jmisb.examples.parserplugin.timemessage;

import org.jmisb.api.klv.UniversalLabel;

/** Constants used in the Time Message example. */
public class TimeMessageConstants {
    /**
     * The Universal Label used in the time message.
     *
     * <p>In this case, its the KLV Universal Label (UL) for Precision Time Stamp from SMPTE RP210.
     * Your code may or may not be formally published.
     */
    public static final UniversalLabel TIME_STAMP_UL =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x03, 0x07, 0x02, 0x01, 0x01,
                        0x01, 0x05, 0x00, 0x00
                    });
}
