package org.jmisb.api.klv.st0903.vtarget;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vfeature.VFeatureLS;

/**
 * VFeature (ST0903 VTarget Pack Tag 103).
 * <p>
 * From ST0903:
 * <blockquote>
 * Data which describes the target or features of the target (shape, size,
 * dents, number of wheels, thermal signature, etc.). Descriptive information
 * can range from simple text for a label, to complex data sets containing
 * spectral or radiometric data. The definition of a set of elements to describe
 * target features can be a complex undertaking. Rather than create a unique
 * specification, the VFeature LS is based on ISO 19156 [10] and related
 * schemas.
 * <p>
 * ISO 19156 defines a conceptual schema for observations and for features for
 * sampling during observation. These artifacts support the exchange of
 * information describing the acts of observation and their results. It also
 * defines an observation as an act of measuring or otherwise determining the
 * value for properties of features of interest. Such an act may involve the use
 * of a method, sensor, instrument, human observation, algorithm, computation,
 * process, etc. to estimate the value of the property. An observation is
 * associated with a discrete instant or interval of time.
 * <p>
 * Values use a variety of scales including nominal, ordinal, ratio, interval,
 * spatial, and temporal. Combining primitive data types forms aggregate data
 * types with aggregate values, including vectors, tensors and images. A value
 * may be exact, or it may be an estimate, with a finite error. Observation
 * results may have many data types, including primitive types like category or
 * measure, but also more complex types such as time, location and geometry
 * </blockquote>
 */
public class VFeature implements IVmtiMetadataValue
{
    private static final Logger LOG = Logger.getLogger(VFeature.class.getName());

    private final VFeatureLS value;

    /**
     * Create from value.
     *
     * @param featureLocalSet the VFeature Local Set.
     */
    public VFeature(VFeatureLS featureLocalSet)
    {
        value = featureLocalSet;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VFeature LS
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VFeature(byte[] bytes) throws KlvParseException
    {
        value = new VFeatureLS(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        try {
            return value.getBytes();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getDisplayableValue()
    {
        return "[VFeature]";
    }

    @Override
    public final String getDisplayName()
    {
        return "Target Feature";
    }

    /**
     * Get the VFeatureLS.
     *
     * @return the feature local set.
     */
    public VFeatureLS getFeature()
    {
        return value;
    }
}
