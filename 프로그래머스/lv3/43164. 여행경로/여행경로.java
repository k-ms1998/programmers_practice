import java.io.*;
import java.util.*;

class Solution {
    
    static int n;
    static int numOfNodes;
    static Map<String, Integer> idxMap = new HashMap<>();
    static List<String>[] edges;
    static int[][] visited;
    static boolean found = false;
    static String answer = "";
    
    public String[] solution(String[][] tickets) {        
        int tmpIdx = 0;
        n = tickets.length;
        
        edges = new List[10001];
        for(int i = 0; i < 10001; i++){
            edges[i] = new ArrayList<>();
        }
        visited = new int[10001][10001];
        for(int i = 0; i < n; i++){
            String a = tickets[i][0];
            String b = tickets[i][1];
            
            if(!idxMap.containsKey(a)){
                idxMap.put(a, tmpIdx);
                tmpIdx++;
                numOfNodes++;
            }
            if(!idxMap.containsKey(b)){
                idxMap.put(b, tmpIdx);
                tmpIdx++;
                numOfNodes++;
            }
            
            int idxA = idxMap.get(a);
            int idxB = idxMap.get(b);
            visited[idxA][idxB]++;
            edges[idxA].add(b);
        }
        
        dfs("ICN", 0, "ICN");
        
        return answer.split(" ");
    }
    
    public static void dfs(String node, int edgesVisited, String cur){
        int curIdx = idxMap.get(node);
        if(edgesVisited >= n){
            if(!found){
                found = true;
                answer = cur;
            }else{
                // System.out.println("ans="+answer+",cur="+cur);
                if(cur.compareTo(answer) < 0){
                    answer = cur;
                }
            }
            
            return;
        }
        
        for(String adj : edges[curIdx]){
            int adjIdx = idxMap.get(adj);
            if(visited[curIdx][adjIdx] > 0){
                // System.out.println("cur=" + node + ",adj=" + adj + ",edgesVisited=" + edgesVisited);
                visited[curIdx][adjIdx]--;
                dfs(adj, edgesVisited + 1, cur + " " + adj);
                visited[curIdx][adjIdx]++;
            }
        }

    }
    
}