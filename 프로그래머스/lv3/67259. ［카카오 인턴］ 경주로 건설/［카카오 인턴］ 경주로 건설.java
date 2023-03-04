import java.util.*;

/**
Solution: DP
dist[x][y][d] -> (x, y) 좌표에서 d 방향을 보고 있을때 최단 거리
*/
class Solution {
    
    static int n;
    static int[][][] dist;
    static final int INF = 100000000;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    
    public int solution(int[][] board) {
        n = board.length;
        dist = new int[n][n][4];
        for(int y= 0; y < n; y++){
            for(int x = 0; x < n; x++){
                dist[y][x][0] = INF;
                dist[y][x][1] = INF;
                dist[y][x][2] = INF;
                dist[y][x][3] = INF;
            }
        }
        
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 0, 0, 0));
        queue.offer(new Point(0, 0, 0, 1));
        queue.offer(new Point(0, 0, 0, 2));
        queue.offer(new Point(0, 0, 0, 3));
        dist[0][0][0] = 0;
        dist[0][0][1] = 0;
        dist[0][0][2] = 0;
        dist[0][0][3] = 0;
        
        while(!queue.isEmpty()){
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;
            int d = point.d;
            int dir = point.dir;
            
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(board[ny][nx] == 1){
                    continue;
                }
                /*
                현재 보는 방향(dir)이랑 다음으로 움직일 칸이 방형의 변화가 있는지 없는지 확인
                방향이 같으면 d + 100
                방향이 다르면 d + 600 (Because, 해당 칸으로 방향으로 바꾸는데 비용 500 + 실제로 한칸을 움직이는데 드는 비용 100)
                */
                int tmpCost = dir % 2 == i % 2 ? d + 100 : d + 600;
                if(tmpCost < dist[ny][nx][i]){
                    dist[ny][nx][i] = tmpCost;
                    queue.offer(new Point(nx, ny, tmpCost, i));
                }
            }
        }
        
        int answer = INF;
        for(int i = 0; i < 4; i++){
            answer = Math.min(answer, dist[n-1][n-1][i]);
        }
        return answer;
    }
    
    public static class Point{
        int x;
        int y;
        int d;
        int dir;
        
        public Point(int x, int y, int d, int dir){
            this.x = x;
            this.y = y;
            this.d = d;
            this.dir = dir;
        }
    }
}