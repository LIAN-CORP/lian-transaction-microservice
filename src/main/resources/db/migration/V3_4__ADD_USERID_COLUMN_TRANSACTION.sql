ALTER TABLE public.transactions ADD COLUMN IF NOT EXISTS user_id UUID;
UPDATE public.transactions SET user_id = '18d8af95-7ee5-41d4-bc76-b37ff4c11579' WHERE user_id IS NULL;
ALTER TABLE public.transactions ALTER COLUMN user_id SET NOT NULL;