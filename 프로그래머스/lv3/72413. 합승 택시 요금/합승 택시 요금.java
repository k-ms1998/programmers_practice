import java.util.*;

class Solution {
    
    static List<Edge>[] edges;
    static int[][] dist;
    static final int INF = 100000000;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        edges = new List[n + 1];
        dist = new int[n + 1][n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            Arrays.fill(dist[i], INF);
        }
        
        for(int i = 0; i < fares.length; i++){
            int src = fares[i][0];
            int dst = fares[i][1];
            int cost = fares[i][2];
            
            edges[src].add(new Edge(dst, cost));
            edges[dst].add(new Edge(src, cost));
            
            dist[src][dst] = cost;
            dist[dst][src] = cost;
            dist[src][src] = 0;
            dist[dst][dst] = 0;
        }
        
        initDist(n);
        int dist1 = dist[s][a] + dist[s][b];
        int dist2 = INF;
        for(int i = 1; i < n + 1; i++){
            if(i == s || dist[s][i] == INF){
                continue;
            }
            dist2 = Math.min(dist2, dist[s][i] + dist[i][a] + dist[i][b]);
        }
        // System.out.println("dist1=" + dist1 + ", dist2=" + dist2);
        
        return Math.min(dist1, dist2);
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
    
    public static class Edge{
        int dst;
        int cost;
        
        public Edge(int dst, int cost){
            this.dst = dst;
            this.cost = cost;
        }
    }
}