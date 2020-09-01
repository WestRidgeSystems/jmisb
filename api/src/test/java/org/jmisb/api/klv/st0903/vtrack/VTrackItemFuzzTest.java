package org.jmisb.api.klv.st0903.vtrack;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.core.klv.ArrayUtils;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class VTrackItemFuzzTest {
    @Fuzz /* The args to this method will be generated automatically by JQF */
    public void checkCreateValue(
            VTrackItemMetadataKey tag, byte[] bytes, EncodingMode encodingMode) {
        try {
            System.out.println("checkCreateValue: " + ArrayUtils.toHexString(bytes, 16, true));
            IVTrackItemMetadataValue v = VTrackItem.createValue(tag, bytes, encodingMode);
            if (tag == VTrackItemMetadataKey.Undefined) {
                assertNull(v);
            } else {
                assertNotNull(v);
            }
        } catch (KlvParseException | IllegalArgumentException e) {
            // OK when we're fuzzing.
        }
    }
}
