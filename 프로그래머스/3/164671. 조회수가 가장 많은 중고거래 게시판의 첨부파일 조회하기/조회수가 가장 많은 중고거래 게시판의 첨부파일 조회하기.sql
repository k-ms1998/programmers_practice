-- 코드를 입력하세요
SELECT concat("/home/grep/src/", f.BOARD_ID, "/", f.FILE_ID, f.FILE_NAME, f.FILE_EXT) as FILE_PATH
from USED_GOODS_FILE f 
left join USED_GOODS_BOARD b on f.BOARD_ID = b.BOARD_ID
WHERE b.views = (select MAX(views) from USED_GOODS_BOARD)
ORDER BY f.FILE_ID desc;