# 购物车系统规格说明

## 功能概述

提供完整的购物车功能，支持商品加购、数量修改、删除、选中/取消选中、价格计算等核心功能。

## 需求描述

### 核心功能

1. **购物车 CRUD**
   - 添加商品到购物车
   - 修改商品数量
   - 删除购物车项
   - 清空购物车
   - 查询购物车

2. **价格计算**
   - 实时计算商品小计
   - 计算购物车总金额
   - 应用优惠券折扣
   - 应用促销活动折扣

3. **库存校验**
   - 加购时校验库存
   - 数量变更时校验库存
   - 库存不足时提示用户

4. **跨设备同步**
   - 多端购物车数据同步
   - 基于 Redis 实现缓存
   - DB 持久化保证数据不丢失

### 业务规则

1. 同一商品同一 SKU 只能有一个购物车项
2. 商品数量不能超过库存
3. 商品数量不能小于 1（删除除外）
4. 下架或售罄商品自动标记为不可选
5. 购物车数据保留 7 天未活动则清理

## 接口定义

### REST API

| 端点 | 方法 | 描述 | 认证 |
|------|------|------|------|
| /api/cart | GET | 获取购物车 | 是 |
| /api/cart/items | POST | 添加商品 | 是 |
| /api/cart/items/{id} | PUT | 更新数量 | 是 |
| /api/cart/items/{id}/select | PUT | 切换选中 | 是 |
| /api/cart/items/{id} | DELETE | 删除项 | 是 |
| /api/cart | DELETE | 清空购物车 | 是 |
| /api/cart/total | GET | 获取总计 | 是 |

## 数据模型

### Cart 模型

```java
public class Cart {
    private Long id;
    private Long userId;
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItem> items;
}
```

### CartItem 模型

```java
public class CartItem {
    private Long id;
    private Long cartId;
    private Long productId;
    private Long skuId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private Boolean selected;
    private BigDecimal subtotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 商品状态
    private Boolean onSale;        // 是否在售
    private Boolean hasStock;      // 是否有库存
    private Integer stock;         // 当前库存
}
```

## 业务流程

### 添加商品流程

```
用户请求 → 校验商品存在 → 校验库存 → 检查是否已存在
    ↓                                         ↓
   不存在                                   已存在
    ↓                                         ↓
 创建新购物车项                        增加数量
    ↓                                         ↓
   更新总金额                           更新总金额
    ↓
  返回购物车
```

### 价格计算流程

```
遍历购物车项
    ↓
  项是否选中？
    ↓
   是 → 检查商品状态
    ↓
   在售且有库存 → 计算小计（单价 × 数量）
    ↓
   累加总金额
    ↓
  应用优惠券折扣（如有）
    ↓
  应用促销折扣（如有）
    ↓
  返回最终金额
```

## 技术实现

### Redis 数据结构

```
cart:{userId}
  ├─ items: JSON array of cart items
  ├─ totalQuantity: integer
  ├─ totalAmount: decimal
  └─ updatedAt: timestamp

TTL: 7 days (读写时刷新)
```

### 缓存策略

- **读取**: 先读 Redis，未命中读 DB
- **写入**: 先写 Redis，异步写 DB
- **删除**: 先删 Redis，异步删 DB
- **同步**: 每次操作后刷新 TTL

### 数据一致性

- Redis 缓存 + DB 持久化
- 异步队列保证最终一致性
- 定时任务清理过期数据

## 配置项

```yaml
ecommerce:
  cart:
    enabled: true
    cache-type: redis  # redis, db, both
    expire-days: 7
    max-items: 99
    max-quantity: 999
```

## 性能要求

- 购物车查询响应 < 100ms
- 支持并发操作数 > 5000
- 缓存命中率 > 95%
- 数据一致性最终延迟 < 1s

## 异常处理

| 异常场景 | 处理方式 |
|---------|---------|
| 商品不存在 | 返回 404 错误 |
| 库存不足 | 提示最大可购买数量 |
| 购物车不存在 | 自动创建新购物车 |
| 商品下架 | 标记不可选，允许删除 |
| Redis 不可用 | 降级到 DB 查询 |
