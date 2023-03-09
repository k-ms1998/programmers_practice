import java.util.*;

class Solution {
    
    static String answer = "";
    
    public String solution(String p) {
        
        if(checkCorrect(p)){
            answer = p;
        }else{
            answer = findAnswer(p);
        }
        
        return answer;
    }
    
    public static String findAnswer(String org){
        if(org.length() == 0){
            return org;
        }

        String u = "";
        String v = "";
        int n = org.length();
        
        int leftCnt = 0;
        int rightCnt = 0;
        boolean isLevel = false;
        // u 가 '균형잡힌 괄호 문자열이 될때까지' 탐색; u 가 '균형잡힌 괄호 문자열이 되면 나머지 문자열은 v
        for(int i = 0; i < n; i++){
            char c = org.charAt(i);
            if(c == '('){
                leftCnt++;
            }else{
                rightCnt++;
            }
            
            if(isLevel){
                v += String.valueOf(c);
            }else{
                u += String.valueOf(c);
                if(leftCnt == rightCnt){
                    isLevel = true;
                }
            }
        }
        
        if(checkCorrect(u)){
            return u + findAnswer(v);
        }else{
            String tmp = "(";
            tmp += findAnswer(v);
            tmp += ")";
            
            int un = u.length();
            for(int i = 1; i < un - 1; i++){
                char c = u.charAt(i);
                // 괄호 방향을 뒤집어서 추가
                if(c == '('){
                    tmp += ")";
                }else{
                    tmp += "(";
                }
            }
            
            return tmp;
        }
        
    }
    
    // '올바른 괄호 문자열'인지 확인
    // 언제나 각 방향의 괄호가 균형을 맞추고 있으면 됨
    // 즉, 중간에 '(' 의 갯수가 항상 ')'의 갯수보다 크거나 같거나, 마지막에 stack에 남아 있는 값이 없을때
    public static boolean checkCorrect(String org){
        Stack<String> stack = new Stack<>();
        int n = org.length();
        
        for(int i = 0; i < n; i++){
            char c = org.charAt(i);
            if(c == '('){
                stack.push("(");
            }else{
                if(stack.isEmpty()){
                   return false;
                }else{
                    stack.pop();
                }
            }
        }
        if(!stack.isEmpty()){
            return false;
        }
        
        return true;
    }
}