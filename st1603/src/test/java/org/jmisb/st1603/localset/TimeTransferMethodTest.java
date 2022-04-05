package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.TimeTransferMethod;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for TimeTransferMethod enumeration. */
public class TimeTransferMethodTest {

    @Test
    public void check0() {
        TimeTransferMethod uut = TimeTransferMethod.Unknown;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Unknown Time Transfer Method");
        assertEquals(uut.getDisplayableValue(), "Unknown Time Transfer Method");
        assertEquals(uut.getValue(), 0);
        assertEquals(uut, TimeTransferMethod.Unknown);
    }

    @Test
    public void check1() {
        TimeTransferMethod uut = TimeTransferMethod.GlobalPositioningSystem;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Global Positioning System (GPS) PPS");
        assertEquals(uut.getDisplayableValue(), "Global Positioning System (GPS) PPS");
        assertEquals(uut.getValue(), 1);
        assertEquals(uut, TimeTransferMethod.GlobalPositioningSystem);
    }

    @Test
    public void check2() {
        TimeTransferMethod uut = TimeTransferMethod.PrecisionTimeProtocolV1;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Precision Time Protocol (PTP) Version 1");
        assertEquals(uut.getDisplayableValue(), "Precision Time Protocol (PTP) Version 1");
        assertEquals(uut.getValue(), 2);
        assertEquals(uut, TimeTransferMethod.PrecisionTimeProtocolV1);
    }

    @Test
    public void check3() {
        TimeTransferMethod uut = TimeTransferMethod.PrecisionTimeProtocolV2;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Precision Time Protocol (PTP) Version 2");
        assertEquals(uut.getDisplayableValue(), "Precision Time Protocol (PTP) Version 2");
        assertEquals(uut.getValue(), 3);
        assertEquals(uut, TimeTransferMethod.PrecisionTimeProtocolV2);
    }

    @Test
    public void check4() {
        TimeTransferMethod uut = TimeTransferMethod.NetworkTimeProtocolV3;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Network Time Protocol (NTP) Version 3");
        assertEquals(uut.getDisplayableValue(), "Network Time Protocol (NTP) Version 3");
        assertEquals(uut.getValue(), 4);
        assertEquals(uut, TimeTransferMethod.NetworkTimeProtocolV3);
    }

    @Test
    public void check5() {
        TimeTransferMethod uut = TimeTransferMethod.NetworkTimeProtocolV4;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Network Time Protocol (NTP) Version 4");
        assertEquals(uut.getDisplayableValue(), "Network Time Protocol (NTP) Version 4");
        assertEquals(uut.getValue(), 5);
        assertEquals(uut, TimeTransferMethod.NetworkTimeProtocolV4);
    }

    @Test
    public void check6() {
        TimeTransferMethod uut = TimeTransferMethod.IRIG_A;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Inter-range Instrumentation Group (IRIG-A)");
        assertEquals(uut.getDisplayableValue(), "Inter-range Instrumentation Group (IRIG-A)");
        assertEquals(uut.getValue(), 6);
        assertEquals(uut, TimeTransferMethod.IRIG_A);
    }

    @Test
    public void check7() {
        TimeTransferMethod uut = TimeTransferMethod.IRIG_B;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Inter-range Instrumentation Group (IRIG-B)");
        assertEquals(uut.getDisplayableValue(), "Inter-range Instrumentation Group (IRIG-B)");
        assertEquals(uut.getValue(), 7);
        assertEquals(uut, TimeTransferMethod.IRIG_B);
    }

    @Test
    public void checkReserved8() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved8;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (8)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (8)");
        assertEquals(uut.getValue(), 8);
        assertEquals(uut, TimeTransferMethod.Reserved8);
    }

    @Test
    public void checkReserved9() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved9;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (9)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (9)");
        assertEquals(uut.getValue(), 9);
        assertEquals(uut, TimeTransferMethod.Reserved9);
    }

    @Test
    public void checkReserved10() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved10;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (10)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (10)");
        assertEquals(uut.getValue(), 10);
        assertEquals(uut, TimeTransferMethod.Reserved10);
    }

    @Test
    public void checkReserved11() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved11;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (11)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (11)");
        assertEquals(uut.getValue(), 11);
        assertEquals(uut, TimeTransferMethod.Reserved11);
    }

    @Test
    public void checkReserved12() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved12;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (12)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (12)");
        assertEquals(uut.getValue(), 12);
        assertEquals(uut, TimeTransferMethod.Reserved12);
    }

    @Test
    public void checkReserved13() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved13;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (13)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (13)");
        assertEquals(uut.getValue(), 13);
        assertEquals(uut, TimeTransferMethod.Reserved13);
    }

    @Test
    public void checkReserved14() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved14;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (14)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (14)");
        assertEquals(uut.getValue(), 14);
        assertEquals(uut, TimeTransferMethod.Reserved14);
    }

    @Test
    public void checkReserved15() {
        TimeTransferMethod uut = TimeTransferMethod.Reserved15;
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Reserved for future use (15)");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use (15)");
        assertEquals(uut.getValue(), 15);
        assertEquals(uut, TimeTransferMethod.Reserved15);
    }

    @Test
    public void checkLookup() {
        TimeTransferMethod uut = TimeTransferMethod.lookupValue(5);
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Network Time Protocol (NTP) Version 4");
        assertEquals(uut.getDisplayableValue(), "Network Time Protocol (NTP) Version 4");
        assertEquals(uut.getValue(), 5);
        assertEquals(uut, TimeTransferMethod.NetworkTimeProtocolV4);
    }

    @Test
    public void checkLookup0() {
        TimeTransferMethod uut = TimeTransferMethod.lookupValue(0);
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Unknown Time Transfer Method");
        assertEquals(uut.getDisplayableValue(), "Unknown Time Transfer Method");
        assertEquals(uut.getValue(), 0);
        assertEquals(uut, TimeTransferMethod.Unknown);
    }

    @Test
    public void checkLookupUnknown() {
        TimeTransferMethod uut = TimeTransferMethod.lookupValue(16);
        assertEquals(uut.getDisplayName(), "Time Transfer Method");
        assertEquals(uut.getMeaning(), "Unknown Time Transfer Method");
        assertEquals(uut.getDisplayableValue(), "Unknown Time Transfer Method");
        assertEquals(uut.getValue(), 0);
        assertEquals(uut, TimeTransferMethod.Unknown);
    }
}
