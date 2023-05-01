class Solution {
    
    static int n;
    static int[] tree;
    static int[][] dp;
    
    public int solution(int[] cookie) {
        int answer = 0;
        
        n = cookie.length;
        tree = new int[4*n];
        dp = new int[n][n];
        createTree(1, 0, n - 1, cookie);
       

        for(int i = 0; i < n - 1; i++){
            boolean found = false;
            for(int r = n - 1; r >= 0; r--){
                if(i >= r || found){
                    break;
                }
                if(dp[i][r] == 0){
                    dp[i][r] = findSum(1, 0, n - 1, i, r);
                }
                
                int left = i;
                int right = r;
                if(dp[left][right] <= 2*answer){
                    break;
                }
                
                while(left <= right){
                    int m = (left + right) / 2;
                    if(m < i || m + 1 > r){
                        break;
                    }
                    if(dp[i][m] == 0){
                        dp[i][m] = findSum(1, 0, n - 1, i, m);
                    }
                    dp[m + 1][r] = dp[i][r] - dp[i][m];
                    
                    int sumA = dp[i][m];
                    int sumB = dp[m + 1][r];
                    dp[i][r] = sumA + sumB;
                    if(sumA == sumB){
                        answer = Math.max(answer, sumA);
                        found = true;
                        break;
                    }else if(sumA < sumB){
                        left = m + 1;
                    }else {
                        right = m - 1;
                    }
                }
            }
        }
        
        return answer;
    }

    public static int createTree(int idx, int left, int right, int[] cookie){
        if(left == right){
            tree[idx] = cookie[left];
            return tree[idx];
        }
        
        int mid = (left + right) / 2;
        tree[idx] = createTree(2*idx, left, mid, cookie) + createTree(2*idx + 1, mid + 1, right, cookie);
        dp[left][right] = tree[idx];
        
        return tree[idx];
    }
    
    public static int findSum(int idx, int left, int right, int targetL, int targetR){
        if(left < targetL && right < targetL){
            return 0;
        }
        if(left > targetR && right > targetR){
            return 0;
        }
        if(left >= targetL && left <= targetR && right >= targetL && right <= targetR){
            return tree[idx];
        }
        if(left == right){
            return tree[idx];
        }
        
        int mid = (left + right) / 2;       
        int sumA = findSum(2*idx, left, mid, targetL, targetR);
        int sumB = findSum(2*idx + 1, mid + 1, right, targetL, targetR);
        
        return sumA + sumB; 
    }
}