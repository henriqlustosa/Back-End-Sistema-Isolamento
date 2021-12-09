ALTER TABLE pacientes ADD COLUMN usuario_id BIGINT NOT NULL;
ALTER TABLE pacientes ADD FOREIGN KEY (usuario_id) REFERENCES usuarios(id);