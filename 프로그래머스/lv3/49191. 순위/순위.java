class Solution {
    
    static int[][] edges;
    
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        edges = new int[n + 1][n + 1];
        for(int i = 0; i < results.length; i++){
            int a = results[i][0];
            int b = results[i][1];
            
            edges[a][b] = 1;
            edges[b][a] = -1;
        }
        
        floyd(n);
        for(int i = 1; i < n + 1; i++){
            int cnt = 0;
            for(int j = 1; j < n + 1; j++){
                if(edges[i][j] != 0){
                    cnt++;
                }
            }
            if(cnt == n - 1){
                answer++;
            }
        }
        
        return answer;
    }
    
    public static void floyd(int n){
        for(int k = 1; k < n + 1; k++){
            for(int i = 1; i < n + 1; i++){
                for(int j = 1; j < n + 1; j++){
                    if(edges[i][j] != 1 && edges[i][k] == 1 && edges[k][j] == 1){
                        edges[i][j] = 1; 
                        edges[j][i] = -1;
                    }
                }
            }
        }
    }
}