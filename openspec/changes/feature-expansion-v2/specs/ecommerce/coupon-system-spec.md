# 优惠券系统规格说明

## 功能概述

提供完整的优惠券营销功能，支持满减券、折扣券、免邮券等多种类型，支持券的发放、领取、使用、核销全流程管理。

## 需求描述

### 优惠券类型

1. **满减券**: 满足最低消费金额减免固定金额
2. **折扣券**: 按折扣率享受优惠
3. **免邮券**: 免运费
4. **品类券**: 指定品类可用
5. **新人券**: 新注册用户专享
6. **生日券**: 生日当天可用

### 核心功能

1. **优惠券创建**
   - 配置基本信息（名称、类型、面额）
   - 配置使用规则（最低消费、适用商品）
   - 配置发放规则（总量、每人限领）
   - 配置有效期（时间段、有效天数）

2. **优惠券发放**
   - 自动发放（注册、订单完成）
   - 手动领取（领券中心）
   - 活动发放（扫码、分享）
   - 会员发放（等级达标）

3. **优惠券使用**
   - 下单时自动推荐最优券
   - 手动选择优惠券
   - 计算优惠金额
   - 核销优惠券

4. **优惠券管理**
   - 查看优惠券列表
   - 查看使用明细
   - 过期提醒
   - 使用统计

## 接口定义

### REST API

| 端点 | 方法 | 描述 | 认证 |
|------|------|------|------|
| /api/coupons | GET | 获取可用优惠券列表 | 是 |
| /api/coupons/{id}/claim | POST | 领取优惠券 | 是 |
| /api/coupons/user | GET | 获取我的优惠券 | 是 |
| /api/order/calculate-discount | POST | 计算优惠金额 | 是 |
| /api/coupons/{id} | GET | 获取优惠券详情 | 否 |

## 数据模型

### Coupon 模型

```java
public class Coupon {
    private Long id;
    private String couponNo;          // 优惠券编号
    private String name;              // 名称
    private CouponType type;           // 类型
    private BigDecimal discountAmount;  // 优惠金额
    private Integer discountRate;     // 折扣率（%）
    private BigDecimal minAmount;     // 最低消费
    private BigDecimal maxDiscount;  // 最大优惠
    private Integer totalQuantity;    // 发行总量
    private Integer issuedQuantity;   // 已发放
    private Integer usedQuantity;     // 已使用
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CouponStatus status;
}
```

### UserCoupon 模型

```java
public class UserCoupon {
    private Long id;
    private Long userId;
    private Long couponId;
    private String couponNo;
    private UserCouponStatus status;
    private Long orderId;            // 使用订单
    private LocalDateTime obtainedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expireAt;
}
```

## 业务规则

### 领取规则

1. 每人限领 N 张（可配置）
2. 优惠券库存先到先得
3. 新人券仅新用户可领
4. 会员等级券需达到指定等级

### 使用规则

1. 订单金额需满足最低消费
2. 计算优惠金额不超过最大优惠限制
3. 互斥券不可叠加使用
4. 优惠金额不可超过订单金额

### 互斥规则

```java
// 互斥类型
EXCLUSIVE_TYPE {
    MUTUAL_EXCLUSIVE,  // 完全互斥
    SAME_TYPE,        // 同类型互斥
    NONE              // 不互斥
}

// 示例：满减券和折扣券互斥，但免邮券可叠加
```

## 计算逻辑

### 满减券计算

```
订单金额 = 100 元
满减券：满 50 减 10
优惠金额 = MIN(10, 100) = 10 元
实付金额 = 100 - 10 = 90 元
```

### 折扣券计算

```
订单金额 = 100 元
折扣券：9 折
优惠金额 = 100 × 0.1 = 10 元
实付金额 = 100 - 10 = 90 元
```

### 多券叠加（如允许）

```
订单金额 = 100 元
满减券：满 50 减 10
折扣券：9 折
优惠金额 = 10 + (100-10) × 0.1 = 19 元
实付金额 = 100 - 19 = 81 元
```

## 配置项

```yaml
ecommerce:
  coupon:
    enabled: true
    expire-reminder-hours: 24  # 过期提醒小时数
    max-per-user: 5             # 每人最多持有
    auto-claim-new-user: true   # 自动发放新人券
```
