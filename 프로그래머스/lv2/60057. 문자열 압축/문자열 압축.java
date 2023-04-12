import java.util.*;

class Solution {
    
    static int n;
    
    public int solution(String s) {
        n = s.length();
        
        int answer = n;
        for(int i = 1; i < n; i++){
            String compressed = "";
            String subStr = "";
            int cnt = 1;
            for(int j = 0; j < n; j += i){
                if(j + i > n){
                    subStr += s.substring(j, n);
                    break;
                }
                String tmpStr = s.substring(j, j + i);
                // System.out.println(tmpStr);
                if(subStr.equals(tmpStr)){
                    cnt++;
                }else{
                    if(!subStr.equals("")){
                        if(cnt > 1){
                            compressed += (String.valueOf(cnt) + subStr);
                        }else{
                            compressed += subStr;
                        }
                    }
                    subStr = tmpStr;
                    cnt = 1;
                }
            }
            if(cnt > 1){
                compressed += (String.valueOf(cnt) + subStr);
            }else{
                compressed += subStr;
            }
            answer = Math.min(answer, compressed.length());
        }
        
        return answer;
    }
}