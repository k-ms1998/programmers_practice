class Solution {
    
    static int n;
    static int[] index = {0, -3, -2, -1, 0, 1, 2, 3};
    static int[] scores =  new int[4];
    static String[][] map = {{"R", "T"}, {"C", "F"}, {"J", "M"}, {"A", "N"}};
    
    public String solution(String[] survey, int[] choices) {
        n = survey.length;
            
        for(int i = 0; i < n; i++){
            String s = survey[i];
            int choice = choices[i];
            int score = index[choice];
            
            String a = String.valueOf(s.charAt(0));
            String b = String.valueOf(s.charAt(1));
            if(a.equals("R")){
                scores[0] += score;
            }else if(a.equals("T")){
                scores[0] -= score;
            }else if(a.equals("C")){
                scores[1] += score;
            }else if(a.equals("F")){
                scores[1] -= score;
            }else if(a.equals("J")){
                scores[2] += score;
            }else if(a.equals("M")){
                scores[2] -= score;
            }else if(a.equals("A")){
                scores[3] += score;
            }else if(a.equals("N")){
                scores[3] -= score;
            }
        }
                
        String answer = "";
        for(int i = 0; i < 4; i++){
            if(scores[i] <= 0){
                answer += map[i][0];
            }else{
                answer += map[i][1];
            }
        }
        
        
        return answer;
    }
}