CREATE TABLE customers (
    id UUID PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    curp VARCHAR(18) NOT NULL,
    birth_date DATE NOT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT false,
    phone_verified BOOLEAN NOT NULL DEFAULT false,
    identity_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    -- Restricciones únicas con nombres legibles de negocio
    CONSTRAINT uk_customers_email UNIQUE (email),
    CONSTRAINT uk_customers_phone UNIQUE (phone)
);