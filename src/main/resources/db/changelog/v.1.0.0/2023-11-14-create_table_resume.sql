create TABLE IF NOT EXISTS resumes
(
    id       bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name     character varying(100) NOT NULL,
    user_id  bigint                 REFERENCES users(id) ON DELETE CASCADE,
    data     bytea                  NOT NULL
);
