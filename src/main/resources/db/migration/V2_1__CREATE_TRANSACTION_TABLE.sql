DO
$do$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_type WHERE typname = 'type_movement') THEN
            CREATE TYPE type_movement AS ENUM ('COMPRA', 'VENTA', 'CREDITO');
        END IF;
END
$do$;

CREATE TABLE IF NOT EXISTS public.Transaction(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    type_movement type_movement NOT NULL,
    transaction_date DATE NOT NULL,
    user_id UUID NOT NULL,
    client_id UUID NOT NULL,
    CONSTRAINT fk_client_transaction FOREIGN KEY (client_id) REFERENCES public.Client(id)
);