ALTER TABLE modules ADD COLUMN deploy_date timestamp with time zone NOT NULL DEFAULT now();
