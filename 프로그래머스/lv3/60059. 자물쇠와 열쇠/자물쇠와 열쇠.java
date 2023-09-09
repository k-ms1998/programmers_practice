import java.io.*;
import java.util.*;

/**
Solution: BruteForce
모든 경우의 수를 생각해서 정답 구하기
But, 시간초과를 방지하기 위해 visited를 통해 이미 확인한 경우의 수이면 건더뛰기
-> visited[r][down][right][up][left] = r번 회전시키고, 밑으로 d번, 오른쪽으로 r번, 위쪽으로 u번 그리고 왼쪽으로 l번 움지였을때의 상태 

열쇠와 자물쇠가 맞는지 확인하기:
1. 자물쇠의 홈들의 위치를 구하기
-> (0,0) 부터 시작해서 가장 첫번째 홈 구하기
    -> 해당 홈을 기준으로 시작해서 다른 홈들은 상대적으로 어디에 있는지 확인해서 리스트에 저장하기
2. 각 상태의 열쇠에 대해서 돌기 부분 구하기
-> (0,0) 부터 시작해서 가장 첫번째 돌기 구하기
    -> 해당 돌기를 기준으로 시작해서 다른 돌기들은 상대적으로 어디에 있는지 확인해서 리스트에 저장하기
3. 그렇게 구한 좌표들을 x가 작은 순서, y가 작은 순서로 정렬
4. 두 리스트들을 앞에서 부터 순서대로 확인해서 모든 값을 정확히 일치하는지 확인하기
-> 정확히 일치하면 해당 열쇠를 자물쇠를 열 수 있는 것
*/
class Solution {
    
    static boolean answer = false;
    static int n, m;
    static List<Point> holes = new ArrayList<>();
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static boolean[][][][][] visited;
    // rotate, down, right, up, left
    
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