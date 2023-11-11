create TABLE IF NOT EXISTS users
(
    id                bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name              character varying(50) NOT NULL,
    surname           character varying(50),
    password          character varying(250) NOT NULL,
    email             character varying(70) UNIQUE NOT NULL
);
