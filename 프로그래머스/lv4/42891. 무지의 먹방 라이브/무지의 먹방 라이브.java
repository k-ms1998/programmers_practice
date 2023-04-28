import java.util.*;
import java.io.*;

/**
Solution: PQ
1. 음식들을 총 소요시간이 낮은 순으로 정렬/PQ에 저장
2. 순서대로 PQ에서 음식 pop
3. pop한 음식을 모두 먹는데 걸리는 시간 =  pop을 먹는데 걸리는 시간 * 남은 음식들의 개수
    -> 남은 음식이 2, 3, 4 현재 음식이 2일때,1초당 하나의 음식을 섭취하고 다음 음식으로 넘어감
        -> 
        -> 0초: 2, 3, 4
        -> 1초: 1, 3, 4
        -> 2초: 1, 2, 4
        -> 3초: 1, 2, 3
        ...
        -> 6초: 0, 1, 2
4. 마지막으로 pop한 음식을 다 먹을때까지 드는 시간이 k보다 작을때까지 2~3반복
5. 마지막으로 pop한 음식을 다 먹을때까지 드는 시간이 k보다 커지면, 현재 남은 음식들을 최대한 먹고 break
    -> k = 10, t = 5, pop한 음식 = 3, 남은 음식의 개수 = 2일때:
        -> t + pop한 음식 * 남은 음식의 개수(5 + 3*2 = 11) < k(10)
            -> k를 넘기 전까지 남은 음식들 2개를 2번씩은 더 먹을 수 있음 -> t = t + 2*2 = 9
            -> t = 9, k = 10, diff = k - t = 1
                -> 0번째 인덱스부터 diff만큼 움직임
                    -> answer = 해당 인덱스 + 1(코드는 0번째 인덱스부터 시작하지만, 답은 1번째 인덱스부터 시작한다고 가정하기 때문에)
*/
class Solution {
    
    static int n;
    static boolean[] used;
    static long usedTotal = 0L;
    
    public int solution(int[] food_times, long k) {
        int answer = 0;
        
        n = food_times.length;
        used = new boolean[n];
        PriorityQueue<Food> queue = new PriorityQueue<>();
        for(int i = 0; i < n; i++){
            queue.offer(new Food(i, food_times[i]));
        }
        
        long t = 0L;
        while(t <= k && !queue.isEmpty()){
            int size = queue.size();
            Food food = queue.poll();
            int num = food.num;
            long time = food.time - usedTotal; // usedTotal = 지금까지 모두 섭취한 음식들의 총합
            // (4, 2, 3) 음식이 있을때, 맨 처음에 2를 먼저 다 섭취 -> (2, 0, 1), usedTotal = 2가 됨
            if(time <= 0){  // time <= 0이면 섭취를 완료한 음식이기 때문에 건너뛰기
                used[num] = true;
                continue;
            }
            if(t + size*time <= k){
                used[num] = true;
                t += size*time;
                usedTotal += time;
            }else{
                queue.offer(food);
                int lastSize = queue.size();
                while(t + lastSize <= k){
                    t += lastSize;
                }
                break;
            }
        }
        long diff = k - t;
        int idx = -1;
        if(!queue.isEmpty()){
            for(int i = 0; i < n; i++){
                if(used[i]){ // 이미 섭취를 완료한 음식이면 건너뛰기
                    continue;
                }
                if(diff <= 0){
                    idx = i;
                    break;
                }
                diff--;
            }
        }
        
        return idx == -1 ? -1 : idx + 1;
    }
    
    public static class Food implements Comparable<Food>{
        int num;
        int time;
        
        public Food(int num, int time){
            this.num = num;
            this.time = time;
        }
        
        @Override
        public int compareTo(Food o){
            return this.time - o.time;
        }
        
        @Override
        public String toString(){
            return "{num=" + num + ", time=" + time + "}";
        }
    }
}