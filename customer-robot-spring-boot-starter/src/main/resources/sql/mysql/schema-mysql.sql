-- =====================================================
-- 客服机器人 Starter MySQL 建表脚本
-- Customer Robot Starter MySQL Schema Script
-- =====================================================

-- 客户表 / Customer Table
CREATE TABLE IF NOT EXISTS customer_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '客户 ID / Customer ID',
    customer_no VARCHAR(50) NOT NULL UNIQUE COMMENT '客户编号 / Customer number',
    name VARCHAR(100) NOT NULL COMMENT '客户名称 / Customer name',
    phone VARCHAR(20) COMMENT '联系电话 / Phone',
    email VARCHAR(100) COMMENT '电子邮箱 / Email',
    source VARCHAR(20) COMMENT '客户来源 / Customer source',
    type VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '客户类型 / Customer type',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '客户状态 / Customer status',
    remarks TEXT COMMENT '备注 / Remarks',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_customer_no (customer_no),
    INDEX idx_phone (phone),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表 / Customer Table';

-- 对话表 / Conversation Table
CREATE TABLE IF NOT EXISTS customer_conversation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '对话 ID / Conversation ID',
    conversation_no VARCHAR(50) NOT NULL UNIQUE COMMENT '对话编号 / Conversation number',
    customer_id BIGINT NOT NULL COMMENT '客户 ID / Customer ID',
    agent_id BIGINT COMMENT '客服 ID / Agent ID',
    status VARCHAR(20) NOT NULL DEFAULT 'WAITING' COMMENT '对话状态 / Conversation status',
    type VARCHAR(20) NOT NULL DEFAULT 'TEXT' COMMENT '对话类型 / Conversation type',
    priority VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '优先级 / Priority',
    title VARCHAR(200) COMMENT '标题 / Title',
    start_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间 / Start time',
    end_time DATETIME COMMENT '结束时间 / End time',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 / Update time',
    INDEX idx_conversation_no (conversation_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_agent_id (agent_id),
    INDEX idx_status (status),
    FOREIGN KEY (customer_id) REFERENCES customer_customer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话表 / Conversation Table';

-- 消息表 / Message Table
CREATE TABLE IF NOT EXISTS customer_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '消息 ID / Message ID',
    conversation_id BIGINT NOT NULL COMMENT '对话 ID / Conversation ID',
    sender_id BIGINT COMMENT '发送者 ID / Sender ID',
    receiver_id BIGINT COMMENT '接收者 ID / Receiver ID',
    sender_type VARCHAR(20) COMMENT '发送者类型 / Sender type',
    message_type VARCHAR(20) NOT NULL DEFAULT 'TEXT' COMMENT '消息类型 / Message type',
    content TEXT NOT NULL COMMENT '消息内容 / Message content',
    is_read TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已读 / Is read',
    read_at DATETIME COMMENT '读取时间 / Read time',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_is_read (is_read),
    FOREIGN KEY (conversation_id) REFERENCES customer_conversation(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表 / Message Table';

-- 会话表 / Session Table
CREATE TABLE IF NOT EXISTS customer_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '会话 ID / Session ID',
    token VARCHAR(100) NOT NULL UNIQUE COMMENT '会话令牌 / Session token',
    customer_id BIGINT COMMENT '客户 ID / Customer ID',
    ip_address VARCHAR(50) COMMENT 'IP 地址 / IP address',
    user_agent VARCHAR(500) COMMENT '用户代理 / User agent',
    login_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间 / Login time',
    last_active_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后活跃时间 / Last active time',
    expiry_at DATETIME NOT NULL DEFAULT (CURRENT_TIMESTAMP + INTERVAL 24 HOUR) COMMENT '过期时间 / Expiry time',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '会话状态 / Session status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 / Creation time',
    INDEX idx_token (token),
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (status),
    INDEX idx_expiry_at (expiry_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表 / Session Table';
