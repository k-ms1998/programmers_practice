/*
Floyd-Warshall
1. 각 노드간의 최단거리를 알고 있어야 하기 때문에 floyd로 찾아줌 (3 <= n <= 200 , 27 <= n^3 <= 8,000,000)
2. 이때, 같이 합승을 하다가 중간에 각자 갈 수 있는 것이 중요 => (s->i) + (i->a) + (i->b)
    처음부터 끝까지 같이 하는게 제일 적은 비용이 들수도 있음 => (s->a) + (a->b) OR (s->b) + (b->a)
    처음부터 각자 따로 가는게 더 제일 적은 비용이 들수도 있음 => (s->a) + (s->b)
*/
class Solution {
    
    static int[][] dist;
    static final int INF = 100000000;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        dist = new int[n + 1][n + 1];
        for(int i = 1; i < n + 1; i++){
            for(int j = 1; j < n + 1; j++){
                dist[i][j] = INF;
            }
        }
        
        for(int i = 0; i < fares.length; i++){
            int n1 = fares[i][0];
            int n2 = fares[i][1];
            int f = fares[i][2];
            
            dist[n1][n2] = f;
            dist[n2][n1] = f;
        }
        floyd(n);
        
        int answer = INF;
        answer = Math.min(dist[s][a] + dist[s][b], Math.min(dist[s][a] + dist[a][b], dist[s][b] + dist[b][a]));
        for(int i = 1; i < n + 1; i++){
            if(i == s || dist[s][i] >= INF){
                continue;
            }else if(i == a && dist[i][b] < INF){
                answer = Math.min(answer, dist[s][i] + dist[i][b]);
            }else if(i == b && dist[i][a] < INF){
                answer = Math.min(answer, dist[s][i] + dist[i][a]);
            }else{
                if(dist[i][a]  < INF && dist[i][b] < INF){
                    answer = Math.min(answer, dist[s][i] + dist[i][a] + dist[i][b]);
                }
            }
        }
        
        return answer;
    }
    
    public static void floyd(int n){
        for(int k = 1; k < n + 1; k++){
            for(int i = 1; i < n + 1; i++){
                for(int j = i + 1; j < n + 1; j++){
                    if(dist[i][k] == INF || dist[k][j] == INF || i == k || j == k || i == j){
                        continue;
                    }
                    
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[j][k]);
                    dist[j][i] = dist[i][j];
                }
            }
        }
        
    }
}