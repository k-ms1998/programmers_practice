class Solution {
    
    static int[] numCnt = new int [46];
    
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = new int[2];
        
        for(int i = 0; i < 6; i++){
            numCnt[win_nums[i]]++;
        }
        int zeroCnt = 0;
        int cnt = 0;
        for(int i = 0; i < 6; i++){
            int cur = lottos[i];
            if(numCnt[cur] > 0){
                numCnt[cur]--;
                cnt++;
            }else if(cur == 0){
                zeroCnt++;
            }
        }
        
        int winCnt = zeroCnt + cnt;
        if(winCnt == 6){
            answer[0] = 1;
        }else if(winCnt == 5){
            answer[0] = 2;
        }else if(winCnt == 4){
            answer[0] = 3;
        }else if(winCnt == 3){
            answer[0] = 4;
        }else if(winCnt == 2){
            answer[0] = 5;
        }else{
            answer[0] = 6;
        }
        
        if(cnt == 6){
            answer[1] = 1;
        }else if(cnt == 5){
            answer[1] = 2;
        }else if(cnt == 4){
            answer[1] = 3;
        }else if(cnt == 3){
            answer[1] = 4;
        }else if(cnt == 2){
            answer[1] = 5;
        }else{
            answer[1] = 6;
        }
        
        return answer;
    }
}