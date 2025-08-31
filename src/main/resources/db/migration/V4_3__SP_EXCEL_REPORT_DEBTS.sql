CREATE OR REPLACE FUNCTION SP_EXCEL_REPORT_DEBTS(start_date DATE, end_date DATE)
RETURNS TABLE (
    id UUID,
    client_name VARCHAR,
    total_amount NUMERIC,
    remaining_amount NUMERIC,
    status VARCHAR,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
) AS
$$
    BEGIN
        -- VALIDATE IF PAYMENT CONNECTION EXISTS
        IF NOT
            ('conn_payment' = ANY(dblink_get_connections()))
        THEN
            PERFORM dblink_connect('conn_payment', 'server_payment_remote');
        END IF;

        RETURN QUERY
        SELECT
            debt.id as id,
            c.name as client_name,
            debt.total_amount as total_amount,
            debt.remaining_amount as remaining_amount,
            debt.status as status,
            debt.created_at as created_at,
            debt.updated_at as updated_at
        FROM client c
        INNER JOIN (
            SELECT *
            FROM dblink('conn_payment', 'SELECT id, total_amount, remaining_amount, status, created_at, updated_at, client_id FROM debt WHERE created_at BETWEEN TO_TIMESTAMP('''||start_date||''', ''YYYY-MM-DD'') AND TO_TIMESTAMP('''||end_date||''', ''YYYY-MM-DD'')')
            AS debt(
                id UUID,
                total_amount NUMERIC,
                remaining_amount NUMERIC,
                status VARCHAR,
                created_at TIMESTAMP,
                updated_at TIMESTAMP,
                client_id UUID
            )
        ) debt ON debt.client_id = c.id
        ORDER BY debt.created_at ASC;

    END;
$$ language plpgsql;