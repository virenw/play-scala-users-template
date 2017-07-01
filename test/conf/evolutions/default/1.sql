# --- !Ups

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstName       TEXT,
    lastName        TEXT,
    username        TEXT   NOT NULL UNIQUE,
    email           TEXT,
    passwordHash    TEXT
);

# --- !Downs