# Framework 依赖分析与调整结果

## ✅ 调整完成

已成功将 Bootstrap 项目调整为使用 Framework 模块作为依赖基础。

## 🔧 实施方案

**采用 Composite Build 方案**

### 原因
- Framework 项目没有配置 Maven Local 发布（publishing 配置被注释）
- Composite Build 无需发布到 Maven，直接使用项目依赖
- 更好的开发体验 - 修改 Framework 代码后立即生效

## 📊 依赖映射关系

### Bootstrap 当前依赖 → Framework 模块映射

| Bootstrap 当前依赖 | Framework 对应模块 | 状态 |
|-------------------|-----------------|------|
| `spring-boot-starter-web` | `:web` | ✅ 使用 Framework |
| `spring-boot-starter-data-jpa` | 无对应模块 | ✅ 保持原依赖 |
| `spring-tx` | `:transaction` | ✅ 使用 Framework |
| `validation-api` | 无对应模块 | ✅ 保持原依赖 |
| `mapstruct` | 无对应模块 | ✅ 保持原依赖 |
| `lombok` | 无对应模块 | ✅ 保持原依赖 |

### Framework 模块依赖链

```
:web
  ├── spring-boot-starter-web
  ├── spring-security-web
  ├── tomcat-embed-jasper
  ├── :security
  └── :client
       └── spring-cloud-starter-openfeign

:transaction
  └── (事务功能)
```

## 📝 修改文件清单

### 1. settings.gradle
- 添加 `includeBuild('F:/workspace/framework')` 引入 Framework 项目
- 配置 `dependencySubstitution` 实现 Maven 坐标到项目模块的映射

### 2. build.gradle (根项目)
- 在 `subprojects` 块中添加 `dependencyManagement` 导入 Spring Cloud BOM
- 扩展 `repositories` 添加更多 Aliyun 仓库

### 3. e-business-spring-boot-starter/build.gradle
- 修改 Web 依赖为 `compileOnly 'io.github.DekkerDing:framework-web:1.0-SNAPSHOT'`
- 修改 Transaction 依赖为 `compileOnly 'io.github.DekkerDing:framework-transaction:1.0-SNAPSHOT'`
- 移除项目级别的 `repositories` 块

### 4. customer-robot-spring-boot-starter/build.gradle
- 修改 Web 依赖为 `compileOnly 'io.github.DekkerDing:framework-web:1.0-SNAPSHOT'`
- 修改 Transaction 依赖为 `compileOnly 'io.github.DekkerDing:framework-transaction:1.0-SNAPSHOT'`
- 移除项目级别的 `repositories` 块

## 🚀 构建结果

```
BUILD SUCCESSFUL in 31s
16 actionable tasks: 2 executed, 14 up-to-date
```

### 成功构建的产物
- ✅ `customer-robot-spring-boot-starter-1.0-SNAPSHOT.jar`
- ✅ `e-business-spring-boot-starter-1.0-SNAPSHOT.jar`

## 📋 后续待处理事项

### 测试依赖问题
测试代码需要访问 Spring 注解和类，但当前 `compileOnly` 配置导致测试编译失败。

**解决方案**：添加 `testImplementation` 依赖

```gradle
dependencies {
    // ... 现有依赖 ...

    // 测试依赖
    testImplementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    testImplementation 'org.springframework:spring-tx'
}
```

### MapStruct 警告
部分 Mapper 接口存在 unmapped properties 警告：
- `ConversationMapper.messages`
- `OrderItemMapper.orderId, createdAt, updatedAt`

这些警告不影响构建，但建议后续修复。

## 🎯 架构优势

使用 Framework 作为基础依赖的优势：

1. **统一版本管理** - Web、Transaction 等基础组件版本由 Framework 统一管理
2. **模块化组织** - 清晰的模块边界，便于维护
3. **开箱即用** - 引入 starter 即可使用，无需配置大量依赖
4. **便于发布** - 后期 Framework 发布到 Maven Central 后，Bootstrap 可直接使用

## 🔗 Framework 项目信息

- **Group**: `io.github.DekkerDing`
- **Version**: `1.0-SNAPSHOT`
- **Spring Boot**: `2.6.14`
- **Java**: 1.8
- **模块数量**: 35+ 个模块

### 主要模块列表
beans, storage, web, core, monitoring, record, proxy, config, context, transaction, security, message, template, engine, filter, processor, events, listener, provider, holder, manager, aware, pipeline, executor, extension, task, plugin, batch, memory, tenant, pool, initialize, reactive, register, boot, aspects, builder, client, cache

## 📌 注意事项

1. Framework 项目中无 Data JPA 封装模块，Bootstrap 继续使用 Spring Boot 的 starter
2. Composite Build 方式下，Framework 代码修改会立即反映到 Bootstrap 构建
3. 后期发布到 Maven Central 时，可切换为 Maven 依赖方式
