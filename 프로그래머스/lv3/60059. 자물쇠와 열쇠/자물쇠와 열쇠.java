import java.util.*;

class Solution {
    
    static int n, m;
    static boolean answer = false;
    static int[][] map;
    static int flag = 0;
    
    public boolean solution(int[][] key, int[][] lock) {     
        n = key.length;
        m = lock.length;

        map = new int[3*m][3*m];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                map[y + m][x + m] = lock[y][x]; 
            }
        }

        for(int i = 0; i < 4; i++){     
            flag = i;
            findAnswer(key);
            if(answer){
                break;
            }
            key = rotateClockWise(key);
        }
        
        return answer;
    }
    
    public static void findAnswer(int[][] key){
        for(int y = 0; y < (3*m - n); y++){
            for(int x = 0; x < (3*m - n); x++){
                updateMap(key, x, y);
                boolean cur = checkMap();        
                // if(flag == 1){
                //     for(int yy = 0; yy < 3*n; yy++){
                //         for(int xx = 0; xx < 3*m; xx++){
                //             System.out.print(map[yy][xx] + " ");
                //         }
                //         System.out.println();
                //     }
                //     System.out.println("=========");
                // }

                if(cur){
                    answer = true;
                    return;
                }
                undoMap(key, x, y);
            }
        }
    }
    
    public static boolean checkMap(){
        for(int y = m; y < 2*m; y++){
            for(int x = m; x < 2*m; x++){
                if(map[y][x] != 1){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static void updateMap(int[][] key, int sx, int sy){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                map[y + sy][x + sx] += key[y][x];
            }
        }
    }    
    
    public static void undoMap(int[][] key, int sx, int sy){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                map[y + sy][x + sx] -= key[y][x];
            }
        }
    }
    
    public static int[][] rotateClockWise(int[][] key){
        int[][] tmp = new int[n][n];
        
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                int ny = x;
                int nx = (n - 1) - y;
                tmp[ny][nx] = key[y][x];
            }
        }
        
        return tmp;
    }
    
    public static int[][] rotateAntiClockWise(int[][] key){
        int[][] tmp = new int[n][n];
        
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                int ny = (n - 1) - x;
                int nx = y;
                tmp[ny][nx] = key[y][x];
            }
        }
        return tmp;
    }
    
    public static void printGrid(int[][] grid){
        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[0].length; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

}