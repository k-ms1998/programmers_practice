
import java.util.*;

class Solution {

    static Map<String, Integer> nums = new HashMap<>();
    
    public int solution(String s) {
        initNums();
        
        String tmp = "";
        int answer = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c >= '0' && c <= '9'){
                answer = 10*answer + Integer.parseInt(String.valueOf(c));
            }else{
                tmp += String.valueOf(c);
            }
            if(nums.containsKey(tmp)){
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