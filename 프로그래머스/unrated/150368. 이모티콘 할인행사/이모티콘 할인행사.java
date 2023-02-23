class Solution {
    
    static int[] discount = {10, 20, 30, 40};
    static int targetDepth;
    static int maxPlusCnt = 0;
    static int maxTotalProfit = 0;
    
    public int[] solution(int[][] users, int[] emoticons) {

        int[] costPerUser = new int[users.length];
        targetDepth = emoticons.length;
        findAnswer(0, users.length, costPerUser, users, emoticons);
        System.out.println("maxPlusCnt = " + maxPlusCnt + ", maxTotalProfit = " + maxTotalProfit);
        
        int[] answer = {maxPlusCnt, maxTotalProfit};
        return answer;
    }
    
    public static void findAnswer(int depth, int n, int[] costPerUser, int[][] users, int[] emoticons){
        if(depth == targetDepth){
            int plusCnt = 0;
            int totalProfit = 0;
            for(int i = 0; i < n; i++){
                if(costPerUser[i] >= users[i][1]){
                    plusCnt++;
                }else{
                    totalProfit += costPerUser[i];
                }
            }
            
            if(maxPlusCnt < plusCnt){
                maxPlusCnt = plusCnt;
                maxTotalProfit = totalProfit;
            }else if(maxPlusCnt == plusCnt){
                maxTotalProfit = Math.max(maxTotalProfit, totalProfit);
            }
            return;
        }
        
        for(int i = 0; i < 4; i++){
            int curDiscount = discount[i];
            int afterDiscount = emoticons[depth] * (100 - curDiscount) / 100;
            for(int j = 0; j < n; j++){
                if(curDiscount >= users[j][0]){
                    costPerUser[j] += afterDiscount;
                }
            }
            findAnswer(depth + 1, n, costPerUser,  users, emoticons);
            for(int j = 0; j < n; j++){
                if(curDiscount >= users[j][0]){
                    costPerUser[j] -= afterDiscount;
                }
            }
        }
        
    }
}