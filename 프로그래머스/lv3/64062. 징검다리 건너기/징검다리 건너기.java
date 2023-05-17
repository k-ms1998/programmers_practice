import java.util.*;

/**
Solution: Sliding Window
->ex:
=> stones = [2, 4, 5, 3, 2, 1, 4, 2, 5, 1], k = 3일때, 3개씩 묶어서 가장 큰 값들 중에서, 작은 값이 정답
=> (2, 4, 5), (4, 5, 3), (5, 3, 2), (3, 2, 1), (2, 1, 4), (1, 4, 2), (4, 2, 5), (2, 5, 1)
=> 5, 5, 5, 3, 4, 4, 5, 5
=> Answer = 3
*/
class Solution {
        
    public int solution(int[] stones, int k) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n2.value - n1.value;
            }
        });
        
        int answer = 0;
        for(int i = 0; i < k; i++){
            queue.offer(new Node(i, stones[i]));
            answer = Math.max(answer, stones[i]);
        }
        for(int i = k; i < stones.length; i++){
            queue.offer(new Node(i, stones[i]));
            while(true){
                Node node = queue.peek(); // 가장 큰 값
                if(node.idx > i - k && node.idx <= i){ // 노드가 현재 탐색 중인 범위 안에 있을때만 확인; 범위 밖이면 pop
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