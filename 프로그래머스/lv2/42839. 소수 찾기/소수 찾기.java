import java.util.*;

class Solution {
    
    static int n;
    static int[] nums;
    static boolean[] used;
    static boolean[] found;
    static int answer = 0;
    
    public int solution(String numbers) {
        n = numbers.length();
        nums = new int[n];
        used = new boolean[n];
        int exp = 1;
        int maxNum = 0;
        for(int i = 0; i < n; i++){
            nums[i] = numbers.charAt(i) - '0';
            maxNum = Math.max(maxNum, nums[i]);
            exp *= 10;
        }
        found = new boolean[exp];
        
        findAnswer(0, 0);

        return answer;
    }
    
    public static void findAnswer(int depth, int cur){
        if(cur >= 2 && !found[cur]){
            found[cur] = true;
            if(checkCur(cur)){
                answer++;
            }
        }
        if(depth == n){
            return;
        }
        
        for(int i = 0; i < n; i++){
            if(!used[i]){
                used[i] = true;
                findAnswer(depth + 1, 10*cur + nums[i]);
                used[i] = false;
            }
        }
    }
    
    public static boolean checkCur(int target){
        for(int i = 2; i < target; i++){
            if(target % i == 0){
                return false;
            }
        }
        
        return true;
    }
}