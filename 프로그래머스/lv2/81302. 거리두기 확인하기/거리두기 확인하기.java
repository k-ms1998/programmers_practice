import java.util.*;

class Solution {
    
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static final int INF = 1000000000;
    
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        
        // 5개의 5x5 강의실
        // 각 강의실 확인
        for(int i = 0; i < 5; i++){
            int grid[][] = new int[5][5];
            int dist[][] = new int[5][5];
            List<Point> interviewee = new ArrayList<>();
            for(int y = 0; y < 5; y++){
                String[] cur = places[i][y].split("");
                for(int x = 0; x < 5; x++){
                    dist[y][x] = INF;
                    if(cur[x].equals("P")){
                        grid[y][x] = 1;
                        interviewee.add(new Point(x, y, 0));
                    }else if(cur[x].equals("O")){
                        grid[y][x] = 0;
                    }else{
                        grid[y][x] = -1;
                    }
                }
            }  
            
            // interviewee == 면접자들의 위치
            // 각 면접자로부터 출발해서, 다른 면접자까지의 위치 계산
            // BFS로 풀이; 파티션(-1)이 있으면, 넘어가지 못하는 벽처럼 생각해서 skip
            // 시작지점으로부터 현재 위치가 3이상이면 거리두기 조건을 만족하기 때문에 더 이상 탐색 X
            boolean compliant = true;
            for(Point p: interviewee){
                int sx = p.x;
                int sy = p.y;
                dist[sy][sx] = 0;
                Deque<Point> queue = new ArrayDeque<>();
                queue.offer(p);
                while(!queue.isEmpty()){
                    if(!compliant){
                        break;
                    }
                    Point point = queue.poll();
                    int x = point.x;
                    int y = point.y;
                    int d = point.d;
                    
                    if(d >= 3){
                        continue;
                    }
                    
                    for(int j = 0; j < 4; j++){
                        int nx = x + dx[j];
                        int ny = y + dy[j];
                        if(nx < 0 || ny < 0 || nx >= 5 || ny >= 5){
                            continue;
                        }
                        
                        int curD = Math.abs(sx-nx) + Math.abs(sy-ny);
                        if(dist[ny][nx] <= curD){
                            continue;
                        }
                        dist[ny][nx] = curD;
                        if(grid[ny][nx] == -1){
                            continue;
                        }else if(grid[ny][nx] == 0){
                            queue.offer(new Point(nx, ny, curD));
                        }else{
                            // 현재거리가 2이하인데, 다른 면접자랑 만나면 거리두기 조건 만족 X -> break
                            if(curD <= 2){
                                compliant = false;
                                break;
                            }
                        }
                    }
                }
            }
            
            
            if(compliant){
                answer[i] = 1;
            }else{
                answer[i] = 0;
            }
        }
        
        return answer;
    }
    
    public static class Point{
        int x;
        int y;
        int d;
        
        public Point(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}