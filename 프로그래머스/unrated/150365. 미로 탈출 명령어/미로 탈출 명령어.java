class Solution {
    
    //d, l, r, u 순
    static int[] dx = {0, -1, 1, 0};
    static int[] dy = {1, 0, 0, -1};
    static String[] comm = {"d", "l", "r", "u"};
    static String ans = "impossible";
    static boolean found = false;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        int dist = Math.abs(x - r) + Math.abs(y - c);
        if(dist % 2 == k % 2){
            move(n, m, y, x, 0, "", k, c, r);   
        }
        
        return ans;
    }
    
    public static void move(int n, int m, int x, int y, int dist, String cur, int target, int dstX, int dstY){
        int remainingDist = target - dist;
        int distFromCurToTarget = Math.abs(x - dstX) + Math.abs(y - dstY);
        if(remainingDist < distFromCurToTarget){
            return;
        }
        if(dist > target){
            return;
        }
        if(x == dstX && y == dstY && dist == target){
            if(found){
                return;
            }
            found = true;
            ans = cur;
            
            return;
        }
        
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            if(nx <= 0 || ny <= 0 || nx > m || ny > n){
                continue;
            }
            
            move(n, m, nx, ny, dist + 1, cur + comm[i], target, dstX, dstY);
            if(found){
                return;
            }
        }
    }
}