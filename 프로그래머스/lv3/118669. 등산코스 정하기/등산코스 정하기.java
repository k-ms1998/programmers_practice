import java.util.*;

/**
Gate->Summit까지의 intensity와 Summit->Gate의 intensity는 같이 때문에 Gate->Summit만 확인
각 Gate에서 출발해서, 도달 가능한 각 Summit까지의 intensity 구하기

이때, 시간 단축을 위해 dp 사용
dpTo[0] = 노드 0까지 도달하는데 필요한 최소한의 intensity
만약에, 현재 x노드를 탐색중이고, 현재 intesnsity가 dpTo[x]보다 크면 탐색 멈춤
-> dpTo[x]가 더 작으면, 노드 x를 거쳤을때 더 작은 intesnsity를 구할 수 없기 때문에
*/
class Solution {
    
    static List<Edge>[] edges;
    static boolean[] isGate;
    static boolean[] isSummit;
    static int ansSummit = Integer.MAX_VALUE;
    static int ansIntensity = Integer.MAX_VALUE;
    static final int INF = 100000000;
    static int[] dpTo;

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        edges = new List[n + 1];
        dpTo = new int[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        Arrays.fill(dpTo, INF);
        
        isGate = new boolean[n + 1];
        for(int i = 0; i < gates.length; i++){
            isGate[gates[i]] = true;
        }
        
        isSummit = new boolean[n + 1];
        for(int i = 0; i < summits.length; i++){
            isSummit[summits[i]] = true;
        }
        
        for(int i = 0; i < paths.length; i++){
            int a = paths[i][0];
            int b = paths[i][1];
            int c = paths[i][2];
            
            edges[a].add(new Edge(b, c));
            edges[b].add(new Edge(a, c));
        }
        
        for(int i = 0; i < gates.length; i++){
            findAnswerTo(gates[i], gates[i], 0);
        }
        
        int[] answer = {ansSummit, ansIntensity};
        return answer;
    }
    
    public static void findAnswerTo(int start, int node, int intensity){
        if(intensity > ansIntensity){
            return;
        }
        if(isSummit[node]){
            if(ansIntensity == intensity){
                if(ansSummit > node){
                    ansSummit = node;
                }
            }else if(ansIntensity > intensity){
                ansIntensity = intensity;
                ansSummit = node;
            }
            
            return;
        }
        
        for(Edge edge : edges[node]){
            int next = edge.dst;
            int cost = edge.cost;
            
            if(isGate[next]){
                continue;
            }
            
            int tmpIntensity = Math.max(intensity, cost);
            if(dpTo[next] > tmpIntensity){
                dpTo[next] = tmpIntensity;
                findAnswerTo(start, next, tmpIntensity);
            }
        }
    }
    
    public static class Edge{
        int dst;
        int cost;
        
        public Edge(int dst, int cost){
            this.dst = dst;
            this.cost = cost;
        }
    }
}