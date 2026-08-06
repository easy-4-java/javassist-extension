# javassist-plus

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-8-orange)](https://github.com/easy-4-java/javassist-plus) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

> **Status**: maintained on the `feature/1.0.x` line (JDK 8). Artifacts are not yet published to Maven Central; they are distributed through the project's private repository and GitHub Releases.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

`javassist-plus` is a utility layer on top of [Javassist](https://www.javassist.org/) (3.30.2-GA) that makes bytecode manipulation more convenient: dynamic proxy creation, programmatic class/field/method generation, annotation copying and inspection, interface simplification, and `ClassPool` management.

What it is:

- `JavassistProxy` — static helpers that create subclass-based proxies via `javassist.util.proxy.ProxyFactory` with a `MethodHandler` interceptor;
- `ClassGenerator` — a fluent builder for generating classes at runtime (interfaces, superclass, fields, methods, constructors);
- `Mixin` / `Wrapper` — bytecode-backed delegation and reflective property/method access wrappers;
- `JavassistUtils` / `ClassPoolFactory` — annotation creation/copying, parameter-name extraction, and `ClassPool` lifecycle management (per-`ClassLoader` pools, `ClassPath` registration);
- `InterfaceSimplifier` — makes an interface's methods available through simplified wrappers.

What it is not:

- Not a fork of Javassist — it compiles against and depends on the upstream `org.javassist:javassist` artifact;
- Not a code-instrumentation agent; classes are generated at runtime in the same JVM.

Typical scenarios:

| Scenario | What to use |
| :--- | :--- |
| Runtime proxy with an interceptor callback | `JavassistProxy.getProxy(target)` / `getProxy(Class, MethodHandler)` |
| Generating a class from scratch (fields + methods) | `ClassGenerator.newInstance()` fluent builder |
| Copying annotations onto generated `CtClass` / `CtField` / `CtMethod` | `JavassistUtils.createAnnotation` / `copyAnnotations` / `addClassAnnotation` |
| Reading method parameter names at runtime | `JavassistUtils.getMethodParamNames(...)` |
| Multiple class-loaders each with their own pool | `ClassPoolFactory.getClassPool(...)` |

## 2. Features & Status

| Capability | Status | Notes |
| :--- | :--- | :--- |
| Dynamic proxies (subclass-based) | Implemented | `JavassistProxy` with `MethodHandler` interception |
| Runtime class generation | Implemented | `ClassGenerator` builder (interfaces / superclass / fields / methods / constructors) |
| Field & method bytecode builders | Implemented | `CtFieldBuilder`, `CtAnnotationBuilder`, `MethodSignature` |
| Mixin / Wrapper | Implemented | `Mixin.mixin(...)` delegation, `Wrapper` reflective access |
| Annotation tooling | Implemented | create / clone / copy annotations, member values (`JavassistUtils`, `visit` visitors) |
| ClassPool management | Implemented | `ClassPoolFactory` (default pool, `ClassPath`-aware, per-classloader pools) |
| Interface simplification | Implemented | `InterfaceSimplifier`, `SimplifiedClass`, `SimplifiedMethod` |
| Tests | Present | JUnit 4 examples (`JavassistProxy_Test`, `JavassistExample1-4`, ...) in `src/test` |

## 3. Requirements & Compatibility

| Item | Requirement |
| :--- | :--- |
| JDK | 8+ |
| Maven | 3.0+ (Maven Wrapper `mvnw` included) |
| Dependencies | javassist 3.30.2-GA, commons-lang3 3.20.0, slf4j-api 2.0.18, lombok (provided); JUnit 4.13.2 (test) |

Version lines:

| Branch | JDK | Version pattern |
| :--- | :---: | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
Application code (POJO / interface / annotations)
        |
        v
io.github.easy4j.javassist
   |-- proxy/JavassistProxy  ------>  javassist.util.proxy.ProxyFactory
   |-- bytecode/ClassGenerator ----->  ClassPool -> CtClass (fields/methods/ctors)
   |-- bytecode/Mixin, Wrapper ----->  reflective + CtClass-backed access
   |-- utils/ClassPoolFactory ------>  ClassPool (per-ClassLoader, ClassPath)
   `-- simplify/InterfaceSimplifier->  simplified method wrappers

        |  (javassist 3.30.2-GA bytecode toolkit)
        v
Runtime-generated classes in the same JVM
```

Single-module jar. Package layout under `io.github.easy4j.javassist`:

| Package | Contents |
| :--- | :--- |
| `io.github.easy4j.javassist.proxy` | `JavassistProxy` |
| `io.github.easy4j.javassist.bytecode` | `ClassGenerator`, `CtFieldBuilder`, `CtAnnotationBuilder`, `MethodSignature`, `Mixin`, `Wrapper`, `Proxy` |
| `io.github.easy4j.javassist.bytecode.visit` | `MemberValueCreationVisitor`, `ArrayIndexAssigningVisitor` |
| `io.github.easy4j.javassist.utils` | `ClassPoolFactory`, `JavassistUtils`, `Assert`, `ReflectUtils`, `ReflectionUtils`, `ClassHelper` |
| `io.github.easy4j.javassist.simplify` | `InterfaceSimplifier`, `SimplifiedClass`, `SimplifiedMethod` |
| `io.github.easy4j.javassist.exception` | `NoSuchPropertyException`, `NoSuchMethodException` |

## 5. Installation

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>javassist-plus</artifactId>
    <version>1.0.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle:

```groovy
implementation 'io.github.easy4j:javassist-plus:1.0.x.20260630-SNAPSHOT'
```

The snapshot is served from the project's private repository (see `distributionManagement` in the pom). No Maven Central release is available yet.

## 6. Quick Start

Create a proxy around an existing object (real API, mirrored from `JavassistProxy_Test`):

```java
import io.github.easy4j.javassist.proxy.JavassistProxy;

A target = new A();
A proxy = JavassistProxy.getProxy(target);

proxy.save("xxx"); // method call goes through the MethodHandler interceptor:
                   // logs the method name, then "开启事务", delegates via
                   // proceed.invoke(self, args), then "提交事务"
```

`getProxy(Class<T>, MethodHandler)` lets you supply your own `javassist.util.proxy.MethodHandler`; the generated subclass only intercepts methods matched by the internal `MethodFilter` (all except `finalize`).

## 7. Configuration

Plain library — no configuration properties or prefixes. The only tunables are the `MethodHandler` and `MethodFilter` you pass to `JavassistProxy`, the `ClassPool` you obtain from `ClassPoolFactory`, and the builders you chain in `ClassGenerator`.

## 8. Core Usage / API

### 8.1 Generating a class with `ClassGenerator`

```java
import io.github.easy4j.javassist.bytecode.ClassGenerator;

ClassGenerator generator = ClassGenerator.newInstance();
generator.setClassName("com.example.DynamicPojo");
generator.setSuperClass(Object.class);
generator.addField("private String name;");
generator.addMethod("public String getName() { return this.name; }");
Class<?> clazz = generator.toClass();   // loads the generated class in this JVM
```

### 8.2 ClassPool management

```java
import io.github.easy4j.javassist.utils.ClassPoolFactory;
import javassist.ClassPool;

// default pool with java.util / java.lang / java.lang.reflect imported
ClassPool pool = ClassPoolFactory.getDefaultPool();

// a pool bound to the current context ClassLoader (ClassPath-aware)
ClassPool pool2 = ClassPoolFactory.getClassPoolForCurrentContextClassLoader();
```

Background on the Javassist concepts this library wraps: Javassist works at the Java language level instead of raw bytecode. `CtClass` is the in-memory handle for a class (obtained via `ClassPool.get(String classname)`); `ClassPool` is the container that holds `CtClass` objects and resolves their class paths; `CtField` / `CtMethod` / `CtConstructor` model members, which are added to a `CtClass` via `addField` / `addMethod` / `addConstructor` (a class is frozen after `writeFile` / `toClass` / `toBytecode` and must be `defrost`ed before further edits). This is the foundation the builders in this project automate.

## 9. Testing & Build

```bash
./mvnw clean verify
```

The build is configured with:

- JUnit 4 (`junit` 4.13.2) + Maven Surefire; `src/test` contains proxy examples (`JavassistProxy_Test`, `JavassistProxyExample1/3/4`) and generation examples (`JavassistExample1-4`, `UserGenerator`, `JavassistWebserviceGenerator`);
- JaCoCo coverage reporting plus a line-coverage check rule with a 90% minimum target (`haltOnFailure=false`);
- Source and Javadoc jars attached at package time;
- a `central` release profile (GPG signing + Central publishing) reserved for official releases.

## 10. Versioning & Branches

Three parallel version lines, each bound to a JDK baseline:

| Branch | JDK | Version pattern | Maintenance |
| :--- | :---: | :--- | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` | Current development line |
| `feature/2.0.x` | 17 | `2.0.x.*` | Maintained in parallel |
| `feature/3.0.x` | 21 | `3.0.x.*` | Maintained in parallel |

Snapshots on this branch are versioned `1.0.x.20260630-SNAPSHOT`.

## 11. Contributing & License

Contributions are welcome — open an issue or pull request on GitHub. All source files are licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).
