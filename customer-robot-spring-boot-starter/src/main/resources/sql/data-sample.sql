-- =====================================================
-- 客服机器人 Starter 示例数据脚本
-- Customer Robot Starter Sample Data Script
-- =====================================================

-- 插入客户数据 / Insert Customers
INSERT INTO customer_customer (customer_no, name, phone, email, source, type) VALUES
('C20240001', '张三', '13800138001', 'zhangsan@example.com', 'WEB', 'NORMAL'),
('C20240002', '李四', '13800138002', 'lisi@example.com', 'APP', 'VIP'),
('C20240003', '王五', '13800138003', 'wangwu@example.com', 'WECHAT', 'NORMAL');

-- 插入对话数据 / Insert Conversations
INSERT INTO customer_conversation (conversation_no, customer_id, status, type, priority, title) VALUES
('CONV20240001', 1, 'IN_PROGRESS', 'TEXT', 'NORMAL', '产品咨询'),
('CONV20240002', 2, 'WAITING', 'TEXT', 'HIGH', 'VIP 客户服务'),
('CONV20240003', 3, 'CLOSED', 'TEXT', 'NORMAL', '售后服务');
