package org.jmisb.st0903;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.st0903.shared.EncodingMode;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class VmtiLocalSetFuzzTest {
    @Fuzz /* The args to this method will be generated automatically by JQF */
    public void checkCreateValue(VmtiMetadataKey tag, byte[] bytes, EncodingMode encodingMode) {
        try {
            System.out.println("checkCreateValue: " + ArrayUtils.toHexString(bytes, 16, true));
            IVmtiMetadataValue v = VmtiLocalSet.createValue(tag, bytes, encodingMode);
            if ((tag == VmtiMetadataKey.Undefined) || (tag == VmtiMetadataKey.Checksum)) {
                assertNull(v);
            } else {
                assertNotNull(v);
            }
        } catch (KlvParseException | IllegalArgumentException e) {
            // OK when we're fuzzing.
        }
    }
}
