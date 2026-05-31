# 智能对话引擎规格说明

## 功能概述

提供智能对话能力，包括意图识别、实体提取、多轮对话管理、知识库问答等功能。

## 需求描述

### 意图识别

系统能够识别用户输入的意图类型，支持以下意图：

- `QUERY_PRODUCT` - 商品咨询
- `QUERY_ORDER` - 订单查询
- `QUERY_PAYMENT` - 支付问题
- `QUERY_DELIVERY` - 物流查询
- `REFUND_REQUEST` - 退款申请
- `COMPLAINT` - 投诉建议
- `GREETING` - 问候
- `UNKNOWN` - 未知意图

### 实体提取

从用户输入中提取关键实体：

- 商品信息：商品ID、商品名称、分类
- 订单信息：订单号、订单状态
- 用户信息：用户ID、手机号
- 时间信息：日期、时间段
- 数量信息：数量、金额

### 知识库匹配

基于关键词和语义相似度匹配知识库答案：

- 关键词匹配（精确匹配）
- TF-IDF 相似度匹配
- 余弦相似度计算
- 置信度阈值过滤（默认 0.7）

### 多轮对话

支持上下文管理：

- 对话历史记录（最近 10 轮）
- 上下文实体提取
- 槽位填充机制
- 对话状态管理

## 接口定义

### 输入接口

```java
public interface DialogueInput {
    String getUserId();      // 用户ID
    String getMessage();     // 用户消息
    String getSessionId();   // 会话ID
    String getContext();     // 上下文信息（可选）
}
```

### 输出接口

```java
public interface DialogueOutput {
    String getResponse();     // 机器人回复
    String getIntent();        // 识别的意图
    Double getConfidence();   // 置信度
    Map<String, Object> getEntities();  // 提取的实体
    Boolean getEscalate();    // 是否需要转人工
}
```

## API 端点

| 端点 | 方法 | 描述 | 认证 |
|------|------|------|------|
| /api/dialogue/chat | POST | 发送消息 | 是 |
| /api/dialogue/intents | GET | 获取意图列表 | 是 |
| /api/dialogue/history | GET | 获取对话历史 | 是 |

## 数据模型

### 意图模型

```java
public class DialogueIntent {
    private Long id;
    private String intentName;      // 意图名称
    private String intentCode;      // 意图编码
    private String keywords;        // 关键词（逗号分隔）
    private String responseTemplate; // 回复模板
    private Double confidenceThreshold; // 置信度阈值
    private Integer priority;        // 优先级
}
```

### 对话日志模型

```java
public class DialogueLog {
    private Long id;
    private Long conversationId;
    private String userId;
    private String userInput;
    private String botResponse;
    private String intent;
    private Double confidence;
    private Map<String, Object> entities;
    private LocalDateTime createdAt;
}
```

## 业务规则

### 意图识别规则

1. 关键词匹配优先级高于语义匹配
2. 置信度低于阈值时返回 `UNKNOWN` 意图
3. 连续 3 次未知意图建议转人工
4. 投诉类意图直接转人工

### 知识库匹配规则

1. 先精确匹配问题
2. 再计算语义相似度
3. 返回相似度最高的答案
4. 相似度低于 0.5 时回复兜底话术

### 多轮对话规则

1. 保留最近 10 轮对话历史
2. 上下文实体按时间倒序匹配
3. 槽位填充失败时追问用户
4. 对话超时时间 30 分钟

## 技术约束

- 单次对话响应时间 < 500ms
- 支持并发对话数 > 1000
- 意图识别准确率 > 85%
- 知识库匹配准确率 > 80%

## 配置项

```yaml
customer-robot:
  dialogue:
    enabled: true
    confidence-threshold: 0.7
    max-history-turns: 10
    session-timeout-minutes: 30
    unknown-escalate-threshold: 3
```
