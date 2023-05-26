import java.util.*;

class Solution {
    
    static long sum1 = 0L;
    static long sum2 = 0L;
    static Deque<Integer> q1 = new ArrayDeque<>();
    static Deque<Integer> q2 = new ArrayDeque<>();
    static final int INF = 10000000;
    
    public int solution(int[] queue1, int[] queue2) {
        for(int i = 0; i < queue1.length; i++){
            sum1 += queue1[i];
            q1.offer(queue1[i]);
        }
        for(int i = 0; i < queue2.length; i++){
            sum2 += queue2[i];
            q2.offer(queue2[i]);
        }
        
        int answer = INF;
        
        int maxCnt = 2* (queue1.length + queue2.length);
        int cnt = 0;
        while(cnt <= maxCnt){
            if(sum1 == sum2){
                answer = cnt;
                break;
            }else if(sum1 > sum2){
                int num = q1.poll();
                sum1 -= num;
                sum2 += num;
                q2.offer(num);
            }else{
                int num = q2.poll();
                sum2 -= num;
                sum1 += num;
                q1.offer(num);
            }
            cnt++;
        }
        
        return answer == INF ? -1 : answer;
    }
    

}