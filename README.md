# Spring Native Example

An example application to demo Spring Native. 

**Please read [this blog entry](https://hantsy.medium.com/building-your-first-spring-native-application-ae169136e544) for more details**.

## Prerequisites 

* Java 17
* Apache Maven 3.8.1+
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

Run the application in the Docker container.

```bash
$ docker run -rm  hantsy/spring-native-demo:latest
```

### Build Native Executable Application

> It requires the installation of GraalVM.

```bash
$ mvn clean package -Pspring-native,build-native-image
```

Run the application.

```bash
$ ./target/com.example.demo.demoapplication
```


## Smoke Tests

When the application is running at *[http://localhost:8080](http://localhost:8080)*,  there is a `FunctionalTests` that is used to verify the APIs from HTTP Client view.

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

