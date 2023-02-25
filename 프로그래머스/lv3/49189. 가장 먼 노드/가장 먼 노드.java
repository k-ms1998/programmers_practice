import java.io.*;
import java.util.*;

class Solution {
    
    static List<Integer>[] edges;
    static int[] dist;
    static final int INF = 1000000000;
    
    public int solution(int n, int[][] edge) {       
        edges =  new List[n + 1];
        dist = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] =  new ArrayList<>();
            dist[i] = INF;
        }

        for(int i = 0; i < edge.length; i++){
            int a = edge[i][0];
            int b = edge[i][1];
            
            edges[a].add(b);
            edges[b].add(a);
        }
        
        int maxDist = 0;
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.d - e2.d;
            }
        });
        queue.offer(new Edge(1, 0));
        dist[1] = 0;
        while(!queue.isEmpty()){
            Edge cur = queue.poll();
            int node = cur.num;
            int d = cur.d;
            maxDist = Math.max(maxDist, d);
            
            for(int adj: edges[node]){
                if(dist[adj] > d + 1){
                    dist[adj] = d + 1;
                    queue.offer(new Edge(adj, d + 1));
                }
            }
        }
        
        int answer = 0;
        for(int i = 1; i < n + 1; i++){
            if(dist[i] == maxDist){
                answer++;
            }
        }
        
        return answer;
    }
    
    public static class Edge{
        int num;
        int d;
        
        public Edge(int num, int d){
            this.num = num;
            this.d = d;
        }
    }
}