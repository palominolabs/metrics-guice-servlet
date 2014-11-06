# Quick Start

### Get the artifacts

Maven:

```xml
<dependency>
  <groupId>com.palominolabs.metrics</groupId>
  <artifactId>metrics-guice-servlet</artifactId>
  <version>3.1.3-SNAPSHOT</version>
</dependency>
```

Gradle:

```
compile 'com.palominolabs.metrics:metrics-guice:3.1.3-SNAPSHOT'
```

### Install the AdminServletModule

First, [set up the Guice Servlet integration](https://github.com/google/guice/wiki/ServletModule) if you haven't already.

```java
// somewhere in your Guice module setup
install(new AdminServletModule());
```

### Use it

Your `AdminServlet` will now be exposed on the default URIs for metrics, health checks, etc. You can also customize the URIs used with an alternate `AdminServletModule` constructor.

