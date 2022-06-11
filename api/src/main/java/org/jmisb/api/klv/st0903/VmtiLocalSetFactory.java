package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/**
 * VMTI Local Set Factory (ST 0903).
 *
 * <p>From ST:
 *
 * <blockquote>
 * This standard defines Local Sets for VMTI and Track Metadata. These Local Sets may be embedded within a MISB ST0601LS,
 * or they may stand alone.  The latter permits VMTI and Track Metadata to be provided independent of Motion Imagery essence.
 * This is useful in constrained bandwidth environments, where the Motion Imagery may be omitted in favor of the VMTI data.
 * </blockquote>
 */
public class VmtiLocalSetFactory implements IMisbMessageFactory {

    /**
     * Create from bytes.
     *
     * @param bytes the VMTI byte data
     */
    @Override
    public VmtiLocalSet create(byte[] bytes) throws KlvParseException {
        return new VmtiLocalSet(bytes);
    }
}
