import java.util.*;

/**
Solution: Flood + Kruskal + Union-Find
=> 사다리를 놓지 않고도 움직일 수 있으면 비용 증가 X => 그러므로, 사다리를 놓는 비용만 생각하면 됨
    => 섬 문제와 비슷: 섬들끼리 모두 도달 가능한 최소 비용만 계산하면 됨 -> 섬들끼리 연결하는데 비용 계산

1. (0,0) 부터 시작해서 모든 좌표 탐색
2. 각 좌표로 부터, 사다리를 놓지 않고 도달할 수 있는 좌표들끼리 묶고 (Flood), 해당 묶음에 속해 있는 모든 점들 저장
3. 각 섬들에 속한 모든 좌표들 확인
    -> 각 좌표보다 속해 있는 모든 좌표 확인후, 다른 묶음과 인접해 있는 점들 찾기
        -> 두 좌표간의 간선 저장
            -> 간선 = 하나의 묶음으로 부터 인접해 있는 묶으로 가는데 드는 비용
                -> a =  하나의 묶음, b =  다른 하나의 묶음, dist = 두 좌표 사이에 사다리를 놓는 비용
4. 간선들을 가중치가 낮은순으로 정렬
5. 가중치가 낮은 간선들을 순서대로 탐색(Kruskal)
6. 이미 연결되어 있는지 확인(Union-Find)
    -> 연결이 안되어 있으면 간선 추가 -> answer 업데이트 -> answer = answer + dist;

*/
class Solution {
    
    static int n;
    static int[][] island;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static final int INF = 100000000;
    static Map<Integer, List<Point>> lists = new HashMap<>();
    static int[] parent;
    
    public int solution(int[][] land, int height) {
        int answer = 0;
        n = land.length;
        island = new int[n][n];
        
        int num = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                // 각 섬들끼리 묶기
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
                    
                    lists.put(num, list); // 각 섬마다 해당 섬에 속해 있는 모든 좌표들 확인하기
                }
            }
        }
        
        // 간선들 생성하기
        List<Edge> edges = new ArrayList<>();
        parent = new int[num + 1];
        for(int i = 1; i <= num; i++){
            parent[i] = i;
            List<Point> list = lists.get(i);
            for(Point p : list){ // 현재 섬에 속한 모든 점들 확인
                for(int  j = 0; j < 4; j++){ // 각 점마다 인접한 노드 확인
                    int nx = p.x + dx[j];
                    int ny = p.y + dy[j];
                    
                    if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                        continue;
                    }
                    
                    if(island[ny][nx] != i){ //인접한 점이 현재 점이랑 다른 섬이면 간성 생성
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

        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.dist - e2.dist;
            }
        });
        for(Edge edge: edges){ // 가중치가 낮은 순으로 모든 간선 확인
            int a = edge.a;
            int b = edge.b;
            int dist = edge.dist;
            
            int rootA = findRoot(a);
            int rootB = findRoot(b);
            
            if(rootA != rootB){ // 이미 서로 연결되어 있는 상태가 아니면 연결 시키기
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