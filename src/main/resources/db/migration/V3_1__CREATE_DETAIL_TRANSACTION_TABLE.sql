CREATE TABLE IF NOT EXISTS public.detail_transaction(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    unit_price INT4 NOT NULL,
    quantity INT4 NOT NULL,
    transaction_id UUID NOT NULL,
    product_id UUID NOT NULL,
    CONSTRAINT fk_detailtransaction_transaction FOREIGN KEY (transaction_id) REFERENCES public.Transactions(id)
);