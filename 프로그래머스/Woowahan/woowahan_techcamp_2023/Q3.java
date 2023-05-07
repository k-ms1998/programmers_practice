package woowahan_techcamp_2023;

import java.util.*;

/**
 * Solution: 구현 + DFS
 *
 *
["EEEEMEEEE", "EEEEEEEEM", "EEEEEEMEE", "EEEEEEEEE", "EEMEEEEEM", "EEEEEEEEE", "MEEEEEMEE", "EEEMEEEEE", "MEEEEEMEE"]
x = 0, y = 0
["BBB1EEEEE", "BBB112EEE", "BBBBB1EEE", "B111B112E", "B1E1BBB1E", "12E1B112E", "EEE111EEE", "EEEEEEEEE", "EEEEEEEEE"]

["EEEEE", "EEMEE", "EEEEE", "EEEEE"], 2, 0, ["B1E1B", "B1E1B", "B111B", "BBBBB"]
["MME", "EEE", "EME"], 0, 0, ["XME", "EEE", "EME"]
*/
class Q3 {

    static int n, m;
    static char[][] gridC;
    static int[][] grid;
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

    static boolean[][] visited;
    static String[][] ans;


    public String[] solution(String[] board, int y, int x) {
        n = board.length;
        m = board[0].length();
        ans = new String[n][m];

        gridC = new char[n][m];
        for(int i = 0; i < n; i++){
            String cur = board[i];
            for(int j = 0; j < m; j++){
                gridC[i][j] = cur.charAt(j);
            }
        }

        grid = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(gridC[i][j] != 'M'){
                    int cnt = 0;
                    for(int r = 0; r < 8; r++){
                        int nx = j + dx[r];
                        int ny = i + dy[r];

                        if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                            continue;
                        }

                        if(gridC[ny][nx] == 'M'){
                            cnt++;
                        }
                    }
                    grid[i][j] = cnt;
                }
            }
        }

        if(gridC[y][x] != 'M'){
            visited = new boolean[n][m];
            dfs(x, y);
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(!visited[i][j]){
                        ans[i][j] = "E";
                    }
                }
            }
        }else{
            ans[y][x] = "X";
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(i == y && j == x){
                        continue;
                    }

                    if(gridC[i][j] == 'M'){
                        ans[i][j] = "M";
                    }else{
                        ans[i][j] = "E";
                    }
                }
            }
        }

        String[] answer = new String[n];
        for(int i = 0; i < n; i++){
            String cur = "";
            for(int j = 0; j < m; j++){
                cur += ans[i][j];
            }
            answer[i] = cur;
        }

        return answer;
    }

    public static void dfs(int x, int y){
        if(gridC[y][x] == 'M'){
            return;
        }

        // System.out.println("x=" + x + ", y=" + y);
        visited[y][x] = true;
        if(grid[y][x] >= 1){
            ans[y][x] = String.valueOf(grid[y][x]);
            return;
        }else{
            ans[y][x] = "B";
            for(int i = 0; i < 8; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                    continue;
                }

                if(!visited[ny][nx]){
                    visited[ny][nx] = true;
                    dfs(nx, ny);
                }

            }
        }
    }
}