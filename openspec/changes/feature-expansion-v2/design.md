# 功能扩展 v2.0 设计文档

## 系统架构设计

### 整体架构

```
┌─────────────────────────────────────────────────────────────────────┐
│                          Bootstrap v2.0                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │                    customer-robot-spring-boot-starter v2.0     │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Domain Layer                                                 │  │
│  │  ├── Customer, Conversation, Message, Session (v1.0)          │  │
│  │  ├── KnowledgeBase, DialogueIntent, DialogueLog (NEW)         │  │
│  │  ├── Ticket, TicketFlow, TicketTag (NEW)                    │  │
│  │  ├── CustomerProfile, CustomerTag, CustomerBehavior (NEW)    │  │
│  │  ├── QuickReply, ReplyCategory (NEW)                         │  │
│  │  └── ConversationMetrics, AgentPerformance (NEW)             │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Service Layer                                                │  │
│  │  ├── DialogueService - 智能对话引擎                           │  │
│  │  ├── KnowledgeBaseService - 知识库管理                       │  │
│  │  ├── TicketService - 工单流转                                │  │
│  │  ├── CustomerProfileService - 客户画像                       │  │
│  │  ├── QuickReplyService - 快捷回复                            │  │
│  │  └── AnalyticsService - 统计分析                              │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Repository Layer                                             │  │
│  │  ├── JPA Repository (新模块使用 JPA)                         │  │
│  │  └── MyBatis Mapper (v1.0 模块保持兼容)                      │  │
│  └─────────────────────────────────────────────────────────────┘  │
│                                                                      │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │                    e-business-spring-boot-starter v2.0        │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Domain Layer                                                 │  │
│  │  ├── Product, Order, Payment, User (v1.0)                    │  │
│  │  ├── Cart, CartItem (NEW)                                    │  │
│  │  ├── Coupon, UserCoupon, SeckillActivity, GroupBuy (NEW)     │  │
│  │  ├── ProductReview, ServiceReview (NEW)                      │  │
│  │  ├── Delivery, Tracking, FreightTemplate (NEW)              │  │
│  │  ├── Refund, Return, Exchange (NEW)                          │  │
│  │  ├── Member, PointsLog, Benefit (NEW)                        │  │
│  │  ├── SearchLog, RecommendLog (NEW)                            │  │
│  │  └── ProductIndex (ES 文档) (NEW)                             │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Service Layer                                                │  │
│  │  ├── CartService - 购物车管理 (Redis + DB)                    │  │
│  │  ├── CouponService - 优惠券引擎                               │  │
│  │  ├── SeckillService - 秒杀引擎 (Redis 分布式锁)              │  │
│  │  ├── GroupBuyService - 拼团引擎                               │  │
│  │  ├── ReviewService - 评价管理                                 │  │
│  │  ├── DeliveryService - 物流管理                               │  │
│  │  ├── AfterSalesService - 售后服务                             │  │
│  │  ├── MemberService - 会员体系                                 │  │
│  │  ├── SearchService - 搜索引擎 (ES)                           │  │
│  │  └── RecommendService - 推荐引擎                              │  │
│  ├─────────────────────────────────────────────────────────────┤  │
│  │  Repository Layer                                             │  │
│  │  ├── JPA Repository (新模块)                                 │  │
│  │  ├── MyBatis Mapper (复杂查询)                                │  │
│  │  ├── Redis Cache (购物车、秒杀)                               │  │
│  │  └── Elasticsearch Repository (搜索)                           │  │
│  └─────────────────────────────────────────────────────────────┘  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### 分层架构设计

```
┌─────────────────────────────────────────────────────────────┐
│                      API Layer                               │
│              (Controller + @RestController)                  │
├─────────────────────────────────────────────────────────────┤
│                      Service Layer                            │
│                 (Business Logic + @Service)                  │
├─────────────────────────────────────────────────────────────┤
│                   Repository Layer                            │
│              (Data Access + Repository)                       │
├──────────────────┬──────────────────────────────────────────┤
│                  │                                             │
│   JPA/MyBatis    │      Redis / ES / RabbitMQ                │
│   (Primary DB)   │      (Cache / Search / MQ)                 │
└──────────────────┴──────────────────────────────────────────┘
```

## 模块详细设计

### 一、客服机器人模块设计

#### 1.1 智能对话引擎

```java
┌─────────────────────────────────────────────────────────────┐
│                    DialogueEngine                            │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  输入: 用户消息                                               │
│       │                                                       │
│       ▼                                                       │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │ 意图识别     │───▶│ 实体提取     │───▶│ 知识匹配     │     │
│  │ Intent      │    │ Entity      │    │ Knowledge    │     │
│  │ Recognition │    │ Extraction  │    │ Matching     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│         │                  │                  │               │
│         └──────────────────┼──────────────────┘               │
│                            │                                   │
│                            ▼                                   │
│                   ┌─────────────┐                             │
│                   │ 答案生成     │                             │
│                   │ Response    │                             │
│                   │ Generation  │                             │
│                   └─────────────┘                             │
│                            │                                   │
│                            ▼                                   │
│  输出: 机器人回复                                             │
│                                                              │
└─────────────────────────────────────────────────────────────┘

算法设计:
1. 意图识别: 关键词匹配 + TF-IDF 相似度
2. 实体提取: 正则表达式 + 命名实体识别
3. 知识匹配: 向量空间模型 + 余弦相似度
```

#### 1.2 工单系统设计

```java
工单状态机:
┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐
│ CREATED│─▶│PENDING │─▶│ASSIGNED│─▶│RESOLVED│─▶│ CLOSED │
└────────┘  └────────┘  └────────┘  └────────┘  └────────┘
     │           │           │           │           │
     └───────────┴───────────┴───────────┴───────────┘
                      超时/关闭

工单流转规则:
- 自动分配: 按客服工作量负载均衡
- 手动分配: 管理员指定处理人
- 升级规则: 超时自动升级到上级
- 回访规则: 解决后 N 天自动回访
```

#### 1.3 客户画像设计

```java
RFM 模型实现:
┌─────────────────────────────────────────────────────────────┐
│                    RFM Analyzer                               │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  Recency (最近一次消费时间)                                   │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐                       │
│  │ R ≤ 30  │  │30 < R ≤90│  │  R > 90 │                       │
│  │  天     │  │   天    │  │   天    │                       │
│  │  5 分   │  │   3 分   │  │   1 分   │                       │
│  └─────────┘  └─────────┘  └─────────┘                       │
│                                                              │
│  Frequency (消费频率)                                         │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐                       │
│  │ F ≥ 5   │  │3 ≤ F < 5│  │  F < 3  │                       │
│  │  次/年  │  │  次/年  │  │  次/年  │                       │
│  │  5 分   │  │   3 分   │  │   1 分   │                       │
│  └─────────┘  └─────────┘  └─────────┘                       │
│                                                              │
│  Monetary (消费金额)                                          │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐                       │
│  │ M ≥ 5000│  │500≤M<5000│  │ M < 500 │                       │
│  │   元    │  │   元    │  │   元    │                       │
│  │  5 分   │  │   3 分   │  │   1 分   │                       │
│  └─────────┘  └─────────┘  └─────────┘                       │
│                                                              │
│  客户分层:                                                    │
│  - 重要价值客户 (R≥4 AND F≥4 AND M≥4): VIP 专属服务           │
│  - 重要发展客户 (R≥4 AND F≥4): 营销刺激                      │
│  - 重要保持客户 (R≥4 AND M≥4): 关怀挽回                      │
│  - 一般发展客户 (F≥2): 常规营销                              │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### 二、电商模块设计

#### 2.1 购物车系统设计

```java
┌─────────────────────────────────────────────────────────────┐
│                   Cart Architecture                           │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────────────────────────────────────────────┐     │
│  │              Redis Cache Layer                       │     │
│  │  ┌─────────────┐    ┌─────────────┐                 │     │
│  │  │ Cart Data   │    │ Cart TTL    │                 │     │
│  │  │ (Hash)      │    │ (7 Days)    │                 │     │
│  │  └─────────────┘    └─────────────┘                 │     │
│  └─────────────────────────────────────────────────────┘     │
│                    │                                        │
│                    │ 同步                                   │
│                    ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │              Database Layer                            │     │
│  │  ┌─────────────┐    ┌─────────────┐                 │     │
│  │  │ Cart Table  │    │ Cart Item   │                 │     │
│  │  │ (持久化)    │    │ (明细)     │                 │     │
│  │  └─────────────┘    └─────────────┘                 │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                              │
│  操作策略:                                                   │
│  - 读取: 先读 Redis，未命中读 DB                             │
│  - 写入: 先写 Redis，异步写 DB                               │
│  - 删除: 先删 Redis，异步删 DB                               │
│                                                              │
└─────────────────────────────────────────────────────────────┘

购物车数据结构:
{
  "cart:userId": {
    "items": [
      {
        "productId": 123,
        "skuId": 456,
        "quantity": 2,
        "selected": true,
        "price": 99.00,
        "addedAt": "2026-05-31T10:00:00"
      }
    ],
    "totalAmount": 198.00,
    "totalQuantity": 2,
    "updatedAt": "2026-05-31T10:00:00"
  }
}
```

#### 2.2 秒杀系统设计

```java
┌─────────────────────────────────────────────────────────────┐
│               Seckill System Architecture                     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  客户端请求:                                                  │
│  ┌─────────────────────────────────────────────────────┐     │
│  │  POST /api/seckill/{activityId}/order               │     │
│  └─────────────────────────────────────────────────────┘     │
│                      │                                        │
│                      ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │           限流层 (Rate Limiter)                       │     │
│  │  - 令牌桶算法 (10000 QPS)                             │     │
│  │  - 用户限流 (1 次/秒)                                 │     │
│  └─────────────────────────────────────────────────────┘     │
│                      │                                        │
│                      ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │         Redis 预扣库存层                              │     │
│  │  ┌───────────────────────────────────────────────┐ │     │
│  │  │ DECRBY seckill:stock:{activityId} 1           │ │     │
│  │  │ → 返回剩余库存                                  │ │     │
│  │  └───────────────────────────────────────────────┘ │     │
│  │  ┌───────────────────────────────────────────────┐ │     │
│  │  │ SETEX seckill:order:{userId}:{activityId} 1 60│ │     │
│  │  │ → 防止重复秒杀                                  │     │
│  │  └───────────────────────────────────────────────┘ │     │
│  └─────────────────────────────────────────────────────┘     │
│                      │                                        │
│                      ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │           消息队列层 (RabbitMQ)                        │     │
│  │  ┌───────────────────────────────────────────────┐ │     │
│  │  │ seckill.order.queue                            │ │     │
│  │  │ → 异步创建订单                                   │ │     │
│  │  └───────────────────────────────────────────────┘ │     │
│  └─────────────────────────────────────────────────────┘     │
│                      │                                        │
│                      ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │           订单服务层                                  │     │
│  │  - 创建秒杀订单                                       │     │
│  │  - 设置订单状态 (预占库存)                            │     │
│  │  - 发送支付消息                                       │     │
│  └─────────────────────────────────────────────────────┘     │
│                      │                                        │
│                      ▼                                        │
│  ┌─────────────────────────────────────────────────────┐     │
│  │           支付超时处理                                 │     │
│  │  - 15 分钟未支付 = 释放库存                          │     │
│  │  - 支付成功 = 扣减库存                               │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                              │
└─────────────────────────────────────────────────────────────┘

秒杀活动时间轴:
┌────────┐    ┌────────┐    ┌────────┐    ┌────────┐
│ 预热   │───▶│ 活动中 │───▶│ 已结束 │───▶│ 清理   │
│预热期  │    │秒杀期  │    │结算期  │    │归档期  │
└────────┘    └────────┘    └────────┘    └────────┘
```

#### 2.3 搜索引擎设计

```java
┌─────────────────────────────────────────────────────────────┐
│              Search Engine Architecture                       │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  数据同步:                                                   │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐      │
│  │   MySQL     │───▶│  Canal Binlog│───▶│Elasticsearch│      │
│  │ (Product)   │    │   增量同步    │    │   (Index)   │      │
│  └─────────────┘    └─────────────┘    └─────────────┘      │
│                           │                               │       │
│                           │ 全量同步                      │       │
│                           ▼                               │       │
│                    ┌─────────────┐                        │       │
│                    │定时任务全量同步│                        │       │
│                    └─────────────┘                        │       │
│                                                              │
│  ES 索引结构:                                               │
│  {                                                           │
│    "id": "123",                                             │
│    "name": "测试商品",                                       │
│    "name_pinyin": "ceshangpin",                             │
│    "category": "电子产品",                                   │
│    "categoryId": 1,                                         │
│    "price": 99.99,                                          │
│    "sales": 1000,                                          │
│    "stock": 500,                                           │
│    "status": "ACTIVE",                                     │
│    "tags": ["热销", "新品"],                                │
│    "attributes": {                                          │
│      "brand": "Apple",                                      │
│      "model": "iPhone 15"                                  │
│    },                                                       │
│    "createdAt": "2026-05-31T10:00:00",                      │
│    "updatedAt": "2026-05-31T10:00:00"                      │
│  }                                                          │
│                                                              │
│  搜索查询 DSL:                                              │
│  {                                                           │
│    "query": {                                               │
│      "bool": {                                             │
│        "should": [                                         │
│          { "match": { "name": "关键词" }},               │
│          { "match": { "name_pinyin": "jianpinyin" }},     │
│          { "match": { "tags": "关键词" }}                │
│        ]                                                   │
│      }                                                     │
│    },                                                      │
│    "filter": {                                             │
│      "term": { "status": "ACTIVE" },                       │
│      "range": { "price": { "gte": 10, "lte": 1000 }}       │
│    },                                                      │
│    "sort": [                                               │
│      { "_score": { "order": "desc" }},                     │
│      { "sales": { "order": "desc" }}                       │
│    ]                                                       │
│  }                                                          │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

#### 2.4 推荐系统设计

```java
┌─────────────────────────────────────────────────────────────┐
│             Recommendation Architecture                        │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  推荐场景:                                                   │
│  ┌─────────────────────────────────────────────────────┐     │
│  │  场景          │ 算法              │ 输出数量        │     │
│  ├─────────────────────────────────────────────────────┤     │
│  │  首页推荐      │ 个性化协同过滤    │ 20            │     │
│  │  详情页推荐    │ 相似商品/看了又看  │ 10            │     │
│  │  购物车推荐    │ 凑单/关联推荐     │ 5             │     │
│  │  支付成功推荐  │ 再来一单          │ 10            │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                              │
│  协同过滤算法:                                              │
│  ┌─────────────────────────────────────────────────────┐     │
│  │  User-Based CF:                                      │     │
│  │    1. 找到相似用户 (余弦相似度)                        │     │
│  │    2. 推荐相似用户喜欢但当前用户未看过的商品          │     │
│  │                                                       │     │
│  │  Item-Based CF:                                      │     │
│  │    1. 找到相似商品 (共同购买次数)                      │     │
│  │    2. 推荐与购买商品相关的其他商品                      │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                              │
│  推荐流程:                                                   │
│  用户行为 → 实时更新用户向量 → Redis 缓存推荐结果          │
│       ↓                                                    │
│  定时离线计算 → 模型训练 → 更新推荐池                     │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## 数据库设计

### 客服机器人扩展表

```sql
-- 知识库表
CREATE TABLE knowledge_base (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(500) NOT NULL COMMENT '问题',
    answer TEXT NOT NULL COMMENT '答案',
    category_id BIGINT COMMENT '分类ID',
    tags VARCHAR(200) COMMENT '标签',
    priority INT DEFAULT 0 COMMENT '优先级',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态',
    created_by BIGINT COMMENT '创建人',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    FULLTEXT idx_question (question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识库表';

-- 对话意图表
CREATE TABLE dialogue_intent (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    intent_name VARCHAR(100) NOT NULL COMMENT '意图名称',
    intent_code VARCHAR(50) NOT NULL COMMENT '意图编码',
    keywords TEXT COMMENT '关键词',
    response_template TEXT COMMENT '回复模板',
    confidence_threshold DECIMAL(3,2) DEFAULT 0.7 COMMENT '置信度阈值',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_code (intent_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话意图表';

-- 工单表
CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_no VARCHAR(50) NOT NULL COMMENT '工单编号',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    conversation_id BIGINT COMMENT '关联对话ID',
    type VARCHAR(50) NOT NULL COMMENT '工单类型',
    priority VARCHAR(20) DEFAULT 'NORMAL' COMMENT '优先级',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态',
    title VARCHAR(200) COMMENT '标题',
    description TEXT COMMENT '描述',
    assigned_to BIGINT COMMENT '分配给',
    resolved_by BIGINT COMMENT '处理人',
    resolution TEXT COMMENT '解决方案',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    resolved_at DATETIME COMMENT '解决时间',
    INDEX idx_customer (customer_id),
    INDEX idx_status (status),
    INDEX idx_assigned (assigned_to)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- 工单流转表
CREATE TABLE ticket_flow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_id BIGINT NOT NULL COMMENT '工单ID',
    from_status VARCHAR(20) NOT NULL COMMENT '原状态',
    to_status VARCHAR(20) NOT NULL COMMENT '新状态',
    operator_id BIGINT COMMENT '操作人ID',
    remark VARCHAR(500) COMMENT '备注',
    created_at DATETIME NOT NULL,
    INDEX idx_ticket (ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单流转表';

-- 客户画像表
CREATE TABLE customer_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    r_score INT DEFAULT 0 COMMENT '最近消费得分',
    f_score INT DEFAULT 0 COMMENT '消费频率得分',
    m_score INT DEFAULT 0 COMMENT '消费金额得分',
    level VARCHAR(20) DEFAULT 'NORMAL' COMMENT '客户等级',
    tags VARCHAR(500) COMMENT '客户标签',
    last_order_date DATETIME COMMENT '最后下单时间',
    total_orders INT DEFAULT 0 COMMENT '总订单数',
    total_amount DECIMAL(12,2) DEFAULT 0 COMMENT '总消费金额',
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_customer (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户画像表';

-- 快捷回复表
CREATE TABLE quick_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT COMMENT '分类ID',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    tags VARCHAR(200) COMMENT '标签',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_by BIGINT COMMENT '创建人',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_category (category_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快捷回复表';
```

### 电商扩展表

```sql
-- 购物车表
CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_quantity INT DEFAULT 0 COMMENT '商品总数量',
    total_amount DECIMAL(12,2) DEFAULT 0 COMMENT '总金额',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 购物车项表
CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT NOT NULL COMMENT '购物车ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    sku_id BIGINT COMMENT 'SKU ID',
    quantity INT NOT NULL COMMENT '数量',
    selected BOOLEAN DEFAULT TRUE COMMENT '是否选中',
    price DECIMAL(10,2) NOT NULL COMMENT '加入时价格',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_cart (cart_id),
    INDEX idx_product (product_id),
    UNIQUE KEY uk_cart_product (cart_id, product_id, sku_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车项表';

-- 优惠券表
CREATE TABLE coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    coupon_no VARCHAR(50) NOT NULL COMMENT '优惠券编号',
    name VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    type VARCHAR(20) NOT NULL COMMENT '类型: FULL_REDUCTION/DISCOUNT/FREE_SHIPPING',
    discount_amount DECIMAL(10,2) COMMENT '优惠金额',
    discount_rate INT COMMENT '折扣率(%)',
    min_amount DECIMAL(10,2) DEFAULT 0 COMMENT '最低消费金额',
    max_discount DECIMAL(10,2) COMMENT '最大优惠金额',
    total_quantity INT NOT NULL COMMENT '发行总量',
    issued_quantity INT DEFAULT 0 COMMENT '已发放数量',
    used_quantity INT DEFAULT 0 COMMENT '已使用数量',
    valid_days INT COMMENT '有效天数',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_no (coupon_no),
    INDEX idx_status_time (status, start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 用户优惠券表
CREATE TABLE user_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    coupon_id BIGINT NOT NULL COMMENT '优惠券ID',
    coupon_no VARCHAR(50) NOT NULL COMMENT '优惠券编号',
    status VARCHAR(20) DEFAULT 'AVAILABLE' COMMENT '状态: AVAILABLE/USED/EXPIRED',
    order_id BIGINT COMMENT '使用订单ID',
    obtained_at DATETIME NOT NULL COMMENT '获得时间',
    used_at DATETIME COMMENT '使用时间',
    expire_at DATETIME NOT NULL COMMENT '过期时间',
    INDEX idx_user (user_id),
    INDEX idx_coupon (coupon_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

-- 秒杀活动表
CREATE TABLE seckill_activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_no VARCHAR(50) NOT NULL COMMENT '活动编号',
    name VARCHAR(100) NOT NULL COMMENT '活动名称',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    seckill_price DECIMAL(10,2) NOT NULL COMMENT '秒杀价格',
    stock INT NOT NULL COMMENT '秒杀库存',
    limit_per_user INT DEFAULT 1 COMMENT '每人限购',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_product (product_id),
    INDEX idx_time (start_time, end_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀活动表';

-- 拼团表
CREATE TABLE group_buy (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_no VARCHAR(50) NOT NULL COMMENT '活动编号',
    name VARCHAR(100) NOT NULL COMMENT '活动名称',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    group_price DECIMAL(10,2) NOT NULL COMMENT '拼团价格',
    original_price DECIMAL(10,2) NOT NULL COMMENT '原价',
    min_people INT NOT NULL COMMENT '成团人数',
    max_people INT COMMENT '上限人数',
    valid_hours INT DEFAULT 24 COMMENT '有效小时数',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_product (product_id),
    INDEX idx_time (start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团活动表';

-- 拼团订单表
CREATE TABLE group_buy_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    group_buy_id BIGINT NOT NULL COMMENT '拼团ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    is_leader BOOLEAN DEFAULT FALSE COMMENT '是否团长',
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_group (group_buy_id),
    INDEX idx_order (order_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团订单表';

-- 商品评价表
CREATE TABLE product_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    rating INT NOT NULL COMMENT '评分 1-5',
    content TEXT COMMENT '评价内容',
    reply_content TEXT COMMENT '商家回复',
    reply_time DATETIME COMMENT '回复时间',
    helpful_count INT DEFAULT 0 COMMENT '有用数',
    status VARCHAR(20) DEFAULT 'PUBLISHED',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_product (product_id),
    INDEX idx_user (user_id),
    INDEX idx_order (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价表';

-- 会员表
CREATE TABLE member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    level VARCHAR(20) DEFAULT 'NORMAL' COMMENT '会员等级',
    growth_value INT DEFAULT 0 COMMENT '成长值',
    points INT DEFAULT 0 COMMENT '积分',
    exp_date DATETIME COMMENT '会员过期日期',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_user (user_id),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 积分日志表
CREATE TABLE points_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    points INT NOT NULL COMMENT '积分变化(正负)',
    type VARCHAR(50) NOT NULL COMMENT '类型: EARN/CONSUME/EXPIRE',
    source VARCHAR(50) COMMENT '来源',
    order_id BIGINT COMMENT '关联订单',
    balance INT NOT NULL COMMENT '变动后余额',
    remark VARCHAR(200) COMMENT '备注',
    created_at DATETIME NOT NULL,
    INDEX idx_user (user_id),
    INDEX idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分日志表';
```

## 技术实现要点

### Redis 数据结构设计

```java
// 购物车数据
String cartKey = "cart:" + userId;
Hash: cart:userId
  - items: JSON array of cart items
  - totalAmount: total price
  - totalQuantity: total quantity
  - updatedAt: timestamp
TTL: 7 days

// 秒杀库存
String stockKey = "seckill:stock:" + activityId;
String: "seckill:stock:1001" = "50"

// 秒杀防重复
String orderKey = "seckill:order:" + userId + ":" + activityId;
String: "seckill:order:10001:1001" = "1"
TTL: 60 seconds

// 优惠券缓存
String couponKey = "coupon:" + couponNo;
Hash: coupon:12345
  - id: coupon ID
  - type: FULL_REDUCTION
  - discountAmount: 10.00
  - minAmount: 100.00
TTL: 1 hour

// 用户推荐缓存
String recommendKey = "recommend:" + userId;
List: recommend:10001
  - [productId1, productId2, ...]
TTL: 30 minutes

// 搜索热词
String hotwordKey = "search:hotwords";
ZSet: search:hotwords
  - keyword1: score 100
  - keyword2: score 80
TTL: 1 day
```

### 分布式锁设计

```java
// 秒杀扣库存
String lockKey = "lock:seckill:" + activityId;
RedisLock lock = redisLock.acquire(lockKey, 5, TimeUnit.SECONDS);
try {
    // 扣库存操作
} finally {
    lock.release();
}

// 优惠券核销
String lockKey = "lock:coupon:" + couponNo;
RedisLock lock = redisLock.acquire(lockKey, 3, TimeUnit.SECONDS);
try {
    // 核销操作
} finally {
    lock.release();
}
```

### 消息队列设计

```java
// 秒杀订单队列
Queue: seckill.order.queue
Exchange: seckill.exchange
RoutingKey: seckill.order

// 订单支付成功通知
Queue: order.paid.queue
Exchange: order.exchange
RoutingKey: order.paid

// 评价通知
Queue: review.created.queue
Exchange: review.exchange
RoutingKey: review.created

// 退款处理
Queue: refund.process.queue
Exchange: refund.exchange
RoutingKey: refund.process
```

### ES 索引设计

```java
// 商品索引
PUT /product_index
{
  "mappings": {
    "properties": {
      "name": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart",
        "fields": {
          "pinyin": {
            "type": "text",
            "analyzer": "pinyin"
          }
        }
      },
      "category": {
        "type": "keyword"
      },
      "price": {
        "type": "double"
      },
      "sales": {
        "type": "long"
      },
      "status": {
        "type": "keyword"
      },
      "tags": {
        "type": "keyword"
      },
      "attributes": {
        "type": "object",
        "properties": {
          "brand": {"type": "keyword"},
          "model": {"type": "text"}
        }
      },
      "createdAt": {
        "type": "date"
      }
    }
  }
}
```

## 性能优化策略

### 缓存策略

| 数据类型 | 缓存策略 | TTL | 更新方式 |
|---------|---------|-----|---------|
| 购物车 | Redis Hash | 7天 | 写时更新 |
| 商品信息 | Redis Hash | 1小时 | 定时同步 |
| 秒杀库存 | Redis String | 活动期 | 实时扣减 |
| 优惠券 | Redis Hash | 1小时 | 按需加载 |
| 推荐结果 | Redis List | 30分钟 | 定时计算 |
| 搜索热词 | Redis ZSet | 1天 | 实时更新 |

### 数据库优化

1. **读写分离**: 主库写入，从库读取
2. **分表策略**: 
   - 订单表按月分表
   - 日志表按月分表
3. **索引优化**: 
   - 覆盖索引减少回表
   - 复合索引注意字段顺序
4. **批量操作**: 
   - 购物车批量查询
   - 订单批量更新

### 接口优化

1. **异步化**: 
   - 秒杀订单异步创建
   - 评价统计异步更新
2. **限流**: 
   - 秒杀接口令牌桶限流
   - 搜索接口用户级限流
3. **降级**: 
   - ES 不可用时降级到 DB 搜索
   - 推荐服务降级到热门商品

## 安全设计

### 数据安全

1. **敏感数据加密**: 
   - 用户手机号脱敏显示
   - 支付信息加密存储
2. **操作审计**: 
   - 工单操作日志
   - 退款操作日志
3. **权限控制**: 
   - 基于角色的访问控制
   - 敏感操作二次验证

### 防刷机制

1. **秒杀防刷**: 
   - 图形验证码
   - 用户限流
   - IP 限流
2. **评价防刷**: 
   - 内容质量检测
   - 异常行为识别
3. **优惠券防刷**: 
   - 设备指纹
   - 领取次数限制

---

**设计版本**: 1.0  
**创建日期**: 2026-05-31  
**架构师**: Bootstrap Team
