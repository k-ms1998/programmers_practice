
import java.util.*;

/**
(15 분)
간단 구현
핵심: 숫자가 문자로 연속될때 판별
ex: sixseven -> 67
*/
class Solution {

    static Map<String, Integer> nums = new HashMap<>();
    
    public int solution(String s) {
        initNums(); //  문자랑 숫자를 매핑해주기
        
        String tmp = "";
        int answer = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c >= '0' && c <= '9'){ // 현재 문자가 숫자이면 그대로 정답에 추가
                answer = 10*answer + Integer.parseInt(String.valueOf(c));
            }else{
                tmp += String.valueOf(c); // 알파벳이면 tmp에 추가
            }
            if(nums.containsKey(tmp)){ // tmp가 nums에 key값이 존재할때마다 숫자로 변환해서 정답에 추가 -> 연속되다리도 판별할수 있음; ex: sixseven에면 tmp가 "six"이면 6이 추가되고 tmp는 초기화 됨 -> 다시 seven을 독립적으로 넣을 수 있음
                answer = 10 * answer + nums.get(tmp);
                tmp = "";
            }
        }
        return answer;
    }
    
    public static void initNums(){
        nums.put("zero", 0);
        nums.put("one", 1);
        nums.put("two", 2);
        nums.put("three", 3);
        nums.put("four", 4);
        nums.put("five", 5);
        nums.put("six", 6);
        nums.put("seven", 7);
        nums.put("eight", 8);
        nums.put("nine", 9);
    }
}