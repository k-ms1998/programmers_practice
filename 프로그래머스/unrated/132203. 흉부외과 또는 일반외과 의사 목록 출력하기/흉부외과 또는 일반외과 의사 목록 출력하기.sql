-- 코드를 입력하세요
SELECT DR_NAME, DR_ID, MCDP_CD, date_format(HIRE_YMD, "%Y-%m-%d") as "HIRE_YMD" from DOCTOR where MCDP_CD = "CS" OR  MCDP_CD = "GS" order by HIRE_YMD desc, DR_NAME;