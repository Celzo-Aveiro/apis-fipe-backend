CREATE TABLE veiculos (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    marca_id     BIGINT       NOT NULL,
    codigo_fipe  VARCHAR(20)  NOT NULL,
    modelo       VARCHAR(255) NOT NULL,
    observacoes  TEXT,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT fk_veiculos_marca        FOREIGN KEY (marca_id) REFERENCES marcas (id),
    CONSTRAINT uk_veiculos_marca_codigo UNIQUE (marca_id, codigo_fipe)
);

CREATE INDEX idx_veiculos_marca_id ON veiculos (marca_id);

COMMENT ON TABLE  veiculos             IS 'Modelos de veículos da FIPE, associados a uma marca.';
COMMENT ON COLUMN veiculos.codigo_fipe IS 'Código do modelo na FIPE.';
COMMENT ON COLUMN veiculos.observacoes IS 'Notas livres editáveis pelo usuário.';
