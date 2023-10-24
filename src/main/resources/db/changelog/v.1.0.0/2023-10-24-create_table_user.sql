create TABLE IF NOT EXISTS users
(
    id                bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name              character varying(250) NOT NULL,
    surname           character varying(250),
    password          character varying(100) NOT NULL,
    email             character varying(250) UNIQUE NOT NULL
);
