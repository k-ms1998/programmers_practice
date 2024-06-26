/**
Solution: 구현
1. (0,0) 부터 마지막 좌표까지 매번 탐색
2. 탐색 할때, 각 좌표에 대해서 둘러쌓인 8개의 좌표 확인
    -> (x, y) 이면, (x-1, y-1), (x, y-1), (x+1, y-1), (x+1, y), (x+1,y+1), (x,y+1), (x-1,y+1), (x-1, y)
        -> (x,y) == (x-1, y) == (x-1, y-1) == (x, y-1) 인지 확인
        -> (x,y) == (x, y-1) == (x+1, y-1) == (x+1, y) 인지 확인
        -> (x,y) == (x+1, y) == (x+1, y+1) == (x, y+1) 인지 확인
        -> (x,y) == (x, y+1) == (x-1, y+1) == (x-1, y) 인지 확인
3. 블록을 2x2 만들수 있으면, '0' 으로 변경
4. '0'인 블록들을 확인해, 블록들 없에서, 없어진 블록들에 의해서 grid 업데이트 시키기
    -> 아직 남아있는 블록들을 아래로 떨어뜨리기
        
*/
class Solution {
    
    static char grid[][];
    static int[] dx = {-1, 1, 1, -1};
    static int[] dy = {-1, -1, 1, 1};
    
    // m = 행, n = 열
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        grid = new char[m][n];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < n; x++){
                char c = board[y].charAt(x);
                grid[y][x] = c;
            }
        }
        
        // printGrid(m, n);
        while(true){
            int cnt = checkGrid(m, n);
            // printGrid(m, n);
            if(cnt == 0){
                break;
            }
            
            answer += cnt;
        }
        
        
        return answer;
    }
    
    public static int checkGrid(int m, int n){
        int cnt = 0;
        boolean[][] visited = new boolean[m][n];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < n; x++){
                if(visited[y][x] || grid[y][x] == '0'){
                    continue;
                }
                
                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    
                    if(nx < 0 || ny < 0 || nx >= n || ny >= m){
                        continue;
                    }
                    
                    if(grid[y][x] == grid[ny][x] && grid[ny][x] == grid[y][nx]
                       && grid[y][nx] == grid[ny][nx]){
                        visited[y][x] = true;
                        visited[ny][x] = true;
                        visited[y][nx] = true;
                        visited[ny][nx] = true;
                    }
                }
            }
        }
        
        for(int y = 0; y < m; y++){
            for(int x = 0; x < n; x++){
                if(visited[y][x]){
                    grid[y][x] = '0';
                    cnt++;
                }
            }
        }
        
        // updateGrid
        for(int x = 0; x < n; x++){
            for(int y = m - 1;  y >= 0; y--){
                if(grid[y][x] != '0'){
                    int ny = y + 1;
                    char cur = grid[y][x];
                    while(ny < m){
                        if(grid[ny][x] == '0'){
                            grid[ny][x] = cur;
                            grid[ny - 1][x] = '0';
                            ny++;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        
        return cnt;
    }
    
    public static void printGrid(int m, int n){
        for(int y = 0; y < m; y++){
            for(int x = 0; x < n; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }
}