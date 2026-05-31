# OpenSpec 目录组织

本文档说明 Bootstrap 项目的 OpenSpec 目录结构和用途。

## 📁 目录结构

```
openspec/
├── README.md                    # 本文件 - OpenSpec 目录说明
├── config.yaml                  # OpenSpec 配置文件
├── .bilingual-spec.md           # 双语规范配置
├── changes/                     # 变更记录目录
│   └── 2026-05-31-complete-both-starters-v1/  # 已归档的变更
├── specs/                       # 规格说明目录
│   └── (各功能模块的规格文档)
└── docs/                        # 项目文档目录
    ├── FRAMEWORK_DEPENDENCY_ANALYSIS.md  # Framework 依赖分析
    └── RELEASE_NOTES.md                 # 发布说明
```

## 📋 目录说明

### changes/
存放所有变更记录和归档的变更。每个变更都有独立的子目录，包含相关的 proposal、design、tasks 等文档。

### specs/
存放各功能模块的规格说明文档，描述系统的需求和功能规范。

### docs/
存放项目相关的文档，包括：
- `FRAMEWORK_DEPENDENCY_ANALYSIS.md` - Framework 项目依赖分析与集成方案
- `RELEASE_NOTES.md` - 项目发布说明和版本历史
- `DOCUMENTATION_SPEC.md` - 文档管理规范（**重要：所有新文档必须遵循此规范**）

## 📖 文档管理规范

所有项目文档的创建、命名、引用都应遵循 `/openspec/docs/DOCUMENTATION_SPEC.md` 中定义的规范。

### 核心规则
- 所有 `.md` 文档必须存储在 `/openspec/docs/` 目录
- 只有 `README.md` 可以放在项目根目录
- 文档命名需遵循规范定义的格式
- 引用文档时使用完整路径 `/openspec/docs/文件名.md`

## 🔗 相关链接

- 项目主 README: `/README.md`
- Framework 项目: `F:/workspace/framework`
