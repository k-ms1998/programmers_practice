import java.util.*;

/**
n x n 배열 회전시키기
-> 시계 방향:  ny = x & nx = (n - 1) - y
-> 반시계 방향: nx = y & ny = (n - 1) - x
    
1. lock의 3배 크기인 map 생성
2. map의 중앙에 lock 배치
3. key를 시계방향으로 회전
4. 회전된 key를 map의 (0,0) 부터 시작해서 배치
5. lock이 저장되어 있는 부분 확인
6. lock이 저장되어 있는 부분이 모두 1이면, 자물쇠와 열쇠가 딱 맞게 만나는 부문. 아닐 경우, 3~6 반복
    (0이면, 열쇠가 자물쇠를 채우지 못한 부분, 2이면 열쇠의 돌출된 부분과 자물쇠의 돌출된 부분이 맞닿는 부분)

*/
class Solution {
    
    static int n, m;
    static boolean answer = false;
    static int[][] map;
    
    public boolean solution(int[][] key, int[][] lock) {     
        n = key.length;
        m = lock.length;

        map = new int[3*m][3*m];
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                map[y + m][x + m] = lock[y][x]; // map의 중간부분에 자물쇠 배치
            }
        }

        for(int i = 0; i < 4; i++){     
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
    
    // 시계 방향 회전
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
    
    // 반시계 방향 회전
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

}