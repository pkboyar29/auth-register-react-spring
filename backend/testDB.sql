CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL CHECK (LENGTH(username) >= 6) UNIQUE,
    first_name VARCHAR(16) NOT NULL CHECK (LENGTH(first_name) >= 2),
    last_name VARCHAR(16) NOT NULL CHECK (LENGTH(last_name) >= 2),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    age_limit VARCHAR(7) NOT NULL CHECK (age_limit IN ('under18', 'over18')),
    gender VARCHAR(6) NOT NULL CHECK (gender IN ('male', 'female')),
    accept_rules BOOLEAN NOT NULL,
    theme VARCHAR(5) NOT NULL DEFAULT 'light' CHECK (theme IN ('light', 'dark')),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO users (username, first_name, last_name, email, password, age_limit, gender, accept_rules, theme)
VALUES ('lolkazxc', 'Эдуард', 'Мусатов', 'edik90922909@gmail.com', 'ABCabc123!', 'over18', 'male', TRUE, 'light')
SELECT * FROM users