DO $$
    BEGIN
        -- Tipo
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.columns
            WHERE table_schema = 'public'
            AND table_name = 'client'
            AND column_name = 'phone'
            AND data_type = 'character varying'
            AND character_maximum_length = 15
        ) THEN
            ALTER TABLE public.client
            ALTER COLUMN phone TYPE VARCHAR(15);
        END IF;

        -- Set null
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.columns
            WHERE table_schema = 'public'
              AND table_name = 'client'
              AND column_name = 'phone'
              AND is_nullable = 'YES'
              AND character_maximum_length = 15
        ) THEN
            ALTER TABLE public.client
            ALTER COLUMN phone TYPE VARCHAR(15);
        END IF;

        -- Unique
        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint c
            JOIN pg_attribute a
            ON a.attrelid = c.conrelid
                   AND a.attnum = ANY(c.conkey)
            WHERE c.conrelid = 'public.client'::regclass
              AND c.contype = 'u'
              AND a.attname = 'phone'
        ) THEN
            ALTER TABLE public.client
            ADD CONSTRAINT phone_unique UNIQUE (phone);
        END IF;
END $$;