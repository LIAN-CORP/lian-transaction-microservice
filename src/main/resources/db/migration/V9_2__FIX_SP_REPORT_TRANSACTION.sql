CREATE OR REPLACE PROCEDURE public.sp_fill_temp_transaction_report(IN start_date date, IN end_date date)
    LANGUAGE plpgsql
AS $procedure$
DECLARE
    v_conn_str TEXT := 'host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}';
    v_conn_name TEXT := 'stock_conn';
    v_end_limit TIMESTAMP := (end_date + INTERVAL '1 day');
    v_exists BOOLEAN;
BEGIN

    SELECT v_conn_name = ANY (dblink_get_connections()) INTO v_exists;
    IF v_exists THEN
        PERFORM dblink_disconnect(v_conn_name);
    END IF;

    PERFORM dblink_connect_u(v_conn_name, v_conn_str);

    -- DELETE ALL DATA FROM TEMP TABLE
    TRUNCATE temp_transaction_report;

    -- INSERT DATA
    INSERT INTO temp_transaction_report(
        transaction_id, client_name, client_phone, type_movement, transaction_date,
        detail_transaction_id, unit_price, quantity, total_price, product_id,product_name
    )
    SELECT t.id,
           c.name,
           c.phone,
           t.type_movement,
           t.transaction_date,
           dt.id,
           dt.unit_price,
           dt.quantity,
           (dt.unit_price * dt.quantity),
           dt.product_id,
           s.name
    FROM transactions t
             INNER JOIN client c ON t.client_id = c.id
             INNER JOIN detail_transaction dt on t.id = dt.transaction_id
             INNER JOIN dblink(v_conn_name, 'SELECT id, name FROM product')
        AS s(product_id uuid, name VARCHAR) ON s.product_id = dt.product_id
    WHERE t.transaction_date >= start_date::timestamp
      AND t.transaction_date < v_end_limit
    ORDER BY t.transaction_date ASC;

    PERFORM dblink_disconnect(v_conn_name);

EXCEPTION
    WHEN OTHERS THEN
        IF (v_conn_name = ANY (dblink_get_connections())) THEN
            PERFORM dblink_disconnect(v_conn_name);
        END IF;
        RAISE EXCEPTION 'Error en sp_fill_temp_transaction_report: %', SQLERRM;
END;
$procedure$
