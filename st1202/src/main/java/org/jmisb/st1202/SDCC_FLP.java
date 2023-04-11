package org.jmisb.st1202;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st1010.SDCC;
import org.jmisb.st1010.SDCCParser;
import org.jmisb.st1010.SDCCSerialiser;

/**
 * Standard Deviation and Correlation Coefficient (SDCC) Floating Length Pack (FLP).
 *
 * <p>In many applications, the knowledge of the uncertainty of all estimated values is critical to
 * understand the performance of a system. Thus, it is desirable to provide a means to propagate the
 * uncertainty information of the transformation parameters. The Generalized Transformation LS
 * utilizes the format described in MISB ST 1010 for transmitting the standard deviation and
 * correlation coefficient information.
 */
public class SDCC_FLP implements IGeneralizedTransformationMetadataValue, INestedKlvValue {

    /** Underlying SDCC value. */
    private SDCC sdcc;

    /**
     * Construct from value.
     *
     * @param value the SDCC instance to copy values from
     */
    public SDCC_FLP(final SDCC value) {
        this.sdcc = new SDCC(value);
    }
    /**
     * Construct from encoded bytes.
     *
     * @param bytes encoded byte array, per ST 1010.
     * @throws KlvParseException if parsing fails
     */
    public SDCC_FLP(byte[] bytes) throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        this.sdcc = parser.parse(bytes);
    }

    @Override
    public byte[] getBytes() {
        SDCCSerialiser serialiser = new SDCCSerialiser();
        serialiser.setPreferMode1(true);
        serialiser.setSparseEnabled(false);
        return serialiser.encode(sdcc);
    }

    @Override
    public String getDisplayName() {
        return "Standard Deviation and Correlation Coefficients";
    }

    @Override
    public String getDisplayableValue() {
        // TODO: see if we can do better
        return "[SDCC]";
    }

    /**
     * Get the standard deviation and correlation coefficient object.
     *
     * @return copy of the ST 1010 SDCC object.
     */
    public SDCC getSDCC() {
        return new SDCC(sdcc);
    }

    /**
     * Get the standard deviation and correlation coefficient matrix.
     *
     * @return copy of the ST 1010 SDCC values matrix.
     */
    public double[][] getSDCCMatrix() {
        return sdcc.getValues();
    }

    @Override
    public IKlvValue getField(IKlvKey identifier) {
        return sdcc.getField(identifier);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return sdcc.getIdentifiers();
    }
}
