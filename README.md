[![Maven Central](https://img.shields.io/maven-central/v/com.excelsiorjet/excelsior-jet-winservice-api.svg)](https://maven-badges.herokuapp.com/maven-central/com.excelsiorjet/excelsior-jet-winservice-api)
Excelsior JET WinService API
=====

[Excelsior JET](https://www.excelsiorjet.com) eases conversion of programs 
designed to run on the JVM into Windows services by providing a custom
WinService API and necessary support in the compiler and runtime system.

The API is published at Maven Central to enable Maven and Gradle users
to reference it from their projects and subsequently build those projects
with Excelsior JET [Maven](https://www.excelsiorjet.com/maven-plugin)
and [Gradle](https://www.excelsiorjet.com/gradle-plugin) plugins.

The complete documentation for the API can be found in the "Windows Services"
chapter of the [Excelsior JET User's Guide](https://www.excelsiorjet.com/docs).

**Note:** The API can be used only in conjunction with the Excelsior JET JVM,
as other JVM implementations do not support it.

### Usage

To include the WinService API in a Maven-based build, add the following dependency to the `<dependencies>` section  of your `pom.xml` file:

```xml
<dependency>
    <groupId>com.excelsiorjet</groupId>
    <artifactId>excelsior-jet-winservice-api</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

To include the WinService API in a Gradle-based build, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    compileOnly "com.excelsiorjet:excelsior-jet-winservice-api:1.0.0"
}
```

Support for Windows Services in Excelsior JET Maven and Gradle plugins will appear soon.