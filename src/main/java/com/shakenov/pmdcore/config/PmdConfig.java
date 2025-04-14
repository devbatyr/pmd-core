package com.shakenov.pmdcore.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for running a PMD analysis.
 * Use the builder to construct a valid and immutable config object.
 */
public class PmdConfig {
    private String code;
    private String fileName;
    private String rulesetPath;
    private String suppressionsPath;
    private String format = "text";
    private String minimumPriority;
    private String pmdPath = "pmd";
    private List<String> extraArgs = new ArrayList<>();

    public String getCode() { return code; }
    public String getFileName() { return fileName; }
    public String getRulesetPath() { return rulesetPath; }
    public String getSuppressionsPath() { return suppressionsPath; }
    public String getFormat() { return format; }
    public String getMinimumPriority() { return minimumPriority; }
    public String getPmdPath() { return pmdPath; }
    public List<String> getExtraArgs() { return extraArgs; }

    /**
     * Creates a new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PmdConfig}.
     */
    public static class Builder {
        private final PmdConfig config = new PmdConfig();

        /**
         * Sets the source code to analyze.
         */
        public Builder code(String code) {
            config.code = code;
            return this;
        }

        /**
         * Sets the logical file name for the code (used for temp file).
         */
        public Builder fileName(String fileName) {
            config.fileName = fileName;
            return this;
        }

        /**
         * Sets the path to the ruleset XML file (required).
         */
        public Builder rulesetPath(String path) {
            config.rulesetPath = path;
            return this;
        }

        /**
         * Sets the path to the suppressions XML file (optional).
         */
        public Builder suppressionsPath(String path) {
            config.suppressionsPath = path;
            return this;
        }

        /**
         * Sets the output format (default is "text").
         */
        public Builder format(String format) {
            config.format = format;
            return this;
        }

        /**
         * Sets the minimum priority level to include (optional).
         */
        public Builder minimumPriority(String priority) {
            config.minimumPriority = priority;
            return this;
        }

        /**
         * Sets the path to the PMD binary (default is "pmd").
         */
        public Builder pmdPath(String path) {
            config.pmdPath = path;
            return this;
        }

        /**
         * Adds a single extra argument to the PMD command.
         */
        public Builder extraArg(String arg) {
            config.extraArgs.add(arg);
            return this;
        }

        /**
         * Adds a list of extra arguments to the PMD command.
         */
        public Builder extraArgs(List<String> args) {
            config.extraArgs.addAll(args);
            return this;
        }


        /**
         * Builds and returns the config instance.
         * Ensures required fields are set and freezes extraArgs.
         */
        public PmdConfig build() {
            if (config.code == null || config.code.isBlank()) {
                throw new IllegalStateException("code is required for PMD analysis.");
            }
            if (config.fileName == null || config.fileName.isBlank()) {
                throw new IllegalStateException("fileName is required for PMD analysis.");
            }
            if (config.rulesetPath == null || config.rulesetPath.isBlank()) {
                throw new IllegalStateException("rulesetPath is required for PMD analysis.");
            }

            config.extraArgs = List.copyOf(config.extraArgs); // Make immutable

            return config;
        }
    }
}
