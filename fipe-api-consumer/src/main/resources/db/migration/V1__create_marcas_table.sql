CREATE TABLE marcas (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_fipe  VARCHAR(20)  NOT NULL,
    nome         VARCHAR(120) NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uk_marcas_codigo_fipe UNIQUE (codigo_fipe)
);

COMMENT ON TABLE  marcas             IS 'Marcas de veículos importadas da FIPE.';
COMMENT ON COLUMN marcas.codigo_fipe IS 'Código da marca na FIPE.';
