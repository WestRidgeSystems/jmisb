package org.jmisb.api.klv.st1201;

import java.math.BigInteger;

/** DTO class to support JsonBasedTest. */
public class TestVector {

    public TestVector() {}

    public double a;
    public double b;
    public int L;
    public double sF;
    public double sR;
    public double Zoffset;
    public String x;
    public BigInteger forwardResult;
    public String forwardResultHex;
    public String reverseResult;
    public double delta;
}
