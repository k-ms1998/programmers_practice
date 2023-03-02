class Solution {
    
    static int n;
    static int[][] grid = new int[101][101];
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int answer = Integer.MAX_VALUE;
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        n = rectangle.length;
        
        for(int i = 0; i < n; i++){
            int x1 = rectangle[i][0] * 2;
            int y1 = rectangle[i][1] * 2;
            int x2 = rectangle[i][2] * 2;
            int y2 = rectangle[i][3] * 2;
        
            for(int y = y1; y <= y2; y++){
                if(grid[y][x1] != 3){
                    grid[y][x1] = 1;
                }
                if(grid[y][x2] != 3){
                    grid[y][x2] = 1;
                }
            }
            for(int x = x1; x <= x2; x++){
                if(grid[y1][x] != 3){
                    grid[y1][x] = 1;
                }
                if(grid[y2][x] != 3){
                    grid[y2][x] = 1;
                }
            }
            for(int y = y1 + 1; y < y2; y++){
                for(int x = x1 + 1; x < x2; x++){
                    grid[y][x] = 3;
                }
            }
        }
        // for(int y = 21; y >= 0; y--){
        //     for(int x = 0; x <= 21; x++){
        //         System.out.print(grid[y][x] + " ");
        //     }
        //     System.out.println();
        // }
        dfs(2*characterX, 2*characterY, 2*itemX, 2*itemY, 0);
        
        return answer / 2;
    }
 
    public static void dfs(int x, int y, int itemX, int itemY, int cnt){
        if(x == itemX && y == itemY){
            answer = Math.min(answer, cnt);
            return;
        }
        
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            if(nx <= 0 || ny <= 0 || nx > 100 || ny > 100){
                continue;
            }
            if(grid[ny][nx] == 1){
                grid[ny][nx] = 0;
                dfs(nx, ny, itemX, itemY, cnt + 1);
                grid[ny][nx] = 1;
            }
        }
    }
}