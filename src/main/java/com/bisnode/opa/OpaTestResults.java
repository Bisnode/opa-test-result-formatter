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

    public static OpaTestResults fromJson(@Nullable String json) throws JsonProcessingException {
        if (isEmpty(json)) {
            return new OpaTestResults(Collections.emptyList());
        }

        return new OpaTestResults(Arrays.asList(OBJECT_MAPPER.readValue(json, OpaTestCase[].class)));
    }

    private static boolean isEmpty(@Nullable String json) {
        return json == null || json.isBlank() || json.trim().equals("null");
    }

    public OpaTestResults(List<OpaTestCase> testCases) {
        this.testCases = testCases;
    }

}
