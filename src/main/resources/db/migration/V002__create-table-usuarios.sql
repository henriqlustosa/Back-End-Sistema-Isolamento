CREATE TABLE usuarios (
 id SERIAL PRIMARY KEY,
  nome varchar(255) NOT NULL,
  login varchar(255) NOT NULL,
  senha varchar(255) NOT NULL
);