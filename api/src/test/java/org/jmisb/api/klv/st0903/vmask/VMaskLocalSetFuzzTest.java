package org.jmisb.api.klv.st0903.vmask;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.ArrayUtils;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class VMaskLocalSetFuzzTest {
    @Fuzz /* The args to this method will be generated automatically by JQF */
    public void checkCreateValue(VMaskMetadataKey tag, byte[] bytes) {
        try {
            System.out.println("checkCreateValue: " + ArrayUtils.toHexString(bytes, 16, true));
            IVmtiMetadataValue v = VMaskLS.createValue(tag, bytes);
            if (tag == VMaskMetadataKey.Undefined) {
                assertNull(v);
            } else {
                assertNotNull(v);
            }
        } catch (KlvParseException | IllegalArgumentException e) {
            // OK when we're fuzzing.
        }
    }
}
