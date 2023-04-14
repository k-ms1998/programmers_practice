import java.util.*;

/**
Solution: 구현 + 비트마스킹
1. 문제 조건에 따라서 기둥/보 설치 및 제거
2. 설치 할때, 설치한 위치에 설치 됐다고 알리기 + 기둥/보 끝점에도 반대 방향으로 설치가 됐음을 알기기
    -> (0, 0)에 기둥 설치 -> grid[0][0] = 0001, 끝점 = (0, 1) -> grid[1][0] = 0100
3. 제거 할때는, 조건에 따라서 제거
    -> 제거 후, 모든 좌표 확인해서 기둥/보가 설치 된 좌표들 확인
    -> 기둥/보 설치 요건을 만족시키는지 확인
    -> 만족하지 못하면 제거를 하면 되는 것이므로, 저게한 부분 원상 복구
4. 다시 한번 모든 좌표를 확인하면서, 기둥/보 가 설치된 좌표들 확인 후 정답 반환
*/
class Solution {
    
    static int[][] grid;
    static int[] dx = {0, 1};
    static int[] dy = {1, 0};
    
    public int[][] solution(int n, int[][] build_frame) {        
        /*
        grid[y][x]의 값음 0000~1111
        => 0000: 아무것도 설치 안되어 있음
        => 0001: 기둥 설치(위쪽 방향)
        => 0010: 보 설치(오른쪽 방향)
        => 0100: 기둥 설치(아래 방향)
        => 1000: 보 설치(왼쪽 방향)
        */
        grid = new int[n + 1][n + 1];
        for(int i = 0 ; i < build_frame.length; i++){
            int[] frame = build_frame[i];
            int x = frame[0];
            int y = frame[1];
            int a = frame[2]; // 0 == 기둥, 1 == 보
            int b = frame[3]; // 0 == 삭제, 1 == 설치
            
            if(b == 0){ // 삭제
                int nx = x + dx[a];
                int ny = y + dy[a];
                /*
                제거 = a & ~b
                
                1. 먼저, 요구하는 대로 제거 진행
                2. 제거 후, (0,0)부터 (n,n) 까지 탐색하면서, 남은 모든 기둥들과 보들 확인
                3. 각 기둥과 보가 유지가 가능한지 확인(== 각 기둥과 보가 설치 조건을 만족하는지 확인)
                4. 만약, 하나라도 유지가 불가능하면, 제거가 불가능 하다는 뜻이기 때문에 원상 복구(cannotRemove(n) == false)
                */
                grid[y][x] = grid[y][x] & ~(1 << a);
                grid[ny][nx] = grid[ny][nx] & ~(1 << a + 2);
                if(cannotRemove(n)){ // 제거 불가능
                    // 제거 불가능하므로, 다시 기둥/보 추가해줌
                    grid[y][x] = grid[y][x] | (1 << a);
                    grid[ny][nx] = grid[ny][nx] | (1 << a + 2);
                }
            }else{ // 설치
                int nx = x + dx[a];
                int ny = y + dy[a];
                if(a == 0){ // 기둥 설치
                    if(checkVertical(x, y)){ // 기둥을 추가할 수 있는지 확인
                        // (x, y) 에서 보 설치 -> 끝점: (x, y + 1) => grid[y][x] = 0001, grid[y + 1][x] = 0100
                        grid[y][x] = grid[y][x] | (1 << 0); // 현재 위치에서 위쪽으로 기둥 설치
                        grid[ny][nx] = grid[ny][nx] | (1 << 2); // 설치한 기둥의 끝 점에서 아래 방향으로도 기둥이 설치 되었다고 알려줌
                    }
                }else{ //보 설치
                    if(checkHorizontal(x, y, nx, ny)){ // 보를 추가할 수 있는지 확인
                        // (x, y) 에서 보 설치 -> 끝점: (x + 1, y) => grid[y][x] = 0010, grid[y][x + 1] = 1000
                        grid[y][x] = grid[y][x] | (1 << 1); // 현재 위치에서 오른쪽으로 보 설치
                        grid[ny][nx] = grid[ny][nx] | (1 << 3); // 설치한 보의 끝 점에서 왼쪽으로도 보가 설치 되었다고 알려줌
                    }
                }
            }
        }
        
        Deque<Point> queue = new ArrayDeque<>();
        // 모든 좌표 확인(문제의 요구사항에 맞춰서, x좌표 순으로 먼저 탐색)
        for(int x = 0; x < n + 1; x++){
            for(int y= 0; y < n + 1; y++){
                // 현재 위치에 기둥 또는 보가 설치되어 있을때(이때, 기둥은 위쪽 방향, 보는 오른쪽 방향을 보고 있을때만 확인)
                // Because, 문제에서 기둥은 위쪽 방향, 보는 오른쪽 방향으로만 설치하기 때문에
                if((grid[y][x] & (1 << 0)) == (1 << 0) || (grid[y][x] & (1 << 1)) == (1 << 1)){
                    if((grid[y][x] & (1 << 0)) == (1 << 0)){
                        queue.offer(new Point(x, y, 0));
                        int nx = x + dx[0];
                        int ny = y + dy[0];
                        grid[y][x] = grid[y][x] & ~(1 << 0); // 중복을 막기 위해서, 탐색하면 지워줌
                        grid[ny][nx] = grid[ny][nx] & ~(1 << 2); // 중복을 막기 위해서, 탐색하면 지워줌
                    }
                    if((grid[y][x] & (1 << 1)) == (1 << 1)){
                        queue.offer(new Point(x, y, 1));
                        int nx = x + dx[1];
                        int ny = y + dy[1];
                        grid[y][x] = grid[y][x] & ~(1 << 1); // 중복을 막기 위해서, 탐색하면 지워줌
                        grid[ny][nx] = grid[ny][nx] & ~(1 << 3); // 중복을 막기 위해서, 탐색하면 지워줌
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
                        if(!checkVertical(x, y)){ // 현재 위치에 기둥 설치 조건을 만족하지 안하는지 확인 -> 설치 불가능이면 멈추기
                            return true;
                        }
                    }
                    if((grid[y][x] & (1 << 1)) == (1 << 1)){ // (x, y)에 보 설치됨
                        int nx = x + dx[1];
                        int ny = y + dy[1];
                        if(!checkHorizontal(x, y, nx, ny)){ // 현재 위치에 보 설치 조건을 만족하지 안하는지 확인->설치 불가능이면 멈추기
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    // 기둥을 설치할 수 있는지 확인
    public static boolean checkVertical(int x, int y){
        return y == 0 || // 바닥에 설치할때
            ((grid[y][x] & (1 << 2)) == (1 << 2)) || // 현재 위치 밑에 기둥이 설치되어 있을때
            ((grid[y][x] & (1 << 1)) == (1 << 1)) || // 현재 위치에서 왼쪽에서 보가 들어올때
            ((grid[y][x] & (1 << 3)) == (1 << 3));   // 현재 위치에서 오른쪽으로 보가 나갈때
    }
    
    //보를 설치할 수 있는지 확인
    public static boolean checkHorizontal(int x, int y, int nx, int ny){
        return ((grid[y][x] & (1 << 2)) == (1 << 2)) || // 보의 시작 위치에 아래로 기둥이 설치 되어 있을때
            ((grid[ny][nx] & (1 << 2)) == (1 << 2) ||   // 보가 끝나는 위치 아래로 기둥이 설치 되어 있을때
            (((grid[y][x] & (1 << 3)) == (1 << 3)) && ((grid[ny][nx] & (1 << 1)) == (1 << 1)))); // 보의 시작과 끝 위치 모두 보가 설치되어 있을때
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