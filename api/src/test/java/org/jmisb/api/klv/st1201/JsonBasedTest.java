package org.jmisb.api.klv.st1201;

import static org.testng.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.testng.annotations.Test;

/** Tests using test vectors in JSON file. */
public class JsonBasedTest {

    public JsonBasedTest() {}

    @Test
    public void doTest() throws IOException {
        try (InputStream in =
                Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("org/jmisb/api/klv/st1201/st1201tests.json")) {
            ObjectMapper mapper = new ObjectMapper();
            TestVector[] testVectors = mapper.readValue(in, TestVector[].class);
            for (TestVector testVector : testVectors) {
                runForwardTest(testVector);
                runReverseTest(testVector);
            }
        }
    }

    private void runForwardTest(TestVector testVector) {
        // System.out.println("forward: " + testVector.a + ", " + testVector.b + ", " +
        // testVector.L);
        FpEncoder encoder = new FpEncoder(testVector.a, testVector.b, testVector.L);
        byte[] expected = parseHexString(testVector.forwardResultHex);
        try {
            Double x = Double.parseDouble(testVector.x);
            byte[] encoded = encoder.encode(x);
            assertEquals(encoded, expected);
        } catch (NumberFormatException nfe) {
            if ("+QNaN".equals(testVector.x)) {
                byte[] encoded = encoder.encode(Double.NaN);
                assertEquals(encoded, expected);
            } else if ("-QNaN".equals(testVector.x)) {
                // TODO: see https://github.com/WestRidgeSystems/jmisb/issues/161
            } else {
                throw new IllegalArgumentException("Need to parse: " + testVector.x);
            }
        }
    }

    private void runReverseTest(TestVector testVector) {
        //  System.out.println("reverse: " + testVector.a + ", " + testVector.b + ", " +
        // testVector.L);
        FpEncoder encoder = new FpEncoder(testVector.a, testVector.b, testVector.L);
        byte[] input = parseHexString(testVector.forwardResultHex);
        try {
            Double expected = Double.parseDouble(testVector.x);
            Double encoded = encoder.decode(input);
            assertEquals(encoded, expected, testVector.delta);
        } catch (NumberFormatException nfe) {
            if ("+QNaN".equals(testVector.x)) {
                double encoded = encoder.decode(input);
                assertEquals(encoded, Double.NaN);
            } else if ("-QNaN".equals(testVector.x)) {
                // TODO: see https://github.com/WestRidgeSystems/jmisb/issues/161
            } else {
                throw new IllegalArgumentException("Need to parse: " + testVector.x);
            }
        }
    }

    private byte[] parseHexString(String s) {
        if (s.startsWith("0x")) {
            return parseHexStringNoPrefix(s.substring("0x".length()));
        } else {
            return parseHexStringNoPrefix(s);
        }
    }

    private byte[] parseHexStringNoPrefix(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte)
                            ((Character.digit(s.charAt(i), 16) << 4)
                                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
