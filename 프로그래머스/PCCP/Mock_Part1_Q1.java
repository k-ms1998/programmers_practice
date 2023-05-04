import java.util.*;

/**
 * PCCP 모의고사 1회 1번:
 * https://school.programmers.co.kr/learn/courses/15008/lessons/121683
 */
class Mock_Part1_Q1 {

    static int n;
    static int[] cnt = new int[26]; // 알파벳의 수 = 26

    public String solution(String input_string) {
        n = input_string.length();
        String answer = "";
        String unique = String.valueOf(input_string.charAt(0));
        String prev = String.valueOf(input_string.charAt(0));
        for(int i = 1; i < n; i++){
            String cur = String.valueOf(input_string.charAt(i));
            if(!prev.equals(cur)){
                unique += cur;
                prev = cur;
            }
        }

        for(int i = 0; i < unique.length(); i++){
            char c = unique.charAt(i);
            cnt[c - 'a']++;
        }

        for(int i = 0; i < 26; i++){
            char c = (char)('a' + i);
            if(cnt[i] > 1){
                answer += String.valueOf(c);
            }
        }


        return answer.length() == 0 ? "N" : answer;
    }
}