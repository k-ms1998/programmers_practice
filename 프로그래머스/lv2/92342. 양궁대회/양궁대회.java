class Solution {
    
    static int[] ryan =  new int[11];
    static int diff;
    
    public int[] solution(int n, int[] info) {
        
        int[] tmp = new int[11];
        findAnswer(0, n, 0, tmp, info);
        
        int[] answer;
        if(diff == 0){
            answer = new int[1];
            answer[0] = -1;
        }else{
            answer = new int[11];
            for(int i = 0; i < 11; i++){
                answer[i] = ryan[10 - i];
            }
        }
        
        return answer;
    }
    
    public static void findAnswer(int idx, int n, int used, int[] scores, int[] apeach){
        if(idx == 11 || n == used){
            if(n != used){
                return;
            }

            calculateScore(apeach, scores);
            return;
        }
        
        int[] tmp = new int[11];
        for(int i = 0; i < 11; i++){
            tmp[i] = scores[i];
        }
        for(int i = 0; i <= n - used; i++){
            tmp[idx] = i;
            findAnswer(idx + 1, n, used + i, tmp, apeach);
        }
    }
    
    public static void calculateScore(int[] a, int[] r){
        int scoreA = 0;
        int scoreR = 0;
        for(int i = 0; i < 11; i++){
            if(a[i] == 0 && r[10 - i] == 0){
                continue;
            }
            if(a[i] >= r[10 - i]){
                scoreA += (10-i);
            }else{
                scoreR += (10-i);
            }
        }
        
        int curDiff = scoreR - scoreA;
        if(diff < curDiff){
            // for(int i = 0; i < 11; i++){
            //     System.out.print(r[i] + " ");
            // }
            // System.out.println();
            
            diff = curDiff;
            for(int i = 0; i < 11; i++){
                ryan[i] = r[i];
            }
        }else if(diff == curDiff){           
            // for(int i = 0; i < 11; i++){
            //     System.out.print(r[i] + " ");
            // }
            // System.out.println();
            for(int i = 0; i < 11; i++){
                if(r[10 - i] > ryan[10 - i]){
                    for(int j = 0; j < 11; j++){
                        ryan[j] = r[j];
                    }
                    break;
                }
            }
        }
    }
}