import java.util.*;

class Solution {
    
    static int n;
    static int[][] dp;
    static int maxAlp = 0;
    static int maxCop = 0;
    static final int INF = 100000000;
    
    public int solution(int alp, int cop, int[][] problems) {
        int answer = INF;
        
        n = problems.length;
        for(int i = 0; i < n; i++){
            maxAlp = Math.max(maxAlp, problems[i][0]);
            maxCop = Math.max(maxCop, problems[i][1]);
        }

        dp = new int[151][151];
        for(int i = 0; i < 151; i++){
            for(int j = 0; j < 151; j++){
                dp[i][j] = INF;
            }
        }
        
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(alp, cop, 0));
        dp[alp][cop] = 0;
        
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int a = Math.min(info.a, maxAlp);
            int c = Math.min(info.c, maxCop);
            int time = info.time;
            
            // System.out.println("a=" + a + ", c=" + c);
            if(a >= maxAlp && c >= maxCop){
                answer = Math.min(answer, time);
                continue;
            }
            
            if(a + 1 <= maxAlp){
                if(dp[a + 1][c] > time + 1){
                    dp[a + 1][c] = time + 1;
                    queue.offer(new Info(a + 1, c, time + 1));
                }
            }
            if(c + 1 <= maxCop){
                if(dp[a][c + 1] > time + 1){
                    dp[a][c + 1] = time + 1;
                    queue.offer(new Info(a, c + 1, time + 1));
                }
            }
            for(int i = 0; i < n; i++){
                if(a >= problems[i][0] && c >= problems[i][1]){
                    int na = a + problems[i][2];
                    int nc = c + problems[i][3];
                    int nextT = problems[i][4];
                    na = Math.min(na, maxAlp);
                    nc = Math.min(nc, maxCop);
                    if(dp[na][nc] > time + nextT){
                        dp[na][nc] = time + nextT;
                        queue.offer(new Info(na, nc, time + nextT));
                    }
                }
            }
        }
        
        return answer;
    }
    
    public static class Info{
        int a;
        int c;
        int time;
        
        public Info(int a, int c, int time){
            this.a = a;
            this.c = c;
            this.time = time;
        }
        
    }
}