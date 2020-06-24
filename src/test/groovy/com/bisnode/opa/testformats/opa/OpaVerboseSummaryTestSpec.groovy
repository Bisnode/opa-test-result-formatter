package com.bisnode.opa.testformats.opa

import com.bisnode.opa.OpaTestResults
import spock.lang.Specification

class OpaVerboseSummaryTestSpec extends Specification {

    def 'should create summary with all outcomes'() {
        given:
            OpaTestResults allOutcomes = OpaTestResults.fromJson(ALL_OUTCOMES.text)
            OpaVerboseSummary opaVerboseSummary = OpaVerboseSummary.of(allOutcomes)

        when:
            String summary = opaVerboseSummary.summary()

        then:
            summary ==
"""SUMMARY
--------------------------------------------------------------------------------
data.http.request.authz.test_allow_is_false_by_default: PASS (495.819µs)
data.http.request.authz.test_allow_if_path_is_error: ERROR (227.27µs)
  src/test/resources/opa/policies/policies-test.rego:8: eval_builtin_error: div: divide by zero
data.http.request.authz.test_allow_if_path_is_ping: PASS (451.928µs)
data.http.request.authz.test_not_allow_if_without_jwt: FAIL (596.126µs)
data.http.request.authz.test_not_allow_if_without_jwt#01: PASS (1.187468ms)
data.http.request.authz.test_not_allow_if_jwt_without_group_for_messages: PASS (2.013099ms)
data.http.request.authz.test_not_allow_if_jwt_with_wrong_group_for_messages: PASS (1.95541ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_messages: PASS (1.75028ms)
data.http.request.authz.test_allow_if_jwt_with_alternate_groups_for_messages: PASS (1.752927ms)
data.http.request.authz.test_not_allow_if_jwt_without_grop_for_statistics: PASS (1.985184ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_statistics: PASS (1.748708ms)
--------------------------------------------------------------------------------
PASS: 9/11
FAIL: 1/11
ERROR: 1/11
"""
    }

    def 'should create summary with no errors'() {
        given:
            OpaTestResults noErrors = OpaTestResults.fromJson(NO_ERRORS.text)
            OpaVerboseSummary opaVerboseSummary = OpaVerboseSummary.of(noErrors)

        when:
            String summary = opaVerboseSummary.summary()

        then:
            summary ==
                    """SUMMARY
--------------------------------------------------------------------------------
data.http.request.authz.test_allow_is_false_by_default: PASS (495.819µs)
data.http.request.authz.test_allow_if_path_is_ping: PASS (451.928µs)
data.http.request.authz.test_not_allow_if_without_jwt: FAIL (596.126µs)
data.http.request.authz.test_not_allow_if_without_jwt#01: PASS (1.187468ms)
data.http.request.authz.test_not_allow_if_jwt_without_group_for_messages: PASS (2.013099ms)
data.http.request.authz.test_not_allow_if_jwt_with_wrong_group_for_messages: PASS (1.95541ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_messages: PASS (1.75028ms)
data.http.request.authz.test_allow_if_jwt_with_alternate_groups_for_messages: PASS (1.752927ms)
data.http.request.authz.test_not_allow_if_jwt_without_grop_for_statistics: PASS (1.985184ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_statistics: PASS (1.748708ms)
--------------------------------------------------------------------------------
PASS: 9/10
FAIL: 1/10
"""
    }

    def 'should create summary with no failures'() {
        given:
            OpaTestResults noFails = OpaTestResults.fromJson(NO_FAILS.text)
            OpaVerboseSummary opaVerboseSummary = OpaVerboseSummary.of(noFails)

        when:
            String summary = opaVerboseSummary.summary()

        then:
            summary ==
                    """SUMMARY
--------------------------------------------------------------------------------
data.http.request.authz.test_allow_is_false_by_default: PASS (495.819µs)
data.http.request.authz.test_allow_if_path_is_error: ERROR (227.27µs)
  src/test/resources/opa/policies/policies-test.rego:8: eval_builtin_error: div: divide by zero
data.http.request.authz.test_allow_if_path_is_ping: PASS (451.928µs)
data.http.request.authz.test_not_allow_if_without_jwt#01: PASS (1.187468ms)
data.http.request.authz.test_not_allow_if_jwt_without_group_for_messages: PASS (2.013099ms)
data.http.request.authz.test_not_allow_if_jwt_with_wrong_group_for_messages: PASS (1.95541ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_messages: PASS (1.75028ms)
data.http.request.authz.test_allow_if_jwt_with_alternate_groups_for_messages: PASS (1.752927ms)
data.http.request.authz.test_not_allow_if_jwt_without_grop_for_statistics: PASS (1.985184ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_statistics: PASS (1.748708ms)
--------------------------------------------------------------------------------
PASS: 9/10
ERROR: 1/10
"""
    }
    def 'should create summary with passes only'() {
        given:
            OpaTestResults allPass = OpaTestResults.fromJson(ALL_PASS.text)
            OpaVerboseSummary opaVerboseSummary = OpaVerboseSummary.of(allPass)

        when:
            String summary = opaVerboseSummary.summary()

        then:
            summary ==
                    """SUMMARY
--------------------------------------------------------------------------------
data.http.request.authz.test_allow_is_false_by_default: PASS (495.819µs)
data.http.request.authz.test_allow_if_path_is_ping: PASS (451.928µs)
data.http.request.authz.test_not_allow_if_without_jwt#01: PASS (1.187468ms)
data.http.request.authz.test_not_allow_if_jwt_without_group_for_messages: PASS (2.013099ms)
data.http.request.authz.test_not_allow_if_jwt_with_wrong_group_for_messages: PASS (1.95541ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_messages: PASS (1.75028ms)
data.http.request.authz.test_allow_if_jwt_with_alternate_groups_for_messages: PASS (1.752927ms)
data.http.request.authz.test_not_allow_if_jwt_without_grop_for_statistics: PASS (1.985184ms)
data.http.request.authz.test_allow_if_jwt_with_groups_for_statistics: PASS (1.748708ms)
--------------------------------------------------------------------------------
PASS: 9/9
"""
    }

    static File ALL_OUTCOMES = new File('src/test/resources/summary-all-outcomes.json')
    static File NO_ERRORS = new File('src/test/resources/summary-no-errors.json')
    static File NO_FAILS = new File('src/test/resources/summary-no-fails.json')
    static File ALL_PASS = new File('src/test/resources/summary-all-pass.json')

}
