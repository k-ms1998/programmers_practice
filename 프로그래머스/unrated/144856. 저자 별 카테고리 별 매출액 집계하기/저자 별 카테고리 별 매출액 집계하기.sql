SELECT b.author_id AS "AUTHOR_ID", a.author_name as "AUTHOR_NAME", b.category as "CATEGORY", SUM(s.sales * b.price) as "TOTAL_SALES" FROM BOOK b
LEFT JOIN BOOK_SALES s ON b.book_id = s.book_id 
LEFT JOIN AUTHOR a ON b.author_id = a.author_id 
WHERE DATE_FORMAT(s.sales_date, "%Y-%m") = "2022-01"
GROUP BY b.author_id, b.category
ORDER BY b.author_id ASC, b.category DESC;