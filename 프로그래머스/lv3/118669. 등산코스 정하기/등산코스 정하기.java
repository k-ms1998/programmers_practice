import java.util.*;

/*
1. 각 출입구로 부터 산봉우리까지의 intensity 계산
2. 출입구 -> 산봉우리의 intensity == 산봉우리 -> 출입구의 intensity => 그러므로, 각 게이트로 부터 출발해서 산봉우리까지의 가장 작은 intensity가 정답
3. 시간 단축을 위해, 각 노드까지의 intensity를 toSummit에 저장
 -> 이때, 예시 1에서 처럼 1<->4는 3이고, 3<->4는 4이므로, 어느 게이트에서 출발하던, 4를 거치게 되면 1번 게이트에서 출발해야 intensity 최소화 가능
*/
class Solution {
    
    static int N;
    static List<Info>[] edges;
    static boolean[] isSummit;
    static boolean[] isGate;
    static int[] toSummit;

    static int ansCost = 100000000;
    static int ansSummit = 0;
    static final int INF = 100000000;
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        N = n;
        edges = new List[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        
        isSummit = new boolean[n + 1];
        for(int i = 0; i < summits.length ;i++){
            isSummit[summits[i]] = true;
        }
        
        isGate = new boolean[n + 1];
        for(int i = 0; i < gates.length ;i++){
            isGate[gates[i]] = true;
        }
        
        for(int i = 0; i < paths.length; i++){
            int a = paths[i][0];
            int b = paths[i][1];
            int c = paths[i][2];
            
            edges[a].add(new Info(b, c));
            edges[b].add(new Info(a, c));
        }

        toSummit = new int[n + 1];
        Arrays.fill(toSummit, INF);
        for(int i = 0; i < gates.length; i++){
            distToSummit(gates[i], gates[i], 0);
        }
        
        int[] answer = new int[2];
        answer[0] = ansSummit;
        answer[1] = ansCost;
        return answer;
    }
    
    public static void distToSummit(int start, int node, int cost){
        if(cost > ansCost){
            return;
        }
        // 산봉우리에 도착하면 탐색 멈춤
        if(isSummit[node]){
            if(ansCost > cost){
                ansCost = cost;
                ansSummit = node;
                return;
            }
            if(ansCost == cost){
                ansSummit = Math.min(ansSummit, node);
                return;
            }
            return;
        }
        
        for(Info next : edges[node]){
            int dst = next.dst;
            int c = Math.max(cost, next.c);
            if(c > ansCost){
                continue;
            }
            
            // 현재 intensity(c)가 더 작고, 출입구가 아닌 경우에만 탐색
            if(toSummit[dst] > c && !isGate[dst]){
                toSummit[dst] = c;
                distToSummit(start, dst, c);
            }
        }
    }
    
    public static class Info{
        int dst;
        int c;
        
        public Info(int dst, int c){
            this.dst = dst;
            this.c = c;
        }
    }
}