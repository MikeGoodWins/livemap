CREATE TABLE raws
(
    id          bigserial primary key,
    phone       bigint,
    name        varchar,
    birthday    timestamp,
    data_map    jsonb,
    description varchar,
    comment     varchar,
    created     timestamp with time zone
);

CREATE INDEX raws_phone_idx ON raws(phone);
