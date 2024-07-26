DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'user_db') THEN
        CREATE DATABASE user_db;
    END IF;
END
$$;
