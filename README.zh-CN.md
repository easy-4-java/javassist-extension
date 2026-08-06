# javassist-plus

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://github.com/easy-4-java/javassist-plus) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

> **项目状态**：`feature/2.0.x` 版本线维护中（JDK 17）。制品尚未发布到 Maven Central，通过项目私服与 GitHub Releases 分发。

## 目录

- [1. 项目概述](#1-项目概述)
- [2. 功能与状态](#2-功能与状态)
- [3. 环境要求与兼容性](#3-环境要求与兼容性)
- [4. 架构与模块](#4-架构与模块)
- [5. 安装](#5-安装)
- [6. 快速开始](#6-快速开始)
- [7. 配置](#7-配置)
- [8. 核心用法 / API](#8-核心用法--api)
- [9. 测试与构建](#9-测试与构建)
- [10. 版本与分支](#10-版本与分支)
- [11. 贡献与许可](#11-贡献与许可)

## 1. 项目概述

`javassist-plus` 是在 [Javassist](https://www.javassist.org/)（3.30.2-GA）之上的工具层，让字节码操作更方便：动态代理创建、运行时类/字段/方法生成、注解复制与解析、接口简化以及 `ClassPool` 管理。

是什么：

- `JavassistProxy`——基于 `javassist.util.proxy.ProxyFactory` 创建子类代理的静态工具，配合 `MethodHandler` 拦截器；
- `ClassGenerator`——流畅的运行时类生成器（接口、父类、字段、方法、构造方法）；
- `Mixin` / `Wrapper`——基于字节码的委托与反射式属性/方法访问包装；
- `JavassistUtils` / `ClassPoolFactory`——注解创建/复制、方法参数名提取、`ClassPool` 生命周期管理（按 `ClassLoader` 隔离、`ClassPath` 注册）；
- `InterfaceSimplifier`——将接口方法包装为简化调用形式。

不是什么：

- 不是 Javassist 的分支——它编译依赖上游 `org.javassist:javassist` 制品；
- 不是插桩 agent；类是在运行时于同一 JVM 内生成的。

典型场景：

| 场景 | 使用 |
| :--- | :--- |
| 带拦截回调的运行时代理 | `JavassistProxy.getProxy(target)` / `getProxy(Class, MethodHandler)` |
| 从零生成类（字段 + 方法） | `ClassGenerator.newInstance()` 流畅构建器 |
| 向生成的 `CtClass` / `CtField` / `CtMethod` 复制注解 | `JavassistUtils.createAnnotation` / `copyAnnotations` / `addClassAnnotation` |
| 运行时读取方法参数名 | `JavassistUtils.getMethodParamNames(...)` |
| 多个类加载器各自独立的 ClassPool | `ClassPoolFactory.getClassPool(...)` |

## 2. 功能与状态

| 能力 | 状态 | 说明 |
| :--- | :--- | :--- |
| 动态代理（基于子类） | 已实现 | `JavassistProxy` + `MethodHandler` 拦截 |
| 运行时类生成 | 已实现 | `ClassGenerator` 构建器（接口 / 父类 / 字段 / 方法 / 构造方法） |
| 字段与方法字节码构建器 | 已实现 | `CtFieldBuilder`、`CtAnnotationBuilder`、`MethodSignature` |
| Mixin / Wrapper | 已实现 | `Mixin.mixin(...)` 委托、`Wrapper` 反射访问 |
| 注解工具 | 已实现 | 注解创建/克隆/复制、成员值处理（`JavassistUtils`、`visit` 访问器） |
| ClassPool 管理 | 已实现 | `ClassPoolFactory`（默认池、`ClassPath` 感知、按类加载器隔离） |
| 接口简化 | 已实现 | `InterfaceSimplifier`、`SimplifiedClass`、`SimplifiedMethod` |
| 测试 | 已有 | `src/test` 下 JUnit 4 示例（`JavassistProxy_Test`、`JavassistExample1-4` 等） |

## 3. 环境要求与兼容性

| 项目 | 要求 |
| :--- | :--- |
| JDK | 17+ |
| Maven | 3.0+（内置 Maven Wrapper `mvnw`） |
| 依赖 | javassist 3.30.2-GA、commons-lang3 3.20.0、slf4j-api 2.0.18、lombok（provided）；JUnit 4.13.2（测试） |

版本线：

| 分支 | JDK | 版本模式 |
| :--- | :---: | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

## 4. 架构与模块

```text
应用代码 (POJO / 接口 / 注解)
        |
        v
io.github.easy4j.javassist
   |-- proxy/JavassistProxy  ------>  javassist.util.proxy.ProxyFactory
   |-- bytecode/ClassGenerator ----->  ClassPool -> CtClass (字段/方法/构造)
   |-- bytecode/Mixin, Wrapper ----->  反射 + CtClass 访问
   |-- utils/ClassPoolFactory ------>  ClassPool (按 ClassLoader、ClassPath)
   `-- simplify/InterfaceSimplifier->  简化方法包装

        |  (javassist 3.30.2-GA 字节码工具包)
        v
同一 JVM 内运行时生成的类
```

单模块 jar。`io.github.easy4j.javassist` 下的包结构：

| 包 | 内容 |
| :--- | :--- |
| `io.github.easy4j.javassist.proxy` | `JavassistProxy` |
| `io.github.easy4j.javassist.bytecode` | `ClassGenerator`、`CtFieldBuilder`、`CtAnnotationBuilder`、`MethodSignature`、`Mixin`、`Wrapper`、`Proxy` |
| `io.github.easy4j.javassist.bytecode.visit` | `MemberValueCreationVisitor`、`ArrayIndexAssigningVisitor` |
| `io.github.easy4j.javassist.utils` | `ClassPoolFactory`、`JavassistUtils`、`Assert`、`ReflectUtils`、`ReflectionUtils`、`ClassHelper` |
| `io.github.easy4j.javassist.simplify` | `InterfaceSimplifier`、`SimplifiedClass`、`SimplifiedMethod` |
| `io.github.easy4j.javassist.exception` | `NoSuchPropertyException`、`NoSuchMethodException` |

## 5. 安装

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>javassist-plus</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle：

```groovy
implementation 'io.github.easy4j:javassist-plus:2.0.x.x.20260630-SNAPSHOT'
```

快照版本由项目私服提供（见 pom 中 `distributionManagement`）。尚未发布 Maven Central 正式版。

## 6. 快速开始

为已有对象创建代理（真实 API，取自 `JavassistProxy_Test` 的用法）：

```java
import io.github.easy4j.javassist.proxy.JavassistProxy;

A target = new A();
A proxy = JavassistProxy.getProxy(target);

proxy.save("xxx"); // 方法调用会经过 MethodHandler 拦截器：
                   // 打印方法名，然后输出"开启事务"，通过
                   // proceed.invoke(self, args) 委托执行，再输出"提交事务"
```

`getProxy(Class<T>, MethodHandler)` 允许传入自定义的 `javassist.util.proxy.MethodHandler`；生成的子类只拦截内部 `MethodFilter` 匹配的方法（除 `finalize` 外的全部方法）。

## 7. 配置

纯库组件，没有任何配置项或配置前缀。可调点只有：传给 `JavassistProxy` 的 `MethodHandler` 与 `MethodFilter`、从 `ClassPoolFactory` 获取的 `ClassPool`、以及 `ClassGenerator` 上链式调用的构建器。

## 8. 核心用法 / API

### 8.1 使用 `ClassGenerator` 生成类

```java
import io.github.easy4j.javassist.bytecode.ClassGenerator;

ClassGenerator generator = ClassGenerator.newInstance();
generator.setClassName("com.example.DynamicPojo");
generator.setSuperClass(Object.class);
generator.addField("private String name;");
generator.addMethod("public String getName() { return this.name; }");
Class<?> clazz = generator.toClass();   // 在本 JVM 中加载生成的类
```

### 8.2 ClassPool 管理

```java
import io.github.easy4j.javassist.utils.ClassPoolFactory;
import javassist.ClassPool;

// 默认池，已导入 java.util / java.lang / java.lang.reflect
ClassPool pool = ClassPoolFactory.getDefaultPool();

// 绑定当前上下文类加载器的池（ClassPath 感知）
ClassPool pool2 = ClassPoolFactory.getClassPoolForCurrentContextClassLoader();
```

Javassist 概念背景（本库包装的基础）：Javassist 在 Java 语言层面而非原始字节码层面操作。`CtClass` 是类的内存句柄（通过 `ClassPool.get(String classname)` 获取）；`ClassPool` 是持有 `CtClass` 对象并解析其类路径的容器；`CtField` / `CtMethod` / `CtConstructor` 建模类成员，通过 `addField` / `addMethod` / `addConstructor` 添加到 `CtClass`（类执行 `writeFile` / `toClass` / `toBytecode` 后会进入冻结状态，需 `defrost` 后才能继续修改）。本项目中的构建器正是对这一套机制的自动化封装。

## 9. 测试与构建

```bash
./mvnw clean verify
```

构建配置：

- JUnit 4（`junit` 4.13.2）+ Maven Surefire；`src/test` 包含代理示例（`JavassistProxy_Test`、`JavassistProxyExample1/3/4`）与生成示例（`JavassistExample1-4`、`UserGenerator`、`JavassistWebserviceGenerator`）；
- JaCoCo 覆盖率报告 + 行覆盖率检查规则，最低目标 90%（`haltOnFailure=false`）；
- package 阶段附加源码包与 Javadoc 包；
- 提供 `central` 发布 profile（GPG 签名 + Central 发布插件），仅用于正式发布。

## 10. 版本与分支

三条并行版本线，各自绑定一个 JDK 基线：

| 分支 | JDK | 版本模式 | 维护状态 |
| :--- | :---: | :--- | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` | 当前开发线 |
| `feature/2.0.x` | 17 | `2.0.x.*` | 并行维护 |
| `feature/3.0.x` | 21 | `3.0.x.*` | 并行维护 |

本分支快照版本为 `2.0.x.x.20260630-SNAPSHOT`。

## 11. 贡献与许可

欢迎通过 GitHub Issue 或 Pull Request 参与贡献。所有源码基于 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt) 许可。
