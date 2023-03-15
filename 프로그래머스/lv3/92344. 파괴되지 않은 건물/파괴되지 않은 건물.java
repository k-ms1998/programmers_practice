class Solution {
    
    static int n, m;
    static int[][] sums;
    
    public int solution(int[][] board, int[][] skill) {
        n = board.length;
        m = board[0].length;
        sums = new int[n + 1][m + 1];
        for(int i = 0; i < skill.length; i++){
            int type = skill[i][0];
            int r1 = skill[i][1];
            int c1 = skill[i][2];
            int r2 = skill[i][3];
            int c2 = skill[i][4];
            int degree = skill[i][5];
            int damage = type == 1 ? -degree : degree;
            sums[r1][c1] += damage;
            sums[r1][c2 + 1] -= damage;
            sums[r2 + 1][c1] -= damage;
            sums[r2 + 1][c2 + 1] += damage;
            // for(int x = c1 + 1; x <= c2 + 1; x++){
            //     sums[r1][x] += sums[r1][x-1];
            //     sums[r2][x] += sums[r2][x-1];
            // }
            // for(int y = r1 + 1; y <= r2 + 1; y++){
            //     sums[y][c1] += sums[y - 1][c1];
            //     sums[y][c2] += sums[y - 1][c2];
            // }
        }       
        for(int y = 0; y < n; y++){
            for(int x = 1; x < m; x++){
                sums[y][x] += sums[y][x - 1];
            }
        }
        for(int y = 1; y < n; y++){
            for(int x = 0; x < m; x++){
                sums[y][x] += sums[y - 1][x];
            }
        }
        int answer = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                // if(y >= 1){
                //     sums[y][x] += sums[y-1][x];
                // }
                // if(x >= 1){
                //     sums[y][x] += sums[y][x-1];
                // }
                board[y][x] += sums[y][x];
                if(board[y][x] > 0){
                    answer++;
                }
            }
        }


        return answer;
    }
}