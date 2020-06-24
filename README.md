# opa-test-result-formatter

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter) ![build](https://github.com/Bisnode/opa-test-result-formatter/workflows/build/badge.svg)

Single-purpose library to help transform the output of `opa test` into different formats, like JUnit XML or `opa test -v` summary.

## Using the library

### Gradle
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter)
```
implementation("com.bisnode.opa:opa-test-result-formatter:{version}")
```

### Maven
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/opa-test-result-formatter)
```
<dependency>
    <groupId>com.bisnode.opa</groupId>
    <artifactId>opa-test-result-formatter</artifactId>
    <version>{version}</version>
</dependency>
```

### Converting to JUnit XML

```java
String testResultsJson = "..."; // output of opa test --format=json
OpaTestResults testResults = OpaTestResults.fromJson(testResultsJson);
JUnitXml junitXml = JUnitXML.from(testResults);
```

### Converting to `opa test -v` format

```java
String testResultsJson = "..."; // output of opa test --format=json
OpaTestResults testResults = OpaTestResults.fromJson(testResultsJson);
OpaVerboseSummary opaVerboseSummary = OpaVerboseSummary.of(testResults);
String summary = opaVerboseSummary.summary();
```

---

<small>Made with :heart: @ [Bisnode](https://www.bisnode.com/) </small>
