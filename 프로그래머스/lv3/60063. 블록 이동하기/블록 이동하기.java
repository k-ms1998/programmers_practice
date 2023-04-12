import java.util.*;

class Solution {
    
    static int n;
    
    // 0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽
    static int[] dx = {1, 0, -1, 0}; 
    static int[] dy = {0, 1, 0, -1};
    static int[][][] dist; //(x, y) 에서 각 방향에 따라서 최단거리 저장
    static final int INF = 100000000;
    
    public int solution(int[][] board) {
        n = board.length;
        dist = new int[n][n][4];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                Arrays.fill(dist[y][x], INF);
            }
        }
        
        int answer = INF;
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 0, 0, 0)); // 초기
        queue.offer(new Point(1, 0, 2, 0)); // 초기
        dist[0][0][0] = 0;
        dist[0][1][2] = 0;
        while(!queue.isEmpty()){
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;
            int dir = point.dir; // 0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽
            int cnt = point.cnt;
            
            // (x2, y2) = (x, y)의 반대 축
            // ex: (1, 1), dir = 0(오른쪽) 이면, 반대축은(x2, y2) = (2, 1)
            int x2 = x + dx[dir];
            int y2 = y + dy[dir];
            if(x2 < 0 || y2 < 0 || x2 >= n || y2 >= n){
                continue;
            }
                        
            if((x == n - 1 && y == n -1)){
                answer = Math.min(answer, cnt);
                continue;
            }       
            
            // 방향은 그대로, 위치만 이동하기
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nnx = x2 + dx[i];
                int nny = y2 + dy[i]; // 축 두개 모두 이동
                
                if(nx < 0 || ny < 0 || nnx < 0 || nny < 0
                  || nx >= n || ny >= n || nnx >= n || nny >= n){
                    continue;
                }
                
                if(board[ny][nx] == 0 && board[nny][nnx] == 0){ // 두 개의 축이 모두 이동하는 자리가 0이어야 움직일 수 있음
                    if(dist[ny][nx][dir] > cnt + 1){
                        dist[ny][nx][dir] = cnt + 1;
                        queue.offer(new Point(nx, ny, dir, cnt + 1));
                    }
                    
                    // (1, 1), dir = 0 이랑 (2, 1), dir = 2는 사실 같은 칸들을 차지하고 있음
                    // 다만, 중심이 되는 축과 반대편 축의 방향이 다름
                    // 같은 칸을 차지하더라고, 축과 방향이 다르기 때문에 최단거리 업데이트가 가능한지 확인
                    int tDir = (dir + 2) % 4;
                    if(dist[nny][nnx][tDir] > cnt + 1){                        
                        dist[nny][nnx][tDir] = cnt + 1;
                        queue.offer(new Point(nnx, nny, tDir, cnt + 1));
                    }
                }
            }
            
            // (x, y)의 위치는 그대로하고 방향만 회전하기
            for(int i = 0; i < 4; i++){
                if(i % 2 == dir % 2){ // 회전은 90도씩만 가능하므로, 0도 또는 180도는 컨너뛰기
                    continue;
                }
                
                // 방향은 현재 축(x, y)와 반대쪽 축의 관계가 결정함
                // ex: 축이 (2, 2)일때, 반대 축이 (3, 2) => 오른쪽
                //                     반대 축이 (2, 3) => 아래쪽
                //                     반대 축이 (1, 2) => 왼쪽
                //                     반대 축이 (2, 1) => 위쪽
                // (nx, ny)가 반대 축
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                // (x, y) = (1, 1), dir = 0 => (x2, y2) = (2, 1)
                // (x, y) 축에서 시계 방향으로 회전 시키면: (nx, ny) = (1, 2), dir = 1
                // 이때 회전시킬 수 있기 위해서는 (1, 1), (2, 1), (2, 2), (1, 2) 모두 칸이 0이어야 함
                //                              => (x, y), (nx, y2), (x2, ny), (nx, ny)
                if(board[y][x] == 0 && board[y2][nx] == 0 && board[ny][x2] == 0 && board[ny][nx] == 0){
                    // 현재 축에서 방향이 따라서 따로 최단거리 확인 및 업데이트
                    // dist[1][1][0] => (x, y)에서 오른쪽을 바라보고 있을때(반대축이 오른쪽에 있을때), dist[1][1][1] => (x, y)에서 아래쪽을 바라보고 있을때(반대축이 아래쪽에 있을때) 
                    if(dist[y][x][i] > cnt + 1){
                        dist[y][x][i] = cnt + 1;
                        queue.offer(new Point(x, y, i, cnt + 1));
                    }
                    
                    // 위에서 방향은 유지하고 위치만 움직일때 처럼, (x, y)에서 바라보는 방향과 반대축(nx, ny)에서 반대쪽을 바라보는 방향이 같은 2개의 칸을 차지함
                    // 반대 축에 대한 최단거리도 확인 및 업데이트
                    // 회전할때, 반대 축도 함으로써, 다시 반대 축에 대한 회전하는 코드 구현 안해도 됨
                    // 여기서 확인 후, 큐에 넣으면, 어차피 큐에서 각 축에 대해서 확인할때 이동과 회전을 시켜버림
                    int tDir = (i + 2) % 4;
                    if(dist[ny][nx][tDir] >  cnt + 1){                        
                        dist[ny][nx][tDir] =  cnt + 1;
                        queue.offer(new Point(nx, ny, tDir, cnt + 1));
                    }
                }
            }
        }
                        
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