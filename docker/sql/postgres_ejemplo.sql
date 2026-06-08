-- Ejemplo de importación para PostgreSQL
-- Uso: ./08_import_postgres_sql.sh sql/postgres_ejemplo.sql

-- ─── Limpieza ────────────────────────────────────────────────────────────────
DROP TABLE IF EXISTS pedidos    CASCADE;
DROP TABLE IF EXISTS articulos  CASCADE;
DROP TABLE IF EXISTS clientes   CASCADE;
DROP TABLE IF EXISTS categorias CASCADE;

-- ─── Tablas ───────────────────────────────────────────────────────────────────
CREATE TABLE categorias (
    id      SERIAL       PRIMARY KEY,
    nombre  VARCHAR(60)  NOT NULL
);

CREATE TABLE articulos (
    id           SERIAL          PRIMARY KEY,
    nombre       VARCHAR(100)    NOT NULL,
    precio       NUMERIC(10,2)   NOT NULL,
    stock        INTEGER         NOT NULL DEFAULT 0,
    id_categoria INTEGER         REFERENCES categorias(id)
);

CREATE TABLE clientes (
    id        SERIAL       PRIMARY KEY,
    nombre    VARCHAR(80)  NOT NULL,
    email     VARCHAR(120) NOT NULL UNIQUE,
    ciudad    VARCHAR(60),
    activo    BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE pedidos (
    id          SERIAL          PRIMARY KEY,
    id_cliente  INTEGER         NOT NULL REFERENCES clientes(id),
    id_articulo INTEGER         NOT NULL REFERENCES articulos(id),
    cantidad    INTEGER         NOT NULL DEFAULT 1,
    total       NUMERIC(10,2)   NOT NULL,
    fecha       TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- ─── Datos ────────────────────────────────────────────────────────────────────
INSERT INTO categorias (nombre) VALUES
    ('Informática'),
    ('Monitores'),
    ('Periféricos'),
    ('Audio');

INSERT INTO articulos (nombre, precio, stock, id_categoria) VALUES
    ('Portátil Core i7',       999.99,  15, 1),
    ('SSD 1TB NVMe',            89.99,  40, 1),
    ('Monitor 27" 4K',         349.99,   8, 2),
    ('Monitor Curvo 32"',      279.99,  12, 2),
    ('Teclado mecánico',        79.99,  25, 3),
    ('Ratón inalámbrico',       29.99,  50, 3),
    ('Auriculares Bluetooth',   59.99,  20, 4),
    ('Altavoces 2.1',           49.99,  18, 4);

INSERT INTO clientes (nombre, email, ciudad) VALUES
    ('Ana García',    'ana.garcia@correo.es',    'Madrid'),
    ('Luis Martínez', 'luis.martinez@correo.es', 'Barcelona'),
    ('Marta López',   'marta.lopez@correo.es',   'Valencia'),
    ('Carlos Ruiz',   'carlos.ruiz@correo.es',   'Sevilla');

INSERT INTO pedidos (id_cliente, id_articulo, cantidad, total) VALUES
    (1, 1, 1,  999.99),
    (1, 5, 1,   79.99),
    (2, 3, 2,  699.98),
    (3, 6, 2,   59.98),
    (3, 7, 1,   59.99),
    (4, 2, 1,   89.99);

-- ─── Consultas de ejemplo ─────────────────────────────────────────────────────
SELECT a.nombre, a.precio, c.nombre AS categoria
FROM articulos a
JOIN categorias c ON a.id_categoria = c.id
ORDER BY c.nombre, a.precio;

SELECT cl.nombre AS cliente, ar.nombre AS articulo, p.cantidad, p.total
FROM pedidos p
JOIN clientes  cl ON p.id_cliente  = cl.id
JOIN articulos ar ON p.id_articulo = ar.id
ORDER BY cl.nombre;
