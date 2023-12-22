import java.io.*;
import java.util.*;

class Solution {
    
    static int n, m;
    static boolean[][] checked;
    static int[][] island;
    static Map<Integer, Integer> cnts = new HashMap<>();
    
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    
    public int solution(int[][] land) {
        int answer = 0;
        
        n = land.length;
        m = land[0].length;
        
        int num = 0;
        checked = new boolean[n][m];
        island = new int[n][m];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(!checked[y][x] && land[y][x] == 1){
                    num++;
                    int cnt = 1;
                    checked[y][x] = true;
                    island[y][x] = num;
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.offer(new Point(x, y));
                    
                    while(!queue.isEmpty()){
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;
                        
                        for(int i = 0; i < 4; i++){
                            int nx = px + dx[i];
                            int ny = py + dy[i];
                            
                            if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                                continue;
                            }
                            
                            if(!checked[ny][nx] && land[ny][nx] == 1){
                                checked[ny][nx] = true;
                                island[ny][nx] = num;
                                cnt++;
                                queue.offer(new Point(nx, ny));
                            }
                            
                        }
                    }
                    cnts.put(num, cnt);
                }
            }
        }
        
        for(int x = 0; x < m; x++){
            Set<Integer> set = new HashSet<>();
            int total = 0;
            for(int y = 0; y < n; y++){
                if(island[y][x] > 0){
                    set.add(island[y][x]);
                }
            }
            
            for(int idx : set){
                total += cnts.getOrDefault(idx, 0);
            }
            
            answer = Math.max(answer, total);
        }
        
        return answer;
    }
    
    public static class Point{
        int x;
        int y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}