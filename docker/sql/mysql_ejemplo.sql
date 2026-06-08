-- Ejemplo de importación para MySQL
-- Uso: ./05_import_mysql_sql.sh sql/mysql_ejemplo.sql

-- ─── Limpieza ────────────────────────────────────────────────────────────────
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS articulos;

-- ─── Tablas ───────────────────────────────────────────────────────────────────
CREATE TABLE categorias (
    id      INT          NOT NULL AUTO_INCREMENT,
    nombre  VARCHAR(60)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE articulos (
    id           INT            NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100)   NOT NULL,
    precio       DECIMAL(10,2)  NOT NULL,
    stock        INT            NOT NULL DEFAULT 0,
    id_categoria INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_art_cat FOREIGN KEY (id_categoria) REFERENCES categorias(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE clientes (
    id        INT          NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(80)  NOT NULL,
    email     VARCHAR(120) NOT NULL UNIQUE,
    ciudad    VARCHAR(60),
    activo    TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE pedidos (
    id          INT            NOT NULL AUTO_INCREMENT,
    id_cliente  INT            NOT NULL,
    id_articulo INT            NOT NULL,
    cantidad    INT            NOT NULL DEFAULT 1,
    total       DECIMAL(10,2)  NOT NULL,
    fecha       DATETIME       NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
    CONSTRAINT fk_ped_cli FOREIGN KEY (id_cliente)  REFERENCES clientes(id),
    CONSTRAINT fk_ped_art FOREIGN KEY (id_articulo) REFERENCES articulos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─── Datos ────────────────────────────────────────────────────────────────────
INSERT INTO categorias (nombre) VALUES
    ('Informática'),
    ('Monitores'),
    ('Periféricos'),
    ('Audio');

INSERT INTO articulos (nombre, precio, stock, id_categoria) VALUES
    ('Portátil Core i7',       999.99,  15, 1),
    ('SSD 1TB NVMe',           89.99,   40, 1),
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
