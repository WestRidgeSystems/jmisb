package org.jmisb.api.klv.st1403;

/** Results of validation of one part (or sub-part) of a requirement. */
public class ValidationResult {
    private Validity validity;
    private String traceability;
    private String description;

    /**
     * Constructor.
     *
     * @param validity whether the item is considered to have complied or not.
     */
    public ValidationResult(Validity validity) {
        this.validity = validity;
    }

    /**
     * Get the validity status for this result.
     *
     * @return whether the item is considered to have complied or not.
     */
    public Validity getValidity() {
        return validity;
    }

    /**
     * Get the traceability reference.
     *
     * <p>This is the EARS reference, or something else that represents where the requirement came
     * from.
     *
     * @return short text describing the source of the requirement.
     */
    public String getTraceability() {
        return traceability;
    }

    /**
     * Set the traceability reference.
     *
     * <p>This is the EARS reference, or something else that represents where the requirement came
     * from.
     *
     * @param traceability short text describing the source of the requirement.
     */
    public void setTraceability(String traceability) {
        this.traceability = traceability;
    }

    /**
     * Get the description of the result.
     *
     * @return the text description for result.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the result.
     *
     * @param description human readable description for result.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
