-- Cambia transaction_date para almacenar fecha y hora y establecer un valor por defecto
BEGIN;

ALTER TABLE transactions
    ALTER COLUMN transaction_date TYPE TIMESTAMP WITHOUT TIME ZONE
    USING transaction_date::timestamp;

ALTER TABLE transactions
    ALTER COLUMN transaction_date SET DEFAULT now();

COMMIT;
