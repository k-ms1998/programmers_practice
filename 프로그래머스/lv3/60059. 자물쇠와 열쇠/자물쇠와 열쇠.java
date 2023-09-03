import java.io.*;
import java.util.*;

class Solution {
    
    static boolean answer = false;
    static int n, m;
    static List<Point> holes = new ArrayList<>();
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static boolean[][][][][] visited;
    // down, right, up, left
    
    public boolean solution(int[][] key, int[][] lock) {
        n = lock.length;
        m = key.length;
        visited = new boolean[4][m + 1][m + 1][m + 1][m + 1];
        
        holes = findHoles(lock, n, 0);
        Collections.sort(holes, new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                if(p1.x == p2.x){
                    return p1.y - p2.y;
                }
                
                return p1.x - p2.x;
            }
        });

        for(int r = 0; r < 4; r++){ // 회전
            findAnswer(key, r, 0, 0, 0, 0);
            key = rotate(key);
        }
        
        return answer;
    }
    
    public static void findAnswer(int[][] key, int r, int up, int down, int left, int right){
        // System.out.println(up + ", " + down + ", " + left + ", " + right);
        if(answer){
            return;
        }
        if(visited[r][up][down][left][right]){
            return;
        }
        visited[r][up][down][left][right] = true;
        
        List<Point> keyHoles = findHoles(key, m, 1);
        if(checkHoles(keyHoles, holes)){
            answer = true;
            return;
        }
        
        if(down < m){    
            int[][] tmp1 = moveDownOrRight(key, 0);
            findAnswer(tmp1, r, up, down + 1, left, right);
        }
        if(right < m){
            int[][] tmp2 = moveDownOrRight(key, 1);
            findAnswer(tmp2, r, up, down, left, right + 1);
        }
        if(up < m){
            int[][] tmp3 = moveUpOrLeft(key, 2);
            findAnswer(tmp3, r, up + 1, down, left, right);
        }
        if(left < m){
            int[][] tmp4 = moveUpOrLeft(key, 3);
            findAnswer(tmp4, r, up, down, left + 1, right);
        }
    }
    
    public static boolean checkHoles(List<Point> cur, List<Point> target){
        if(cur.size() != target.size()){
            return false;
        }
        Collections.sort(cur, new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                if(p1.x == p2.x){
                    return p1.y - p2.y;
                }
                
                return p1.x - p2.x;
            }
        });
        // System.out.println("cur=" + cur);
        int size = target.size();
        for(int i = 0; i < size; i++){
            Point p1 = cur.get(i);
            Point p2 = target.get(i);
            
            if(p1.x != p2.x || p1.y != p2.y){
                return false;
            }
        }
        
        return true;
    }
    
    public static List<Point> findHoles(int[][] grid, int size, int target){
        List<Point> result = new ArrayList<>();
        
        int sx = 0;
        int sy = 0;
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                if(grid[y][x] == target){
                    if(result.size() == 0){
                        sx = x;
                        sy = y;
                        result.add(new Point(0, 0));
                    }else{
                        result.add(new Point(x - sx, y - sy));
                    }
                }
            }
        }
        
        return result;
    }
    
    public static int[][] rotate(int[][] grid){
        int[][] tmp = new int[m][m];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                int nx = y;
                int ny = m - 1 - x;
                tmp[y][x] = grid[ny][nx];
            }
        }
        
        return tmp;
    }
    
    public static int[][] copyKey(int[][] key){
        int[][] tmp = new int[m][m];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                tmp[y][x] = key[y][x];
            }
        }
        
        return tmp;
    }
    
    public static int[][] moveDownOrRight(int[][] key, int d){
        int[][] tmp = new int[m][m];
        for(int y = m - 1; y >= 0; y--){
            for(int x = m - 1; x >= 0; x--){
                int px = x - dx[d];
                int py = y - dy[d];
                int value = 0;
                if(px >= 0 && py >= 0 && px < m && py < m){
                    value = key[py][px];
                }
                tmp[y][x] = value;
            }
        }
        
        return tmp;
    }
    
    public static int[][] moveUpOrLeft(int[][] key, int d){
        int[][] tmp = new int[m][m];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                int px = x - dx[d];
                int py = y - dy[d];
                int value = 0;
                if(px >= 0 && py >= 0 && px < m && py < m){
                    value = key[py][px];
                }
                tmp[y][x] = value;
            }
        }
        
        return tmp;
    }
    
    public static void printKey(int[][] key){
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                System.out.print(key[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------");
    }
    
    public static class Point{
        int x;
        int y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}