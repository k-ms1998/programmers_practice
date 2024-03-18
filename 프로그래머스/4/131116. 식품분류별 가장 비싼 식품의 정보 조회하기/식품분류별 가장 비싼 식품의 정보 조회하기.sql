SELECT category as "CATEGORY", price as "MAX_PRICE", product_name as "PRODUCT_NAME" FROM food_product
WHERE (category="식용유" and price = (select MAX(price) from food_product WHERE category="식용유"))
or (category="과자" and price = (select MAX(price) from food_product WHERE category="과자"))
or (category="국" and price = (select MAX(price) from food_product WHERE category="국"))
or (category="김치" and price = (select MAX(price) from food_product WHERE category="김치"))
ORDER BY price desc;