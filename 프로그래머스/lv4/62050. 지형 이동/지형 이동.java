import java.util.*;

class Solution {
    
    static int n;
    static int[][] island;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static final int INF = 100000000;
    static Map<Integer, List<Point>> lists = new HashMap<>();
    static Map<Integer, Integer> count = new HashMap<>();
    static int[] parent;
    
    public int solution(int[][] land, int height) {
        int answer = 0;
        n = land.length;
        island = new int[n][n];
        
        int num = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(island[y][x] == 0){
                    num++;
                    island[y][x] = num;
                    List<Point> list = new ArrayList<>();
                    
                    int cnt = 0;
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.offer(new Point(x, y));
                    
                    while(!queue.isEmpty()){
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;
                        int h = land[py][px];
                        list.add(new Point(px, py));
                        
                        for(int i = 0; i < 4; i++){
                            int nx = px + dx[i];
                            int ny = py + dy[i];
                            
                            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                continue;
                            }
                            
                            if(Math.abs(land[ny][nx] - h) <= height){
                                if(island[ny][nx] == 0){    
                                    island[ny][nx] = num;
                                    queue.offer(new Point(nx, ny));
                                }
                            }                            
                        }
                    }
                    
                    lists.put(num, list);
                }
            }
        }
        
//         for(int y= 0 ; y < n; y++){
//             for(int x = 0; x < n; x++){
//                 System.out.print(island[y][x]);
//             }
//             System.out.println();
//         }
        // System.out.println("lists" + lists);
        
        PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.dist - e2.dist;
            }
        });
        parent = new int[num + 1];
        for(int i = 1; i <= num; i++){
            parent[i] = i;
            List<Point> list = lists.get(i);
            for(Point p : list){
                for(int  j = 0; j < 4; j++){
                    int nx = p.x + dx[j];
                    int ny = p.y + dy[j];
                    
                    if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                        continue;
                    }
                    
                    if(island[ny][nx] != i){
                        int diff = Math.abs(land[ny][nx] - land[p.y][p.x]);
                        if(diff > height){
                            int a = Math.min(i, island[ny][nx]);
                            int b = Math.max(i, island[ny][nx]);
                            edges.add(new Edge(a, b, diff));
                        }
                        
                    }
                }
            }
        }
        // System.out.println("edges" + edges);
        while(!edges.isEmpty()){
            Edge edge = edges.poll();
            int a = edge.a;
            int b = edge.b;
            int dist = edge.dist;
            
            int rootA = findRoot(a);
            int rootB = findRoot(b);
            
            if(rootA != rootB){
                parent[b] = rootA;
                parent[rootB] = rootA;
                answer += dist;
            }
        }

        
        return answer;
    }
    
    public static int findRoot(int node){
        if(parent[node] == node){
            return node;
        }
        
        int curParent = findRoot(parent[node]);
        parent[node] = curParent;
        
        return curParent;
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
    
    public static class Edge{
        int a;
        int b;
        int dist;
        
        public Edge(int a, int b, int dist){
            this.a = a;
            this.b = b;
            this.dist = dist;
        }
        
        @Override
        public String toString(){
            return "{a=" + a + ", b=" + b + ", dist=" + dist + "}";
        }
    }
}