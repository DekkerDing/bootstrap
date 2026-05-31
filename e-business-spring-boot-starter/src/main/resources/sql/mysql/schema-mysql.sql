-- =====================================================
-- 电商 Starter MySQL 建表脚本
-- E-commerce Starter MySQL Schema Script
-- =====================================================

-- 商品分类表 / Category Table
CREATE TABLE IF NOT EXISTS ecommerce_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类 ID / Category ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称 / Category name',
    parent_id BIGINT COMMENT '父级 ID / Parent ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_parent_id (parent_id),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表 / Category Table';

-- 商品表 / Product Table
CREATE TABLE IF NOT EXISTS ecommerce_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品 ID / Product ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称 / Product name',
    description VARCHAR(500) COMMENT '商品描述 / Product description',
    price DECIMAL(10,2) NOT NULL COMMENT '商品价格 / Product price',
    stock INT NOT NULL COMMENT '库存数量 / Stock quantity',
    status VARCHAR(20) NOT NULL COMMENT '商品状态 / Product status',
    category_id BIGINT COMMENT '分类 ID / Category ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_name (name),
    FOREIGN KEY (category_id) REFERENCES ecommerce_category(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表 / Product Table';

-- 用户表 / User Table
CREATE TABLE IF NOT EXISTS ecommerce_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户 ID / User ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名 / Username',
    email VARCHAR(100) UNIQUE COMMENT '电子邮箱 / Email',
    phone VARCHAR(20) COMMENT '电话号码 / Phone',
    real_name VARCHAR(50) COMMENT '真实姓名 / Real name',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '用户状态 / User status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表 / User Table';

-- 地址表 / Address Table
CREATE TABLE IF NOT EXISTS ecommerce_address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '地址 ID / Address ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID / User ID',
    receiver_name VARCHAR(50) NOT NULL COMMENT '收件人姓名 / Receiver name',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话 / Phone',
    province VARCHAR(50) NOT NULL COMMENT '省份 / Province',
    city VARCHAR(50) NOT NULL COMMENT '城市 / City',
    district VARCHAR(50) NOT NULL COMMENT '区/县 / District',
    detail_address VARCHAR(200) NOT NULL COMMENT '详细地址 / Detail address',
    postal_code VARCHAR(10) COMMENT '邮政编码 / Postal code',
    is_default TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址 / Is default',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_user_id (user_id),
    INDEX idx_is_default (is_default),
    FOREIGN KEY (user_id) REFERENCES ecommerce_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地址表 / Address Table';

-- 订单表 / Order Table
CREATE TABLE IF NOT EXISTS ecommerce_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单 ID / Order ID',
    order_number VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号 / Order number',
    user_id BIGINT NOT NULL COMMENT '用户 ID / User ID',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '订单状态 / Order status',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额 / Total amount',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_order_number (order_number),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    FOREIGN KEY (user_id) REFERENCES ecommerce_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表 / Order Table';

-- 订单项表 / Order Item Table
CREATE TABLE IF NOT EXISTS ecommerce_order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单项 ID / Order Item ID',
    order_id BIGINT NOT NULL COMMENT '订单 ID / Order ID',
    product_id BIGINT NOT NULL COMMENT '商品 ID / Product ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称 / Product name',
    product_price DECIMAL(10,2) NOT NULL COMMENT '商品价格 / Product price',
    quantity INT NOT NULL COMMENT '数量 / Quantity',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计 / Subtotal',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    FOREIGN KEY (order_id) REFERENCES ecommerce_order(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES ecommerce_product(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表 / Order Item Table';

-- 支付表 / Payment Table
CREATE TABLE IF NOT EXISTS ecommerce_payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '支付 ID / Payment ID',
    order_id BIGINT NOT NULL COMMENT '订单 ID / Order ID',
    transaction_id VARCHAR(100) COMMENT '交易 ID / Transaction ID',
    channel VARCHAR(20) COMMENT '支付渠道 / Payment channel',
    amount DECIMAL(10,2) NOT NULL COMMENT '支付金额 / Payment amount',
    status VARCHAR(20) NOT NULL COMMENT '支付状态 / Payment status',
    callback_data TEXT COMMENT '回调数据 / Callback data',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_order_id (order_id),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_status (status),
    FOREIGN KEY (order_id) REFERENCES ecommerce_order(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付表 / Payment Table';
