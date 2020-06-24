package com.bisnode.opa.testformats.opa;

import com.bisnode.opa.OpaError;
import com.bisnode.opa.OpaTestCase;

import java.util.Optional;

import static java.util.Objects.nonNull;

class TestCase implements Summarizable {

    private final OpaTestCase opaTestCase;
    private final int testNameOccurrence;
    private final TestDuration testDuration;

    private TestCase(OpaTestCase opaTestCase, int testNameOccurrence, TestDuration testDuration) {
        this.opaTestCase = opaTestCase;
        this.testNameOccurrence = testNameOccurrence;
        this.testDuration = testDuration;
    }

    static TestCase of(OpaTestCase opaTestCase, int testNameOccurrence) {
        return new TestCase(opaTestCase, testNameOccurrence, TestDuration.ofNanos(opaTestCase.getDuration()));
    }

    boolean isFailure() {
        return Optional.ofNullable(opaTestCase.getFail()).map(Boolean.TRUE::equals).orElse(false);
    }

    boolean isError() {
        return nonNull(opaTestCase.getError());
    }

    boolean passed() {
        return !isFailure() && !isError();
    }

    @Override
    public String summary() {
        StringBuilder summary = new StringBuilder(opaTestCase.getPackage())
                .append('.').append(opaTestCase.getName());

        if (testNameOccurrence != 0) {
            summary.append(String.format("#%02d", testNameOccurrence));
        }
        summary.append(": ").append(outcome())
                .append(" (").append(testDuration.summary()).append(")");

        if (isError()) {
            OpaError error = opaTestCase.getError();
            if (nonNull(error)) {
                summary.append("\n  ")
                        .append(error.getLocation().getFile())
                        .append(":")
                        .append(error.getLocation().getRow())
                        .append(": ")
                        .append(error.getCode())
                        .append(": ")
                        .append(error.getMessage());
            }
        }

        return summary.toString();
    }

    private String outcome() {
        if (isError()) {
            return "ERROR";
        } else if (isFailure()) {
            return "FAIL";
        }
        return "PASS";
    }
}
