import java.util.*;

class Solution {
    
    static int[] dx = {0, 1, 0, -1}; //아래, 오른쪽, 위, 왼쪽
    static int[] dy = {1, 0, -1, 0};
    static int[][][] dist;
    static int n;  // 0 <= n <= 100
    static final int INF = 10000000;
    
    public int solution(int[][] board) {
        int answer = INF;
        
        n = board.length;
        dist = new int[n][n][4];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                dist[y][x][0] = INF;
                dist[y][x][1] = INF;
                dist[y][x][2] = INF;
                dist[y][x][3] = INF;
            }
        }
        
        Deque<Node> queue = new ArrayDeque<>();
        dist[0][0][1] = 0;
        dist[0][1][3] = 0;
        queue.offer(new Node(0, 0, 1, 0));
        queue.offer(new Node(1, 0, 3, 0));
        
        while(!queue.isEmpty()){
            Node node = queue.poll();
            int x = node.x;
            int y = node.y;
            int d = node.d;
            int cnt = node.cnt;
            int ax = x + dx[d];
            int ay = y + dy[d];
            // System.out.println(x + ", " + y + ", " + d);
            
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nnx = ax + dx[i];
                int nny = ay + dy[i];
                
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(nnx < 0 || nny < 0 || nnx >= n || nny >= n){
                    continue;
                }
                if(board[ny][nx] == 1 || board[nny][nnx] == 1){
                    continue;
                }
                
                if(dist[ny][nx][d] > cnt + 1){
                    dist[ny][nx][d] = cnt + 1;
                    queue.offer(new Node(nx, ny, d, cnt + 1));
                }
                int dd = (d + 2) % 4;
                if(dist[nny][nnx][dd] > cnt + 1){
                    dist[nny][nnx][dd] = cnt + 1;
                    queue.offer(new Node(nnx, nny, dd, cnt + 1));
                }
            }
            
            for(int i = 0; i < 4; i++){
                if(i % 2 == d % 2){
                    continue;
                }
                
                int nx = x + dx[i];
                int ny = y + dy[i];
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(board[ny][nx] == 1 || board[ny][ax] == 1 || board[ay][nx] == 1){
                    continue;
                }
                
                if(dist[y][x][i] > cnt + 1){
                    dist[y][x][i] = cnt + 1;
                    queue.offer(new Node(x, y, i, cnt + 1));
                }
                
                // int ii = (i + 2) % 4;
                // if(dist[ny][nx][ii] > cnt + 1){
                //     dist[ny][nx][ii] = cnt + 1;
                //     queue.offer(new Node(nx, ny, ii, cnt + 1));
                // }
            }
            
            for(int i = 0; i < 4; i++){
                if(i % 2 == d % 2){
                    continue;
                }
                
                int nx = ax + dx[i];
                int ny = ay + dy[i];
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(board[ny][nx] == 1 || board[ny][x] == 1 || board[y][nx] == 1){
                    continue;
                }
                
                if(dist[ay][ax][i] > cnt + 1){
                    dist[ay][ax][i] = cnt + 1;
                    queue.offer(new Node(ax, ay, i, cnt + 1));
                }
                
                // int ii = (i + 2) % 4;
                // if(dist[ny][nx][ii] > cnt + 1){
                //     dist[ny][nx][ii] = cnt + 1;
                //     queue.offer(new Node(nx, ny, ii, cnt + 1));
                // }
            }
        }
        
        // System.out.println(dist[n-1][n-1][0]);
        // System.out.println(dist[n-1][n-1][1]);
        // System.out.println(dist[n-1][n-1][2]);
        // System.out.println(dist[n-1][n-1][3]);
        // System.out.println(dist[n-2][n-1][0]);
        // System.out.println(dist[n-1][n-2][1]);
        for(int i = 0; i < 4; i++){
            answer = Math.min(answer, dist[n-1][n-1][i]);
        }

        
        return answer;
    }
    
    public static class Node{
        int x;
        int y;
        int d;
        int cnt;
        
        public Node(int x, int y, int d, int cnt){
            this.x = x;
            this.y = y;
            this.d = d;
            this.cnt = cnt;
        }
    }
}