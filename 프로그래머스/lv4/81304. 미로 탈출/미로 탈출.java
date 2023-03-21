import java.util.*;

/**
tc: (5, 1, 5, [[1, 2, 1], [2, 3, 1], [3, 2, 1], [3, 5, 1], [1, 5, 10]], [3]) -> result = 5

1. 간선 정보 입력 받음 -> edges = (a -> b)로 가는 방향, edgbesR = 반대 방향 
2. start에서 시작해서 탐색 시작
3. 도착한 노드가 trap이면 status 업데이트
    -> status를 1 추가(trap노드를 몇번 방문했는지도 확인 가능) -> status가 홀수이면 반대 방향
    -> 현재 노드의 status와, 인접한 노드의 status를 비교해서, 반대 방향으로 탐색해야되는지 확인
        -> 만약 둘다 홀수이거나 짝수이면, 두 노드를 잇는 간선이 결국에는 정방향을 바라보고 있음
        -> 짝홀이 서로 다르면, 두 노드를 잇는 간선은 반대 방향을 보고 있음
4. 각 trap노드는 최대 2번 방문 가능 -> trap 노드를 그 이상 방문하게 되더라도, 더 이상 새로운 경로 탐색 불가능
*/
class Solution {
    
    static List<Point>[] edges;
    static List<Point>[] edgesR;
    static boolean[] isTrap;
    static final int INF = 1000000000;
    static int answer = 1000000000;
    
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        edges = new List[n + 1];
        edgesR = new List[n + 1];
        isTrap = new boolean[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            edgesR[i] = new ArrayList<>();
        }
        
        int[] tmp = new int[n + 1];
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
        
        // 우선순위 큐
        PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                return p1.cost - p2.cost;
            }
        });
        queue.offer(new Point(start, 0, tmp));
        while(!queue.isEmpty()){
            Point cur = queue.poll();
            int num = cur.num;
            int cost = cur.cost;
            int[] state = cur.state;
            
            if(num == end){
                answer = cost;
                break;
            }
            
            int[] copyState = new int[n + 1];
            for(int i = 1; i < n + 1; i++){
                copyState[i] = state[i];
            }           
            // 현재 노드가 trap 노드일 경우, 노드의 상태(status) 업데이트
            // 홀수 이면 방향들 반전, 짝수이면 정방향
            copyState[num] = isTrap[num] ? state[num] + 1 : state[num];

            // adjEdges에는 현재 노드로부터 인접한 모든 방향의 간서들 저장 (cur->next && next-> cur)
            List<Point> adjEdges = new ArrayList<>();
            
            // 먼저, 정방향의 간선들 확인
            for(Point p : edges[num]){
                int next = p.num;
                // 두 노드가 모두 홀수 이던가 짝수이면, 정방향의 간선으로 cur->next 이동 가능
                if(copyState[next] % 2 == copyState[num] % 2){
                    adjEdges.add(p);
                }
            }
            for(Point p : edgesR[num]){
                int next = p.num;
                // 두 노드의 홀짝이 다르면, 반대 방햐으이 간선으로 cur->next 이동 가능
                if(copyState[next] % 2 != copyState[num] % 2){
                    adjEdges.add(p);
                }
            }
            
            for(Point p : adjEdges){
                int next = p.num;
                int nextC = p.cost;
                if(copyState[next] < 2){
                    queue.offer(new Point(next, cost + nextC, copyState));
                }

            }
        }
        
        return answer;
    }
    
    public static class Point{
        int num;
        int cost;
        int[] state;
        
        public Point(int num, int cost, int[] state){
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