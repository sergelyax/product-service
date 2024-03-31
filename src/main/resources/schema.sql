-- Создание таблицы products
CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          productName VARCHAR(255) NOT NULL,
                          productPrice DECIMAL(10, 2) NOT NULL,
                          productType VARCHAR(255) NOT NULL
);

-- Вставка примеров данных в таблицу products
INSERT INTO products (productName, productPrice, productType) VALUES
                                                                  ('Craft Beer', 5.99, 'Alcoholic Beverage'),
                                                                  ('Artisan Bread', 3.50, 'Bakery'),
                                                                  ('Organic Tomatoes', 2.99, 'Vegetable'),
                                                                  ('Aged Cheese', 7.49, 'Dairy');
