package com.bisnode.opa.testformats.opa;

import com.bisnode.opa.OpaTestResults;

import java.util.List;

/**
 * This class produces tests summary in the exact same format as {@code opa test -v} command.
 */
public class OpaVerboseSummary implements Summarizable {
    private static final String SEPARATOR = "--------------------------------------------------------------------------------";

    private final List<TestCase> testCases;

    /**
     * Creates a new instance of {@link OpaVerboseSummary}.
     * @param opaTestResults Results that should be summarized.
     * @return Instance of {@link OpaVerboseSummary} for passed results.
     */
    public static OpaVerboseSummary of(OpaTestResults opaTestResults) {
        return new OpaVerboseSummaryFactory(opaTestResults.getTestCases()).create();
    }

    OpaVerboseSummary(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    /**
     * Produces summary of {@link OpaTestResults} mimicking behavior
     * of {@code opa test -v} command.
     * @return Formatted test results summary.
     */
    @Override
    public String summary() {
        StringBuilder summary = new StringBuilder()
                .append("SUMMARY").append('\n')
                .append(SEPARATOR).append('\n');
        testCases.stream().map(TestCase::summary).map(this::withNewLine).forEach(summary::append);
        summary.append(SEPARATOR).append('\n');

        if (passedCount() > 0L) {
            summary.append(String.format("PASS: %d/%d\n", passedCount(), total()));
        }
        if (failedCount() > 0L) {
            summary.append(String.format("FAIL: %d/%d\n", failedCount(), total()));
        }
        if (errorCount() > 0L) {
            summary.append(String.format("ERROR: %d/%d\n", errorCount(), total()));
        }
        return summary.toString();
    }

    private String withNewLine(String testCaseSummary) {
        return testCaseSummary.concat("\n");
    }

    private long passedCount() {
        return testCases.stream().filter(TestCase::passed).count();
    }

    private long failedCount() {
        return testCases.stream().filter(TestCase::isFailure).count();
    }

    private long errorCount() {
        return testCases.stream().filter(TestCase::isError).count();
    }

    private int total() {
        return testCases.size();
    }

}
