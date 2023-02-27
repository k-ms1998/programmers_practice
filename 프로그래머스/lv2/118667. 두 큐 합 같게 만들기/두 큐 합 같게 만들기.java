import java.util.*;

class Solution {
    
    static long baseSumA = 0L;
    static long baseSumB = 0L;
    static long sumA = 0L;
    static long sumB = 0L;
    
    public int solution(int[] queue1, int[] queue2) {
        int answer = -1;
        
        Deque<Integer> queueA = new ArrayDeque<>();
        Deque<Integer> queueB = new ArrayDeque<>();
        for(int i = 0; i < queue1.length; i++){
            sumA += queue1[i];
            queueA.offer(queue1[i]);
        }
        for(int i = 0; i < queue2.length; i++){
            sumB += queue2[i];
            queueB.offer(queue2[i]);
        }
        baseSumA = sumA;
        baseSumB = sumB;
        long totalSum = sumA + sumB;
        if(totalSum % 2 == 0){
            int cnt = 0;
            while(true){
                if(sumA > sumB){
                    int num = queueA.poll();
                    sumA -= num;
                    sumB += num;
                    queueB.offer(num);
                }else if(sumA < sumB){
                    int num = queueB.poll();
                    sumB -= num;
                    sumA += num;
                    queueA.offer(num);
                }else{
                    answer = cnt;
                    break;
                }
                if(baseSumA == sumA && baseSumB == sumB){
                    if(!queueA.isEmpty()){
                        if(queueA.peek() == queue1[0]){
                            if(!queueB.isEmpty()){
                                if(queueB.peek() == queue2[0]){
                                    answer = -1;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(cnt > 600000){
                    answer = -1;
                    break;
                }

                ++cnt;
            } 
        }
        
        return answer;
    }
}