package com.bisnode.opa.testformats.junit;

import com.bisnode.opa.OpaTestCase;
import com.bisnode.opa.OpaTestResults;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@JacksonXmlRootElement(localName = "testsuites")
public class JUnitXml {
    private static final XmlMapper XML_MAPPER = XmlMapper.builder()
            .defaultUseWrapper(false)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    private final List<Suite> suites;

    public JUnitXml(List<Suite> suites) {
        this.suites = suites;
    }

    public static JUnitXml from(OpaTestResults opaTestResults) {
        return new JUnitXml(toSuites(opaTestResults));
    }

    private static List<Suite> toSuites(OpaTestResults opaTestResults) {
        return groupBySuite(opaTestResults).entrySet().stream()
                .map(entry -> new Suite(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    private static Map<String, List<TestCase>> groupBySuite(OpaTestResults opaTestResults) {
        return opaTestResults.getTestCases().stream()
                .collect(
                        groupingBy(OpaTestCase::getPackage,
                                mapping(TestCase::fromOpaTestCase, toList())
                        )
                );
    }

    public String asXmlString() throws JsonProcessingException {
        return XML_MAPPER.writeValueAsString(this);
    }

    @JacksonXmlProperty(localName = "testsuite")
    public List<Suite> getSuites() {
        return suites;
    }

    @JacksonXmlProperty(isAttribute = true)
    public long getFailures() {
        return suites.stream().map(Suite::getFailures).reduce(0L, Long::sum);
    }

    @JacksonXmlProperty(isAttribute = true)
    public long getErrors() {
        return suites.stream().map(Suite::getErrors).reduce(0L, Long::sum);
    }

    @JacksonXmlProperty(isAttribute = true)
    public long getTests() {
        return suites.stream().map(Suite::getTests).reduce(0L, Long::sum);
    }

    @JacksonXmlProperty(isAttribute = true)
    public String getTime() {
        return TimeFormatter.nanosToSeconds(suites.stream().map(Suite::getTimeNanos).reduce(0L, Long::sum));
    }




}
