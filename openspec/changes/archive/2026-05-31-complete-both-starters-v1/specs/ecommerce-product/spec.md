## ADDED Requirements

### Requirement: Product CRUD operations
The system SHALL provide complete Create, Read, Update, Delete operations for product management.

#### Scenario: Create a new product
- **WHEN** a POST request is sent to `/api/products` with valid product data
- **THEN** the system SHALL create a new product record
- **AND** return HTTP 201 with the created product ID
- **AND** the product SHALL be persisted in the database

#### Scenario: Read a product by ID
- **WHEN** a GET request is sent to `/api/products/{id}` with a valid product ID
- **THEN** the system SHALL return the product details
- **AND** return HTTP 200 with the product data

#### Scenario: Read non-existent product
- **WHEN** a GET request is sent to `/api/products/{id}` with a non-existent ID
- **THEN** the system SHALL return HTTP 404

#### Scenario: Update a product
- **WHEN** a PUT request is sent to `/api/products/{id}` with updated product data
- **THEN** the system SHALL update the product record
- **AND** return HTTP 200 with the updated product data

#### Scenario: Delete a product
- **WHEN** a DELETE request is sent to `/api/products/{id}`
- **THEN** the system SHALL delete the product record
- **AND** return HTTP 204

### Requirement: Product listing
The system SHALL support listing products with pagination and filtering capabilities.

#### Scenario: List all products
- **WHEN** a GET request is sent to `/api/products`
- **THEN** the system SHALL return a paginated list of products
- **AND** return HTTP 200 with product list and pagination metadata

#### Scenario: List products with category filter
- **WHEN** a GET request is sent to `/api/products?categoryId={categoryId}`
- **THEN** the system SHALL return products belonging to the specified category
- **AND** return HTTP 200 with filtered product list

#### Scenario: List products with status filter
- **WHEN** a GET request is sent to `/api/products?status={status}`
- **THEN** the system SHALL return products with the specified status (active/inactive)
- **AND** return HTTP 200 with filtered product list

### Requirement: Product fields
The product entity SHALL contain all necessary business fields.

#### Scenario: Product has required fields
- **WHEN** creating a product
- **THEN** the system SHALL accept and store the following fields:
  - `id`: Unique identifier (auto-generated)
  - `name`: Product name (required)
  - `description`: Product description (optional)
  - `price`: Product price (required, positive decimal)
  - `stock`: Available stock quantity (required, non-negative integer)
  - `status`: Product status (ACTIVE/INACTIVE, default ACTIVE)
  - `categoryId`: Reference to category (required)
  - `createdAt`: Creation timestamp (auto-generated)
  - `updatedAt`: Last update timestamp (auto-updated)

### Requirement: Product validation
The system SHALL validate product data before creation and update.

#### Scenario: Validate required fields
- **WHEN** creating a product without required fields (name, price, stock, categoryId)
- **THEN** the system SHALL return HTTP 400 with validation error details
- **AND** the product SHALL NOT be created

#### Scenario: Validate positive price
- **WHEN** creating a product with a negative or zero price
- **THEN** the system SHALL return HTTP 400 with validation error

#### Scenario: Validate non-negative stock
- **WHEN** creating a product with negative stock
- **THEN** the system SHALL return HTTP 400 with validation error

#### Scenario: Validate category exists
- **WHEN** creating a product with a non-existent categoryId
- **THEN** the system SHALL return HTTP 400 with validation error

### Requirement: Product category management
The system SHALL support product category CRUD operations.

#### Scenario: Create a category
- **WHEN** a POST request is sent to `/api/categories` with valid category data
- **THEN** the system SHALL create a new category
- **AND** return HTTP 201 with the created category ID

#### Scenario: List categories
- **WHEN** a GET request is sent to `/api/categories`
- **THEN** the system SHALL return a list of all categories
- **AND** return HTTP 200

#### Scenario: Update a category
- **WHEN** a PUT request is sent to `/api/categories/{id}` with updated category data
- **THEN** the system SHALL update the category
- **AND** return HTTP 200

#### Scenario: Delete a category with products
- **WHEN** a DELETE request is sent to `/api/categories/{id}` that has associated products
- **THEN** the system SHALL return HTTP 400 with error message
- **AND** the category SHALL NOT be deleted

#### Scenario: Delete empty category
- **WHEN** a DELETE request is sent to `/api/categories/{id}` with no associated products
- **THEN** the system SHALL delete the category
- **AND** return HTTP 204

### Requirement: Product module enablement
The product module SHALL be configurable via properties and annotations.

#### Scenario: Enable via @EnableECommerce
- **WHEN** @EnableECommerce annotation is present without module specification
- **THEN** the product module SHALL be enabled by default

#### Scenario: Disable via configuration
- **WHEN** `e-commerce.modules.product.enabled=false` is configured
- **THEN** the product module SHALL NOT be initialized
- **AND** product-related APIs SHALL return HTTP 404

### Requirement: Product stock management
The system SHALL track product stock and prevent overselling.

#### Scenario: Check product stock availability
- **WHEN** querying a product
- **THEN** the response SHALL include the current stock quantity

#### Scenario: Update product stock
- **WHEN** a PATCH request is sent to `/api/products/{id}/stock` with new stock value
- **THEN** the system SHALL update the stock quantity
- **AND** return HTTP 200

#### Scenario: Prevent negative stock
- **WHEN** updating stock to a negative value
- **THEN** the system SHALL return HTTP 400 with validation error
