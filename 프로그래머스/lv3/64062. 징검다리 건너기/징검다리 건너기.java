import java.util.*;

class Solution {
    
    static Map<Integer, Integer> map = new HashMap<>();
    static int answer = 0;
    
    public int solution(int[] stones, int k) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n2.value - n1.value;
            }
        });
        
        for(int i = 0; i < k; i++){
            queue.offer(new Node(i, stones[i]));
            answer = Math.max(answer, stones[i]);
        }
        for(int i = k; i < stones.length; i++){
            queue.offer(new Node(i, stones[i]));
            while(true){
                Node node = queue.peek();
                if(node.idx > i - k && node.idx <= i){
                    break;
                }
                queue.poll();
            }
            answer = Math.min(answer, queue.peek().value);
        }
        answer = Math.min(answer, queue.peek().value);

        return answer;
    }
    
    public static class Node{
        int idx;
        int value;
        
        public Node(int idx, int value){
            this.idx = idx;
            this.value = value;
        }
    }
}