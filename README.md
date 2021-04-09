# spring-native-example
An example application built with Spring Native. Please [this blog entry](https://hantsy.medium.com/building-your-first-spring-native-application-ae169136e544) for details.

## Prerequisites 

* Java 11
* Apache Maven 3.8.1
* Docker 
* GraalVM 21 if building the application to native executable application by GraalVM native image

## Build

Like general Spring Boot applications, run the following command  to run the application in JVM.

```bash
$ mvn spring-boot:run
```

Or run the `DemoApplication` in your IDEs.

### Build Docker Image

```bash
$ mvn spring-boot:built-image -Pspring-native,build-docker-image
```

Run the application in docker container.

```bash
$ docker run -rm  hantsy/spring-native-demo:latest
```

### Build Executable Application

> It requires installation of GraalVM.

```bash
$ mvn clean package -Pspring-native,build-native-image
```

Run the application.

```bash
$ ./target/com.example.demo.demoapplication
```


## Functional Tests

When the application is running at *localhost:8080*,  you can perform a `FunctionalTests` to verify the APIs as a HTTP Client view.

```bash
$ mvn clean test -Pfunctional-test
```


## Resource

* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/html/#build-image)
* [Spring Native Reference Guide](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-mongodb)
* [Configure the Spring AOT Plugin](https://docs.spring.io/spring-native/docs/0.9.1/reference/htmlsingle/#spring-aot-maven)
* [Get Started with GraalVM ](https://www.graalvm.org/docs/getting-started/#install-graalvm)
* [IntelliJ IDEA 2021.1 EAP 5: WSL 2 Support for Maven and Gradle, Support for JSON Path, and More](https://blog.jetbrains.com/idea/2021/02/intellij-idea-2021-1-eap-5/)
