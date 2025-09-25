CREATE TABLE customer (
    unique_id_number BIGINT PRIMARY KEY,
    date_of_birth VARCHAR(20) NOT NULL,
    email_address VARCHAR(50) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    id_type VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    state VARCHAR(20) NOT NULL
);

-- Create sim_details table
CREATE TABLE sim_details (
    sim_id SERIAL PRIMARY KEY,
    service_number BIGINT NOT NULL UNIQUE,
    sim_number BIGINT NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'inactive'
);

-- Insert sample data into customer table
INSERT INTO customer (unique_id_number, date_of_birth, email_address, first_name, last_name, id_type, city, state) VALUES
    (1234567890123456, '1990-05-15', 'john.doe@email.com', 'John', 'Doe', 'Aadhar', 'Bangalore', 'Karnataka'),
    (1234567890123457, '1985-08-22', 'jane.smith@email.com', 'Jane', 'Smith', 'Aadhar', 'Bangalore', 'Karnataka'),
    (1234567890123458, '1992-12-10', 'mike.johnson@email.com', 'Mike', 'Johnson', 'Aadhar', 'Mumbai', 'Maharashtra'),
    (1234567890123459, '1988-03-18', 'sarah.wilson@email.com', 'Sarah', 'Wilson', 'Aadhar', 'Bangalore', 'Karnataka'),
    (1234567890123460, '1995-07-25', 'raj.patel@email.com', 'Raj', 'Patel', 'Aadhar', 'Delhi', 'Delhi');

-- Insert sample data into sim_details table
INSERT INTO sim_details (service_number, sim_number, status) VALUES
    (9876543210, 1111222233334444, 'active'),
    (9876543211, 1111222233334445, 'inactive'),
    (9876543212, 1111222233334446, 'active'),
    (9876543213, 1111222233334447, 'inactive'),
    (9876543214, 1111222233334448, 'active');