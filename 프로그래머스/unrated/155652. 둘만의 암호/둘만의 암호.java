class Solution {
    
    static boolean[] mustSkip = new boolean[26];
    static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",                                      "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    
    public String solution(String s, String skip, int index) {
        String answer = "";
            
        for(int i = 0; i < skip.length(); i++){
            int idx = skip.charAt(i) - 'a';
            mustSkip[idx] = true;
        }
        
        for(int i = 0; i < s.length(); i++){
            int idx = s.charAt(i) - 'a';
            for(int j = 0; j < index; j++){
                ++idx;
                if(idx >= 26){
                    idx = -1;
                    j--;
                }else{
                    if(mustSkip[idx]){
                        j--;
                    }
                }
            }
            
            idx = idx >= 26 ? 0 : idx;
            idx = idx < 0 ? 0 : idx;
            answer += alphabet[idx];
        }
        
        return answer;
    }
}