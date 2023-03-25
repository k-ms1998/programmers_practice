class Solution {
    
    static int[][] grid1;
    static int[][] grid2;
    
    public String[] solution(int n, int[] arr1, int[] arr2) {
        grid1 = new int[n][n];
        grid2 = new int[n][n];
        
        convertArr(n, arr1, grid1);
        convertArr(n, arr2, grid2);
        
        String[] answer = new String[n];
        for(int i = 0; i < n; i++){
            String cur = "";
            for(int j = 0; j < n; j++){
                cur += (grid1[i][j] + grid2[i][j]) >= 1 ? "#" : " ";
            }
            answer[i] = cur;
        }
        
        return answer;
    }
    
    public static void convertArr(int n, int[] arr, int[][] grid){
        for(int i = 0; i < n; i++){
            int cur = arr[i];
            int idx = n - 1;
            while(cur > 0){
                int r = cur % 2;
                grid[i][idx] = r;
                cur /= 2;
                idx--;
            }
        }
    }
}