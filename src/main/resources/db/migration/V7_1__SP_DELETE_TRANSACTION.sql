CREATE OR REPLACE PROCEDURE sp_delete_transaction(transactionId UUID)
LANGUAGE plpgsql AS
$$
DECLARE
    v_debt_id UUID;
    v_detail RECORD;

BEGIN
    -------- CONNECT --------

    PERFORM dblink_connect_u('stock_conn', 'host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}');
    PERFORM dblink_connect_u('payment_conn', 'host=${host} port=${port} dbname=${db_payment} user=${user} password=${password}');

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
                        'UPDATE product SET stock = stock + %s WHERE id = ''%s''',
                        v_detail.quantity, v_detail.product_id
                )
        );
    END LOOP;

    -------- PAYMENT --------
    SELECT debt_id INTO v_debt_id
    FROM dblink(
                 'payment_conn',
                 format('SELECT debt_id FROM payment WHERE transaction_id = ''%s''', transactionId)
    ) AS p(debt_id UUID);

    -- DELETE PAYMENT
    PERFORM dblink_exec(
            'payment_conn',
            format(
                    'DELETE FROM payment WHERE transaction_id = ''%s''',
                    transactionId
            )
    );

    -- DELETE DEBT
    IF v_debt_id IS NOT NULL THEN
        PERFORM dblink_exec(
                'payment_conn',
                format(
                        'DELETE FROM debt WHERE id = ''%s''',
                        v_debt_id
                )
        );
    END IF;

    -------- TRANSACTION --------
    DELETE FROM detail_transaction WHERE transaction_id = transactionId;
    DELETE FROM transactions WHERE id = transactionId;

    PERFORM dblink_disconnect('stock_conn');
    PERFORM dblink_disconnect('payment_conn');

END;
$$