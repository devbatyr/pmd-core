# PMD Core

A lightweight Java library for integrating PMD static code analysis via its CLI, with support for configurable rulesets, suppressions, and Markdown-formatted output.

## ✨ Features
- 🔧 Run PMD CLI programmatically
- 📄 Supports custom rulesets and suppression files
- 📦 Simple configuration via builder-style API
- 📝 Returns detailed violations in a rich model (`PmdResponse`)
- ✅ Easily integrable into GitHub/GitLab/Bitbucket bots or review tools

---

## 📥 Installation

### Maven

```xml
<dependency>
  <groupId>com.shakenov</groupId>
  <artifactId>pmd-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
  implementation 'com.shakenov:pmd-core:1.0.0'
}
```

---

## 🚀 Usage

```java
PmdConfig config = PmdConfig.builder()
    .code("public class Test { void x() {} }")
    .fileName("Test.java")
    .rulesetPath("/path/to/ruleset.xml")
    .suppressionsPath("/path/to/suppressions.xml") // optional
    .pmdPath("/path/to/pmd") // optional, default is "pmd"
    .build();

PmdService pmdService = new PmdService();
PmdResponse response = pmdService.run(config);

System.out.println(response.getFormattedOutput());
```

---

## 📊 Example Output

```
🚨 Violations in file `Test.java`:

🔸 **UnusedPrivateMethod** at line 5  
_Description:_ Avoid unused private methods such as 'x'  
[Documentation](https://docs.pmd-code.org/pmd-doc-7.7.0/pmd_rules_java.html#unusedprivatemethod)
```

---

## 🛠 Requirements
- Java 17 or higher
- PMD CLI 7.7.0 (must be installed separately)

---

## 📄 License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## 🙌 Credits

Made️ by [Batyrkhan Shakenov](https://github.com/devbatyr)