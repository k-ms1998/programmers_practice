package woowahan_techcamp_2023;

import java.util.*;

/**
 * Solution: Union-Find + Kruskal
 * 1. Union-Find로 현재 연결되어 있는 노드들 구하기 (network 배열 탐색)
 * 2. repair 들은 사실상 노드들을 연결시키기 위해 사용 가능한 간선들
 * -> 간선들을 가중치가 낮은 순으로 정렬
 * 3. Kruskal을 이용해서, 가중치가 낮은 간선들을 순차적으로 탐색해서, 이미 연결되어 있지 않은 간선들이면 간선 추가
 * 4. 각 노드에 대해서 다시 한번 root 찾기
 * 5. 나타나는 root가 하나 이면 모든 노드 연결 가능. 하나보다 크면 모든 노드들 연결 불가능 -> -1 출력
 * 
6 [[1,2], [3,5], [4,2], [5,6]], [[3,2,10], [5,4,15]], 10
4 [[1,2]], [[2, 3, 10], [3, 1, 12]], -1
*/
class Q4 {

    static int[] parent;
    static boolean[] roots;

    public int solution(int n, int[][] network, int[][] repair) {
        int answer = 0;

        parent = new int[n + 1];
        roots = new boolean[n + 1];
        for(int i = 0; i < n + 1; i++){
            parent[i] = i;
        }

        for(int i = 0; i < network.length; i++){ // 최대 300000 번
            int a = network[i][0];
            int b = network[i][1];

            int rootA = findRoot(a);
            int rootB = findRoot(b);

            if(rootA != rootB){
                parent[b] = rootA;
                parent[rootB] = rootA;
            }
        }

        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.cost - e2.cost;
            }
        });
        for(int i = 0; i < repair.length; i++){ // 최대 300000 번
            int a = repair[i][0];
            int b = repair[i][1];
            int cost = repair[i][2];

            queue.offer(new Edge(a, b, cost));
        }

        while(!queue.isEmpty()){
            Edge e = queue.poll();
            int a = e.a;
            int b = e.b;
            int cost = e.cost;

            int rootA = findRoot(a);
            int rootB = findRoot(b);
            if(rootA != rootB){
                parent[b] = rootA;
                parent[rootB] = rootA;
                answer += cost;
            }
        }

        int rootCnt = 0;
        for(int i = 1; i < n + 1; i++){
            int r = findRoot(i);
            parent[i] = r;
            if(!roots[r]){
                roots[r] = true;
                rootCnt++;
            }
            if(rootCnt > 1){
                break;
            }
        }

        return rootCnt > 1 ? -1 : answer;
    }

    public static int findRoot(int node){
        if(parent[node] == node){
            return node;
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return next;
    }

    public static class Edge{
        int a;
        int b;
        int cost;

        public Edge(int a, int b, int cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public String toString(){
            return "[" + a + "->" + b + ", cost=" + cost + "]";
        }
    }
}