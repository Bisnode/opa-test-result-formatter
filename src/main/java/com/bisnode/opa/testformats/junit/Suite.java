package com.bisnode.opa.testformats.junit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.Objects;

class Suite {

    private final String name;
    private final List<TestCase> testCases;

    Suite(String name, List<TestCase> testCases) {
        this.name = name;
        this.testCases = testCases;
    }

    @JacksonXmlProperty(isAttribute = true)
    String getName() {
        return name;
    }

    @JacksonXmlProperty(localName = "testcase")
    List<TestCase> getTestCases() {
        return testCases;
    }

    @JacksonXmlProperty(isAttribute = true)
    long getTests() {
        return testCases.size();
    }

    @JacksonXmlProperty(isAttribute = true)
    long getErrors() {
        return testCases.stream().map(TestCase::getError).filter(Objects::nonNull).count();
    }

    @JacksonXmlProperty(isAttribute = true)
    long getFailures() {
        return testCases.stream().filter(TestCase::isFailure).count();
    }

    @JacksonXmlProperty(isAttribute = true)
    String getTime() {
        return TimeFormatter.nanosToSeconds(testCases.stream().map(TestCase::getTimeNanos).reduce(0L, Long::sum));
    }

    @JsonIgnore
    long getTimeNanos() {
        return testCases.stream().map(TestCase::getTimeNanos).reduce(0L, Long::sum);
    }
}
