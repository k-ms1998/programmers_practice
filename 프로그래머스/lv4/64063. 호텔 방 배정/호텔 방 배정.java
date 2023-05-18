import java.util.*;

/**
Solution: Union-Find
(k가 최대 10^12 이기 때문에, 배열 사용 X -> Map 사용)
*/
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
        if(parent == num){ // parent == num 이면, 아직 해당 방에는 사람이 배정이 안된 상태
            root.put(num, parent + 1); // 다음 칸으로 업데이트
            return num;
        }
        
        long next = findRoot(parent); 
        root.put(num, next); // 지금까지 거친 경로를 업데이트
        return next;
    }
}