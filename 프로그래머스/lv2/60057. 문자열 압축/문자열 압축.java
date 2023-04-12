import java.util.*;

class Solution {
    
    static int n;
    
    public int solution(String s) {
        n = s.length();
        
        int answer = n; // 가능한 정답의 최대값은 문자열 s의 길이 -> 즉, 압축이 불가능
        
        /*
        서브스트링의 길이는 1~n 까지 가능
        각 길이마다 문자열 s를 나눠서 탐색
        => O(n ^ 2)
        */
        for(int i = 1; i <= n / 2; i++){ // 서브스트링이 문자열 s의 길이의 반보다 커지면, 어차피 문자열 압축이 불가능해짐
            String compressed = ""; // 압축된 문자열
            
            String subStr = ""; // 연속되는 서브스트링
            int cnt = 1; // 해당 서브스트링이 연속적으로 나타난 횟수
            for(int j = 0; j < n; j += i){
                if(j + i > n){ // 길이 i 만큼 더 이상 못가면, 현재 위치부터 마지막 문자열까지 추가하고 끝
                    subStr += s.substring(j, n);
                    break;
                }
                String tmpStr = s.substring(j, j + i); // j <= index < j + i 까지의 서브스트링 가져옴
                if(subStr.equals(tmpStr)){ // 이전의 서브스트링(subStr) 이랑 같으면 압축 가능하기 때문에 cnt 증가
                    cnt++;
                }else{ // 이전의 서브스트링(subStr)이랑 다르면,더 이상 압축이 안되기 때문에 subStr 업데이트 시켜주고,cnt 초기화
                    if(cnt > 1){ 
                        // 같은 문자열이 2번 이상 반복되어야 압축 가능->압축시 문자열이 연속으로 나탄 횟수(cnt)+해당 문자열 추가
                        compressed += (String.valueOf(cnt) + subStr);
                    }else{ 
                        // 같은 문자열이 한번만 등장하면 압축 X -> 해당 문자열만 추가
                        compressed += subStr;
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