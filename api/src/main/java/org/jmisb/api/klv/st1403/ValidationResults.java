package org.jmisb.api.klv.st1403;

import java.util.ArrayList;
import java.util.List;

/**
 * The results of validation process.
 *
 * <p>This is primarily a container class.
 */
public class ValidationResults {

    private final List<ValidationResult> validationResults = new ArrayList<>();

    /**
     * Add multiple results to this result.
     *
     * @param results the results to add.
     */
    public void addResults(List<ValidationResult> results) {
        validationResults.addAll(results);
    }

    /**
     * Check if the results show conformance.
     *
     * @return true if every requirement was met, otherwise false (one or more non-conformances).
     */
    public boolean isConformant() {
        boolean isConformant = true;
        for (ValidationResult result : validationResults) {
            if (result.getValidity().equals(Validity.DoesNotConform)) {
                isConformant = false;
            }
        }
        return isConformant;
    }

    /**
     * Get the non-conformance parts of the result.
     *
     * @return list containing only the failed (Does not conform) items.
     */
    public List<ValidationResult> getNonConformances() {
        List<ValidationResult> nonConformances = new ArrayList<>();
        validationResults.stream()
                .filter(result -> result.getValidity().equals(Validity.DoesNotConform))
                .forEach(
                        (ValidationResult result) -> {
                            nonConformances.add(result);
                        });
        return nonConformances;
    }
}
