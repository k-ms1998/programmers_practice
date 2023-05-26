import java.util.*;

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