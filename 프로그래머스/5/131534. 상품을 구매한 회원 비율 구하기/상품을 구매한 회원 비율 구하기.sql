-- 코드를 입력하세요
SELECT year(s.SALES_DATE) as YEAR, month(s.SALES_DATE) as MONTH,
    count(distinct(s.USER_ID)) as PUCHASED_USERS, 
            round(
                count(distinct(s.USER_ID))
                / ((SELECT count(*) FROM USER_INFO as i2 WHERE year(i2.JOINED) = 2021)), 1) as PUCHASED_RATIO
FROM ONLINE_SALE as s
LEFT JOIN USER_INFO as i ON i.USER_ID = s.USER_ID
WHERE year(i.JOINED) = 2021
GROUP BY YEAR, MONTH
ORDER BY YEAR asc, MONTH asc;