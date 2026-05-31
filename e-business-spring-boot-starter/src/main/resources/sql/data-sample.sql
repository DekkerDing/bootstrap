-- =====================================================
-- 电商 Starter 示例数据脚本
-- E-commerce Starter Sample Data Script
-- =====================================================

-- 插入商品分类 / Insert Categories
INSERT INTO ecommerce_category (name, parent_id) VALUES
('电子产品 / Electronics', NULL),
('服装 / Clothing', NULL),
('食品 / Food', NULL),
('手机 / Mobile Phones', 1),
('电脑 / Computers', 1),
('男装 / Men''s Clothing', 2),
('女装 / Women''s Clothing', 2);

-- 插入商品 / Insert Products
INSERT INTO ecommerce_product (name, description, price, stock, status, category_id) VALUES
('iPhone 15 Pro', '最新款苹果手机，配备 A17 芯片 / Latest Apple phone with A17 chip', 7999.00, 100, 'ACTIVE', 4),
('MacBook Pro', '14英寸 M3 芯片笔记本电脑 / 14-inch M3 laptop', 12999.00, 50, 'ACTIVE', 5),
('男士 T 恤', '纯棉舒适 T 恤 / Pure cotton comfortable T-shirt', 99.00, 500, 'ACTIVE', 6),
('女士连衣裙', '时尚优雅连衣裙 / Fashionable elegant dress', 199.00, 300, 'ACTIVE', 7);

-- 插入用户 / Insert Users
INSERT INTO ecommerce_user (username, email, phone, real_name, status) VALUES
('john_doe', 'john@example.com', '13800138001', 'John Doe', 'ACTIVE'),
('jane_smith', 'jane@example.com', '13800138002', 'Jane Smith', 'ACTIVE'),
('bob_wilson', 'bob@example.com', '13800138003', 'Bob Wilson', 'ACTIVE');

-- 插入地址 / Insert Addresses
INSERT INTO ecommerce_address (user_id, receiver_name, phone, province, city, district, detail_address, postal_code, is_default) VALUES
(1, 'John Doe', '13800138001', '北京市', '北京市', '朝阳区', '建国路 88 号', '100025', TRUE),
(2, 'Jane Smith', '13800138002', '上海市', '上海市', '浦东新区', '世纪大道 100 号', '200120', TRUE);

-- 注意：订单和支付数据需要根据实际业务逻辑生成
-- Note: Order and payment data should be generated based on actual business logic
