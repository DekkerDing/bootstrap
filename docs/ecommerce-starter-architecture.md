# 电商 Starter 架构设计文档

## 文档说明

本文档详细说明了电商 Starter (e-business-spring-boot-starter) 的架构设计、调用链关系、类与接口的作用和关联关系。

**创建日期**：2026-05-31  
**版本**：v1.0

---

## 目录

1. [整体分层架构](#整体分层架构)
2. [各层详细说明](#各层详细说明)
3. [调用链关系](#调用链关系)
4. [条件装配机制](#条件装配机制)
5. [目录结构](#目录结构)
6. [核心类与接口](#核心类与接口)
7. [依赖关系图](#依赖关系图)

---

## 整体分层架构

```
┌─────────────────────────────────────────────────────────────┐
│                    电商 Starter 分层架构                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  API 层 (REST Controller)                              │   │
│  │  职责：处理 HTTP 请求，参数验证，返回响应              │   │
│  │  ProductController, OrderController, etc.            │   │
│  └────────────────────┬────────────────────────────────┘   │
│                       │ 调用 Service                            │
│                       ▼                                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Service 层 (业务逻辑)                               │   │
│  │  职责：核心业务逻辑，事务管理，协调各种操作          │   │
│  │  ProductService, OrderService, PaymentService          │   │
│  └────────────────────┬────────────────────────────────┘   │
│                       │ 调用 Repository 接口                   │
│                       ▼                                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Repository 接口层 (抽象接口)                        │   │
│  │  职责：定义数据访问操作，框架无关                       │   │
│  │  ProductRepository, OrderRepository, etc.             │   │
│  └──────┬────────────────────────┬────────────────────────┘   │
│         │                        │                            │
│   条件装配检测               JPA 实现                      MyBatis 实现           │
│  ┌────────┴────────┐     ┌─────────────┐            ┌─────────────┐   │
│  │ classpath检测    │     │ ProductJPARepo│            │ProductMapper │   │
│  │ 配置文件检测      │     │ OrderJPARepo │            │OrderMapper  │   │
│  └─────────────────┘     └─────────────┘            └─────────────┘   │
│                           │                                │              │
│                    条件装配选择                MapStruct    XML映射        │
│                           ▼                         │              │
│                  ┌───────────────┐                        ▼              │
│                  │ 领域模型     │  ◄──────────────┐    领域模型      │
│                  │ Product.java  │─┤ MapStruct转换│                │
│                  │ Order.java     │  │ Order.java   │                │
│                  │ 等 (纯 POJO)   │  └──────────────┘                │
│                  └───────────────┘                                    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 各层详细说明

### 1. API 层 (Controller 层)

**位置**：`api/` 目录

**职责**：
- 处理 HTTP 请求（GET, POST, PUT, DELETE）
- 验证请求参数（@Valid, @RequestBody）
- 调用 Service 层方法
- 构建 HTTP 响应（成功/失败）
- 异常处理和错误响应

**关键类**：
- `ProductController` - 商品相关 API
- `OrderController` - 订单相关 API
- `PaymentController` - 支付相关 API
- `UserController` - 用户相关 API
- `CategoryController` - 分类相关 API

**示例代码结构**：
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody ProductRequest request) {
        Product product = productService.create(request);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
}
```

**依赖关系**：
- 依赖 → Service 层接口
- 被依赖 → Spring MVC 框架

---

### 2. Service 层（业务逻辑层）

**位置**：`service/` 目录（按模块分：product/, order/, payment/, user/）

**职责**：
- 实现核心业务逻辑
- 协调多个 Repository 完成复杂业务
- 处理事务边界（@Transactional）
- 业务规则验证
- 数据转换和组装

**关键类**：
- `ProductService` - 商品业务逻辑
- `OrderService` - 订单业务逻辑
- `PaymentService` - 支付业务逻辑
- `UserService` - 用户业务逻辑

**示例代码结构**：
```java
@Service
@Transactional
public class OrderService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    public Order create(OrderRequest request) {
        // 1. 检查库存
        Product product = productRepository.findById(request.getProductId());
        if (!product.hasEnoughStock(request.getQuantity())) {
            throw new InsufficientStockException();
        }
        
        // 2. 创建订单
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.addOrderItem(product, request.getQuantity());
        
        // 3. 扣减库存
        product.decreaseStock(request.getQuantity());
        productRepository.save(product);
        
        // 4. 保存订单
        return orderRepository.save(order);
    }
}
```

**依赖关系**：
- 依赖 → Repository 接口（不依赖具体实现）
- 被依赖 → Controller 层

---

### 3. Repository 接口层（数据访问抽象层）

**位置**：`repository/` 目录

**职责**：
- 定义数据访问操作的抽象接口
- 框架无关（不包含 JPA 或 MyBatis 特定代码）
- 提供领域模型作为参数和返回值

**关键接口**：
```java
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByCategoryId(Long categoryId);
    void deleteById(Long id);
    void decreaseStock(Long productId, Integer quantity);
}
```

**依赖关系**：
- 被依赖 → Service 层
- 实现由 → JPA 模块或 MyBatis 模块提供

---

### 4. JPA 实现层

**位置**：`jpa/` 目录

**目录结构**：
```
jpa/
├── entity/           # JPA Entity 类（带 @Entity 等注解）
├── repository/       # JpaRepository 接口
└── config/           # JPA 配置类
```

**关键类**：

**JPA Entity**（`jpa/entity/ProductEntity.java`）：
```java
@Entity
@Table(name = "products")
public class ProductEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    // 其他字段...
}
```

**JPA Repository**（`jpa/repository/ProductJpaRepository.java`）：
```java
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    // Spring Data JPA 自动提供 CRUD 方法
    // 可以添加自定义查询方法
}
```

**Repository 实现**（`jpa/repository/ProductRepositoryImpl.java`）：
```java
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    
    @Autowired
    private ProductJpaRepository jpaRepository;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);
        return productMapper.toDomain(saved);
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
            .map(productMapper::toDomain);
    }
    
    // 其他方法实现...
}
```

**依赖关系**：
- 依赖 → Spring Data JPA
- 依赖 → MapStruct Mapper（对象转换）
- 实现 → Repository 接口

---

### 5. MyBatis 实现层

**位置**：`mybatis/` 目录

**目录结构**：
```
mybatis/
├── mapper/           # MyBatis Mapper 接口
├── xml/              # MyBatis XML 映射文件
└── config/           # MyBatis 配置类
```

**关键类**：

**Mapper 接口**（`mybatis/mapper/ProductMapper.java`）：
```java
@Mapper
public interface ProductMapper {
    
    @Insert("INSERT INTO products (name, description, price, stock, status, category_id, created_at, updated_at) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock}, #{status}, #{categoryId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
    
    @Select("SELECT * FROM products WHERE id = #{id}")
    @Results(id = "ProductResult", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "name", column = "name"),
        // 其他字段映射
    })
    Product selectById(Long id);
    
    // 其他方法...
}
```

**Repository 实现**（`mybatis/mapper/ProductRepositoryMybatisImpl.java`）：
```java
@Repository
public class ProductRepositoryMybatisImpl implements ProductRepository {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public Product save(Product product) {
        productMapper.insert(product);
        return product;
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productMapper.selectById(id));
    }
    
    // 其他方法实现...
}
```

**依赖关系**：
- 依赖 → MyBatis 框架
- 实现 → Repository 接口

---

## 调用链关系

### 场景 1：创建商品

```
HTTP POST /api/products
    │
    ▼
ProductController.create(@RequestBody ProductRequest request)
    │
    ├─ 验证请求参数
    ├─ 构建 Product 领域模型
    │
    ▼
ProductService.create(Product product)
    │
    ├─ 验证业务规则
    ├─ 设置默认值（status=ACTIVE）
    │
    ▼
ProductRepository.save(Product product)
    │
    ├─► 条件装配选择 JPA 或 MyBatis
    │
    ├─►【如果选择 JPA】：
    │   ProductRepositoryImpl.save(product)
    │   │
    │   ├─ ProductMapper.toEntity(product)
    │   │   │
    │   │   ▼
    │   │   Product (领域) → ProductEntity (JPA Entity)
    │   │   │
    │   │   ▼
    │   │   ProductJpaRepository.save(entity)
    │   │   │
    │   │   ▼
    │   │   JPA 执行 INSERT SQL
    │   │   │
    │   │   ▼
    │   │   返回 ProductEntity (有 ID)
    │   │   │
    │   │   ▼
    │   │   ProductMapper.toDomain(entity)
    │   │   │
    │   │   ▼
    │   │   ProductEntity → Product (领域)
    │   │   │
    │   ▼
    │   返回 Product (领域模型)
    │
    ├─►【如果选择 MyBatis】：
    │   ProductRepositoryMybatisImpl.save(product)
    │   │
    │   ├─ 直接使用 Product (领域模型)
    │   │
    │   ▼
    │   ProductMapper.insert(product)
    │   │
    │   ▼
    │   MyBatis 执行 INSERT SQL
    │   │
    │   ▼
    │   返回 Product (领域模型，MyBatis 自动填充 ID)
    │
    ▼
ProductService 返回 Product
    │
    ▼
ProductController 构建 ResponseEntity<ApiResponse<Product>>
    │
    ▼
HTTP 201 Created + { "code": 200, "data": {...} }
```

### 场景 2：创建订单（复杂业务流程）

```
HTTP POST /api/orders
    │
    ▼
OrderController.create(@RequestBody OrderRequest request)
    │
    ├─ 验证请求参数
    ├─ 构建 Order 领域模型
    │
    ▼
OrderService.create(OrderRequest request)
    │
    ├─► ProductService.checkStock(productId, quantity)
    │   │
    │   ▼
    │   ProductRepository.findById(productId)
    │   │
    │   ├─► 【JPA 路径】
    │   │   ProductRepositoryImpl.findById(id)
    │   │   │
    │   │   ▼
    │   │   ProductMapper.toDomain(jpaRepository.findById(id))
    │   │
    │   └─► 【MyBatis 路径】
    │       ProductRepositoryMybatisImpl.findById(id)
    │       │
    │       ▼
    │       ProductMapper.selectById(id)
    │
    ├─ 检查 product.hasEnoughStock(quantity)
    │   │
    │   ├─ 如果不足 → 抛出 InsufficientStockException
    │   │
    │   └─ 如果充足 → 继续
    │
    ├─► 创建 Order 实体
    │   │
    │   ├─ 设置 orderNumber = generateOrderNumber()
    │   ├─ 设置 status = "PENDING"
    │   ├─ 设置 userId
    │   ├─ 创建 OrderItem 列表
    │   │
    │   └─ 循环订单项：
    │       ├─ 创建 OrderItem(productId, productName, productPrice, quantity)
    │       ├─ 计算小计 = productPrice × quantity
    │       └─ 添加到 order.items
    │
    ├─► OrderRepository.save(order)
    │   │
    │   ├─► 【JPA 路径】
    │   │   OrderRepositoryImpl.save(order)
    │   │   │
    │   │   ▼
    │   │   OrderMapper.toEntity(order)
    │   │   │   │
    │   │   │   ├─ Order → OrderEntity
    │   │   │   │
    │   │   │   ├─ List<OrderItem> → List<OrderItemEntity>
    │   │   │   │
    │   │   │   └─ OrderRepositoryJpaRepository.save(entity)
    │   │   │   │
    │   │   │   ▼
    │   │   │   OrderMapper.toDomain(entity)
    │   │   │
    │   │   └─► 返回 Order (领域模型)
    │   │
    │   └─► 【MyBatis 路径】
    │       OrderRepositoryMybatisImpl.save(order)
    │       │
    │       ▼
    │       OrderMapper.insert(order)
    │       │
    │       ▼
    │       同时插入 order 和 order_items 表
    │       │
    │       ▼
    │       返回 Order (领域模型)
    │
    ├─► ProductService.decreaseStock(productId, totalQuantity)
    │   │
    │   ▼
    │   ProductRepository.decreaseStock(productId, quantity)
    │   │
    │   ├─► 【JPA 路径】
    │   │   ProductRepositoryImpl.decreaseStock(id, quantity)
    │   │   │
    │   │   ▼
    │   │   ProductJpaRepository.findById(id)
    │   │   │
    │   │   ▼
    │   │   ProductMapper.toDomain(entity)
    │   │   │
    │   │   ├─ product.decreaseStock(quantity)
    │   │   │
    │   │   └─ ProductJpaRepository.save(entity)
    │   │
    │   └─► 【MyBatis 路径】
    │       ProductRepositoryMybatisImpl.decreaseStock(id, quantity)
    │       │
    │       ▼
    │       ProductMapper.decreaseStock(id, quantity)
    │
    ▼
OrderService 返回 Order (包含 ID)
    │
    ▼
OrderController 构建 ResponseEntity<ApiResponse<Order>>
    │
    ▼
HTTP 201 Created + { "code": 200, "data": {"id": 123, "orderNumber": "ORD-xxx", ...} }
```

### 场景 3：支付回调处理

```
外部支付平台回调
    │
    ▼ POST /api/payment/callback?channel=alipay
    │
PaymentController.callback(@RequestBody PaymentCallbackRequest request, @RequestParam String channel)
    │
    ├─ 获取回调数据
    ├─ 验证签名（根据 channel 选择验证方式）
    │
    ▼
PaymentService.processCallback(transactionId, channel, callbackData)
    │
    ├─ 根据 transactionId 查询订单
    │   │
    │   ▼
    │   OrderRepository.findByOrderNumber(transactionId)
    │   │
    │   ├─► 返回 Order（如果存在）
    │   │
    │   └─► 返回 empty（订单不存在）
    │
    ├─ 验证回调金额与订单金额是否一致
    │   │
    │   ├─ 不一致 → 标记支付为 FAILED
    │   │
    │   └─ 一致 → 继续
    │
    ├─ 创建或更新 Payment 记录
    │   │
    │   ▼
    │   PaymentRepository.saveOrUpdate(transactionId, status, amount, channel, callbackData)
    │
    ├─ 如果支付成功，更新订单状态
    │   │
    │   ▼
    │   OrderRepository.updateStatus(orderId, "PAID")
    │
    ▼
返回处理结果
    │
    ▼
HTTP 200 OK + "success"
```

---

## 条件装配机制

### 装配决策流程

```
应用启动
    │
    ▼
┌─────────────────────────────────────────────┐
│ spring.factories 自动装配                   │
│ EnableAutoConfiguration =                   │
│   io.github.DekkerDing.ecommerce.config.      │
│   ECommerceAutoConfiguration              │
└─────────────────────────────────────────────┘
    │
    ├─► 检测条件：@ConditionalOnClass(EnableECommerce.class)
    │   类路径中有 @EnableECommerce 注解 → 继续
    │   没有 → 跳过装配
    │
    ├─► 检测条件：@ConditionalOnBean(DataSource.class)
    │   ApplicationContext 中有 DataSource Bean → 继续
    │   没有 → 跳过装配（禁用持久化功能）
    │
    ├─► 检测持久化类型
    │   │
    │   ├─ 检测配置：e-commerce.persistence.type
    │   │   ├─ "jpa" → 启用 JPA 模块
    │   │   ├─ "mybatis" → 启用 MyBatis 模块
    │   │   └─ "auto" → 自动检测
    │   │       │
    │   │       ├─ 有 EntityManager → 启用 JPA
    │   │       ├─ 有 SqlSessionFactory → 启用 MyBatis
    │   │       ├─ 都有 → 打印警告，使用第一个
    │   │       └─ 都没有 → 跳过持久化装配
    │   │
    │   └─ 检测配置：e-commerce.persistence.enabled
    │       false → 跳过所有持久化装配
    │       true → 继续
    │
    └─► 检测模块开关
        │
        ├─ e-commerce.modules.product.enabled
        │   true → 装配 ProductModuleConfiguration
        │   false → 跳过
        │
        ├─ e-commerce.modules.order.enabled
        │   true → 装配 OrderModuleConfiguration
        │   false → 跳过
        │
        ├─ e-commerce.modules.payment.enabled
        │   true → 装配 PaymentModuleConfiguration
        │   false → 跳过
        │
        └─ e-commerce.modules.user.enabled
            true → 装配 UserModuleConfiguration
            false → 跳过
```

### 关键配置类说明

#### 1. ECommerceAutoConfiguration（自动配置入口）

```java
@Configuration
@ConditionalOnClass(EnableECommerce.class)  // 检测 Enable 注解
@ConditionalOnBean(DataSource.class)         // 检测 DataSource
@EnableConfigurationProperties(ECommerceProperties.class)
public class ECommerceAutoConfiguration {
    
    // 只做元数据注册，不装配具体 Bean
    // 具体 Bean 由 @EnableECommerce 导入的 Registrar 注册
}
```

#### 2. @EnableECommerce 注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ECommerceModuleRegistrar.class)
@Import(ECommerceAutoConfiguration.class)
public @interface EnableECommerce {
    
    Module[] modules() default {};  // 空表示全部启用
}
```

#### 3. ECommerceModuleRegistrar（模块注册器）

```java
public class ECommerceModuleRegistrar implements ImportBeanDefinitionRegistrar {
    
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, 
                                       BeanDefinitionRegistry registry) {
        
        // 读取 @EnableECommerce 的 modules 参数
        // 决定注册哪些模块的 Configuration
    }
}
```

#### 4. ProductModuleConfiguration（商品模块配置）

```java
@Configuration
@ConditionalOnProperty(
    name = "e-commerce.modules.product.enabled",
    havingValue = "true",
    matchIfMissing = true  // 默认启用
)
public class ProductModuleConfiguration {
    
    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }
    
    // 装配 JPA 实现或 MyBatis 实现
    @Bean
    @ConditionalOnProperty(
        name = "e-commerce.persistence.type",
        havingValue = "jpa",
        matchIfMissing = false
    )
    public ProductRepository productJpaRepository() {
        return new ProductRepositoryImpl();
    }
    
    @Bean
    @ConditionalOnProperty(
        name = "e-commerce.persistence.type",
        havingValue = "mybatis",
        matchIfMissing = false
    )
    public ProductRepository productMybatisRepository() {
        return new ProductRepositoryMybatisImpl();
    }
}
```

---

## 目录结构

```
e-business-spring-boot-starter/
├── build.gradle                                    # 构建配置
├── src/
│   ├── main/
│   │   ├── java/io/github/DekkerDing/ecommerce/
│   │   │   ├── api/                                    # API 层
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── PaymentController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── CategoryController.java
│   │   │   │
│   │   │   ├── service/                               # Service 层
│   │   │   │   ├── product/
│   │   │   │   │   ├── ProductService.java         # 接口
│   │   │   │   │   └── ProductServiceImpl.java     # 实现
│   │   │   │   ├── order/
│   │   │   │   │   ├── OrderService.java
│   │   │   │   │   └── OrderServiceImpl.java
│   │   │   │   ├── payment/
│   │   │   │   │   ├── PaymentService.java
│   │   │   │   │   └── PaymentServiceImpl.java
│   │   │   │   └── user/
│   │   │   │       ├── UserService.java
│   │   │   │       └── UserServiceImpl.java
│   │   │   │
│   │   │   ├── repository/                            # Repository 接口层
│   │   │   │   ├── ProductRepository.java           # 接口
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── PaymentRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── CategoryRepository.java
│   │   │   │
│   │   │   ├── jpa/                                   # JPA 实现
│   │   │   │   ├── entity/
│   │   │   │   │   ├── ProductEntity.java        # JPA Entity
│   │   │   │   │   ├── OrderEntity.java
│   │   │   │   │   ├── OrderItemEntity.java
│   │   │   │   │   ├── PaymentEntity.java
│   │   │   │   │   ├── UserEntity.java
│   │   │   │   │   ├── AddressEntity.java
│   │   │   │   │   └── CategoryEntity.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── ProductJpaRepository.java  # Spring Data JPA
│   │   │   │   │   ├── OrderJpaRepository.java
│   │   │   │   │   └── ...
│   │   │   │   ├── config/
│   │   │   │   │   └── JpaRepositoryConfig.java  # JPA 配置
│   │   │   │   └── mapper/
│   │   │   │       └── ProductMapper.java         # MapStruct Mapper
│   │   │   │
│   │   │   ├── mybatis/                               # MyBatis 实现
│   │   │   │   ├── mapper/
│   │   │   │   │   ├── ProductMapper.java       # MyBatis Mapper
│   │   │   │   │   ├── OrderMapper.java
│   │   │   │   │   └── ...
│   │   │   │   ├── xml/
│   │   │   │   │   ├── ProductMapper.xml       # SQL 映射
│   │   │   │   │   ├── OrderMapper.xml
│   │   │   │   │   └── ...
│   │   │   │   └── config/
│   │   │   │       └── MybatisConfig.java      # MyBatis 配置
│   │   │   │
│   │   │   ├── domain/                                # 领域模型（框架无关）
│   │   │   │   ├── product/
│   │   │   │   │   ├── Product.java             # 纯 POJO
│   │   │   │   │   └── Category.java
│   │   │   │   ├── order/
│   │   │   │   │   ├── Order.java
│   │   │   │   │   └── OrderItem.java
│   │   │   │   ├── payment/
│   │   │   │   │   └── Payment.java
│   │   │   │   └── user/
│   │   │   │       ├── User.java
│   │   │   │       └── Address.java
│   │   │   │
│   │   │   ├── config/                                # 配置类
│   │   │   │   ├── ECommerceAutoConfiguration.java  # 自动配置
│   │   │   │   ├── ECommerceProperties.java        # 配置属性
│   │   │   │   └── module/
│   │   │   │       ├── ProductModuleConfiguration.java
│   │   │   │       ├── OrderModuleConfiguration.java
│   │   │   │       ├── PaymentModuleConfiguration.java
│   │   │   │       └── UserModuleConfiguration.java
│   │   │   │
│   │   │   ├── annotation/                             # 注解
│   │   │   │   └── EnableECommerce.java
│   │   │   │
│   │   │   └── ECommerceServiceApplication.java   # 主类（可选）
│   │   │
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── spring.factories                # 自动配置声明
│   │       ├── sql/
│   │       │   ├── mysql/
│   │       │   │   ├── schema-mysql.sql         # MySQL 建表脚本
│   │       │   │   ├── V2__add_indexes.sql
│   │       │   │   └── V3__add_payment.sql
│   │       │   └── postgresql/
│   │       │       ├── schema-postgres.sql     # PostgreSQL 建表脚本
│   │       │       ├── V2__add_indexes.sql
│   │       │       └── V3__add_payment.sql
│   │       └── application-default.yml         # 默认配置
│   │
│   └── test/
│       └── java/io/github/DekkerDing/ecommerce/
│           ├── service/
│           │   └── ProductServiceTest.java
│           └── api/
│               └── ProductControllerTest.java
```

---

## 核心类与接口

### 1. 领域模型类

#### Product（商品领域模型）

**文件**：`domain/product/Product.java`

**作用**：
- 表示商品的业务概念
- 包含商品的基本属性和行为
- 框架无关的纯 POJO

**关键字段**：
- `id` - 唯一标识
- `name` - 商品名称
- `price` - 价格
- `stock` - 库存
- `status` - 状态（ACTIVE/INACTIVE）
- `categoryId` - 所属分类

**关键方法**：
- `hasEnoughStock(quantity)` - 检查库存是否充足
- `decreaseStock(quantity)` - 扣减库存
- `increaseStock(quantity)` - 增加库存
- `isActive()` - 是否活动

#### Order（订单领域模型）

**文件**：`domain/order/Order.java`

**作用**：
- 表示订单的业务概念
- 包含订单的基本信息和状态
- 管理订单项列表

**关键字段**：
- `id` - 唯一标识
- `orderNumber` - 订单号（人类可读）
- `userId` - 用户 ID
- `status` - 状态（PENDING/PAID/SHIPPED/COMPLETED/CANCELLED）
- `totalAmount` - 总金额
- `items` - 订单项列表

**关键方法**：
- `addItem(OrderItem)` - 添加订单项
- `recalculateTotal()` - 重新计算总金额
- `canTransitionTo(newStatus)` - 检查状态是否可以转换
- `isCancellable()` - 是否可以取消

#### 其他领域模型

- **OrderItem** - 订单项
- **Payment** - 支付信息
- **User** - 用户信息
- **Address** - 用户地址
- **Category** - 商品分类

### 2. Repository 接口

#### ProductRepository

**文件**：`repository/ProductRepository.java`

**作用**：
- 定义商品数据访问的抽象接口
- 框架无关（不依赖 JPA 或 MyBatis）
- 被 Service 层依赖

**方法签名**：
```java
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByCategoryId(Long categoryId);
    void deleteById(Long id);
    boolean existsById(Long id);
    void decreaseStock(Long productId, Integer quantity);
}
```

**关系**：
- 被 `ProductService` 依赖
- 被 `ProductRepositoryImpl`（JPA）实现
- 被 `ProductRepositoryMybatisImpl`（MyBatis）实现

#### 其他 Repository 接口

- `OrderRepository` - 订单数据访问
- `PaymentRepository` - 支付数据访问
- `UserRepository` - 用户数据访问
- `CategoryRepository` - 分类数据访问

### 3. Service 层类

#### ProductService

**文件**：`service/product/ProductService.java`（接口）

**作用**：
- 定义商品相关的业务逻辑操作
- 协调多个 Repository 完成复杂业务
- 处理业务规则验证

**方法签名**：
```java
public interface ProductService {
    Product create(ProductRequest request);
    Product getById(Long id);
    List<Product> list(ProductQuery query);
    Product update(Long id, ProductRequest request);
    void delete(Long id);
    boolean checkStock(Long productId, Integer quantity);
    void decreaseStock(Long productId, Integer quantity);
}
```

**关系**：
- 被 `ProductController` 依赖
- 依赖 `ProductRepository` 接口
- 被 `ProductServiceImpl` 实现

#### 其他 Service 类

- `OrderService` - 订单业务逻辑
- `PaymentService` - 支付业务逻辑
- `UserService` - 用户业务逻辑

### 4. Controller 层类

#### ProductController

**文件**：`api/ProductController.java`

**作用**：
- 处理商品相关的 HTTP 请求
- 验证请求参数
- 调用 Service 层
- 构建 HTTP 响应

**端点**：
```
POST   /api/products          → 创建商品
GET    /api/products/{id}     → 获取商品详情
GET    /api/products          → 获取商品列表
PUT    /api/products/{id}     → 更新商品
DELETE /api/products/{id}     → 删除商品
PATCH  /api/products/{id}/stock → 更新库存
```

**关系**：
- 被 Spring MVC 框架管理
- 依赖 `ProductService`

### 5. 配置类

#### ECommerceProperties

**文件**：`config/ECommerceProperties.java`

**作用**：
- 绑定配置属性（prefix = "e-commerce"）
- 提供类型安全的配置访问
- 支持 @Valid 验证

**配置结构**：
```yaml
e-commerce:
  persistence:
    type: auto  # auto/jpa/mybatis
    enabled: true
  modules:
    product:
      enabled: true
    order:
      enabled: true
    payment:
      enabled: true
    user:
      enabled: true
  api:
    base-path: /api
    cors-enabled: true
    max-page-size: 100
```

#### ECommerceAutoConfiguration

**文件**：`config/ECommerceAutoConfiguration.java`

**作用**：
- 自动配置入口类
- 通过 spring.factories 被加载
- 使用条件装配控制启用时机

---

## 依赖关系图

### 类依赖关系矩阵

```
┌─────────────────────────────────────────────────────────────────┐
│                    类依赖关系矩阵                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│         │ ProductController │ OrderController │ PaymentController │
│         │        :            │               │                  │
│         ▼        :            ▼               ▼                  │
│ │   ProductService :   OrderService   │  PaymentService      │
│ │        :            :               │                  │
│ │        ▼            :               ▼                  │
│ │ ProductRepository :   OrderRepository  │  PaymentRepository   │
│ │        :            :               │                  │
│ │        ├───────────┴───────────────┼──────────────────┤  │
│ ▼        ▼                              ▼                  ▼  ▼       │
│ ProductRepositoryImpl           OrderRepositoryImpl      PaymentRepositoryImpl
│ (JPA 或 MyBatis 实现由条件装配选择)                                  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 配置依赖关系

```
┌─────────────────────────────────────────────────────────────────┐
│                    配置依赖关系                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  application.yml                                                   │
│  └── e-commerce.persistence.type=jpa                           │
│      │                                                          │
│      ▼                                                          │
│  ECommerceProperties.persistence.type                            │
│      │                                                          │
│      ├─► JPA 条件装配启用                                      │
│      │   │                                                      │
│      │   ▼                                                      │
│      │   ProductRepositoryImpl (JPA)                           │
│      │   OrderRepositoryImpl (JPA)                            │
│      │   ...                                                   │
│      │                                                          │
│      └─► MyBatis 条件装配禁用                                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 数据流转示例

### 示例 1：用户下单完整流程

```
┌───────────────────────────────────────────────────────────────┐
│                        用户下单完整流程                           │
└───────────────────────────────────────────────────────────────┘

步骤 1: 用户浏览商品
  GET /api/products
    → ProductController.list()
    → ProductService.list()
    → ProductRepository.findAll()
    → [JPA/MyBatis] 查询数据库
    → 返回商品列表

步骤 2: 用户创建订单
  POST /api/orders
  请求体: { userId: 1, items: [{ productId: 100, quantity: 2 }] }
    → OrderController.create(request)
    → OrderService.create(request)
    
    ├─ 验证用户存在: UserRepository.findById(1)
    │   → [JPA/MyBatis] 查询 user 表
    │
    ├─ 验证商品存在: ProductRepository.findById(100)
    │   → [JPA/MyBatis] 查询 products 表
    │   → 检查 product.hasEnoughStock(2)
    │
    ├─ 创建订单: new Order()
    │   → 设置 orderNumber = "ORD-xxx"
    │   → 设置 status = "PENDING"
    │
    ├─ 创建订单项: new OrderItem(productId, productName, price, quantity)
    │   → 计算小计 = price × quantity
    │
    ├─ 保存订单: OrderRepository.save(order)
    │   → [JPA] OrderJpaRepository.save(orderEntity)
    │   → [MyBatis] OrderMapper.insert(order)
    │
    └─ 扣减库存: ProductRepository.decreaseStock(100, 2)
        → ProductRepository.findById(100)
        → product.decreaseStock(2)
        → ProductRepository.save(product)
    → 返回订单 (包含订单 ID)

步骤 3: 用户支付
  POST /api/payment/callback
  参数: transactionId=xxx, status=success
    → PaymentController.callback(...)
    → PaymentService.processCallback(...)
    
    ├─ 查询订单: OrderRepository.findByOrderNumber(transactionId)
    │   → [JPA/MyBatis] 查询 orders 表
    │
    ├─ 验证金额: callback.amount == order.totalAmount
    │
    ├─ 创建/更新支付: PaymentRepository.saveOrUpdate(...)
    │   → [JPA/MyBatis] 操作 payments 表
    │
    └─ 更新订单状态: OrderRepository.updateStatus(orderId, "PAID")
        → [JPA/MyBatis] 更新 orders 表
    → 返回支付成功

步骤 4: 查询订单状态
  GET /api/orders/{id}
    → OrderController.getById(id)
    → OrderService.getById(id)
    → OrderRepository.findById(id)
    → [JPA/MyBatis] 查询 orders 和 order_items 表
    → 组装 Order 对象（包含 OrderItems）
    → 返回订单详情
```

---

## 总结

### 设计原则

1. **分层架构**：API → Service → Repository，每层职责清晰
2. **接口隔离**：Service 层依赖抽象接口，不依赖具体实现
3. **条件装配**：根据环境自动选择 JPA 或 MyBatis 实现
4. **框架无关**：领域模型不包含任何框架注解
5. **按需加载**：通过配置控制模块启用/禁用

### 扩展点

如需扩展功能：

1. **添加新的业务模块**：
   - 创建新的 Service、Repository、Controller
   - 添加新的 ModuleConfiguration
   - 在 ECommerceProperties 中添加模块配置

2. **支持新的数据库框架**：
   - 创建新的实现层（如 MongoDB）
   - 实现相同的 Repository 接口
   - 添加条件装配逻辑

3. **添加新的 API 端点**：
   - 在 Controller 中添加新方法
   - 对应的 Service 方法实现业务逻辑

---

## 相关文档

- [OpenSpec 变更提案](../openspec/changes/complete-both-starters-v1/proposal.md)
- [技术设计文档](../openspec/changes/complete-both-starters-v1/design.md)
- [实现任务清单](../openspec/changes/complete-both-starters-v1/tasks.md)
- [双语文档规范](../openspec/.bilingual-spec.md)
