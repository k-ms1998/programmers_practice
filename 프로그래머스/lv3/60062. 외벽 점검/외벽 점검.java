import java.util.*;

class Solution {
    
    static int w;
    static List<Integer> d = new ArrayList<>();
    static int answer;
    static final int INF = 100000000;
    static int[][][] dp;
    static int[][][] count;
    
    public int solution(int n, int[] weak, int[] dist) {
        answer = INF;
        dp = new int[dist.length][weak.length][2];
        count = new int[dist.length][weak.length][1 << weak.length];
        
        for(int i = 0; i < dist.length; i++){
            d.add(dist[i]);
        }
        d.sort(Comparator.reverseOrder());
        
        w = weak.length;
        for(int i = 0; i < w; i++){
            dfs(n, weak, 0, i, 0, 1);
        }
        
        return answer == INF ? -1 : answer;
    }
    
    public static int dfs(int n, int[] weak, int dIdx, int wIdx, int visited, int depth){
        if(dIdx >= d.size()){
            return INF;
        }
        if(visited >= (1 << w) - 1){
            answer = Math.min(answer, depth);
            return depth;
        }
        if(count[dIdx][wIdx][visited] != 0){
            return count[dIdx][wIdx][visited];
        }
        
        count[dIdx][wIdx][visited] = depth;
        
        int curW = weak[wIdx];
        int curD = d.get(dIdx);
        int endA = (curW + curD) % n; // 시계 방향
        int endB = (curW - curD) >= 0 ? (curW - curD) : n + (curW - curD); // 반시계 방향
            
        // 시계 방향
        int tmpA = 0;
        if(dp[dIdx][wIdx][0] != 0){
            tmpA = dp[dIdx][wIdx][0];
        }else{
            for(int i = 0; i < w; i++){
                if(endA <= curW){
                    if((weak[i] >= 0 && weak[i] <= endA) || (weak[i] < n && weak[i] >= curW)){
                        tmpA = tmpA | (1 << i);
                    }
                }else{
                    if(weak[i] >= endA && weak[i] <= curW){
                        tmpA = tmpA | (1 << i);
                    }
                }
            }
            dp[dIdx][wIdx][0] = tmpA;
        }
        tmpA = tmpA | visited;
        // System.out.println("curW=" + curW + ",endA=" + endA + ",tmpA=" + tmpA + ",visited=" +  visited + ",depth=" + depth);
        if(tmpA >= (1 << w) - 1){
            answer = Math.min(answer, depth);
            return depth;
        }
        for(int j = 0; j < w; j++){
            if((tmpA & (1 << j)) != (1 << j)){
                count[dIdx][wIdx][visited] = Math.min(count[dIdx][wIdx][visited], dfs(n, weak, dIdx + 1, j, tmpA, depth + 1));
            }
        }
        
        // 반시계 방향
        int tmpB = 0;
        if(dp[dIdx][wIdx][1] != 0){
            tmpB = dp[dIdx][wIdx][1];
        }else{
            for(int i = 0; i < w; i++){
                if(endB >= curW){
                    if((weak[i] >= 0 && weak[i] <= curW) || (weak[i] < n && weak[i] >= endB)){
                        tmpB = tmpB | (1 << i);
                    }
                }else{
                    if(weak[i] >= endB && weak[i] <= curW){
                        tmpB = tmpB | (1 << i);
                    }
                }
            }
            dp[dIdx][wIdx][1] = tmpB;
        }
        
       // System.out.println("curW=" + curW + ",endB=" + endB + ",tmpB=" + tmpB + ",visited=" +  visited + ",depth=" + depth);
        tmpB = tmpB | visited;
        if(tmpB >= (1 << w) - 1){
            answer = Math.min(answer, depth);
            return depth;
        }
        for(int j = 0; j < w; j++){
            if((tmpB & (1 << j)) != (1 << j)){
                count[dIdx][wIdx][visited] = Math.min(count[dIdx][wIdx][visited], dfs(n, weak, dIdx + 1, j, tmpB, depth + 1));
            }
        }
        
        return INF;
    }
    
}