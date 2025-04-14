package com.shakenov.pmdcore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a PMD analysis.
 */
public class PmdResponse {

    /**
     * The original raw output returned by PMD.
     */
    private final String rawOutput;

    /**
     * The formatted output, usually in Markdown.
     */
    private final String formattedOutput;

    /**
     * A list of individual violations found by PMD.
     */
    private final List<PmdViolation> violations;

    public PmdResponse(String rawOutput, String formattedOutput, List<PmdViolation> violations) {
        this.rawOutput = rawOutput;
        this.formattedOutput = formattedOutput;
        this.violations = violations != null ? new ArrayList<>(violations) : Collections.emptyList();
    }

    public String getRawOutput() {
        return rawOutput;
    }

    public String getFormattedOutput() {
        return formattedOutput;
    }

    public List<PmdViolation> getViolations() {
        return Collections.unmodifiableList(violations);
    }

    /**
     * Returns true if the analysis found at least one violation.
     */
    public boolean hasViolations() {
        return !violations.isEmpty();
    }
}