-- 코드를 입력하세요
SELECT ORDER_ID, PRODUCT_ID, DATE_FORMAT(OUT_DATE, "%Y-%m-%d") AS OUT_DATE,
IF(out_date <= "2022-05-01", "출고완료", IF(out_date > "2022-05-01", "출고대기", "출고미정")) as "출고여부"
FROM FOOD_ORDER
ORDER BY ORDER_ID ASC;