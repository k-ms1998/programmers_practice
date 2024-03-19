SELECT food_type as FOOD_TYPE, rest_id as REST_ID, rest_name as REST_NAME, max(favorites) as FAVORITES
from rest_info r1
where r1.FAVORITES = (select max(favorites) from rest_info r2 where r1.food_type=r2.food_type group by food_type)
group by food_type
ORDER BY FOOD_TYPE desc;