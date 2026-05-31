-- =====================================================
-- 客服机器人 Starter PostgreSQL 建表脚本
-- Customer Robot Starter PostgreSQL Schema Script
-- =====================================================

-- 客户表 / Customer Table
CREATE TABLE IF NOT EXISTS customer_customer (
    id BIGSERIAL PRIMARY KEY,
    customer_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    source VARCHAR(20),
    type VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_customer_customer_no ON customer_customer(customer_no);
CREATE INDEX IF NOT EXISTS idx_customer_phone ON customer_customer(phone);
CREATE INDEX IF NOT EXISTS idx_customer_email ON customer_customer(email);
CREATE INDEX IF NOT EXISTS idx_customer_status ON customer_customer(status);

COMMENT ON TABLE customer_customer IS '客户表 / Customer Table';

-- 对话表 / Conversation Table
CREATE TABLE IF NOT EXISTS customer_conversation (
    id BIGSERIAL PRIMARY KEY,
    conversation_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    agent_id BIGINT,
    status VARCHAR(20) NOT NULL DEFAULT 'WAITING',
    type VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    priority VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    title VARCHAR(200),
    start_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_conv_conversation_no ON customer_conversation(conversation_no);
CREATE INDEX IF NOT EXISTS idx_conv_customer_id ON customer_conversation(customer_id);
CREATE INDEX IF NOT EXISTS idx_conv_agent_id ON customer_conversation(agent_id);
CREATE INDEX IF NOT EXISTS idx_conv_status ON customer_conversation(status);
ALTER TABLE customer_conversation ADD CONSTRAINT fk_conv_customer FOREIGN KEY (customer_id) REFERENCES customer_customer(id) ON DELETE CASCADE;

COMMENT ON TABLE customer_conversation IS '对话表 / Conversation Table';

-- 消息表 / Message Table
CREATE TABLE IF NOT EXISTS customer_message (
    id BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT,
    receiver_id BIGINT,
    sender_type VARCHAR(20),
    message_type VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    content TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    read_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_msg_conversation_id ON customer_message(conversation_id);
CREATE INDEX IF NOT EXISTS idx_msg_sender_id ON customer_message(sender_id);
CREATE INDEX IF NOT EXISTS idx_msg_is_read ON customer_message(is_read);
ALTER TABLE customer_message ADD CONSTRAINT fk_msg_conversation FOREIGN KEY (conversation_id) REFERENCES customer_conversation(id) ON DELETE CASCADE;

COMMENT ON TABLE customer_message IS '消息表 / Message Table';

-- 会话表 / Session Table
CREATE TABLE IF NOT EXISTS customer_session (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(100) NOT NULL UNIQUE,
    customer_id BIGINT,
    ip_address VARCHAR(50),
    user_agent VARCHAR(500),
    login_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_active_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiry_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP + INTERVAL '24 hours',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_session_token ON customer_session(token);
CREATE INDEX IF NOT EXISTS idx_session_customer_id ON customer_session(customer_id);
CREATE INDEX IF NOT EXISTS idx_session_status ON customer_session(status);
CREATE INDEX IF NOT EXISTS idx_session_expiry_at ON customer_session(expiry_at);

COMMENT ON TABLE customer_session IS '会话表 / Session Table';

-- 更新时间戳触发器 / Update timestamp trigger
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_customer_updated_at BEFORE UPDATE ON customer_customer FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_conversation_updated_at BEFORE UPDATE ON customer_conversation FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
