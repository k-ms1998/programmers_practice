import java.util.*;

class Solution {
    
    static int n;
    static int answer = 0;
    static List<Integer>[] es;
    static boolean[][] visited;
    static int s, w;
    
    public int solution(int[] info, int[][] edges) {        
        n = info.length;
        es = new List[n];
        for(int i = 0; i < n; i++){
            es[i] = new ArrayList<>();
        }
        for(int i = 0; i < edges.length; i++){
            int p = edges[i][0];
            int c = edges[i][1];
            
            es[p].add(c);
            es[c].add(p);
        }
        
        visited = new boolean[n][1 << n];
        visited[0][1 << 0] = true;
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(0, 1, 0, 1 << 0));
        while(!queue.isEmpty()){
            Node node = queue.poll();
            int num = node.num;
            int s = node.s;
            int w = node.w;
            int bit = node.bit;
            
            if(s <= w){
                continue;
            }
            
            answer = Math.max(answer, s);
            
            for(int next : es[num]){
                int i = info[next];
                int nBit = bit | (1 << next);
                if(i == 0){
                    if(!visited[next][nBit]){
                        visited[next][nBit] = true;
                        if((bit & (1 << next)) == (1 << next)){
                            queue.offer(new Node(next, s, w, nBit));
                        }else{
                            queue.offer(new Node(next, s + 1, w, nBit));
                        }
                    }
                }else{
                    if(!visited[next][nBit]){
                        visited[next][nBit] = true;
                        if((bit & (1 << next)) == (1 << next)){
                            queue.offer(new Node(next, s, w, nBit));
                        }else{
                            queue.offer(new Node(next, s, w + 1, nBit));
                        }
                    }
                }
            }
        }
        
        return answer;
    }
    
    
    public static class Node{
        int num;
        int s;
        int w;
        int bit;
        
        public Node(int num, int s, int w, int bit){
            this.num = num;
            this.s = s;
            this.w = w;
            this.bit = bit;
        }
    }
}