-- Database: "CRUDServer"

-- DROP DATABASE "CRUDServer";

CREATE DATABASE "CRUDServer"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE "CRUDServer"
  IS 'Database do trabalho Client x Server';
