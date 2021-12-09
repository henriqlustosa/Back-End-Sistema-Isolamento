
create table perfis(
      
  id SERIAL PRIMARY KEY,
  nome varchar(100) NOT NULL
);

create table perfis_usuarios(
      
  usuario_id bigint NOT NULL,
  perfil_id bigint NOT NULL,
  PRIMARY KEY(usuario_id,perfil_id),
  FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
  FOREIGN KEY(perfil_id) REFERENCES perfis(id)
);


INSERT INTO perfis values(1, 'ROLE_ADMIN');
INSERT INTO perfis values(2, 'ROLE_COMUM');