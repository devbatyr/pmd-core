package com.shakenov.pmdcore.model;

import java.util.Objects;

/**
 * Represents a single PMD rule violation.
 */
public class PmdViolation {
    private final String rule;
    private final String description;
    private final int line;
    private final String documentationUrl;

    public PmdViolation(String rule, String description, int line, String documentationUrl) {
        this.rule = rule;
        this.description = description;
        this.line = line;
        this.documentationUrl = documentationUrl;
    }

    public String getRule() {
        return rule;
    }

    public String getDescription() {
        return description;
    }

    public int getLine() {
        return line;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PmdViolation that)) return false;
        return line == that.line &&
                Objects.equals(rule, that.rule) &&
                Objects.equals(description, that.description) &&
                Objects.equals(documentationUrl, that.documentationUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, description, line, documentationUrl);
    }
}