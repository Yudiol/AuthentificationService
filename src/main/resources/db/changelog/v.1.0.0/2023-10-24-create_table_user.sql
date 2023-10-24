CREATE TABLE IF NOT EXISTS users
(
    id           bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name              character varying(250) NOT NULL,
    surname           character varying(250),
    password          character varying(100) NOT NULL,
    email             character varying(250) NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);
