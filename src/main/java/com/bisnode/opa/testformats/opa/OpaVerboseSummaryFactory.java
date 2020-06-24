package com.bisnode.opa.testformats.opa;

import com.bisnode.opa.OpaTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class OpaVerboseSummaryFactory {

    private final List<OpaTestCase> opaTestCases;

    OpaVerboseSummaryFactory(List<OpaTestCase> opaTestCases) {
        this.opaTestCases = opaTestCases;
    }

    OpaVerboseSummary create() {
        TestNameOccurrenceCounter testNameOccurrenceCounter = new TestNameOccurrenceCounter();
        List<TestCase> summarizableTestCases = getTestsInExecutionOrder().stream()
                .map(opaTestCase -> TestCase.of(opaTestCase, testNameOccurrenceCounter.getAndIncrement(opaTestCase.getName())))
                .collect(Collectors.toList());
        return new OpaVerboseSummary(summarizableTestCases);
    }

    /**
     * Test names don't have to be unique. In case the same test name occurs multiple times
     * OPA appends '#01', '#02', '#03', ... to their names, except for the first one ('#00').
     * However, order of tests with repeated names is reversed, so the first test name occurrence
     * in OPA test json is, in fact, the one with highest number. Hence, the need to reverse tests
     * grouped by test name.
     */
    @NotNull
    private List<OpaTestCase> getTestsInExecutionOrder() {
        // We're using LinkedHashMap to preserve insertion order
        LinkedHashMap<String, List<OpaTestCase>> groupedTests = opaTestCases.stream()
                .collect(Collectors.groupingBy(OpaTestCase::getName, LinkedHashMap::new, Collectors.toList()));
        groupedTests.values().forEach(Collections::reverse);
        return groupedTests.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private static class TestNameOccurrenceCounter {
        private final Map<String, Integer> testNameOccurences = new HashMap<>();

        int getAndIncrement(@NotNull String testName) {
            if (!testNameOccurences.containsKey(testName)) {
                testNameOccurences.put(testName, 1);
                return 0;
            }
            return testNameOccurences.compute(testName, (name, count) -> count++);
        }
    }

}
