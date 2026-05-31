# API Documentation / API 文档

本文档详细说明了 Bootstrap 行业解决方案的所有 REST API 端点。

This document describes in detail all REST API endpoints for the Bootstrap Industry Solution Collection.

---

## E-commerce Starter APIs / 电商 Starter APIs

### Base URL / 基础 URL

```
http://localhost:8080/api
```

---

### Product APIs / 商品 API

#### 创建商品 / Create Product

```http
POST /api/products
Content-Type: application/json
```

**请求体 / Request Body:**
```json
{
  "name": "MacBook Pro 16",
  "description": "Apple M3 Max chip, 16-inch display",
  "price": 2499.99,
  "stock": 100,
  "status": "ACTIVE",
  "categoryId": 1
}
```

**响应 / Response:**
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "name": "MacBook Pro 16",
    "description": "Apple M3 Max chip, 16-inch display",
    "price": 2499.99,
    "stock": 100,
    "status": "ACTIVE",
    "categoryId": 1,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  "timestamp": "2024-01-01T10:00:00"
}
```

#### 查询所有商品 / Get All Products

```http
GET /api/products
```

#### 根据 ID 查询商品 / Get Product by ID

```http
GET /api/products/{id}
```

#### 更新库存 / Update Stock

```http
PATCH /api/products/{id}/stock?quantity=150
```

---

### Order APIs / 订单 API

#### 创建订单 / Create Order

```http
POST /api/orders
Content-Type: application/json
```

**请求体 / Request Body:**
```json
{
  "orderNumber": "ORD202401011234",
  "userId": 1,
  "status": "PENDING",
  "items": [
    {
      "productId": 1,
      "productName": "MacBook Pro",
      "productPrice": 2499.99,
      "quantity": 1,
      "subtotal": 2499.99
    }
  ]
}
```

#### 查询订单 / Get Order

```http
GET /api/orders/{id}
```

#### 根据订单号查询 / Get Order by Order Number

```http
GET /api/orders/number/{orderNumber}
```

#### 取消订单 / Cancel Order

```http
POST /api/orders/{id}/cancel
```

---

### Payment APIs / 支付 API

#### 创建支付 / Create Payment

```http
POST /api/payments
Content-Type: application/json
```

**请求体 / Request Body:**
```json
{
  "orderId": 1,
  "transactionId": "TXN202401011234",
  "channel": "ALIPAY",
  "amount": 2499.99,
  "status": "PENDING"
}
```

#### 支付回调 / Payment Callback

```http
POST /api/payments/callback
```

---

### User APIs / 用户 API

#### 创建用户 / Create User

```http
POST /api/users
Content-Type: application/json
```

**请求体 / Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "13800138000",
  "realName": "John Doe",
  "status": "ACTIVE"
}
```

---

## Customer Robot Starter APIs / 客服机器人 Starter APIs

### Base URL / 基础 URL

```
http://localhost:8080/api
```

---

### Customer APIs / 客户 API

#### 创建客户 / Create Customer

```http
POST /api/customers
```

**请求体 / Request Body:**
```json
{
  "customerNo": "C20240001",
  "name": "张三",
  "phone": "13800138001",
  "email": "zhangsan@example.com",
  "source": "WEB",
  "type": "NORMAL"
}
```

---

### Conversation APIs / 对话 API

#### 创建对话 / Create Conversation

```http
POST /api/conversations
```

**请求体 / Request Body:**
```json
{
  "customerId": 1,
  "title": "产品咨询",
  "priority": "NORMAL"
}
```

#### 分配客服 / Assign Agent

```http
POST /api/conversations/{id}/assign?agentId=10
```

#### 关闭对话 / Close Conversation

```http
POST /api/conversations/{id}/close
```

---

### Message APIs / 消息 API

#### 发送消息 / Send Message

```http
POST /api/messages
```

**请求体 / Request Body:**
```json
{
  "conversationId": 1,
  "senderId": 2,
  "senderType": "AGENT",
  "messageType": "TEXT",
  "content": "您好，有什么可以帮您的吗？"
}
```

#### 查询对话消息 / Get Conversation Messages

```http
GET /api/messages/conversation/{conversationId}
```

---

## Response Format / 响应格式

所有 API 使用统一的响应格式：

All APIs use a unified response format:

```json
{
  "code": 200,
  "message": "Success",
  "data": { ... },
  "timestamp": "2024-01-01T10:00:00"
}
```

### 状态码 / Status Codes

| 状态码 / Status Code | 说明 / Description |
|---|---|
| 200 | 成功 / Success |
| 400 | 请求参数错误 / Bad Request |
| 404 | 资源不存在 / Not Found |
| 500 | 服务器内部错误 / Internal Server Error |

---

## Error Response / 错误响应

当发生错误时，响应格式如下：

When an error occurs, the response format is:

```json
{
  "code": 400,
  "message": "Validation failed",
  "data": null,
  "timestamp": "2024-01-01T10:00:00"
}
```

---

更多详细信息请参考 [配置说明](configuration.md)。

For more details, please refer to [Configuration Guide](configuration.md).
