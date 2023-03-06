import java.util.*;

class Solution {
    public String solution(String new_id) {
        int n = new_id.length();
        String step1 = new_id.toLowerCase();
        
        Deque<String> queue = new ArrayDeque<>();
        
        // step 2
        for(int i = 0; i < n; i++){
            char c = step1.charAt(i);
            if(c == '.' || c == '_' || c == '-' || (c >= 'a' && c <= 'z') || ((c >= '0' && c <= '9'))){
                queue.offer(String.valueOf(c));
            }
        }
        
        // step 3
        n = queue.size();
        if(!queue.isEmpty()){
            queue.offer(queue.poll());
        }
        for(int i = 1; i < n; i++){
            String cur = queue.pollFirst();
            String prev = queue.peekLast();
            if(prev.equals(".") && cur.equals(".")){
                continue;
            }

            queue.offer(cur);
        }
        
        // step 4
        n = queue.size();
        for(int i = 0; i < n; i++){
            String cur = queue.peekFirst();
            if(cur.equals(".")){
                queue.pollFirst();
            }else{
                break;
            }
        }

        n = queue.size();
        for(int i = 0; i < n; i++){
            String cur = queue.peekLast();
            if(cur.equals(".")){
                queue.pollLast();
            }else{
                break;
            }
        }
        
        // step 5
        if(queue.isEmpty()){
            queue.offer("a");
        }
        
        // step 6
        n = queue.size();
        if(n >= 16){
            int diff = n - 15;
            for(int i = 0; i < diff; i++){
                queue.pollLast();
            }
            
            for(int i = 0; i < 15; i++){
                String cur = queue.peekLast();
                if(cur.equals(".")){
                    queue.pollLast();
                }else{
                    break;
                }
            }
        }
        
        // step 7
        String lastS = queue.peekLast();
        while(queue.size() <= 2){
            queue.offer(lastS);
        }

        String answer = "";
        while(!queue.isEmpty()){
            answer += queue.poll();
        }
        return answer;
    }
}