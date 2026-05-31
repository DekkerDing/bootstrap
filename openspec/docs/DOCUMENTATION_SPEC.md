# Bootstrap 项目文档管理规范

本文档定义 Bootstrap 项目中所有 Markdown (.md) 文档的管理规范。

## 📁 文档存储位置

所有项目相关的文档文件必须存放在 `/openspec/docs/` 目录下。

**例外：** 只有 `README.md` 可以放在项目根目录，作为项目的主要入口说明。

## 📝 文档分类与命名规范

### 1. 分析与设计文档

**命名格式：** `[主题]_ANALYSIS.md` 或 `[主题]_DESIGN.md`

**示例：**
- `FRAMEWORK_DEPENDENCY_ANALYSIS.md` - Framework 依赖分析
- `ARCHITECTURE_DESIGN.md` - 架构设计文档

### 2. 发布说明文档

**命名格式：** `RELEASE_NOTES.md`

**用途：** 记录项目版本历史、变更内容、发布信息

### 3. 功能规范文档

**命名格式：** `[功能名称]_SPEC.md`

**示例：**
- `CUSTOMER_ROBOT_SPEC.md` - 客服机器人功能规范
- `ECOMMERCE_SPEC.md` - 电商功能规范

### 4. 集成指南文档

**命名格式：** `[主题]_GUIDE.md`

**示例：**
- `FRAMEWORK_INTEGRATION_GUIDE.md` - Framework 集成指南
- `DEPLOYMENT_GUIDE.md` - 部署指南

### 5. 问题记录文档

**命名格式：** `[主题]_ISSUES.md` 或 `[主题]_TROUBLESHOOTING.md`

**示例：**
- `BUILD_ISSUES.md` - 构建问题记录
- `DEPENDENCY_TROUBLESHOOTING.md` - 依赖问题排查

## 🔗 文档引用规范

### 在代码注释中引用

使用相对于项目根的路径：

```java
/**
 * 详见 Framework 集成指南
 * See: /openspec/docs/FRAMEWORK_INTEGRATION_GUIDE.md
 */
```

### 在文档中引用

使用相对路径：

```markdown
## 相关文档

- [Framework 依赖分析](/openspec/docs/FRAMEWORK_DEPENDENCY_ANALYSIS.md)
- [发布说明](/openspec/docs/RELEASE_NOTES.md)
```

### 在 OpenSpec 变更中引用

在 proposal、design、tasks 等文档中引用项目文档：

```markdown
## 参考文档

- Framework 集成方案: /openspec/docs/FRAMEWORK_DEPENDENCY_ANALYSIS.md
```

## 🤖 模型读取指南

### 模型应遵循的规则

1. **查找文档时，首先检查 `/openspec/docs/` 目录**

2. **创建新文档时，默认放置在 `/openspec/docs/` 目录**

3. **文档命名遵循本规范的命名格式**

4. **引用文档时使用完整路径 `/openspec/docs/文件名.md`**

### 模型工作流程

当模型需要处理文档相关任务时：

1. 检查文档是否已存在于 `/openspec/docs/`
2. 如果不存在，按照命名规范创建新文档
3. 如果需要修改，直接编辑 `/openspec/docs/` 中的文件
4. 更新本规范或 `openspec/README.md`（如添加新文档类型）

## 📋 文档模板

### 分析文档模板

```markdown
# [主题] 分析

## 背景
<!-- 描述为什么需要这个分析 -->

## 现状
<!-- 描述当前的情况 -->

## 分析
<!-- 详细分析内容 -->

## 结论
<!-- 分析结论和建议 -->
```

### 设计文档模板

```markdown
# [主题] 设计

## 目标
<!-- 设计目标 -->

## 方案
<!-- 详细设计方案 -->

## 实施步骤
<!-- 具体实施步骤 -->

## 风险与注意事项
<!-- 潜在风险和注意事项 -->
```

### 指南文档模板

```markdown
# [主题] 指南

## 概述
<!-- 简要说明 -->

## 前置条件
<!-- 使用本指南的前置条件 -->

## 步骤
<!-- 详细步骤 -->

## 常见问题
<!-- 常见问题和解决方案 -->
```

## 🔧 维护规范

### 更新文档

1. 文档内容变更时，直接编辑 `/openspec/docs/` 中的文件
2. 在文档顶部更新 `最后更新时间`（如有）
3. 重大变更应在 `RELEASE_NOTES.md` 中记录

### 归档文档

文档过时但需要保留历史记录时：

1. 创建 `/openspec/docs/archived/` 目录
2. 将文档移动到该目录
3. 在 `RELEASE_NOTES.md` 中记录归档信息

### 删除文档

文档确认不再需要时：

1. 确认文档已过时且无保留价值
2. 在本规范或 `openspec/README.md` 中移除相关引用
3. 删除文档文件

## 📌 规范更新

本规范本身也需要维护：

- 新增文档类型时，更新"文档分类与命名规范"部分
- 发现问题时及时修正
- 重大变更需要在 `RELEASE_NOTES.md` 中记录

---

**文档版本：** 1.0  
**最后更新：** 2026-05-31  
**维护者：** Bootstrap 项目组
