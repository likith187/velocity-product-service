DROP TABLE IF EXISTS inventory.product, inventory.supplier, inventory.product_images;

CREATE TABLE IF NOT EXISTS inventory.supplier
(
   id                   BIGINT         NOT NULL AUTO_INCREMENT,
   contact_information  VARCHAR(255),
   name                 VARCHAR(255),
   version              BIGINT         NOT NULL,
   PRIMARY KEY (id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS inventory.product
(
   id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name            VARCHAR(255),
   price           DECIMAL(38,2)   NOT NULL,
   stock_quantity  BIGINT             NOT NULL,
   version         BIGINT          NOT NULL,
   supplier_id     BIGINT          NOT NULL,
   CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES supplier(id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS inventory.product_images
(
   product_id  BIGINT         NOT NULL,
   images      VARCHAR(255),
   CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
)
ENGINE=InnoDB;

CREATE INDEX idx_product_supplier_price ON inventory.product(supplier_id, price);

CREATE INDEX idx_product_price ON inventory.product(price);
