-- Create tables

-- Carriers table
CREATE TABLE carriers (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          contact_name VARCHAR(255),
                          contact_email VARCHAR(255),
                          contact_phone VARCHAR(50),
                          rate_ground DECIMAL(10, 2) DEFAULT 5.00,
                          rate_air DECIMAL(10, 2) DEFAULT 10.00,
                          rate_ocean DECIMAL(10, 2) DEFAULT 3.00,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Warehouses table
CREATE TABLE warehouses (
                            id SERIAL PRIMARY KEY,
                            latitude DECIMAL(10, 7) NOT NULL,
                            longitude DECIMAL(10, 7) NOT NULL,
                            capacity INTEGER NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers table
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           contact_name VARCHAR(255),
                           contact_email VARCHAR(255),
                           contact_phone VARCHAR(50),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Vehicles table
CREATE TABLE vehicles (
                          id SERIAL PRIMARY KEY,
                          plate_number VARCHAR(50) UNIQUE NOT NULL,
                          type VARCHAR(50),
                          capacity DECIMAL(10, 2),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Shipments table
CREATE TABLE shipments (
                           id SERIAL PRIMARY KEY,
                           sender_street VARCHAR(255),
                           sender_city VARCHAR(100),
                           sender_country VARCHAR(100),
                           receiver_street VARCHAR(255),
                           receiver_city VARCHAR(100),
                           receiver_country VARCHAR(100),
                           weight DECIMAL(10, 2) NOT NULL,
                           status_code INTEGER DEFAULT 100,
                           status_description VARCHAR(100) DEFAULT 'Created',
                           carrier_id INTEGER,
                           warehouse_id INTEGER,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (carrier_id) REFERENCES carriers(id) ON DELETE SET NULL,
                           FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE SET NULL
);

-- Routes table
CREATE TABLE routes (
                        id SERIAL PRIMARY KEY,
                        origin_latitude DECIMAL(10, 7) NOT NULL,
                        origin_longitude DECIMAL(10, 7) NOT NULL,
                        destination_latitude DECIMAL(10, 7) NOT NULL,
                        destination_longitude DECIMAL(10, 7) NOT NULL,
                        distance DECIMAL(10, 2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Items table (for warehouse inventory)
CREATE TABLE items (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       quantity INTEGER DEFAULT 0,
                       warehouse_id INTEGER,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE CASCADE
);

-- Orders table
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        order_number VARCHAR(50) UNIQUE NOT NULL,
                        customer_id INTEGER NOT NULL,
                        total_amount DECIMAL(10, 2),
                        status VARCHAR(50) DEFAULT 'Pending',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Order Items table (many-to-many relationship between orders and products)
CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INTEGER NOT NULL,
                             product_id INTEGER NOT NULL,
                             quantity INTEGER NOT NULL,
                             unit_price DECIMAL(10, 2),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Shipment tracking history table
CREATE TABLE shipment_tracking (
                                   id SERIAL PRIMARY KEY,
                                   shipment_id INTEGER NOT NULL,
                                   status_code INTEGER NOT NULL,
                                   status_description VARCHAR(100),
                                   location_latitude DECIMAL(10, 7),
                                   location_longitude DECIMAL(10, 7),
                                   notes TEXT,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (shipment_id) REFERENCES shipments(id) ON DELETE CASCADE
);

-- Vehicle assignments table (for tracking which vehicle handles which shipment)
CREATE TABLE vehicle_assignments (
                                     id SERIAL PRIMARY KEY,
                                     vehicle_id INTEGER NOT NULL,
                                     shipment_id INTEGER NOT NULL,
                                     assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     completed_at TIMESTAMP,
                                     FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
                                     FOREIGN KEY (shipment_id) REFERENCES shipments(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_shipments_carrier ON shipments(carrier_id);
CREATE INDEX idx_shipments_warehouse ON shipments(warehouse_id);
CREATE INDEX idx_shipments_status ON shipments(status_code);
CREATE INDEX idx_items_warehouse ON items(warehouse_id);
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);
CREATE INDEX idx_shipment_tracking_shipment ON shipment_tracking(shipment_id);
CREATE INDEX idx_vehicle_assignments_vehicle ON vehicle_assignments(vehicle_id);
CREATE INDEX idx_vehicle_assignments_shipment ON vehicle_assignments(shipment_id);