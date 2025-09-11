CREATE OR REPLACE FUNCTION get_full_detail_transaction(t_id uuid)
RETURNS TABLE(
 id uuid,
 unit_price numeric(10, 2),
 quantity int4,
 total numeric(10, 2),
 product varchar
) AS $$
BEGIN

    PERFORM dblink_connect_u('host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}');

    RETURN QUERY
        SELECT d.id as id,
               d.unit_price as unit_price,
               d.quantity as quantity,
               (d.unit_price * d.quantity) as total,
               p.name as product
        FROM detail_transaction d
        INNER JOIN (
            SELECT *
            FROM dblink('host=${host} port=${port} dbname=${db_stock} user=${user} password=${password}', 'SELECT id, name FROM product')
                AS p(id uuid, name varchar)) p on p.id = d.product_id
        WHERE d.transaction_id = t_id;
END;
$$ language plpgsql;