CREATE EXTENSION IF NOT EXISTS dblink;

-- REMOTE CONNECTION TO PAYMENT DATABASE
CREATE SERVER IF NOT EXISTS server_payment_remote
    FOREIGN DATA WRAPPER dblink_fdw
    OPTIONS (host 'localhost', dbname 'lian_payment', port '5432');
GRANT USAGE ON FOREIGN SERVER server_payment_remote TO lian_admin;
CREATE USER MAPPING IF NOT EXISTS FOR lian_admin SERVER server_payment_remote OPTIONS (user 'lian_admin', password 'lian_admin!');

-- REMOTE CONNECTION TO STOCK DATABASE
CREATE SERVER IF NOT EXISTS server_stock_remote
FOREIGN DATA WRAPPER dblink_fdw
OPTIONS (host 'localhost', dbname 'lian_stock', port '5432');
GRANT USAGE ON FOREIGN SERVER server_stock_remote TO lian_admin;
CREATE USER MAPPING IF NOT EXISTS FOR lian_admin SERVER server_stock_remote OPTIONS (user 'lian_admin', password 'lian_admin!');


