import java.util.*;

class Solution {
    
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int n, m;
    static final int INF = 100000000;
    static int answer;
    
    public int solution(int[][] board, int[] aloc, int[] bloc) {
        answer = INF;

        n = board.length;
        m = board[0].length;

        answer = Math.abs(findAnswer(aloc[1], aloc[0], bloc[1], bloc[0], board, 0));

        return answer;
    }
    
    public static int findAnswer(int x1,int y1,int x2,int y2,int[][] board,int player){   
        int x = player == 0 ? x1 : x2;
        int y = player == 0 ? y1 : y2;
        
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                continue;
            }
            if(board[ny][nx] == 0){
                continue;
            }
            if(x1 == x2 && y1 == y2){
                list.add(1);
                break;
            }
            board[y][x] = 0;
            int res = 0;
            if(player == 0){
                res = -findAnswer(nx, ny, x2, y2, board, (player + 1) % 2);
            }else{
                res = -findAnswer(x1, y1, nx, ny, board, (player + 1) % 2);
            }
            if(res >= 0){
                res++;
            }else{
                res--;
            }
            list.add(res);
            board[y][x] = 1; 
        }
        
        int result;
        int pMax = -INF, pMin = INF;
        int mMax = -INF, mMin = INF;
        for (int num : list) {
            if (num > 0) {
                pMax = Math.max(pMax, num);
                pMin = Math.min(pMin, num);
            } else {
                mMax = Math.max(mMax, num);
                mMin = Math.min(mMin, num);
            }
        }
        if (pMax == -INF && mMax == -INF) return 0;
        else if (pMax == -INF) return mMin;
        else if (pMax != -INF) return pMin;
        else return 0;
    }
    
    
}