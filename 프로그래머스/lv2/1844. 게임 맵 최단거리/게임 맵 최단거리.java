import java.io.*;
import java.util.*;

class Solution {
    
    static int n, m;
    static int[][] dist;
    static final int INF = 1000000000;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    
    public int solution(int[][] maps) {
        int answer = 0;
        
        n = maps.length;
        m = maps[0].length;
        dist = new int[n + 1][m + 1];
        for(int i = 1; i < n + 1; i++){
            for(int j = 1; j < m + 1; j++){
                dist[i][j] = INF;
            }
        }
        
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(1, 1, 1));
        while(!queue.isEmpty()){
            Node node =  queue.poll();
            int x = node.x;
            int y = node.y;
            int d = node.dist;
            
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(nx <= 0 || ny <= 0 || nx > m || ny > n){
                    continue;
                }
                if(maps[ny - 1][nx - 1] == 0){
                    continue;
                }
                if(dist[ny][nx] > d + 1){
                    dist[ny][nx] = d + 1;
                    queue.offer(new Node(nx, ny, d + 1));
                }
            }
        }
        
        return dist[n][m] == INF ? -1 : dist[n][m];
    }
    
    public class Node{
        int x;
        int y;
        int dist;
        
        public Node(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}