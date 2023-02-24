-- 코드를 입력하세요
SELECT CAR_TYPE, count(*) as "CARS" from CAR_RENTAL_COMPANY_CAR where options REGEXP ('통풍시트|열선시트|가죽시트') group by CAR_TYPE order by CAR_TYPE;