import java.util.*;

/**
Solution: Floyd-Warshall
1. 처음부터 각자 따로 택시 탑승
2. S -> 중간 지점까지 합승 -> 중간 지점부터 도착 지점까지 각자 따로 택시 탑승

1, 2중 가장 작은 값이 정답
*/
class Solution {
    
    static int[][] dist;
    static final int INF = 100000000;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        dist = new int[n + 1][n + 1];
        for(int i = 0; i < n + 1; i++){
            Arrays.fill(dist[i], INF);
        }
        
        for(int i = 0; i < fares.length; i++){
            int src = fares[i][0];
            int dst = fares[i][1];
            int cost = fares[i][2];
            
            dist[src][dst] = cost;
            dist[dst][src] = cost;
            dist[src][src] = 0;
            dist[dst][dst] = 0;
        }
        
        initDist(n);
        
        int answer = dist[s][a] + dist[s][b];
        for(int i = 1; i < n + 1; i++){
            if(i == s || dist[s][i] == INF){
                continue;
            }
            answer = Math.min(answer, dist[s][i] + dist[i][a] + dist[i][b]);
        }
        
        return answer;
    }
    
    public static void initDist(int n){
        for(int k = 1; k < n + 1; k++){
            for(int i = 1; i < n + 1; i++){
                for(int j = 1; j < n + 1; j++){
                    if(i == k || j == k || i == j){
                        continue;
                    }
                    
                    if(dist[i][k] != INF && dist[k][j] != INF){
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
    }
}