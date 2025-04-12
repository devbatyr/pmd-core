package kz.shakenov.pmdcore.service;

import kz.shakenov.pmdcore.config.PmdConfig;
import kz.shakenov.pmdcore.exception.PmdExecutionException;
import kz.shakenov.pmdcore.model.PmdResponse;
import kz.shakenov.pmdcore.model.PmdViolation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class to run PMD static code analysis using external PMD CLI.
 */
public class PmdService {

    /**
     * Executes PMD analysis using the provided configuration.
     *
     * @param config the PMD configuration
     * @return formatted analysis result in Markdown
     */
    public PmdResponse runPmd(PmdConfig config) throws PmdExecutionException {
        try {
            Path tempFile = createTempFile(config);
            List<String> command = buildCommand(config, tempFile);

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);

            Process process = builder.start();
            StringBuilder output = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            process.waitFor();
            Files.delete(tempFile);

            return parseResponse(output.toString(), config.getFileName());

        } catch (Exception e) {
            throw new PmdExecutionException("PMD execution failed", e);
        }
    }

    private Path createTempFile(PmdConfig config) throws Exception {
        Path file = Files.createTempFile(config.getFileName().replace("/", "_"), ".java");
        Files.writeString(file, config.getCode());
        return file;
    }

    private List<String> buildCommand(PmdConfig config, Path tempFile) {
        List<String> command = new ArrayList<>();
        command.add(config.getPmdPath());
        command.add("check");
        command.add("--dir");
        command.add(tempFile.toString());
        command.add("--rulesets");
        command.add(config.getRulesetPath());

        if (config.getSuppressionsPath() != null) {
            command.add("--suppressions");
            command.add(config.getSuppressionsPath());
        }

        if (config.getFormat() != null) {
            command.add("--format");
            command.add(config.getFormat());
        }

        if (config.getMinimumPriority() != null) {
            command.add("--minimum-priority");
            command.add(config.getMinimumPriority());
        }

        command.addAll(config.getExtraArgs());
        return command;
    }

    /**
     * Parses PMD output into structured {@link PmdResponse} with violations.
     *
     * @param pmdOutput raw output from PMD
     * @param fileName  name of the analyzed file
     * @return parsed {@link PmdResponse}
     */
    private PmdResponse parseResponse(String pmdOutput, String fileName) {
        List<PmdViolation> violations = new ArrayList<>();

        Pattern pattern = Pattern.compile("^(.*):(\\d+):\\s+(\\w+):\\s+(.*)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(pmdOutput);

        StringBuilder formatted = new StringBuilder();
        formatted.append("🚨 Violations in file `").append(fileName).append("`:\n\n");

        while (matcher.find()) {
            int line = Integer.parseInt(matcher.group(2));
            String rule = matcher.group(3).trim();
            String description = matcher.group(4).trim();

            String ruleLink = rule.toLowerCase().replaceAll("[^a-z0-9]", "");
            String docsUrl = "https://docs.pmd-code.org/pmd-doc-7.7.0/pmd_rules_java.html#" + ruleLink;

            violations.add(new PmdViolation(rule, description, line, docsUrl));

            formatted.append("🔸 **").append(rule).append("** at line ").append(line).append("\n");
            formatted.append("_Description:_ ").append(description).append("\n");
            formatted.append("[Documentation](").append(docsUrl).append(")\n\n");
            formatted.append("---\n\n");
        }

        String formattedOutput = violations.isEmpty()
                ? "✅ PMD: No violations found."
                : formatted.toString();

        return new PmdResponse(pmdOutput, formattedOutput, violations);
    }
}