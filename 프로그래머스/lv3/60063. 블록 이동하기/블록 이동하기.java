import java.util.*;

class Solution {
    
    static int n;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static final int INF = 100000000;
    
    static int[][][] dist;
    static int answer = 0;
    
    public int solution(int[][] board) {
        n = board.length;
        dist = new int[n][n][4];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                Arrays.fill(dist[y][x], INF);
            }
        }
        
        answer = INF;
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 0, 0, 0)); // 초기
        queue.offer(new Point(1, 0, 2, 0)); // 초기
        dist[0][0][0] = 0;
        dist[0][1][2] = 0;
        while(!queue.isEmpty()){
            // System.out.println(queue);
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;
            int dir = point.dir;
            int cnt = point.cnt;
            
            int x2 = x + dx[dir];
            int y2 = y + dy[dir];
            if(x2 < 0 || y2 < 0 || x2 >= n || y2 >= n){
                continue;
            }
                        
            if((x == n - 1 && y == n -1)){ // || (x2 == n - 1 && y2 == n -1)
                answer = Math.min(answer, cnt);
                continue;
            }       
            
            // 방향은 그대로, 위치만 이동하기
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nnx = x2 + dx[i];
                int nny = y2 + dy[i];
                
                if(nx < 0 || ny < 0 || nnx < 0 || nny < 0
                  || nx >= n || ny >= n || nnx >= n || nny >= n){
                    continue;
                }
                
                if(board[ny][nx] == 0 && board[nny][nnx] == 0){
                    if(dist[ny][nx][dir] > cnt + 1){
                        dist[ny][nx][dir] = cnt + 1;
                        queue.offer(new Point(nx, ny, dir, cnt + 1));
                    }
                    
                    int tDir = (dir + 2) % 4;
                    if(dist[nny][nnx][tDir] > cnt + 1){                        
                        dist[nny][nnx][tDir] = cnt + 1;
                        queue.offer(new Point(nnx, nny, tDir, cnt + 1));
                    }
                }
            }
            
            // (x, y)의 위치는 그대로하고 방향만 회전하기
            for(int i = 0; i < 4; i++){
                if(i % 2 == dir % 2){
                    continue;
                }
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(board[y][x] == 0 && board[ny][x2] == 0 && board[y2][nx] == 0 && board[ny][nx] == 0){
                    if(dist[y][x][i] > cnt + 1){
                        dist[y][x][i] = cnt + 1;
                        queue.offer(new Point(x, y, i, cnt + 1));
                    }
                    
                    int tDir = (i + 2) % 4;
                    if(dist[ny][nx][tDir] >  cnt + 1){                        
                        dist[ny][nx][tDir] =  cnt + 1;
                        queue.offer(new Point(nx, ny, tDir, cnt + 1));
                    }
                }
            }
        }
        
        // for(int y = 0; y < n; y++){
        //     for(int x = 0; x < n; x++){
        //         System.out.print("{" + (dist[y][x][0] == INF ? 0 : dist[y][x][0])
        //                          + ", " + (dist[y][x][1] == INF ? 0 : dist[y][x][1])
        //                          + ", " + (dist[y][x][2] == INF ? 0 : dist[y][x][2])
        //                          + ", " + (dist[y][x][3] == INF ? 0 : dist[y][x][3]) + "} ");
        //     }
        //     System.out.println();
        // }
                        
        return answer;
    }
    
    public static class Point{
        int x;
        int y;
        int dir;
        int cnt;
        
        
        public Point(int x, int y, int dir, int cnt){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.cnt = cnt;
        }
        
        @Override
        public String toString(){
            return "{x=" + x + ",y=" + y + ",dir=" + dir + ",cnt=" + cnt + "}";
        }
    }
}