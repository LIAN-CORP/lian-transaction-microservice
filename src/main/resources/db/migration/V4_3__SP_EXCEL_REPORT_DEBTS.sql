-- CREATE TEMP TABLE
CREATE TABLE IF NOT EXISTS temp_debt_report
(
    id               UUID,
    client_name      VARCHAR,
    total_amount     NUMERIC,
    remaining_amount NUMERIC,
    status           VARCHAR,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP
);

CREATE OR REPLACE PROCEDURE sp_fill_temp_debt_report(start_date DATE, end_date DATE)
LANGUAGE plpgsql AS
$$
BEGIN

    PERFORM dblink_connect_u('host=${host} port=${port} dbname=${db_payment} user=${user} password=${password}');

    -- VALIDATE IF PAYMENT CONNECTION EXISTS
    --IF NOT
    --    ('conn_payment' = ANY (dblink_get_connections()))
    --THEN
    --    PERFORM dblink_connect('conn_payment', 'server_payment_remote');
    --END IF;

    -- DELETE DATA FROM TEMP TABLE
    TRUNCATE temp_debt_report;

    -- INSERT DATA
    INSERT INTO temp_debt_report (id, client_name, total_amount, remaining_amount, status, created_at, updated_at)
    SELECT debt.id               as id,
           c.name                as client_name,
           debt.total_amount     as total_amount,
           debt.remaining_amount as remaining_amount,
           debt.status           as status,
           debt.created_at       as created_at,
           debt.updated_at       as updated_at
    FROM client c
             INNER JOIN (SELECT *
                         FROM dblink('host=${host} port=${port} dbname=${db_payment} user=${user} password=${password}',
                                     'SELECT id, total_amount, remaining_amount, status, created_at, updated_at, client_id FROM debt WHERE created_at BETWEEN TO_TIMESTAMP(''' ||
                                     start_date || ''', ''YYYY-MM-DD'') AND TO_TIMESTAMP(''' || end_date ||
                                     ''', ''YYYY-MM-DD'')')
                                  AS debt(
                                          id UUID,
                                          total_amount NUMERIC,
                                          remaining_amount NUMERIC,
                                          status VARCHAR,
                                          created_at TIMESTAMP,
                                          updated_at TIMESTAMP,
                                          client_id UUID
                                 )) debt ON debt.client_id = c.id
    ORDER BY debt.created_at ASC;

END;
$$;