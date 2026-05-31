# customer-robot-conversation Specification

## Purpose
TBD - created by archiving change complete-both-starters-v1. Update Purpose after archive.
## Requirements
### Requirement: Conversation lifecycle management
The system SHALL support creating, managing, and closing customer service conversations.

#### Scenario: Create new conversation
- **WHEN** a POST request is sent to `/api/conversations` with valid conversation data
- **THEN** the system SHALL create a new conversation record
- **AND** assign a unique conversation ID
- **AND** set initial status to ACTIVE
- **AND** return HTTP 201 with the conversation details

#### Scenario: Get conversation by ID
- **WHEN** a GET request is sent to `/api/conversations/{id}` with a valid conversation ID
- **THEN** the system SHALL return the complete conversation details
- **AND** return HTTP 200

#### Scenario: Get non-existent conversation
- **WHEN** a GET request is sent to `/api/conversations/{id}` with a non-existent ID
- **THEN** the system SHALL return HTTP 404

#### Scenario: List conversations with pagination
- **WHEN** a GET request is sent to `/api/conversations`
- **THEN** the system SHALL return a paginated list of conversations
- **AND** return HTTP 200 with pagination metadata

#### Scenario: Close conversation
- **WHEN** a PUT request is sent to `/api/conversations/{id}/close`
- **AND** the conversation is currently ACTIVE
- **THEN** the system SHALL update the conversation status to CLOSED
- **AND** record the closed timestamp
- **AND** return HTTP 200

#### Scenario: Reopen closed conversation
- **WHEN** a PUT request is sent to `/api/conversations/{id}/reopen`
- **AND** the conversation is currently CLOSED
- **THEN** the system SHALL update the conversation status to ACTIVE
- **AND** return HTTP 200

### Requirement: Conversation fields
The conversation entity SHALL contain all necessary business fields.

#### Scenario: Conversation has required fields
- **WHEN** creating a conversation
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique conversation identifier (auto-generated)
  - `customerId`: Customer identifier (required)
  - `agentId`: Assigned agent ID (optional, null for bot conversations)
  - `channel`: Communication channel (WEB/WECHAT/PHONE/EMAIL, etc.)
  - `status`: Conversation status (ACTIVE/CLOSED/TRANSFERRED)
  - `priority`: Conversation priority (LOW/NORMAL/HIGH/URGENT, default NORMAL)
  - `tags`: Conversation tags for categorization (optional)
  - `createdAt`: Conversation creation timestamp (auto-generated)
  - `updatedAt`: Last update timestamp (auto-updated)
  - `closedAt`: Conversation close timestamp (nullable)

### Requirement: Message management
The system SHALL support sending and retrieving messages within conversations.

#### Scenario: Send message to conversation
- **WHEN** a POST request is sent to `/api/conversations/{id}/messages` with valid message data
- **AND** the conversation exists and is ACTIVE
- **THEN** the system SHALL create a new message record
- **AND** link it to the conversation
- **AND** return HTTP 201 with the message details

#### Scenario: Send message to closed conversation
- **WHEN** a POST request is sent to `/api/conversations/{id}/messages`
- **AND** the conversation is CLOSED
- **THEN** the system SHALL return HTTP 400 with error message
- **AND** the message SHALL NOT be created

#### Scenario: Get conversation messages
- **WHEN** a GET request is sent to `/api/conversations/{id}/messages`
- **THEN** the system SHALL return all messages for the conversation in chronological order
- **AND** return HTTP 200

#### Scenario: Get conversation messages with pagination
- **WHEN** a GET request is sent to `/api/conversations/{id}/messages?page={page}&size={size}`
- **THEN** the system SHALL return paginated messages
- **AND** return HTTP 200 with pagination metadata

### Requirement: Message fields
The message entity SHALL contain all necessary communication fields.

#### Scenario: Message has required fields
- **WHEN** creating a message
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique message identifier (auto-generated)
  - `conversationId`: Reference to parent conversation
  - `senderId`: Sender identifier (customer or agent)
  - `senderType`: Sender type (CUSTOMER/AGENT/SYSTEM)
  - `content`: Message text content
  - `messageType`: Message type (TEXT/IMAGE/FILE/SYSTEM_NOTIFICATION)
  - `direction`: Message direction (INBOUND/OUTBOUND)
  - `sentAt`: Message sent timestamp (auto-generated)
  - `readAt`: Message read timestamp (nullable)
  - `metadata`: Additional metadata in JSON format (optional)

### Requirement: Message read status
The system SHALL track message read status for agent messages.

#### Scenario: Mark message as read
- **WHEN** a PUT request is sent to `/api/messages/{id}/read`
- **THEN** the system SHALL update the message read timestamp
- **AND** return HTTP 200

#### Scenario: Get unread message count
- **WHEN** a GET request is sent to `/api/conversations/{id}/unread-count`
- **THEN** the system SHALL return the count of unread messages in the conversation
- **AND** return HTTP 200

### Requirement: Conversation assignment
The system SHALL support assigning conversations to agents.

#### Scenario: Assign conversation to agent
- **WHEN** a PUT request is sent to `/api/conversations/{id}/assign` with agent ID
- **AND** the conversation exists and is ACTIVE
- **THEN** the system SHALL update the agentId field
- **AND** set status to ASSIGNED if not already assigned
- **AND** return HTTP 200

#### Scenario: Reassign conversation to different agent
- **WHEN** a PUT request is sent to `/api/conversations/{id}/assign` with a different agent ID
- **AND** the conversation is currently assigned to another agent
- **THEN** the system SHALL update the agentId field
- **AND** create a system message noting the transfer
- **AND** return HTTP 200

#### Scenario: Unassign conversation
- **WHEN** a DELETE request is sent to `/api/conversations/{id}/assign`
- **THEN** the system SHALL set agentId to null
- **AND** set status to ACTIVE (or previous non-assigned status)
- **AND** return HTTP 200

### Requirement: Conversation module enablement
The conversation module SHALL be configurable via properties and annotations.

#### Scenario: Enable via @EnableCustomerRobot
- **WHEN** @EnableCustomerRobot annotation is present without module specification
- **THEN** the conversation module SHALL be enabled by default

#### Scenario: Disable via configuration
- **WHEN** `customer-robot.modules.conversation.enabled=false` is configured
- **THEN** the conversation module SHALL NOT be initialized
- **AND** conversation-related APIs SHALL return HTTP 404

### Requirement: Conversation search and filtering
The system SHALL support searching and filtering conversations.

#### Scenario: Filter conversations by status
- **WHEN** a GET request is sent to `/api/conversations?status={status}`
- **THEN** the system SHALL return conversations with the specified status
- **AND** return HTTP 200

#### Scenario: Filter conversations by agent
- **WHEN** a GET request is sent to `/api/conversations?agentId={agentId}`
- **THEN** the system SHALL return conversations assigned to the specified agent
- **AND** return HTTP 200

#### Scenario: Filter conversations by customer
- **WHEN** a GET request is sent to `/api/conversations?customerId={customerId}`
- **THEN** the system SHALL return conversations for the specified customer
- **AND** return HTTP 200

#### Scenario: Filter conversations by priority
- **WHEN** a GET request is sent to `/api/conversations?priority={priority}`
- **THEN** the system SHALL return conversations with the specified priority
- **AND** return HTTP 200

### Requirement: Conversation tagging
The system SHALL support adding and removing tags from conversations.

#### Scenario: Add tag to conversation
- **WHEN** a POST request is sent to `/api/conversations/{id}/tags` with tag name
- **THEN** the system SHALL add the tag to the conversation
- **AND** return HTTP 200

#### Scenario: Remove tag from conversation
- **WHEN** a DELETE request is sent to `/api/conversations/{id}/tags/{tag}`
- **THEN** the system SHALL remove the tag from the conversation
- **AND** return HTTP 200

#### Scenario: Filter conversations by tag
- **WHEN** a GET request is sent to `/api/conversations?tag={tag}`
- **THEN** the system SHALL return conversations with the specified tag
- **AND** return HTTP 200

