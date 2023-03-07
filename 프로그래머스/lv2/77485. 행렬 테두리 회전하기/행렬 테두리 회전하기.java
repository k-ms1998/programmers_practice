class Solution {
    
    static int n;
    static int[][] grid;
    static int r, c;
    
    public int[] solution(int rows, int columns, int[][] queries) {
        r = rows;
        c = columns;
        
        int num = 1;
        grid = new int[rows + 1][columns + 1];
        for(int x = 1; x < rows + 1; x++){
            for(int y = 1; y < columns + 1; y++){
                grid[x][y] = num;
                num++;
            }
        }
        
        // x1 < x2, y1 < y2
        n = queries.length;
        int answer[] = new int[n];
        for(int i = 0; i < n; i++){
            int x1 = queries[i][0];
            int y1 = queries[i][1];
            int x2 = queries[i][2];
            int y2 = queries[i][3];
            
            int lastIdx = grid[x1 + 1][y1];
            int cur = lastIdx;
            for(int x = x1 + 1; x < x2; x++){
                grid[x][y1] = grid[x + 1][y1];
                cur = Math.min(cur, grid[x][y1]);
            }
            // printGrid();
            for(int y = y1; y < y2; y++){
                grid[x2][y] = grid[x2][y + 1];
                cur = Math.min(cur, grid[x2][y]);
            }
            // printGrid();
            for(int x = x2; x > x1; x--){
                grid[x][y2] = grid[x-1][y2];
                cur = Math.min(cur, grid[x][y2]);
            }            
            // printGrid();
            for(int y = y2; y > y1; y--){
                grid[x1][y] = grid[x1][y - 1];
                cur = Math.min(cur, grid[x1][y]);
            }            
            grid[x1][y1] = lastIdx;
            // printGrid();
            // System.out.println("===============");
            
            answer[i] = cur;
        }
        
        return answer;
    }
    
    public static void printGrid(){
        for(int x = 1; x < r + 1; x++){
            for(int y = 1; y < c + 1; y++){
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }
}