#!/usr/bin/env python3
import sys
import os
import time
import argparse
import math
import json


class Encoder:
    def __init__(self, verboseOutput=False):
        self.verbose = verboseOutput

    def startingPointA(self, a, b, g):
        if self.verbose:
            print("Calculating IMAPA for %f, %f, %f" % (a, b, g))
        assert a < b
        assert g < (b - a)
        Lbits = math.ceil(math.log(b-a, 2)) - math.floor(math.log(g, 2)) + 1
        if self.verbose:
            print("\tLbits: %i" % Lbits)
        self.L = math.ceil(Lbits/8)
        if self.verbose:
            print("\tL: %i" % self.L)
        return (Lbits, self.L)

    def startingPointB(self, a, b, L):
        if self.verbose:
            print("Calculating IMAPB for %f, %f, %i" % (a, b, L))
        assert a < b
        self.bPow = math.ceil(math.log(b-a, 2))
        if self.verbose:
            print("\tbPow: %i" % self.bPow)
        self.dPow = 8 * L - 1
        if self.verbose:
            print("\tdPow: %i" % self.dPow)
        self.sF = math.pow(2, self.dPow - self.bPow)
        if self.verbose:
            print("\tsF: %f" % self.sF)
        self.sR = math.pow(2, self.bPow - self.dPow)
        if self.verbose:
            print("\tsR: %f" % self.sR)
        self.Zoffset = 0.0
        if (a < 0 and b > 0):
            self.Zoffset = self.sF * a - math.floor(self.sF * a)
        if self.verbose:
            print("\tZoffset: %f" % self.Zoffset)
        return (self.sF, self.sR, self.Zoffset)

    def forwardMapping(self, sF, a, Zoffset, x, L):
        if self.verbose:
            print("calculating forward mapping for %f, %f, %f, %f" %
                  (sF, a, Zoffset, x))
        if x == math.inf:
            y = 0b11001000 << (8 * (L - 1))
            if self.verbose:
                print("\ty: +Inf, %s" % hex(y))
            return y
        if x == -math.inf:
            y = 0b11101000 << (8 * (L - 1))
            if self.verbose:
                print("\ty: -Inf, %s" % hex(y))
            return y
        if not math.isfinite(x):
            assert False, "forwardMapping doesn't work for " + str(x)
        y = math.trunc(sF * (x-a) + Zoffset)
        if self.verbose:
            print("\ty: %i, %s" % (y, hex(y)))
        return y

    def getForwardMappingForQNaN(self, L):
        if self.verbose:
            print("getting +QNaN special case for length %i" % L)
        y = 0b11010000 << (8 * (L - 1))
        if self.verbose:
            print("\ty: +QNaN, %s" % hex(y))
        return y

    def getForwardMappingForMinusQNaN(self, L):
        if self.verbose:
            print("getting -QNaN special case for length %i" % L)
        y = 0b11110000 << (8 * (L - 1))
        if self.verbose:
            print("\ty: -QNaN, %s" % hex(y))
        return y

    def reverseMapping(self, sR, a, Zoffset, y, L):
        if self.verbose:
            print("calculating reverse mapping for %f, %f, %f, %f" %
                  (sR, a, Zoffset, y))
        msb = y >> (8 * (L - 1))
        if (msb & 0x80):
            allOtherBytesAreZero = True
            for i in range(L-1):
                byte = (y >> (8 * i)) & 0xFF
                if not byte == 0:
                    allOtherBytesAreZero = False
            if not allOtherBytesAreZero:
                print("Special case")
                # TODO: process
                return 0
        # TODO: handle cases where y is a special value
        x = sR * (y - Zoffset) + a
        if self.verbose:
            print("\tx: %f" % x)
        return x


def doArgs(argList, name):
    parser = argparse.ArgumentParser(description=name)

    parser.add_argument('-v', "--verbose", action="store_true",
                        help="Enable verbose debugging", default=False)
    return parser.parse_args(argList)


def main():
    progName = "ST1204"
    args = doArgs(sys.argv[1:], progName)

    verbose = args.verbose
    if verbose:
        print("Starting %s" % (progName))
    startTime = float(time.time())
    validateExamples(verbose)

    generateData(verbose)

    if verbose:
        print("Finished in %0.4f seconds" % (time.time() - startTime))
    return


def validateExamples(verbose):
    doExample3(verbose)
    doExample4(verbose)
    do0601Tag96(verbose)
    do0601Tag103(verbose)
    do0601Tag109(verbose)
    do0601Tag112(verbose)
    do0601Tag113(verbose)
    do0601Tag114(verbose)
    do0601Tag117(verbose)
    do0601Tag118(verbose)
    do0601Tag119(verbose)
    do0601Tag120(verbose)
    # TODO: Tag 130
    do0601Tag132(verbose)
    do0601Tag134(verbose)


def doExample3(verbose):
    if verbose:
        print("Example 3")
    encoder = Encoder(verbose)
    a = -900.0
    b = 19000.0
    g = 0.5
    (Lbits, L) = encoder.startingPointA(a, b, g)
    assert Lbits == 17
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    for (x, y) in [(-900.0, 0), (0.0, 230400), (10.0, 232960)]:
        Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
        assert y == y
        X = encoder.reverseMapping(sR, a, Zoffset, y, L)
        assert x == X


def doExample4(verbose):
    if verbose:
        print("Example 4")
    encoder = Encoder(verbose)
    a = 0.1
    b = 0.9
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    for (x, y) in [(0.1, 0), (0.5, 13107), (0.9, 26214)]:
        Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
        assert y == Y
        X = encoder.reverseMapping(sR, a, Zoffset, y, L)
        assert math.fabs(x - X) < 0.0001


def do0601Tag96(verbose):
    if verbose:
        print("ST0601 Tag 96")
    encoder = Encoder(verbose)
    a = 0.0
    b = 1500000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 13898.5463
    y = int("0x00D92A", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.25


def do0601Tag103(verbose):
    if verbose:
        print("ST0601 Tag 103")
    encoder = Encoder(verbose)
    a = -900.0
    b = 40000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 23456.24
    y = int("0x2F921E", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.007825


def do0601Tag109(verbose):
    if verbose:
        print("ST0601 Tag 109")
    encoder = Encoder(verbose)
    a = 0.0
    b = 21000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 1.625
    y = int("0x0001A0", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.00039


def do0601Tag112(verbose):
    if verbose:
        print("ST0601 Tag 112")
    encoder = Encoder(verbose)
    a = 0.0
    b = 360.0
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 125.0
    y = int("0x1F40", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.016625


def do0601Tag113(verbose):
    if verbose:
        print("ST0601 Tag 113")
    encoder = Encoder(verbose)
    a = -900.0
    b = 40000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 2150.0
    y = int("0x05F500", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.07


def do0601Tag114(verbose):
    if verbose:
        print("ST0601 Tag 114")
    encoder = Encoder(verbose)
    a = -900.0
    b = 40000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 2154.5
    y = int("0x05F740", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.07


def do0601Tag117(verbose):
    if verbose:
        print("ST0601 Tag 117")
    encoder = Encoder(verbose)
    a = -1000.0
    b = 1000.0
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 1.0
    y = int("0x3E90", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.0625


def do0601Tag118(verbose):
    if verbose:
        print("ST0601 Tag 118")
    encoder = Encoder(verbose)
    a = -1000.0
    b = 1000.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 0.004176
    y = int("0x3E8011", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.000244


def do0601Tag119(verbose):
    if verbose:
        print("ST0601 Tag 119")
    encoder = Encoder(verbose)
    a = -1000.0
    b = 1000.0
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = -50.0
    y = int("0x3B60", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.0625


def do0601Tag120(verbose):
    if verbose:
        print("ST0601 Tag 120")
    encoder = Encoder(verbose)
    a = 0
    b = 100.0
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 72.0
    y = int("0x4800", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.004


def do0601Tag132(verbose):
    if verbose:
        print("ST0601 Tag 132")
    encoder = Encoder(verbose)
    a = 1
    b = 99999.0
    L = 3
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 2400
    y = int("0x0257C0", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.015625


def do0601Tag134(verbose):
    if verbose:
        print("ST0601 Tag 134")
    encoder = Encoder(verbose)
    a = 0
    b = 100.0
    L = 2
    (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
    x = 55.0
    y = int("0x3700", 16)
    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
    assert y == Y
    X = encoder.reverseMapping(sR, a, Zoffset, y, L)
    assert math.fabs(x - X) < 0.0039


def generateData(verbose):
    cases = []
    if verbose:
        print("Generating data")
    encoder = Encoder(verbose)
    for a in [-10000.0, -100.0, -1.0, 0.0, 0.1, 1.0, 1.001, 1500000.0]:
        for b in [-100.0, -1.0, 0.0, 0.1, 1.0, 1.001, 1500000.0, 1500000.1]:
            if not a < b:
                continue
            for L in [1, 2, 3, 4, 5, 6, 7, 8]:
                (sF, sR, Zoffset) = encoder.startingPointB(a, b, L)
                testInputs = [a, b, (a+b)/2]
                if a < 0 and b > 0 and (not 0.0 in testInputs):
                    testInputs.append(0.0)
                for x in testInputs:
                    Y = encoder.forwardMapping(sF, a, Zoffset, x, L)
                    Yhex = "{0:#0{1}x}".format(Y, L*2+2)
                    X = encoder.reverseMapping(sR, a, Zoffset, Y, L)
                    delta = math.fabs(x - X)
                    if verbose:
                        print("Delta:", delta)
                    ref = {'a': a, 'b': b, 'L': L, 'sF': sF, 'sR': sR, 'Zoffset': Zoffset,
                           'x': x, "forwardResult": Y, "forwardResultHex": Yhex, "reverseResult": X, "delta": delta}
                    cases.append(ref)
                Y = encoder.forwardMapping(sF, a, Zoffset, math.inf, L)
                Yhex = "{0:#0{1}x}".format(Y, L*2+2)
                ref = {'a': a, 'b': b, 'L': L, 'sF': sF, 'sR': sR, 'Zoffset': Zoffset,
                       'x': "Infinity", "forwardResult": Y, "forwardResultHex": Yhex, "reverseResult": "Infinity", "delta": 0}
                cases.append(ref)
                Y = encoder.forwardMapping(sF, a, Zoffset, -math.inf, L)
                Yhex = "{0:#0{1}x}".format(Y, L*2+2)
                ref = {'a': a, 'b': b, 'L': L, 'sF': sF, 'sR': sR, 'Zoffset': Zoffset,
                       'x': "-Infinity", "forwardResult": Y, "forwardResultHex": Yhex, "reverseResult": "-Infinity", "delta": 0}
                cases.append(ref)
                Y = encoder.getForwardMappingForQNaN(L)
                Yhex = "{0:#0{1}x}".format(Y, L*2+2)
                msb = Y >> (8 * (L - 1))
                assert msb == 0b11010000
                for i in range(0, L-1):
                    byte = (Y >> (8 * i)) & 0xFF
                    assert byte == 0
                ref = {'a': a, 'b': b, 'L': L, 'sF': sF, 'sR': sR, 'Zoffset': Zoffset,
                       'x': "+QNaN", "forwardResult": Y, "forwardResultHex": Yhex, "reverseResult": "+QNaN", "delta": 0}
                cases.append(ref)
                Y = encoder.getForwardMappingForMinusQNaN(L)
                Yhex = "0x%x" % Y
                msb = Y >> (8 * (L - 1))
                assert msb == 0b11110000
                for i in range(0, L-1):
                    byte = (Y >> (8 * i)) & 0xFF
                    assert byte == 0
                ref = {'a': a, 'b': b, 'L': L, 'sF': sF, 'sR': sR, 'Zoffset': Zoffset,
                       'x': "-QNaN", "forwardResult": Y, "forwardResultHex": Yhex, "reverseResult": "-QNaN", "delta": 0}
                cases.append(ref)
    print(json.dumps(cases, sort_keys=True, indent=4, separators=(',', ': ')))


if __name__ == '__main__':
    #sys.argv = ["programName.py","--input","test.txt","--output","tmp/test.txt"]
    main()
