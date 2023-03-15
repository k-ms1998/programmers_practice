/**
누적합:
각 skill에 대해서 (r1, c1) ~ (r2, c2) 까지의 값을 업데이트 해주면 시간 초과 발생
그러므로, 누적합을 이용해서 해결
ex: (0,0) ~ (0, 2) 까지 2만큼 내구도를 낮출때 [-2, 0, 0, 2] 누적합 배열을 업데이트
    [-2, 0, 0, 2]를 x = 0 ~ 3까지 누적합을 구하면 [-2, -2, -2, 0]이됨
        => 즉, 값 변경을 원했던 (0,0), (0, 1), (0, 2)는 -2 씩되고, (0,3)의 값은 변경되지 않음
ex: (0,0) ~ (1, 2) 까지 2만큼 내구도를 낮출때 [-2, 0, 0, 2] 누적합 배열을 업데이트
                                           [0, 0, 0, 0]  
                                           [2, 0, 0, -2]  
    x축으로 먼저 누적합 구하면 [-2, -2, -2, 0]  
                            [0, 0, 0, 0]
                            [2, 2, 2, 0] ,
    y축으로 먼저 누적합 구하면 [-2, -2, -2, 0]  
                            [-2, -2, -2, 0]
                            [0, 0, 0, 0]
        => 즉, 원했던대로 (0, 0) ~ (1, 2) 까지의 -2로 업데이트 됨
            => sums[y][x] + board[y][x]를 하게 되면, board[y][x]는 모든 공격 및 회복을 받은 최종 상태의 board[y][x]가 됨
                            
*/
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
                board[y][x] += sums[y][x];
                if(board[y][x] > 0){
                    answer++;
                }
            }
        }


        return answer;
    }
}