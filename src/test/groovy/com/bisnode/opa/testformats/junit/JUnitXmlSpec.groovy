package com.bisnode.opa.testformats.junit

import com.bisnode.opa.OpaTestResults
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

@SuppressWarnings("SpaceAfterClosingBrace")
class JUnitXmlSpec extends Specification {

    static File JUNIT_XSD = new File('src/test/resources/junit.xsd')
    static File SINGLE_PACKAGE = new File("src/test/resources/single-testsuite.json")
    static File SINGLE_PACKAGE_NO_FAILS = new File("src/test/resources/single-testsuite-nofails.json")
    static File SINGLE_PACKAGE_NO_ERRORS = new File("src/test/resources/single-testsuite-noerrors.json")
    static File MULTIPLE_PACKAGES = new File("src/test/resources/multiple-testsuites.json")

    OpaTestResults singleTestSuite = OpaTestResults.fromJson(SINGLE_PACKAGE.text)
    OpaTestResults multipleTestSuites = OpaTestResults.fromJson(MULTIPLE_PACKAGES.text)
    XmlSlurper xmlSlurper = new XmlSlurper()

    void 'should not fail on converting'() {
        when:
            String xmlString = JUnitXml.from(singleTestSuite).asXmlString()
        then:
            noExceptionThrown()
    }

    void 'should validate single suite against JUnit xsd'() {
        given:
            def validator = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(JUNIT_XSD)
                    .newValidator()

        when:
            validator.validate(new StreamSource(new StringReader(JUnitXml.from(singleTestSuite).asXmlString())))

        then:
            noExceptionThrown()
    }

    void 'should validate multiple suites against JUnit xsd'() {
        given:
            def validator = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(JUNIT_XSD)
                    .newValidator()

        when:
            validator.validate(new StreamSource(new StringReader(JUnitXml.from(multipleTestSuites).asXmlString())))

        then:
            noExceptionThrown()
    }

    void 'should recognize single test suite'() {
        when:
            String xmlString = JUnitXml.from(singleTestSuite).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml.name() == 'testsuites'
        and: 'one suite recognized'
            outputXml.children().size() == 1
            outputXml.testsuite['@name'] == 'data.kubernetes.authz'
        and: 'all test cases are in said suite'
            outputXml.testsuite.children().size() == 22
    }

    @Unroll
    void 'should sum #stat'() {
        when:
            String xmlString = JUnitXml.from(singleTestSuite).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml["@${stat}"] == outputXml.testsuite["@${stat}"]
            outputXml["@${stat}"] == expectedValue

        where:
            stat       | expectedValue
            'time'     | '0.015'
            'failures' | 6
            'errors'   | 1
    }

    void 'should map error'() {
        when:
            String xmlString = JUnitXml.from(singleTestSuite).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml.testsuite.testcase.find { testcase -> testcase.children()[0].name() == 'error' }.size() == 1
            outputXml.testsuite.testcase.find { testcase -> testcase.children()[0].name() == 'error' }.error['@message'] == 'div: divide by zero'
            outputXml.testsuite.testcase.find { testcase -> testcase.children()[0].name() == 'error' }.error['@type'] == 'eval_builtin_error'
    }

    void 'should handle no failures'() {
        when:
            String xmlString = JUnitXml.from(OpaTestResults.fromJson(SINGLE_PACKAGE_NO_FAILS.text)).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            noExceptionThrown()
        and:
            outputXml['@failures'] == outputXml.testsuite['@failures']
            outputXml['@failures'] == 0
    }

    void 'should handle no errors'() {
        when:
            String xmlString = JUnitXml.from(OpaTestResults.fromJson(SINGLE_PACKAGE_NO_ERRORS.text)).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            noExceptionThrown()
        and:
            outputXml['@errors'] == outputXml.testsuite['@errors']
            outputXml['@errors'] == 0
    }

    void 'should recognize multiple test suites'() {
        when:
            String xmlString = JUnitXml.from(multipleTestSuites).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml.children().size() == 2
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.kubernetes.authz' }
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.http.request.authz' }
        and: 'tests are in respective suites'
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.kubernetes.authz' }.children().size() == 22
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.http.request.authz' }.children().size() == 11
    }

    @Unroll
    void 'should sum #stat for multiple suites'() {
        when:
            String xmlString = JUnitXml.from(multipleTestSuites).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml["@${stat}"] == expectedTotal
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.kubernetes.authz' }["@${stat}"] == expectedKubeSuite
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.http.request.authz' }["@${stat}"] == expectedHttpSuite

        where:
            stat       | expectedKubeSuite | expectedHttpSuite | expectedTotal
            'time'     | '0.015'           | '0.019'           | '0.034'
            'failures' | 6                 | 1                 | 7
            'errors'   | 1                 | 0                 | 1
    }

    void 'should not mix suites'() {
        when:
            String xmlString = JUnitXml.from(multipleTestSuites).asXmlString()
            def outputXml = xmlSlurper.parseText(xmlString)
        then:
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.kubernetes.authz' }.children()
                    .each { child -> child.className == 'tbac-policy-test.rego' }
            outputXml.testsuite.find { suite -> suite['@name'] == 'data.http.request.authz' }.children()
                    .each { child -> child.className == 'src/test/resources/opa/policies/policies-test.rego' }
    }

}
