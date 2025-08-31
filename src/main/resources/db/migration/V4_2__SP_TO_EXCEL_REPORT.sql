CREATE OR REPLACE FUNCTION SP_TO_EXCEL_REPORT(date start_date, date end_date)
returns table (
    transaction_id UUID,
    client_name VARCHAR,
    client_phone VARCHAR,
    type_movement VARCHAR,
    transaction_date DATE,
    detail_transaction_id UUID,
    unit_price NUMERIC,
    quantity int4,
    total_price NUMERIC,
    product_id UUID,
    product_name VARCHAR
) as
$$
    BEGIN
        PERFORM dblink_connect('conn_payment', 'server_payment_remote');
        PERFORM dblink_connect('conn_stock', 'server_stock_remote');
        RETURN QUERY
        SELECT
            t.id as transaction_id,
            c.name as client_name,
            c.phone as client_phone,
            t.type_movenemt as type_movement,
            t.transaction_date as transaction_date,
            dt.id as detail_transaction_id,
            dt.unit_price as unit_price,
            dt.quantity as quantity,
            (dt.unit_price * dt.quantity) as total_price,
            dt.product_id as product_id,
            s.name as product_name
        FROM transactions t
        INNER JOIN client c ON t.client_id = c.id
        INNER JOIN detail_transaction dt on t.id = dt.transaction_id
        INNER JOIN (
            SELECT *
            FROM dblink('conn_stock', format('SELECT id, name FROM product WHERE id ''%s''', dt.product_id))
            AS s(product_id uuid, name VARCHAR)
        ) s ON s.product_id = dt.product_id
        WHERE t.transaction_date BETWEEN start_date AND end_date;
    END;
$$ language plpgsql;
