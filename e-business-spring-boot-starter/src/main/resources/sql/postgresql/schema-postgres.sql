-- =====================================================
-- 电商 Starter PostgreSQL 建表脚本
-- E-commerce Starter PostgreSQL Schema Script
-- =====================================================

-- 商品分类表 / Category Table
CREATE TABLE IF NOT EXISTS ecommerce_category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_category_parent_id ON ecommerce_category(parent_id);
CREATE INDEX IF NOT EXISTS idx_category_name ON ecommerce_category(name);

COMMENT ON TABLE ecommerce_category IS '商品分类表 / Category Table';
COMMENT ON COLUMN ecommerce_category.id IS '分类 ID / Category ID';
COMMENT ON COLUMN ecommerce_category.name IS '分类名称 / Category name';
COMMENT ON COLUMN ecommerce_category.parent_id IS '父级 ID / Parent ID';
COMMENT ON COLUMN ecommerce_category.created_at IS '创建时间 / Creation time';
COMMENT ON COLUMN ecommerce_category.updated_at IS '更新时间 / Update time';

-- 商品表 / Product Table
CREATE TABLE IF NOT EXISTS ecommerce_product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    category_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_product_category_id ON ecommerce_product(category_id);
CREATE INDEX IF NOT EXISTS idx_product_status ON ecommerce_product(status);
CREATE INDEX IF NOT EXISTS idx_product_name ON ecommerce_product(name);
ALTER TABLE ecommerce_product ADD CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES ecommerce_category(id) ON DELETE SET NULL;

COMMENT ON TABLE ecommerce_product IS '商品表 / Product Table';
COMMENT ON COLUMN ecommerce_product.id IS '商品 ID / Product ID';
COMMENT ON COLUMN ecommerce_product.name IS '商品名称 / Product name';
COMMENT ON COLUMN ecommerce_product.description IS '商品描述 / Product description';
COMMENT ON COLUMN ecommerce_product.price IS '商品价格 / Product price';
COMMENT ON COLUMN ecommerce_product.stock IS '库存数量 / Stock quantity';
COMMENT ON COLUMN ecommerce_product.status IS '商品状态 / Product status';
COMMENT ON COLUMN ecommerce_product.category_id IS '分类 ID / Category ID';

-- 用户表 / User Table
CREATE TABLE IF NOT EXISTS ecommerce_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    real_name VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_username ON ecommerce_user(username);
CREATE INDEX IF NOT EXISTS idx_user_email ON ecommerce_user(email);
CREATE INDEX IF NOT EXISTS idx_user_status ON ecommerce_user(status);

COMMENT ON TABLE ecommerce_user IS '用户表 / User Table';
COMMENT ON COLUMN ecommerce_user.id IS '用户 ID / User ID';
COMMENT ON COLUMN ecommerce_user.username IS '用户名 / Username';
COMMENT ON COLUMN ecommerce_user.email IS '电子邮箱 / Email';
COMMENT ON COLUMN ecommerce_user.phone IS '电话号码 / Phone';
COMMENT ON COLUMN ecommerce_user.real_name IS '真实姓名 / Real name';
COMMENT ON COLUMN ecommerce_user.status IS '用户状态 / User status';

-- 地址表 / Address Table
CREATE TABLE IF NOT EXISTS ecommerce_address (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    receiver_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    province VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    detail_address VARCHAR(200) NOT NULL,
    postal_code VARCHAR(10),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_address_user_id ON ecommerce_address(user_id);
CREATE INDEX IF NOT EXISTS idx_address_is_default ON ecommerce_address(is_default);
ALTER TABLE ecommerce_address ADD CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES ecommerce_user(id) ON DELETE CASCADE;

COMMENT ON TABLE ecommerce_address IS '地址表 / Address Table';
COMMENT ON COLUMN ecommerce_address.id IS '地址 ID / Address ID';
COMMENT ON COLUMN ecommerce_address.user_id IS '用户 ID / User ID';
COMMENT ON COLUMN ecommerce_address.receiver_name IS '收件人姓名 / Receiver name';
COMMENT ON COLUMN ecommerce_address.phone IS '联系电话 / Phone';
COMMENT ON COLUMN ecommerce_address.province IS '省份 / Province';
COMMENT ON COLUMN ecommerce_address.city IS '城市 / City';
COMMENT ON COLUMN ecommerce_address.district IS '区/县 / District';
COMMENT ON COLUMN ecommerce_address.detail_address IS '详细地址 / Detail address';
COMMENT ON COLUMN ecommerce_address.postal_code IS '邮政编码 / Postal code';
COMMENT ON COLUMN ecommerce_address.is_default IS '是否默认地址 / Is default';

-- 订单表 / Order Table
CREATE TABLE IF NOT EXISTS ecommerce_order (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_order_number ON ecommerce_order(order_number);
CREATE INDEX IF NOT EXISTS idx_order_user_id ON ecommerce_order(user_id);
CREATE INDEX IF NOT EXISTS idx_order_status ON ecommerce_order(status);
ALTER TABLE ecommerce_order ADD CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES ecommerce_user(id) ON DELETE CASCADE;

COMMENT ON TABLE ecommerce_order IS '订单表 / Order Table';
COMMENT ON COLUMN ecommerce_order.id IS '订单 ID / Order ID';
COMMENT ON COLUMN ecommerce_order.order_number IS '订单号 / Order number';
COMMENT ON COLUMN ecommerce_order.user_id IS '用户 ID / User ID';
COMMENT ON COLUMN ecommerce_order.status IS '订单状态 / Order status';
COMMENT ON COLUMN ecommerce_order.total_amount IS '订单总金额 / Total amount';

-- 订单项表 / Order Item Table
CREATE TABLE IF NOT EXISTS ecommerce_order_item (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_order_item_order_id ON ecommerce_order_item(order_id);
CREATE INDEX IF NOT EXISTS idx_order_item_product_id ON ecommerce_order_item(product_id);
ALTER TABLE ecommerce_order_item ADD CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES ecommerce_order(id) ON DELETE CASCADE;
ALTER TABLE ecommerce_order_item ADD CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES ecommerce_product(id) ON DELETE SET NULL;

COMMENT ON TABLE ecommerce_order_item IS '订单项表 / Order Item Table';
COMMENT ON COLUMN ecommerce_order_item.id IS '订单项 ID / Order Item ID';
COMMENT ON COLUMN ecommerce_order_item.order_id IS '订单 ID / Order ID';
COMMENT ON COLUMN ecommerce_order_item.product_id IS '商品 ID / Product ID';
COMMENT ON COLUMN ecommerce_order_item.product_name IS '商品名称 / Product name';
COMMENT ON COLUMN ecommerce_order_item.product_price IS '商品价格 / Product price';
COMMENT ON COLUMN ecommerce_order_item.quantity IS '数量 / Quantity';
COMMENT ON COLUMN ecommerce_order_item.subtotal IS '小计 / Subtotal';

-- 支付表 / Payment Table
CREATE TABLE IF NOT EXISTS ecommerce_payment (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    transaction_id VARCHAR(100),
    channel VARCHAR(20),
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    callback_data TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_payment_order_id ON ecommerce_payment(order_id);
CREATE INDEX IF NOT EXISTS idx_payment_transaction_id ON ecommerce_payment(transaction_id);
CREATE INDEX IF NOT EXISTS idx_payment_status ON ecommerce_payment(status);
ALTER TABLE ecommerce_payment ADD CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES ecommerce_order(id) ON DELETE CASCADE;

COMMENT ON TABLE ecommerce_payment IS '支付表 / Payment Table';
COMMENT ON COLUMN ecommerce_payment.id IS '支付 ID / Payment ID';
COMMENT ON COLUMN ecommerce_payment.order_id IS '订单 ID / Order ID';
COMMENT ON COLUMN ecommerce_payment.transaction_id IS '交易 ID / Transaction ID';
COMMENT ON COLUMN ecommerce_payment.channel IS '支付渠道 / Payment channel';
COMMENT ON COLUMN ecommerce_payment.amount IS '支付金额 / Payment amount';
COMMENT ON COLUMN ecommerce_payment.status IS '支付状态 / Payment status';
COMMENT ON COLUMN ecommerce_payment.callback_data IS '回调数据 / Callback data';

-- 更新时间戳触发器 / Update timestamp trigger
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON ecommerce_category FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_product_updated_at BEFORE UPDATE ON ecommerce_product FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_user_updated_at BEFORE UPDATE ON ecommerce_user FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_address_updated_at BEFORE UPDATE ON ecommerce_address FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_order_updated_at BEFORE UPDATE ON ecommerce_order FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_order_item_updated_at BEFORE UPDATE ON ecommerce_order_item FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON ecommerce_payment FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
