class Solution {
    public String solution(String[] cards1, String[] cards2, String[] goal) {
        int idx1 = 0;
        int idx2 = 0;
        boolean flag = true;
        
        for(int i = 0; i < goal.length; i++){
            if(idx1 < cards1.length){
                if(goal[i].equals(cards1[idx1])){
                    idx1++;
                    continue;
                }    
            }
            if(idx2 < cards2.length){
                if(goal[i].equals(cards2[idx2])){
                    idx2++;
                    continue;
                }    
            }
            
            flag = false;
            break;
        }
        
        return flag ? "Yes" : "No";
    }
}