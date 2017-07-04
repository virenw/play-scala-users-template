# --- !Ups

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name            TEXT,
    username        TEXT   NOT NULL UNIQUE,
    email           TEXT,
    passwordHash    TEXT
);

# --- !Downs