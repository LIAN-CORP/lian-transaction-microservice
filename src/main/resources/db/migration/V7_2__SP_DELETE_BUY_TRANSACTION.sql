CREATE OR REPLACE PROCEDURE sp_delete_buy_transaction(transactionId UUID)
    LANGUAGE plpgsql AS
$$
DECLARE
    v_detail RECORD;

BEGIN
    -------- CONNECT --------
    PERFORM dblink_connect_u('stock_conn', 'host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}');

    -------- STOCK --------
    FOR v_detail IN
        SELECT id, product_id, quantity
        FROM detail_transaction
        WHERE transaction_id = transactionId
        LOOP
            -- RECOVER STOCK
            PERFORM dblink_exec(
                    'stock_conn',
                    format(
                            'UPDATE product SET stock = stock - %s WHERE id = ''%s''',
                            v_detail.quantity, v_detail.product_id
                    )
                    );
        END LOOP;

    DELETE FROM detail_transaction WHERE transaction_id = transactionId;
    DELETE FROM transactions WHERE id = transactionId;

    PERFORM dblink_disconnect('stock_conn');
END;
$$