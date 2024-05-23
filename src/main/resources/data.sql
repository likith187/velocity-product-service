INSERT INTO inventory.supplier (name, contact_information, version) VALUES ('Dove', 'dove.com', 1);
INSERT INTO inventory.supplier (name, contact_information, version) VALUES ('Himalaya', 'himalaya.com', 1);
INSERT INTO inventory.supplier (name, contact_information, version) VALUES ('Samsung', 'samsung.com', 1);

INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Soap', 1, 19.99, 100, 1);
INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Hair conditioner', 1, 29.99, 200, 1);
INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Shampoo', 1, 39.99, 150, 1);
INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Face wash', 2, 49.99, 80, 1);
INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Samsung galaxy fold', 3, 59.99, 60, 1);
INSERT INTO inventory.product (name, supplier_id, price, stock_quantity, version) 
VALUES ('Samsung TV', 3, 69.99, 120, 1);

