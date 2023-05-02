import java.util.*;

public class Solution {
    
    static int n;
    static long minH = Integer.MAX_VALUE;
    static long maxH = 0;
    static long answer = Long.MAX_VALUE;
    
    public long solution(int[][] land, int P, int Q) {
        
        n = land.length;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                maxH = Math.max(maxH, land[y][x]);
                minH = Math.min(minH, land[y][x]);
            }
        }
        
        findAnswer(minH, maxH, land, P, Q);
        
        return answer;
    }
    
    public static void findAnswer(long left, long right, int[][] land, int p, int q){
        if(left > right){
            return;
        }
        
        long mid = (left + right) / 2;
        long cntL = findH(mid, land, p, q);
        long cntR = findH(mid + 1, land, p, q);
       
        answer = Math.min(cntL, cntR);
        if(cntL < cntR){
            findAnswer(left, mid - 1, land, p, q);
        }else{
            findAnswer(mid + 1, right, land, p, q);
        }
    }
    
    public static long findH(long h, int[][] land, int p, int q){
        long cnt = 0;
        
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(land[y][x] > h){
                    cnt += q*(land[y][x] - h);
                }else{
                    cnt += p*(h - land[y][x]);
                }
            }
        }
        
        return cnt;
    }
}