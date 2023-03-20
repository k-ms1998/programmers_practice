import java.util.*;

class Solution {
    
    static int n;
    static Map<String, Integer> idx = new HashMap<>();
    static int[] total;
    static int[] answer = new int[2];
    
    public int[] solution(String[] gems) {
        n = gems.length;
        answer[0] = 1;
        answer[1] = n;
        
        total = new int[n];
        int curIdx = 0;
        for(int i = 0; i < n; i++){
            String gem = gems[i];
            if(!idx.containsKey(gem)){
                total[curIdx]++;
                idx.put(gem, curIdx);
                curIdx++;
            }else{
                int tmp = idx.get(gem);
                total[tmp]++;
            }
        }
        
        Set<String> bought = new HashSet<>();
        int[] tmp = new int[idx.size()];
        int left = 0;
        int right = 0;
        while(right < n){
            if(bought.size() == idx.size()){
                if(right - (left + 1) < answer[1] - answer[0]){
                    answer[0] = left + 1;
                    answer[1] = right;   
                }
                
                int leftIdx = idx.get(gems[left]);
                if(tmp[leftIdx] >= 1){
                    tmp[leftIdx]--;
                    if(tmp[leftIdx] == 0){
                        bought.remove(gems[left]);
                    }
                }
                
                left++;
            }else{
                int rightIdx = idx.get(gems[right]);
                bought.add(gems[right]);
                tmp[rightIdx]++;
                right++;
            }   

            
        }
        while(true){
            // System.out.println(bought + ", left = " + left + ", right = " + right);
            if(bought.size() != idx.size()){
                break;
            }
            if(bought.size() == idx.size()){
                if(right - (left+1) < answer[1] - answer[0]){
                    answer[0] = left + 1;
                    answer[1] = right;   
                }
                int leftIdx = idx.get(gems[left]);
                if(tmp[leftIdx] >= 1){
                    tmp[leftIdx]--;
                    if(tmp[leftIdx] == 0){
                        bought.remove(gems[left]);
                    }
                }
                left++;
            }
        }
        
        
        return answer;
    }
}