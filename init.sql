DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'user_db') THEN
        CREATE DATABASE user_db;
    END IF;
END
$$;

\c user_db;

DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_sequences WHERE sequencename = 'user_seq') THEN
        CREATE SEQUENCE user_seq
        START WITH 1
        INCREMENT BY 1  -- ⚠️ Set increment size to 1
        NO CYCLE;
    END IF;
END
$$;
