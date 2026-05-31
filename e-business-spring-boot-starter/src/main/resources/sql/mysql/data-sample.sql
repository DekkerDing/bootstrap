-- ============================================================================
-- E-commerce Starter - Sample Data
-- 电商 Starter - 示例数据
-- ============================================================================

-- 插入分类数据 / Insert sample categories
INSERT INTO ecommerce_category (id, name, parent_id) VALUES
(1, 'Electronics', NULL),
(2, 'Computers', 1),
(3, 'Phones', 1),
(4, 'Clothing', NULL),
(5, 'Men', 4),
(6, 'Women', 4);

-- 插入商品数据 / Insert sample products
INSERT INTO ecommerce_product (name, description, price, stock, status, category_id) VALUES
('Laptop Pro 15', 'High-performance laptop with 15-inch display', 1299.99, 50, 'AVAILABLE', 2),
('Smartphone X', 'Latest smartphone with advanced features', 799.99, 100, 'AVAILABLE', 3),
('Wireless Headphones', 'Premium noise-cancelling headphones', 199.99, 200, 'AVAILABLE', 1),
('Men\'s T-Shirt', 'Comfortable cotton t-shirt', 29.99, 500, 'AVAILABLE', 5),
('Women\'s Dress', 'Elegant summer dress', 79.99, 150, 'AVAILABLE', 6);

-- 插入用户数据 / Insert sample users
INSERT INTO ecommerce_user (username, email, phone, real_name, status) VALUES
('john_doe', 'john@example.com', '1234567890', 'John Doe', 'ACTIVE'),
('jane_smith', 'jane@example.com', '0987654321', 'Jane Smith', 'ACTIVE'),
('bob_wilson', 'bob@example.com', '5555555555', 'Bob Wilson', 'ACTIVE');

-- 插入地址数据 / Insert sample addresses
INSERT INTO ecommerce_address (user_id, receiver_name, phone, province, city, district, detail_address, postal_code, is_default) VALUES
(1, 'John Doe', '1234567890', 'California', 'Los Angeles', 'Downtown', '123 Main Street, Apt 4B', '90001', TRUE),
(1, 'John Doe', '1234567890', 'California', 'San Francisco', 'Mission District', '456 Oak Avenue', '94102', FALSE),
(2, 'Jane Smith', '0987654321', 'New York', 'New York City', 'Manhattan', '789 Broadway, Suite 100', '10001', TRUE),
(3, 'Bob Wilson', '5555555555', 'Texas', 'Houston', 'Downtown', '321 Elm Street', '77001', TRUE);
