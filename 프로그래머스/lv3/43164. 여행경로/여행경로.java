import java.io.*;
import java.util.*;

/*
백트래킹
*/
class Solution {
    
    static int n;
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
        /*
        각 문자열 노드에 인덱스 번호를 부여해서 idxMap에 저장
        visited[a][b] => a->b 로 가는 항공권의 갯수 저장 (a->b 로 가는 항공권의 갯수가 여러개일 수 있기 때문에)
        */
        for(int i = 0; i < n; i++){
            String a = tickets[i][0];
            String b = tickets[i][1];
            
            if(!idxMap.containsKey(a)){
                idxMap.put(a, tmpIdx);
                tmpIdx++;
            }
            if(!idxMap.containsKey(b)){
                idxMap.put(b, tmpIdx);
                tmpIdx++;
            }
            
            int idxA = idxMap.get(a);
            int idxB = idxMap.get(b);
            visited[idxA][idxB]++;
            edges[idxA].add(b);
        }
        
        dfs("ICN", 0, "ICN");
        
        return answer.split(" ");
    }
    
    /**
    ICN 부터 시작해서 인접한 노드들로 이동
    인접한 노드로 이동할때마다, visited[a][b]의 값을 1감소
    다시 돌아오면 1증가(백트래킹)
    
    visited[a][b]의 값이 0이면, 더 이상 a->b로 갈수 있는 항공권 존재 X => 인접한 b 노드로 이동 X
    
    인접한 노드들로 이동힐떄, 현재까지 사용한 항공권의 갯수(edge)가 총 항공권의 갯수보다 크거나 같으면 종료
    종료 할때, 현재 이동한 경로랑, answer랑 비교해서, 사전적으로 더 앞에 있는 경로를 answer에 저장
    
    */
    public static void dfs(String node, int edgesVisited, String cur){
        int curIdx = idxMap.get(node);
        if(edgesVisited >= n){
            /*
            found == false -> 처음으로 모든 도시를 방문하는 경로 찾음
                => answer 업데이트
                
            foudn == true -> 이미 모든 도시를 방문한 경로를 찾은 적이 있음
                => cur랑 answer를 비교해서, 사전적으로 더 앞에 있는 경로 찾기
                => cur.compareTo(answer) < 0는 cur - answer < 0이랑 같다고 생각하면 됨 => cur < answer => cur이 answer보다 사전적으로 빨리 옴
            */
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
                visited[curIdx][adjIdx]--;
                dfs(adj, edgesVisited + 1, cur + " " + adj);
                visited[curIdx][adjIdx]++;
            }
        }

    }
    
}