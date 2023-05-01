import java.util.*;

class Solution {
    
    static int n;
    static int answer = 0;
    
    public int solution(int[] d, int budget) {
              
        n = d.length;
        List<Integer> dd = new ArrayList<>();
        for(int i = 0; i < n; i++){
            dd.add(d[i]);
        }
        Collections.sort(dd);
        
        int total = 0;
        for(int i = 0; i < dd.size(); i++){
            total += dd.get(i);
            if(total > budget){
                break;
            }
            answer++;
        }
        
        return answer;
    }
    
    
}