class Solution {
    
    static boolean[] used = new boolean[4];
    static String[] able= {"aya", "ye", "woo", "ma"};
    
    public int solution(String[] babbling) {
        int answer = findAnswer(0, "", babbling, 0);
        
        return answer;
    }
    
    public static int findAnswer(int depth, String cur, String[] babbling, int answer){
        if(depth == 4){
            for(int i = 0; i < babbling.length; i++){
                if(cur.equals(babbling[i])){
                    answer++;
                }
            }
            return answer;
        }
        
        for(int i = 0; i < babbling.length; i++){
                if(cur.equals(babbling[i])){
                    answer++;
                }
            }
        for(int i = 0; i < 4; i++){
            if(!used[i]){
                used[i] = true;
                answer = findAnswer(depth + 1, cur + able[i], babbling, answer);
                used[i] = false;
            }
        }
        
        return answer;
    }
}