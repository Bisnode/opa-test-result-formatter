# opa-test-result-formatter

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/test-result-formatter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.bisnode.opa/test-result-formatter) ![build](https://github.com/Bisnode/test-result-formatter/workflows/build/badge.svg)

Single-purpose library to help transform the output of `opa test` into different formats, like JUnit XML or `opa test -v` summary.

## Using the library

### Gradle

```
implementation("com.bisnode.opa:opa-test-result-formatter:0.1.0")
```

### Maven

```
<dependency>
    <groupId>com.bisnode.opa</groupId>
    <artifactId>test-result-formatter</artifactId>
    <version>0.1.0</version>
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

<small> Made with :heart: @ Bisnode</small>