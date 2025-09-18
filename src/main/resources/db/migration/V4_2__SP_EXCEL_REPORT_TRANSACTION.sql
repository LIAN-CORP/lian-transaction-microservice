-- CREATE TABLE
CREATE TABLE IF NOT EXISTS temp_transaction_report
(
    transaction_id        UUID,
    client_name           VARCHAR,
    client_phone          VARCHAR,
    type_movement         VARCHAR,
    transaction_date      DATE,
    detail_transaction_id UUID,
    unit_price            NUMERIC,
    quantity              int4,
    total_price           NUMERIC,
    product_id            UUID,
    product_name          VARCHAR
);

CREATE OR REPLACE PROCEDURE sp_fill_temp_transaction_report(start_date DATE, end_date DATE)
LANGUAGE plpgsql AS
$$
BEGIN
    -- DELETE ALL DATA FROM TEMP TABLE
    TRUNCATE temp_transaction_report;

    --PERFORM dblink_disconnect('conn_stock');
    --PERFORM dblink_connect('conn_stock', 'server_stock_remote');

    PERFORM dblink_connect_u('stock_conn','host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}');

    -- VALIDATE IF STOCK CONNECTION EXISTS
    --IF NOT
    --    ('conn_stock' = ANY (dblink_get_connections()))
    --THEN
    --    PERFORM dblink_connect('conn_stock', 'server_stock_remote');
    --END IF;

    -- INSERT DATA
    INSERT INTO temp_transaction_report(transaction_id, client_name, client_phone, type_movement, transaction_date,
                                        detail_transaction_id, unit_price, quantity, total_price, product_id,
                                        product_name)
    SELECT t.id                          as transaction_id,
           c.name                        as client_name,
           c.phone                       as client_phone,
           t.type_movement               as type_movement,
           t.transaction_date            as transaction_date,
           dt.id                         as detail_transaction_id,
           dt.unit_price                 as unit_price,
           dt.quantity                   as quantity,
           (dt.unit_price * dt.quantity) as total_price,
           dt.product_id                 as product_id,
           s.name                        as product_name
    FROM transactions t
             INNER JOIN client c ON t.client_id = c.id
             INNER JOIN detail_transaction dt on t.id = dt.transaction_id
             INNER JOIN (SELECT *
                         FROM dblink('stock_conn', 'SELECT id, name FROM product')
                                  AS s(product_id uuid, name VARCHAR)) s ON s.product_id = dt.product_id
    WHERE t.transaction_date BETWEEN start_date AND end_date
    ORDER BY t.transaction_date ASC;
    PERFORM dblink_disconnect('stock_conn');
END;
$$;
