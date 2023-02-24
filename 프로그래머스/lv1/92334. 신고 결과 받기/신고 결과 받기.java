import java.io.*;
import java.util.*;

class Solution {
    
    static int n;
    static Map<String, Integer> idx = new HashMap<>();
    static boolean[][] reported;
    static int[] reportedCnt;
    static boolean[] banned;
    
    public int[] solution(String[] id_list, String[] report, int k) {
        n = id_list.length;
        int[] answer = new int[n];
        
        for(int i = 0; i < n; i++){
            idx.put(id_list[i], i);
        }
        
        reported = new boolean[n][n];
        reportedCnt = new int[n];
        for(int i = 0; i < report.length; i++){
            String[] cur = report[i].split(" ");
            int a = idx.get(cur[0]);
            int b = idx.get(cur[1]); //a -> b 신고
            
            if(!reported[a][b]){
                reported[a][b] = true;
                reportedCnt[b]++;
            }
        }
        
        banned = new boolean[n];
        for(int i = 0; i < n; i++){
            int cnt = 0;
            for(int j = 0; j < n; j++){
                if(i == j){
                    continue;
                }
                
                if(reported[i][j]){
                    if(reportedCnt[j] >= k){
                        ++cnt;
                    }
                }
            }
            answer[i] = cnt;
            // System.out.print(cnt + " ");
        }
        // System.out.println();
        
        return answer;
    }
}