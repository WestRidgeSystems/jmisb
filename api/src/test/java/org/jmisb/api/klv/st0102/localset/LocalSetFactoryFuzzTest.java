package org.jmisb.api.klv.st0102.localset;

import static org.testng.Assert.assertNotNull;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.core.klv.ArrayUtils;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class LocalSetFactoryFuzzTest {
    @Fuzz /* The args to this method will be generated automatically by JQF */
    public void checkCreateValue(SecurityMetadataKey tag, byte[] bytes) {
        try {
            System.out.println("checkCreateValue: " + ArrayUtils.toHexString(bytes, 16, true));
            ISecurityMetadataValue v = LocalSetFactory.createValue(tag, bytes);
            assertNotNull(v);
        } catch (IllegalArgumentException e) {
            // OK when we're fuzzing.
        }
    }
}
