class Solution {
    
    static int[] scores = {0, 3, 2, 1, 0, 1, 2, 3};
    static int[][] totalScores = new int[4][2];
    
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        
        int n = survey.length;
        for(int i = 0; i < n; i++){
            String[] tmp = survey[i].split("");
            int score = scores[choices[i]];
            
            String idxStr = choices[i] <= 3 ? tmp[0] : tmp[1];
            int idxA = getIdx(idxStr);
            int idxB;
            if(idxStr.equals("R") || idxStr.equals("C") || idxStr.equals("J") || idxStr.equals("A")){
                idxB = 0;
            }else{
                idxB = 1;
            }
            
            totalScores[idxA][idxB] += score;
            
        }
        
        for(int i = 0; i < 4; i++){
            // System.out.println(totalScores[i][0] + ", " + totalScores[i][1]);
            int scoreA = totalScores[i][0];
            int scoreB = totalScores[i][1];
            if(scoreA > scoreB){
                answer += getString(i, 0);
            }else if(scoreA < scoreB){
                answer += getString(i, 1);
            }else{
                String strA = getString(i, 0);
                String strB = getString(i, 1);
                if(strA.compareTo(strB) < 0){
                    answer += strA;
                }else{
                    answer += strB;
                }
            }
        }
        
        return answer;
    }
    
    public static String getString(int idxA, int idxB){
        if(idxA == 0){
            return idxB == 0 ? "R" : "T";
        }else if(idxA == 1){
            return idxB == 0 ? "C" : "F";
        }else if(idxA == 2){
            return idxB == 0 ? "J" : "M";
        }else{
            return idxB == 0 ? "A" : "N";
        }
    }
    
    public static int getIdx(String s){
        if(s.equals("R") || s.equals("T")){
            return 0;
        }else if(s.equals("C") || s.equals("F")){
            return 1;
        }else if(s.equals("J") || s.equals("M")){
            return 2;
        }else{
            return 3;
        }
    }
}