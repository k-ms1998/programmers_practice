import java.io.*;
import java.util.*;

class Solution {
    
    static int[] mentors = new int[6];
    static List<Con>[] consulting = new List[6];
    static int answer = Integer.MAX_VALUE;
    static boolean[][][][][] visited = new boolean[21][21][21][21][21];
    static 

    public int solution(int k, int n, int[][] reqs) {        
        for(int i = 1; i < k + 1; i++){
            mentors[i] = 1;
            consulting[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < reqs.length; i++){
            int a = reqs[i][0];
            int b = reqs[i][1];
            int c = reqs[i][2];
            
            consulting[c].add(new Con(a, b));
        }
        
        findAnswer(k, k, n);
        
        return answer;
    }
    
    public static void findAnswer(int used, int k, int n){
        if(visited[mentors[1]][mentors[2]][mentors[3]][mentors[4]][mentors[5]]){
            return;
        }
        visited[mentors[1]][mentors[2]][mentors[3]][mentors[4]][mentors[5]] = true;
        
        if(used >= n){
            int totalWaitTime = 0;
            for(int i = 1; i < k + 1; i++){
                int size = mentors[i];
                int waitTime = 0;
                PriorityQueue<Con> queue = new PriorityQueue<>(new Comparator<Con>(){
                    @Override
                    public int compare(Con c1, Con c2){
                        return (c1.start + c1.duration) - (c2.start + c2.duration); 
                    }
                });
                for(Con c : consulting[i]){
                    int start = c.start;
                    int duration = c. duration;
                    if(queue.size() < size){
                        queue.offer(c);
                    }else{
                        Con cur = queue.poll();
                        int curEnd = cur.start + cur.duration;
                        if(curEnd > start){
                            waitTime += (curEnd - start);
                            queue.offer(new Con(curEnd, duration));
                        }else{
                            queue.offer(new Con(start, duration));
                        }
                    }
                }
                // System.out.println(i + ", " + waitTime);
                totalWaitTime += waitTime;
            }
            
            answer = Math.min(answer, totalWaitTime);
            
            return;
        }
        
        for(int i = 1; i < k + 1; i++){
            mentors[i]++;
            findAnswer(used + 1, k, n);
            mentors[i]--;
        }
        
    }
    
    public static class Con{
        int start;
        int duration;
        
        public Con(int start, int duration){
            this.start = start;
            this.duration = duration;
        }
        
    }
}