select f.FLAVOR FROM FIRST_HALF as f LEFT JOIN JULY as j ON f.FLAVOR = j.FLAVOR
GROUP BY FLAVOR ORDER BY sum(f.TOTAL_ORDER + j.TOTAL_ORDER) desc limit 3;
