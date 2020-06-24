# opa-test-result-formatter

Single-purpose library to help transform the output of `opa test` into different formats, like JUnit XML.

## Using the library

Import the library

### Gradle
```
implementation("com.bisnode.opa:opa-test-result-formatter:0.0.1")
```
### Maven

```
<dependency>
    <groupId>com.bisnode.opa</groupId>
    <artifactId>test-result-formatter</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Converting to JUnit XML

```java
String testResultsJson = "..."; // output of opa test --format=json
OpaTestResults testResults = OpaTestResults.fromJson(testResultsJson);
JUnitXml junitXml = JUnitXML.from(testResults);
```
