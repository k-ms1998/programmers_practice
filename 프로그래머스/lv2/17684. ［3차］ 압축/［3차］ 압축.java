import java.util.*;

class Solution {
    
    static Map<String, Integer> map = new HashMap<>();
    
    public int[] solution(String msg) { 
        initMap();
        Deque<Integer> queue = new ArrayDeque<>();
        
        String tmp = "";
        int index = 27;
       for(int i = 0; i < msg.length(); i++){
            String prev = tmp;
            tmp += String.valueOf(msg.charAt(i));
            if(!map.containsKey(tmp)){
                // System.out.println(map.get(prev));
                queue.offer(map.get(prev));
                map.put(tmp, index);
                tmp = "";
                index++;
                i--;
            }
       }
        if(!map.containsKey(tmp)){
            // System.out.println(index);
            queue.offer(index);
        }else{
            // System.out.println(map.get(tmp));
            queue.offer(map.get(tmp));
        }
        
        int[] answer = new int[queue.size()];
        int idx = 0;
        while(!queue.isEmpty()){
            answer[idx] = queue.poll();
            idx++;
        }
        
        return answer;
    }
    
    public static void initMap(){
        for(int i = 0; i < 26; i++){
            char c = (char)('A' + i);
            map.put(String.valueOf(c), i + 1);
        }
    }
}