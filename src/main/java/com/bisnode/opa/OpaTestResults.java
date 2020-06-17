package com.bisnode.opa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpaTestResults {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final List<OpaTestCase> testCases;

    /**
     * Factory method creating {@link OpaTestResults} instance out of OPA's test JSON report.
     * Report is produced by {@code opa test --format=json} command.
     *
     * @param json OPA test report in JSON format.
     * @return {@link OpaTestResults} containing parsed test results.
     * @throws JsonProcessingException Thrown by {@link ObjectMapper}.
     */
    public static OpaTestResults fromJson(@Nullable String json) throws JsonProcessingException {
        if (isEmpty(json)) {
            return new OpaTestResults(Collections.emptyList());
        }

        return new OpaTestResults(Arrays.asList(OBJECT_MAPPER.readValue(json, OpaTestCase[].class)));
    }

    private static boolean isEmpty(@Nullable String json) {
        return json == null || json.isBlank() || json.trim().equals("null");
    }

    private OpaTestResults(List<OpaTestCase> testCases) {
        this.testCases = testCases;
    }

    /**
     * @return {@link List} of {@link OpaTestCase} contained this {@link OpaTestResults}.
     */
    public List<OpaTestCase> getTestCases() {
        return testCases;
    }

}
