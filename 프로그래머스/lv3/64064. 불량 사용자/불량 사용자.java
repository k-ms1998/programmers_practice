import java.util.*;

class Solution {
    
    static Map<String, List<Integer>> map = new HashMap<>();
    static boolean[] used;
    
    public int solution(String[] user_id, String[] banned_id) {
        int answer = 0;
        
        used = new boolean[(1 << user_id.length)];
        for(int i = 0; i < user_id.length; i++){
            String id = user_id[i];
            for(int bit = 0; bit < (1 << id.length()); bit++){
                String masked = "";
                for(int j = 0; j < id.length(); j++){
                    if((bit & (1 << j)) == (1 << j)){
                        masked += "*";
                    }else{
                        masked += String.valueOf(id.charAt(j));
                    }
                }
                List<Integer> value = map.getOrDefault(masked, new ArrayList<>());
                value.add(i);
                map.put(masked, value);
            }
        }
        
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0; i < banned_id.length; i++){
            String banned = banned_id[i];
            int usedBit = 0;
            Set<String> set = new HashSet<>();
            List<Integer> value = map.getOrDefault(banned, new ArrayList<>());
            for(int idx : value){
                usedBit = usedBit | (1 << idx);
            }
            if(usedBit != 0){
                boolean flag = true;
                List<Integer> tmp = new ArrayList<>();
                for(int j = 0; j < user_id.length; j++){
                    if((usedBit & (1 << j)) == (1 << j)){
                        tmp.addAll(map.get(user_id[j]));
                    }
                }
                
                list.add(tmp);
            }
        }
        // System.out.println("list=" + list);
        
        findComb(0, list.size(), list, 0, new HashSet<>(), user_id);
        
        for(int i = 0; i < (1 << user_id.length); i++){
            if(used[i]){
                answer++;
            }
        }
        
        return answer;
    }
    
    public static void findComb(int depth, int target, List<List<Integer>> list, int bit, Set<String> set, String[] user_id){
        if(depth >= target){
            used[bit] = true;
            return;
        }
        
        for(int idx : list.get(depth)){
            String id = user_id[idx];
            if(!set.contains(id)){
                set.add(id);
                findComb(depth + 1, target, list, bit | (1 << idx), set, user_id);
                set.remove(id);
            }
        }
        
    }
}