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
    public String getName() {
        return name;
    }

    @JacksonXmlProperty(isAttribute = true, localName = "classname")
    public String getClassName() {
        return className;
    }

    @JacksonXmlProperty(isAttribute = true)
    public String getTime() {
        return TimeFormatter.nanosToSeconds(time);
    }

    @JsonIgnore
    public long getTimeNanos() {
        return time;
    }

    @JsonIgnore
    public boolean isFailure() {
        return failure;
    }

    //that's a nasty hack
    @JacksonXmlProperty(localName = "failure")
    public String getFailure() {
        return isFailure() ? "" : null;
    }

    @JacksonXmlProperty(localName = "error")
    public Error getError() {
        return error;
    }


}
