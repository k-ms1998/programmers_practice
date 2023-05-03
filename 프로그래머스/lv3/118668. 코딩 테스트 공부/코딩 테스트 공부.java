import java.util.*;
/**
Solution: DP
-> (alg, cop)을 얻는데 최단시간을 업데이트하면서 정답 찾기
    -> dp[i][j] = i의 알고력과 j의 코딩력을 얻는데 필요한 최단 시간
-> 다익스트라를 이용한 최단거리 문제와 비슷

1. 구해야하는 target 값들 구하기
-> 모든 문제를 풀기 위해 필요한 알고력과 코딩력을 얻는데 걸리는 최단 시간 구하기
-> target 값들 = 모든 문제들 중에서 요구되는 알고력과 코딩력의 최대값
-> 알고력과 코딩력의 최대값을 구하는데 필요한 최단시간 구하기

2. BFS로 최단시간 구하기
-> 각 회차마다 모든 경우의 확인하기
    -> 1. 문제를 풀지 않고 1의 시간을 사용해서 1의 알고력 증가시키기
    -> 2. 문제를 풀지 않고 1의 시간을 사용해서 1의 코딩력 증가시키기
    -> 3. 문제를 풀어서 알고력과 코딩력을 증가시키기
    => 매 회차마다 알고력과 코딩력을 증가 시킬때, 해당 값들을 얻는데 필요한 최단시간이 업데이트 되는지 확인
        -> dp[i][j]의 값이 현재까지 걸린 시간 + 증가시키는데 걸리는 시간 보다 크면 업데이트
3. answer = dp[maxAlp][maxCop]
*/
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

        int initAlp = Math.max(alp, maxAlp); // 초기의 알고력이 문제에서 필요한 최대의 알고력보다 클 수 있음
        int initCop = Math.max(cop, maxCop); // 초기의 코딩력이 문제에서 필요한 최대의 코딩력보다 클 수 있음
        dp = new int[initAlp + 1][initCop + 1]; 
        for(int i = 0; i <= initAlp; i++){
            for(int j = 0; j <= initCop; j++){
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
            
            if(a >= maxAlp && c >= maxCop){ // target에 도달 했을때
                answer = Math.min(answer, time);
                continue;
            }
            
            // 문제를 풀지 않고 1의 시간을 사용해서 1의 알고력 증가시키기
            if(a + 1 <= maxAlp){
                if(dp[a + 1][c] > time + 1){
                    dp[a + 1][c] = time + 1;
                    queue.offer(new Info(a + 1, c, time + 1));
                }
            }
            // 문제를 풀지 않고 1의 시간을 사용해서 1의 코딩력 증가시키기
            if(c + 1 <= maxCop){
                if(dp[a][c + 1] > time + 1){
                    dp[a][c + 1] = time + 1;
                    queue.offer(new Info(a, c + 1, time + 1));
                }
            }
            // 문제를 풀어서 알고력과 코딩력을 증가시키기
            // 함정: 모든 문제를 무조건 다 1번 이상씩 풀 필요는 없으며, 이미 푼 문제는 다시 풀어도 됨
            // 즉, 문제를 풀었는지 굳이 따로 따지지 않고, 현재의 알고력과 코딩력을 문제를 풀수 있으면, 해당 문제를 풀었을때 얻는 알고력과 코딩력을 확인하고, 해당 값들이 추가 됐을때 최단시간이 업데이트 되는지만 확인하면 됨
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