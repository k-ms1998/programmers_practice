import java.util.*;

class Solution {
    
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static List<Point>[] cards = new List[7];
    static final int INF = 10000000;
    static int answer;
    
    public int solution(int[][] board, int r, int c) {
        for(int i = 0; i < 7; i++){
            cards[i] = new ArrayList<>();
        }
        
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                if(board[y][x] > 0){
                    int num = board[y][x];
                    cards[num].add(new Point(x, y));
                }
            }
        }
        
        answer = INF;
        findAnswer(board, r, c, 0, 0);
        
        return answer;
    }
    
    public static void findAnswer(int[][] board, int r, int c, int cardsFound, int d){
        if(checkBoard(board)){
            answer = Math.min(answer, d);
            return;
        }
        
        int[][] dist = new int[4][4];
        initDist(board, r, c, dist);
        
        for(int i = 1; i < 7; i++){
            if((cardsFound & (1 << i)) != (1 << i) && cards[i].size() == 2){
                Point a = cards[i].get(0);
                Point b = cards[i].get(1);
                                
                int[][] tmpDistA = new int[4][4];
                findDistFromAtoB(board, a, b, tmpDistA);
                
                int[][] tmpDistB = new int[4][4];
                findDistFromAtoB(board, b, a, tmpDistB);
                                
                int[][] copyBoard = new int[4][4];
                for(int y = 0; y < 4; y++){
                    for(int x = 0; x < 4; x++){
                        copyBoard[y][x] = board[y][x];
                    }
                }
                copyBoard[a.y][a.x] = 0;
                copyBoard[b.y][b.x] = 0;
                int distAB = tmpDistA[b.y][b.x];
                int distBA = tmpDistB[a.y][a.x];

                
                // a -> b
                findAnswer(copyBoard, b.y, b.x, cardsFound | (1 << i), d + dist[a.y][a.x] + distAB + 2);
                // b -> a
                findAnswer(copyBoard, a.y, a.x, cardsFound | (1 << i), d + dist[b.y][b.x] + distBA + 2);
            }
        }
    }
    
    public static void initDist(int[][] board, int r, int c, int[][] dist){
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                dist[y][x] = INF;
            }
        }
        
        dist[r][c] = 0;
        Deque<Info> q1 = new ArrayDeque<>();
        q1.offer(new Info(c, r, 0));
        while(!q1.isEmpty()){
            Info info = q1.poll();
            int x = info.x;
            int y = info.y;
            int cnt = info.cnt;
            
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(checkXY(nx, ny)){
                    continue;
                }
                
                if(dist[ny][nx] > cnt + 1){
                    dist[ny][nx] = cnt + 1;
                    q1.offer(new Info(nx, ny, cnt + 1));
                }
                
                nx = x;
                ny = y;
                while(true){
                    int nnx = nx + dx[i];
                    int nny = ny + dy[i];
                    
                    if(checkXY(nnx, nny)){
                        break;
                    }
                    if(board[nny][nnx] > 0){
                        nx = nnx;
                        ny = nny;
                        break;
                    }
                    
                    nx += dx[i];
                    ny += dy[i];
                }
                
                if(dist[ny][nx] > cnt + 1){
                    dist[ny][nx] = cnt + 1;
                    q1.offer(new Info(nx, ny, cnt + 1));
                }
            }
        }
    }
    
    public static void findDistFromAtoB(int[][] board, Point a, Point b, int[][] tmpDist){
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                tmpDist[y][x] = INF;
            }
        }
                
        tmpDist[a.y][a.x] = 0;
        Deque<Info> q2 = new ArrayDeque<>();
        q2.offer(new Info(a.x, a.y, 0));
        while(!q2.isEmpty()){
            Info info = q2.poll();
            int x = info.x;
            int y = info.y;
            int cnt = info.cnt;
            
            if(x == b.x && y == b.y){
                continue;
            }
            
            for(int j = 0; j < 4; j++){
                int nx = x + dx[j];
                int ny = y + dy[j];

                if(checkXY(nx, ny)){
                    continue;
                }

                if(tmpDist[ny][nx] > cnt + 1){
                    tmpDist[ny][nx] = cnt + 1;
                    q2.offer(new Info(nx, ny, cnt + 1));
                }

                nx = x;
                ny = y;
                while(true){
                    int nnx = nx + dx[j];
                    int nny = ny + dy[j];

                    if(checkXY(nnx, nny)){
                        break;
                    }
                    if(board[nny][nnx] > 0){
                        nx = nnx;
                        ny = nny;
                        break;
                    }

                    nx += dx[j];
                    ny += dy[j];
                }

                if(tmpDist[ny][nx] > cnt + 1){
                    tmpDist[ny][nx] = cnt + 1;
                    q2.offer(new Info(nx, ny, cnt + 1));
                }
            }
        }
    }

    public static boolean checkBoard(int[][] board){
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                if(board[y][x] > 0){
                    return false;
                }
            }
        }
    
        return true;
    }
    
    public static void printBoard(int[][] board){
        for(int y= 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }
    
    public static boolean checkXY(int x, int y){
        if(x < 0 || y < 0 || x >= 4 || y >= 4){
            return true;
        }
        return false;
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
    
    public static class Info{
        int x;
        int y;
        int cnt;
        
        public Info(int x, int y, int cnt){
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }
}