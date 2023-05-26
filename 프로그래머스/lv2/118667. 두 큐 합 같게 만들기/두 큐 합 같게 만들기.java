import java.util.*;

class Solution {
    
    static long initSum1 = 0L;
    static long initSum2 = 0L;
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
        initSum1 = sum1;
        initSum2 = sum2;
        
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
            
            if(queue1.length < queue2.length){
                if(checkQueue1(queue1)){
                    break;
                }
            }else{
                if(checkQueue1(queue1)){
                    break;
                }  
            }
        }
        
        return answer == INF ? -1 : answer;
    }
    
    public static boolean checkQueue1(int[] queue1){
        if(initSum1 == sum1){
            if(queue1.length == q1.size()){
                if(queue1[0] == q1.peek()){
                    Deque<Integer> tmp = new ArrayDeque<>();
                    boolean flag = true;
                    for(int i = 0; i < q1.size(); i++){
                        int num = q1.poll();
                        tmp.offer(num);
                        if(num != queue1[i]){
                            flag = false;
                        }
                    }

                    q1 = tmp;
                    
                    return flag;
                }
            }
        }
        
        return false;
    }
    
    public static boolean checkQueue2(int[] queue2){
        if(initSum2 == sum2){
            if(queue2.length == q2.size()){
                if(queue2[0] == q2.peek()){
                    Deque<Integer> tmp = new ArrayDeque<>();
                    boolean flag = true;
                    for(int i = 0; i < q2.size(); i++){
                        int num = q2.poll();
                        tmp.offer(num);
                        if(num != queue2[i]){
                            flag = false;
                        }
                    }

                    q2 = tmp;
                    
                    return flag;
                }
            }
        }
        
        return false;
    }
}