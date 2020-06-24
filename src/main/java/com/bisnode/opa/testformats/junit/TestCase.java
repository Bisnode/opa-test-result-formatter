package com.bisnode.opa.testformats.junit;

import com.bisnode.opa.OpaTestCase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Optional;

class TestCase {
    private final String name;
    private final String className;
    private final long time;
    private final boolean failure;
    private final Error error;

    static TestCase fromOpaTestCase(OpaTestCase opaTestCase) {
        return new TestCase(
                opaTestCase.getName(),
                opaTestCase.getLocation().getFile(),
                opaTestCase.getDuration(),
                Optional.ofNullable(opaTestCase.getFail()).orElse(false),
                Optional.ofNullable(opaTestCase.getError()).map(Error::fromOpaError).orElse(null)
        );
    }

    private TestCase(String name, String className, long time, boolean failure, Error error) {
        this.name = name;
        this.className = className;
        this.time = time;
        this.failure = failure;
        this.error = error;
    }

    @JacksonXmlProperty(isAttribute = true)
    String getName() {
        return name;
    }

    @JacksonXmlProperty(isAttribute = true, localName = "classname")
    String getClassName() {
        return className;
    }

    @JacksonXmlProperty(isAttribute = true)
    String getTime() {
        return TimeFormatter.nanosToSeconds(time);
    }

    @JsonIgnore
    long getTimeNanos() {
        return time;
    }

    @JsonIgnore
    boolean isFailure() {
        return failure;
    }

    // If the test has failed there needs to be a child <failure></failure> in JUnit XML.
    // Otherwise no children should be present. The only way of achieving this behavior
    // using Jackson XML Mapper was to emulate an empty string property called "failure"
    // (so it would create <failure></failure> with no text inside) and return null when
    // the test passed. The mapper is configured to ignore null values during serialization,
    // so when null is returned for "failure" it doesn't append any children to "testcase"
    // and just serializes it as <testcase .... />.
    @JacksonXmlProperty(localName = "failure")
    String getFailure() {
        return isFailure() ? "" : null;
    }

    @JacksonXmlProperty(localName = "error")
    Error getError() {
        return error;
    }


}
