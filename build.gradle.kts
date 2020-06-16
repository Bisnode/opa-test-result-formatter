import java.util.*
import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType

version = "0.0.1"
group = "com.bisnode.opa"

plugins {
    groovy
    java
    `java-library`
    `maven-publish`
    maven
    signing
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.0")
    implementation("org.jetbrains:annotations:19.0.0")

    testImplementation("org.codehaus.groovy:groovy-all:2.5.7")
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = VERSION_11
    targetCompatibility = VERSION_11
    withJavadocJar()
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    source = sourceSets["main"].allJava
}

tasks.wrapper {
    version = "6.3"
    distributionType = DistributionType.ALL
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "opa-test-result-formatter"
            from(components["java"])
            pom {
                name.set("OPA test result formatter")
                description.set("Open Policy Agent test result converter to JUnit XML")
                url.set("https://github.com/Bisnode/opa-test-result-formatter")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Bisnode/opa-test-result-formatter.git")
                    developerConnection.set("scm:git:ssh://github.com:Bisnode/opa-test-result-formatter.git")
                    url.set("https://github.com/Bisnode/opa-test-result-formatter.git")
                }
                developers {
                    developer {
                        name.set("Team Night's Watch")
                        email.set("nights-watch@bisnode.com")
                        organization.set("Bisnode")
                        organizationUrl.set("https://www.bisnode.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val ossrhUsername: String? by project
            val ossrhPassword: String? by project
            name = "OSSRH"
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    val decodedKey = signingKey
            ?.let { Base64.getDecoder().decode(signingKey) }
            ?.let { String(it) }
    useInMemoryPgpKeys(decodedKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}