import java.util.*;

class Solution {
    
    static int n;
    static Map<Long, Long> root = new HashMap<>();
        
    public long[] solution(long k, long[] room_number) {
        n = room_number.length;
        long[] answer = new long[n];
        for(int i = 0; i < n; i++){
            long num = room_number[i];
            long parent = findRoot(num);
            answer[i] = parent;
        }
        
        return answer;
    }
    
    public static long findRoot(long num){
        long parent = root.getOrDefault(num, num);
        if(parent == num){
            root.put(num, parent + 1);
            return num;
        }
        
        long next = findRoot(parent);
        root.put(num, next);
        return next;
    }
}