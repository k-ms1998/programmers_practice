select board_id, writer_id, title, price, if(status="DONE", "거래완료", if(status="SALE", "판매중", "예약중")) as status
from used_goods_board
where year(created_date)=2022 and month(created_date)=10 and day(created_date)=5
order by board_id desc;