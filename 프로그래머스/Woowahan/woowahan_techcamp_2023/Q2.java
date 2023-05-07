package woowahan_techcamp_2023;

import java.util.*;

/**
 * Solution: 구현
 * -> 각 대각선들에 대해서, (시작지점에서 대각선이 위치한 점까지 가는 경우의 수 * 해댕 점에서 대각선으로 움직인 점에서 도착지점까지 가는 경우의 수)의 합
 *
2, 2, [[1,1], [2,2]], 12
51, 37, [[17, 19]], 3225685
*/
class Q2 {

    static final int MOD = 10000019;
    static long[][] fromStart;
    static int[] tx = {1, 0};
    static int[] ty = {0, 1};

    public int solution(int width, int height, int[][] diagonals) {
        int answer = 0;

        fromStart = new long[width + 1][height + 1];
        initFromStart(width, height);

        for(int i = 0; i < diagonals.length; i++){
            int x = diagonals[i][0];
            int y = diagonals[i][1];

            // 대각선이 (2,2) 네모에 있으며, 대각선의 좌표들은 (1,2), (2,1)
            // (0,0)->(1,2) * (2,1)->(width, height) + (0,0)->(2,1) * (1,2)->(width, height)
            long cntAA = fromStart[x][y - 1];
            long cntAB = findCnt(x - 1, y, width, height, width, height);

            long cntBA = fromStart[x - 1][y];
            long cntBB = findCnt(x, y - 1, width, height, width, height);

            answer += (cntAA * cntAB) % MOD;
            answer %= MOD;

            answer += (cntBA * cntBB) % MOD;
            answer %= MOD;
        }

        return answer % MOD;
    }

    public static long findCnt(int sx, int sy, int targetX, int targetY, int width, int height){
        long[][] grid = new long[width + 1][height + 1];
        grid[sx][sy] = 0;
        for(int x = sx; x <= targetX; x++){
            grid[x][sy] = 1;
        }
        for(int y = sy; y <= targetY; y++){
            grid[sx][y] = 1;
        }

        for(int y = sy + 1; y <= targetY; y++){
            for(int x = sx + 1; x <= targetX; x++){
                grid[x][y] = (grid[x][y-1] + grid[x-1][y]) % MOD;
            }
        }

        return grid[targetX][targetY] % MOD;
    }

    public static void initFromStart(int targetX, int targetY){
        fromStart[0][0] = 0;
        for(int x = 0; x <= targetX; x++){
            fromStart[x][0] = 1;
        }
        for(int y = 0; y <= targetY; y++){
            fromStart[0][y] = 1;
        }

        for(int y = 1; y <= targetY; y++){
            for(int x = 1; x <= targetX; x++){
                fromStart[x][y] = (fromStart[x][y-1] % MOD + fromStart[x-1][y] % MOD) % MOD;
            }
        }
    }
}