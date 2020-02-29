package org.jmisb.api.klv.st1204;

/**
 * Implementation of ST1204 Appendix B Check Value calculations.
 */
class CheckValue {

    static int[][] P_PERMUTATIONS;
    static int[][] Q_PERMUTATIONS;

    static int pMap(int i) {
        boolean a = (i & 0x8) == 0x8;
        boolean b = (i & 0x4) == 0x4;
        boolean c = (i & 0x2) == 0x2;
        boolean d = (i & 0x1) == 0x1;
        int res = 0;
        res += (a ? 0x1 : 0x0);
        res += (d ? 0x2 : 0x0);
        res += (c ? 0x4 : 0x0);
        res += ((a ^ b) ? 0x8 : 0x0);
        return res;
    }

    static int qMap(int i) {
        boolean a = (i & 0x8) == 0x8;
        boolean b = (i & 0x4) == 0x4;
        boolean c = (i & 0x2) == 0x2;
        boolean d = (i & 0x1) == 0x1;
        int res = 0;
        res += (c ? 0x1 : 0x0);
        res += (b ? 0x2 : 0x0);
        res += ((a ^ d) ? 0x4 : 0x0);
        res += (d ? 0x8 : 0x0);
        return res;
    }

    static {
        P_PERMUTATIONS = new int[15][16];
        for (int j = 0; j < 16; j++) {
            P_PERMUTATIONS[0][j] = j;
        }
        for (int i = 1; i < 15; i++) {
            for (int j = 0; j < 16; j++) {
                P_PERMUTATIONS[i][j] = pMap(P_PERMUTATIONS[i - 1][j]);
            }
        }

        Q_PERMUTATIONS = new int[15][16];
        for (int j = 0; j < 16; j++) {
            Q_PERMUTATIONS[0][j] = j;
        }
        for (int i = 1; i < 15; i++) {
            for (int j = 0; j < 16; j++) {
                Q_PERMUTATIONS[i][j] = qMap(Q_PERMUTATIONS[i - 1][j]);
            }
        }
    }

    static int checkHexString(String hex) {
        int pCheck = 0;
        int qCheck = 0;
        for (int x = 0; x < hex.length(); ++x) {
            int i = x + 1;
            String charI = hex.substring(x, i);
            int hexValue = Integer.parseUnsignedInt(charI, 16);
            int pLookup = P_PERMUTATIONS[i % 15][hexValue];
            pCheck = pCheck ^ pLookup;
            int qLookup = Q_PERMUTATIONS[i % 15][hexValue];
            qCheck = qCheck ^ qLookup;
        }
        int checkValue = (pCheck << 4) + qCheck;
        return checkValue;
    }
    
    static int checkString(String s) {
        String hex = s.replaceAll("[^0-9a-fA-F]", "");
        return checkHexString(hex);
    }
}
