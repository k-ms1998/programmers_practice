import java.util.*;

/**
Solution: BitMasking + DFS
1. 각 user_id 에 대해서 마스킹 할 수 있는 모든 조합 찾기
-> frodo => frodo, *rodo, f*odo, fr*do, fro*o, frod*, **odo, ... , *****
2. 각 banned_id 에 대해서, 제재 대상 아이디들 모두 찾기
-> list = {{banned_id[0]와 일치하는 user_id들},{banned_id[1]와 일치하는 user_id들},{banned_id[2]와 일치하는 user_id들} ... }
3. list에 있는 원소들의 조합으로 제재 대상 아이디 목록의 경우의 수 확인하기
-> 이때, 중복되는 user_id가 있으면 안되기 때문에, 지금까지 제대 대상에 추가된 user_id를 확인하면서 조합 만들기

ex:
user_id = ["frodo", "fradi", "crodo", "abc123", "frodoc"], 	banned_id = ["fr*d*", "abc1**"]

fr*d* => ["frodo", "fradi"]
abc1** => ["abc123"] 
조합= [["frodo", "abc123"], ["fradi", abc123"]]
*/
class Solution {
    
    static Map<String, List<Integer>> map = new HashMap<>();
    static boolean[] used;
    
    public int solution(String[] user_id, String[] banned_id) {       
        used = new boolean[(1 << user_id.length)]; // 제대 대상의 아이디들의 조합
        
        // 1. 각 user_id 에 대해서 마스킹 할 수 있는 모든 조합 찾기
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
        
        // 2. 각 banned_id 에 대해서, 제재 대상 아이디들 모두 찾기
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0; i < banned_id.length; i++){
            String banned = banned_id[i];
            int usedBit = 0;

            List<Integer> tmp = new ArrayList<>();
            List<Integer> value = map.getOrDefault(banned, new ArrayList<>());
            for(int idx : value){
                tmp.addAll(map.get(user_id[idx]));
            }
                
            list.add(tmp);
        }
        
        findComb(0, list.size(), list, 0, new HashSet<>(), user_id);
        
        int answer = 0;

        for(int i = 0; i < (1 << user_id.length); i++){
            if(used[i]){
                answer++;
            }
        }
        
        return answer;
    }
    
    // 3. list에 있는 원소들의 조합으로 제재 대상 아이디 목록의 경우의 수 확인하기
    public static void findComb(int depth, int target, List<List<Integer>> list, int bit, Set<String> set, String[] user_id){
        if(depth >= target){
            used[bit] = true;
            return;
        }
        
        for(int idx : list.get(depth)){
            String id = user_id[idx];
            if(!set.contains(id)){ // 이미 제재 대상으로 추가된 아이디이면 무시하기
                set.add(id);
                findComb(depth + 1, target, list, bit | (1 << idx), set, user_id);
                set.remove(id);
            }
        }
        
    }
}