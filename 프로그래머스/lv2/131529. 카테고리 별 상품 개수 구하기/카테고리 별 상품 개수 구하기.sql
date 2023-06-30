SELECT SUBSTR(A.product_code, 1, 2) AS category, COUNT(*) AS products
FROM product A
GROUP BY category
ORDER BY A.product_code