import java.util.*;

class Solution {
    
    static List<Point>[] edges;
    static List<Point>[] edgesR;
    static boolean[] isTrap;
    static int[][] dist;
    static int[] visited;
    static final int INF = 1000000000;
    static int answer = 1000000000;
    
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        edges = new List[n + 1];
        edgesR = new List[n + 1];
        isTrap = new boolean[n + 1];
        dist = new int[n + 1][n + 1];
        visited = new int[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            edgesR[i] = new ArrayList<>();
            for(int j = i; j < n + 1; j++){
                dist[i][j] = INF;
                dist[j][i] = INF;
            }
        }
        
        boolean[] tmp = new boolean[n + 1];
        for(int i = 0; i < roads.length; i++){
            int a = roads[i][0];
            int b = roads[i][1];
            int c = roads[i][2];
            
            edges[a].add(new Point(b, c, tmp));
            edgesR[b].add(new Point(a, c, tmp));
        }
        for(int i = 0; i < traps.length; i++){
            isTrap[traps[i]] = true;
        }
        
        PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                return p1.cost - p2.cost;
            }
        });
        queue.offer(new Point(start, 0, tmp));
        dist[0][start] = 0;
        dist[start][0] = 0;
        while(!queue.isEmpty()){
            // System.out.println(queue);
            Point cur = queue.poll();
            int num = cur.num;
            int cost = cur.cost;
            boolean[] state = cur.state;
            
            if(num == end){
                answer = cost;
                break;
            }
            
            visited[num]++;
            boolean[] copyState = new boolean[n + 1];
            for(int i = 1; i < n + 1; i++){
                copyState[i] = state[i];
            }           
            copyState[num] = isTrap[num] ? !state[num] : state[num];

            List<Point> adjEdges = new ArrayList<>();
            for(Point p : edges[num]){
                int next = p.num;
                if(copyState[next] == copyState[num]){
                    adjEdges.add(p);
                }
            }
            for(Point p : edgesR[num]){
                int next = p.num;
                if(copyState[next] != copyState[num]){
                    adjEdges.add(p);
                }
            }
            
            for(Point p : adjEdges){
                int next = p.num;
                int nextC = p.cost;
                if(visited[next] < n){
                    dist[num][next] = cost + nextC;
                    queue.offer(new Point(next, cost + nextC, copyState));
                }

            }
        }
        
        return answer;
    }
    
    public static class Point{
        int num;
        int cost;
        boolean[] state;
        
        public Point(int num, int cost, boolean[] state){
            this.num = num;
            this.cost = cost;
            this.state = state;
        }
        
        @Override
        public String toString(){
            return "{num=" + num + ", cost=" + cost + ",state[" + num + "]=" + state[num] + "}";
        }
    }
}