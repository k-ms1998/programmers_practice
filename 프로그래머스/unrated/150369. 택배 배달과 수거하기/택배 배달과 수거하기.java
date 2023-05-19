class Solution {
   
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0L;
        
        long dSum = 0L;
        long pSum = 0L;
        for(int i = n - 1; i >= 0; i--){
            dSum += deliveries[i];
            pSum += pickups[i];
            
            while(dSum > 0 || pSum > 0){
                answer += (i + 1);
                dSum -= cap;
                pSum -= cap;
            }
        }
        
        return 2 * answer;
    }
}