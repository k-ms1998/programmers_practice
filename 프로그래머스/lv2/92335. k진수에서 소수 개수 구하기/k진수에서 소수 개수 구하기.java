import java.util.*;

/**
핵심: 제곱근을 이용해서 빠르게 소수를 찾기(isPrime() 메서드  확인)
*/
class Solution {
    
    static String converted = "";
    static int size;
    static int answer = 0;
    
    public int solution(int n, int k) {
        
        findAnswer(n, k);
        
        return answer;
    }
                   
    // 소수를 빨리 찾기 위해서 제곱근을 이용
    public static boolean isPrime(long num){
        if(num <= 1){
            return false;
        }
        for(long i = 2L; i*i <= num; i++){
            if(num % i == 0 || num % (i*i) == 0){
                return false;
            }
        }
        
        return true;
    }
    
    public static void findAnswer(int n, int k){
        Stack<Integer> stack = new Stack<>();
        while(n > 0){
            stack.push(n % k);
            n /= k;
        }
     
        long num =  0L;
        while(!stack.isEmpty()){
            int cur = stack.pop();
            if(cur == 0){
                if(isPrime(num)){
                    answer++;
                }
                num = 0L;
            }else{
                num = 10*num + cur;
            }
        }
        if(isPrime(num)){   
            answer++;
        }
        
    }
}