import java.util.*;

class Solution {
    
    static int[][] grid;
    static int[] dx = {0, 1};
    static int[] dy = {1, 0};
    
    public int[][] solution(int n, int[][] build_frame) {        
        grid = new int[n + 1][n + 1];
        for(int i = 0 ; i < build_frame.length; i++){
            int[] frame = build_frame[i];
            int x = frame[0];
            int y = frame[1];
            int a = frame[2]; // 0 == 기둥, 1 == 보
            int b = frame[3]; // 0 == 삭제, 1 == 설치
            
            if(b == 0){
                int nx = x + dx[a];
                int ny = y + dy[a];
                // 제거 = a & ~b
                
                grid[y][x] = grid[y][x] & ~(1 << a);
                grid[ny][nx] = grid[ny][nx] & ~(1 << a + 2);
                if(cannotRemove(n)){ // 제거 불가능
                    grid[y][x] = grid[y][x] | (1 << a);
                    grid[ny][nx] = grid[ny][nx] | (1 << a + 2);
                }
            }else{
                int nx = x + dx[a];
                int ny = y + dy[a];
                if(a == 0){ // 기둥 설치
                    if(y == 0 || ((grid[y][x] & (1 << 2)) == (1 << 2)) 
                       || ((grid[y][x] & (1 << 1)) == (1 << 1)) || ((grid[y][x] & (1 << 3)) == (1 << 3))){
                        grid[y][x] = grid[y][x] | (1 << 0);
                        grid[ny][nx] = grid[ny][nx] | (1 << 2);
                    }
                }else{ //보 설치
                    if(((grid[y][x] & (1 << 2)) == (1 << 2)) || ((grid[ny][nx] & (1 << 2)) == (1 << 2)
                      || (((grid[y][x] & (1 << 3)) == (1 << 3)) && ((grid[ny][nx] & (1 << 1)) == (1 << 1))))){
                        grid[y][x] = grid[y][x] | (1 << 1);
                        grid[ny][nx] = grid[ny][nx] | (1 << 3);
                    }
                }
            }
        }
        
        Deque<Point> queue = new ArrayDeque<>();
        for(int x = 0; x <= n; x++){
            for(int y= 0; y <= n; y++){
                if((grid[y][x] & (1 << 0)) == (1 << 0) || (grid[y][x] & (1 << 1)) == (1 << 1)){
                    if((grid[y][x] & (1 << 0)) == (1 << 0)){
                        queue.offer(new Point(x, y, 0));
                        int nx = x + dx[0];
                        int ny = y + dy[0];
                        grid[y][x] = grid[y][x] & ~(1 << 0);
                        grid[ny][nx] = grid[ny][nx] & ~(1 << 2);
                    }
                    if((grid[y][x] & (1 << 1)) == (1 << 1)){
                        queue.offer(new Point(x, y, 1));
                        int nx = x + dx[1];
                        int ny = y + dy[1];
                        grid[y][x] = grid[y][x] & ~(1 << 1);
                        grid[ny][nx] = grid[ny][nx] & ~(1 << 3);
                    }
                }
            }
        }
        
        int[][] answer = new int[queue.size()][3];
        int idx = 0;
        while(!queue.isEmpty()){
            Point p = queue.poll();
            answer[idx][0] = p.x;
            answer[idx][1] = p.y;
            answer[idx][2] = p.d;
            idx++;
        }
        
        return answer;
    }
    
    public static boolean cannotRemove(int n){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(grid[y][x] != 0){
                    if((grid[y][x] & (1 << 0)) == (1 << 0)){ // (x, y)에 기둥 설치됨
                        if(!(y == 0 || ((grid[y][x] & (1 << 2)) == (1 << 2)
                           || ((grid[y][x] & (1 << 1)) == (1 << 1) || ((grid[y][x] & (1 << 3)) == (1 << 3)))))){
                            
                            return true;
                        }
                    }
                    if((grid[y][x] & (1 << 1)) == (1 << 1)){ // (x, y)에 보 설치됨
                        int nx = x + dx[1];
                        int ny = y + dy[1];
                        if(!((grid[y][x] & (1 << 2)) == (1 << 2) || (grid[ny][nx] & (1 << 2)) == (1 << 2) ||
                        ((grid[y][x] & (1 << 3)) == (1 << 3) && ((grid[ny][nx] & (1 << 1)) == (1 << 1))))){

                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public static class Point{
        int x;
        int y;
        int d;
        
        public Point(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}