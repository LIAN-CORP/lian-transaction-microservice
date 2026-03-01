CREATE OR REPLACE PROCEDURE public.sp_fill_temp_debt_report(IN start_date date, IN end_date date)
    LANGUAGE plpgsql
AS $procedure$
DECLARE
    conn_exists BOOLEAN;
    query_text TEXT;
BEGIN

    -- 1. Verify if connection exists & connect if not connection do not exists
    SELECT 'payment_conn' = ANY (dblink_get_connections()) INTO conn_exists;
    IF conn_exists THEN
        PERFORM dblink_disconnect('payment_conn');
    END IF;

    PERFORM dblink_connect_u('payment_conn','host=${host} port=${port} dbname=${db_payment} user=${user} password=${password}');

    -- 2. DELETE DATA FROM TEMP TABLE
    TRUNCATE temp_debt_report;

    -- 3. Build dblink query using format() to avoid problems with quotation marks
    query_text := format(
            'SELECT id, total_amount, remaining_amount, status, created_at, updated_at, client_id
             FROM debt
             WHERE created_at::date BETWEEN %L AND %L',
            start_date, end_date
                  );

    -- 4. INSERT DATA
    BEGIN
        INSERT INTO temp_debt_report (id, client_name, total_amount, remaining_amount, status, created_at, updated_at)
        SELECT debt.id               as id,
               c.name                as client_name,
               debt.total_amount     as total_amount,
               debt.remaining_amount as remaining_amount,
               debt.status           as status,
               debt.created_at       as created_at,
               debt.updated_at       as updated_at
        FROM client c
                 INNER JOIN dblink('payment_conn', query_text)
            AS debt (
                     id UUID,
                     total_amount NUMERIC,
                     remaining_amount NUMERIC,
                     status VARCHAR,
                     created_at TIMESTAMP,
                     updated_at TIMESTAMP,
                     client_id UUID
                ) ON debt.client_id = c.id
        ORDER BY debt.created_at ASC;

    EXCEPTION WHEN OTHERS THEN
        -- If something fails, disconnect dblink
        IF 'payment_conn' = ANY (dblink_get_connections()) THEN
            PERFORM dblink_disconnect('payment_conn');
        END IF;
        RAISE EXCEPTION 'Error al ejecutar el reporte: %', SQLERRM;
    END;

    -- 5. Close connection success status
    PERFORM dblink_disconnect('payment_conn');

END;
$procedure$