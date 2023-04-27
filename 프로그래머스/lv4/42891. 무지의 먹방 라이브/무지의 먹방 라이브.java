import java.util.*;
import java.io.*;

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
            // System.out.println("foods=" + queue + ", usedTotal=" + usedTotal + ", t=" + t);
            int size = queue.size();
            Food food = queue.poll();
            int num = food.num;
            long time = food.time - usedTotal;
            // System.out.println("time=" + time);
            if(time <= 0){
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
        // System.out.println("foods=" + queue + ", t=" + t);
        long diff = k - t;
        int idx = -1;
        if(!queue.isEmpty()){
            idx = 0;
            // System.out.println("diff=" + diff);
            for(int i = 0; i < n; i++){
                if(used[i]){
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