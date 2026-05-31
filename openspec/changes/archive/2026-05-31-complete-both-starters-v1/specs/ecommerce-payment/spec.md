## ADDED Requirements

### Requirement: Payment callback processing
The system SHALL accept and process payment callbacks from payment providers.

#### Scenario: Process successful payment callback
- **WHEN** a POST request is sent to `/api/payment/callback` with valid payment success notification
- **THEN** the system SHALL validate the callback signature
- **AND** create or update a payment record with status SUCCESS
- **AND** update the associated order status to PAID
- **AND** return HTTP 200 acknowledging receipt

#### Scenario: Process failed payment callback
- **WHEN** a POST request is sent to `/api/payment/callback` with payment failure notification
- **THEN** the system SHALL create or update a payment record with status FAILED
- **AND** keep the order status as PENDING
- **AND** return HTTP 200 acknowledging receipt

#### Scenario: Reject invalid callback signature
- **WHEN** a POST request is sent to `/api/payment/callback` with invalid signature
- **THEN** the system SHALL return HTTP 403
- **AND** the payment SHALL NOT be processed

#### Scenario: Handle duplicate callback
- **WHEN** a duplicate payment callback is received for the same transaction
- **THEN** the system SHALL return HTTP 200
- **AND** the payment record SHALL NOT be duplicated
- **AND** the order SHALL NOT be updated twice

### Requirement: Payment status query
The system SHALL support querying payment status by order ID.

#### Scenario: Query existing payment
- **WHEN** a GET request is sent to `/api/payment/order/{orderId}`
- **AND** a payment exists for the order
- **THEN** the system SHALL return the payment details including status and amount
- **AND** return HTTP 200

#### Scenario: Query non-existent payment
- **WHEN** a GET request is sent to `/api/payment/order/{orderId}`
- **AND** no payment exists for the order
- **THEN** the system SHALL return HTTP 404

### Requirement: Payment fields
The payment entity SHALL contain all necessary business fields.

#### Scenario: Payment has required fields
- **WHEN** creating a payment record
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique payment identifier (auto-generated)
  - `orderId`: Reference to the associated order
  - `transactionId`: External payment provider transaction ID
  - `channel`: Payment channel (ALIPAY/WECHAT/credit_card/etc.)
  - `amount`: Payment amount
  - `status`: Payment status (PENDING/SUCCESS/FAILED/REFUNDED)
  - `callbackData`: Raw callback data from payment provider
  - `createdAt`: Payment creation timestamp (auto-generated)
  - `updatedAt`: Last update timestamp (auto-updated)

### Requirement: Payment signature validation
The system SHALL validate payment callback signatures to prevent fraud.

#### Scenario: Validate Alipay callback
- **WHEN** receiving an Alipay callback
- **THEN** the system SHALL verify the signature using Alipay public key or secret
- **AND** reject callbacks with invalid signatures

#### Scenario: Validate WeChat Pay callback
- **WHEN** receiving a WeChat Pay callback
- **THEN** the system SHALL verify the signature using WeChat Pay API key
- **AND** reject callbacks with invalid signatures

### Requirement: Payment amount validation
The system SHALL validate payment amounts match order amounts.

#### Scenario: Validate payment amount matches order
- **WHEN** processing a payment callback
- **THEN** the payment amount SHALL match the order total amount
- **AND** if amounts don't match, the payment SHALL be marked as FAILED

#### Scenario: Handle zero payment amount
- **WHEN** a payment callback with zero amount is received
- **THEN** the system SHALL mark the payment as FAILED
- **AND** return HTTP 400

### Requirement: Payment channel support
The system SHALL support multiple payment channels.

#### Scenario: Support Alipay
- **WHEN** payment channel is set to ALIPAY
- **THEN** the system SHALL process Alipay-specific callback format
- **AND** validate using Alipay signature method

#### Scenario: Support WeChat Pay
- **WHEN** payment channel is set to WECHAT
- **THEN** the system SHALL process WeChat Pay-specific callback format
- **AND** validate using WeChat Pay signature method

#### Scenario: Support credit card
- **WHEN** payment channel is set to CREDIT_CARD
- **THEN** the system SHALL process credit card payment callback format

### Requirement: Payment module enablement
The payment module SHALL be configurable via properties and annotations.

#### Scenario: Enable via @EnableECommerce
- **WHEN** @EnableECommerce annotation is present without module specification
- **THEN** the payment module SHALL be enabled by default

#### Scenario: Disable via configuration
- **WHEN** `e-commerce.modules.payment.enabled=false` is configured
- **THEN** the payment module SHALL NOT be initialized
- **AND** payment-related APIs SHALL return HTTP 404

### Requirement: Payment retry mechanism
The system SHALL support payment retry logic for failed payments.

#### Scenario: Retry failed payment
- **WHEN** a payment fails with retryable error
- **THEN** the system SHALL maintain the payment record with FAILED status
- **AND** allow subsequent payment attempt for the same order

#### Scenario: Prevent multiple successful payments
- **WHEN** an order already has a SUCCESS payment
- **THEN** the system SHALL reject any additional payment callbacks
- **AND** return HTTP 400

### Requirement: Payment idempotency
The system SHALL ensure idempotent payment processing.

#### Scenario: Idempotent callback processing
- **WHEN** the same callback is received multiple times
- **THEN** the system SHALL process it only once
- **AND** return the same result for subsequent requests

### Requirement: Payment notification
The system SHALL provide hooks for payment notification events.

#### Scenario: Payment success notification
- **WHEN** a payment status changes to SUCCESS
- **THEN** the system SHALL publish a payment success event
- **AND** allow other components to subscribe to this event

#### Scenario: Payment failure notification
- **WHEN** a payment status changes to FAILED
- **THEN** the system SHALL publish a payment failure event
- **AND** allow other components to subscribe to this event
