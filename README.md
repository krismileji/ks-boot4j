<div align="center">

# ⚡ Ks-boot4j

**基于 Spring Boot 3 的企业级快速开发框架**

*零侵入 · 轻量化 · 易扩展 · 开箱即用*

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/technologies/)

[快速开始](#-快速开始) · [核心特性](#-核心特性) · [模块说明](#-模块说明) · [文档](#-文档) · [贡献指南](#-贡献)

</div>

---

## 📖 简介

**Ks-boot4j** 是一个基于 [Spring Boot 3](https://spring.io/projects/spring-boot) 的快速开发框架，在 Spring Boot
的基础上只做增强不做改变，更关注提升开发效率和系统稳定性，用起来尽量「开箱即用」。

### 💡 设计理念

- **🎯 开发者友好**：遵循「约定优于配置」原则，通过自动配置减少样板代码
- **🏢 企业级可靠**：提供异常处理、日志记录、性能监控等企业常用能力
- **🔌 高度可扩展**：模块化设计，每个功能模块相对独立，可按需接入
- **🚀 性能优化**：提供连接池、缓存机制、异步处理等常见优化手段

---

## ✨ 核心特性

### 🎨 零侵入设计

无论是新建项目还是已有项目，均可以无缝接入，不影响现有代码结构

### 📦 开箱即用

- ✅ **全局异常处理**：统一的异常捕获和响应封装
- ✅ **请求日志记录**：自动记录 Controller 请求信息，支持链式扩展
- ✅ **跨域支持**：一个注解搞定全局 CORS 配置
- ✅ **XSS 防护**：内置 XSS 过滤器，保护应用安全
- ✅ **参数校验**：集成 AOP 参数校验，支持自定义校验逻辑
- ✅ **统一响应**：标准化的 API 响应格式

### 🎯 现代化技术栈

- **Spring Boot 3.5.8**：基于最新的 Spring Boot 3 版本，兼容 Jakarta EE 9+
- **Java 25**：支持最新的 Java 语言特性
- **MyBatis-Plus 3.5.14**：简化数据库访问逻辑
- **Fastjson2 2.0.60**：高性能 JSON 处理
- **Redis 集成**：提供基于 Redis 的缓存能力，支持 Spring Cache 等常见用法

---

## 🚀 快速开始

### 环境要求

|       类型        |  版本   | 最低支持版本 |
|:---------------:|:-----:|:------:|
|    **Java**     |  25   |   21   |
| **Spring Boot** | 3.5.8 | 3.0.13 |
|    **Maven**    | 3.6+  | 3.6.0  |

### 方式一：使用 Maven BOM（推荐）

**Step 1**：在 `pom.xml` 中引入 BOM 依赖管理

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>host.springboot.framework</groupId>
            <artifactId>krismile-boot3-bom</artifactId>
            <version>0.2.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**Step 2**：按需引入模块（无需指定版本）

```xml

<dependencies>
    <!-- 核心自动配置模块 -->
    <dependency>
        <groupId>host.springboot.framework</groupId>
        <artifactId>krismile-boot3-autoconfigure</artifactId>
    </dependency>

    <!-- MyBatis-Plus 增强模块（可选） -->
    <dependency>
        <groupId>host.springboot.framework</groupId>
        <artifactId>krismile-boot3-starter-mybatisplus</artifactId>
    </dependency>
</dependencies>
```

### 方式二：直接引入依赖

```xml

<dependency>
    <groupId>host.springboot.framework</groupId>
    <artifactId>krismile-boot3-autoconfigure</artifactId>
    <version>0.2.0</version>
</dependency>
```

如需使用 Redis 功能，也可以直接引入：

```xml

<dependency>
    <groupId>host.springboot.framework</groupId>
    <artifactId>krismile-boot3-starter-redis</artifactId>
    <version>0.2.0</version>
</dependency>
```

### 启用功能

在 Spring Boot 启动类上添加注解：

```java
import host.springboot.framework.autoconfigure.web.annotation.EnableDefaultGlobalCors;
import host.springboot.framework.context.advice.annotation.EnableGlobalControllerAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGlobalControllerAdvice  // 启用全局异常处理
@EnableDefaultGlobalCors       // 启用全局跨域支持
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### Hello World 示例

```java
import host.springboot.framework3.core.response.R;
import host.springboot.framework3.core.response.vo.SingleVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public SingleVO<String> hello(@RequestParam String name) {
        return R.okSingle("Hello, " + name + "!");
    }
}
```

访问：`http://localhost:8080/api/hello?name=World`

响应：

```json
{
  "success": true,
  "errorCode": "SUCCESS",
  "errorMessage": "成功",
  "data": "Hello, World!"
}
```

---

## 📦 模块说明

|                   模块                   |    职责    |             主要功能              |
|:--------------------------------------:|:--------:|:-----------------------------:|
|         **krismile-boot3-bom**         |  依赖版本管理  |        统一管理框架内各模块的版本依赖        |
|        **krismile-boot3-core**         | 核心工具类和常量 |    常量定义、枚举、异常类、工具类、统一响应封装     |
|       **krismile-boot3-context**       | 上下文和中间件  |    全局异常处理、AOP 日志、过滤器、类型转换器    |
|    **krismile-boot3-autoconfigure**    |   自动配置   |        属性配置、条件装配、组件注册         |
| **krismile-boot3-starter-mybatisplus** |   数据访问   |  MyBatis-Plus 增强、服务层抽象、自动填充   |
|    **krismile-boot3-starter-redis**    |   缓存支持   | RedisTemplate、Spring Cache 集成 |

---

## 📚 文档

详细文档请访问：[文档中心](https://www.springboot.host)

## 🌟 为什么选择 Ks-boot4j?

### 对比传统开发方式

|       功能        |           传统方式           |        Ks-boot4j         |
|:---------------:|:------------------------:|:------------------------:|
|   **全局异常处理**    | 手动编写 `@ControllerAdvice` |         ✅ 一个注解启用         |
|   **请求日志记录**    |       手动编写 AOP 切面        |      ✅ 自动记录，支持链式扩展       |
|    **跨域配置**     |    手动配置 `CorsFilter`     |         ✅ 一个注解搞定         |
|   **XSS 防护**    |       手动编写 Filter        |         ✅ 配置即可启用         |
| **Service 层容错** |        手动 null 检查        | ✅ 提供 Ignore/Validate 双模式 |
|    **自动填充**     |         手动设置字段值          |       ✅ 自动填充时间和用户        |
|   **统一响应格式**    |           手动封装           |     ✅ 开箱即用的 `R` 工具类      |

### 对比其他框架

- **相比 Spring Boot**：增强不改变，保持原生生态兼容性
- **相比 Ruoyi**：更轻量，模块化设计，按需引入
- **相比 JeecgBoot**：更简洁，专注核心功能，易于理解和扩展

---

## 🤝 贡献

我们欢迎任何形式的参与与反馈，如果你准备提 PR，推荐按照下面的流程来：

1. **Fork** 本仓库
2. 创建特性分支（`git checkout -b feature/develop`）
3. 提交你的更改（`git commit -m 'Add some develop'`）
4. 推送到远程分支（`git push origin feature/develop`）
5. 提交 **Pull Request**

### 贡献小提示

- 提交代码前请确保通过现有测试
- 保持代码风格与现有项目一致
- 提交信息尽量精准简洁
- 新功能配套补充文档或示例

---

## 👥 开发团队

- **JiYinchuan** - *项目负责人* - [jyc@krismile.cn](mailto:jyc@krismile.cn)

---

## 🔗 相关链接

- [官方网站](https://ks.springboot.host)
- [文档中心](https://ks.springboot.host)
- [GitHub](https://github.com/krismileji/ks-boot4j)
- [Gitee](https://gitee.com/krismileJ/ks-boot4j)

---

## 💬 交流与支持

如果你在使用过程中遇到问题，可以通过以下方式获取帮助：

- 📧 邮件：[jyc@krismile.cn](mailto:jyc@krismile.cn)
- 💻 Issues：[GitHub Issues](https://github.com/krismileji/ks-boot4j/issues)
- 🐛 Bug 反馈：[提交 Bug](https://github.com/krismileji/ks-boot4j/issues/new)

---

## ⭐ Star History

如果这个项目对你有帮助，请给我们一个 ⭐ Star 支持！

---

<div align="center">

**让开发更简单，让代码更优雅** 🚀

Made with ❤️ by [Krismile](https://www.krismile.cn)

</div>