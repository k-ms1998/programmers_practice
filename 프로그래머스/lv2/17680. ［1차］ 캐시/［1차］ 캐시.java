import java.util.*;

class Solution {
    
    static Deque<String> cache = new ArrayDeque<>();
    static Map<String, Boolean> inCache = new HashMap<>();
    
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        if(cacheSize == 0){
            answer = cities.length * 5;
        }else{
            for(int i = 0; i < cities.length; i++){
                String city = cities[i].toLowerCase();
                if(!inCache.containsKey(city)){
                    inCache.put(city, false);
                }

                boolean isInCache = inCache.get(city);
                if(!isInCache){
                    answer += 5;
                    if(cache.size() >= cacheSize){
                        String popped = cache.poll();
                        inCache.put(popped, false);
                    }
                    cache.offer(city);
                    inCache.put(city, true);
                }else{
                    answer += 1;
                    Deque<String> tmp = new ArrayDeque<>();
                    while(!cache.isEmpty()){
                        String c = cache.poll();
                        if(c.equals(city)){
                            continue;
                        }
                        tmp.offer(c);
                    }
                    while(!tmp.isEmpty()){
                        cache.offer(tmp.poll());
                    }
                    cache.offer(city);
                }
                // System.out.println(cache);
            }
        }
        
        
        
        return answer;
    }
}