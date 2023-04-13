import java.util.*;

/**
Solution: 그리디 + 완전탐색 + DP(메모이제이션) + 비트마스킹
그리디: -> dist 가 가장 큰 값들을 순서대로 탐색
완전탐색: ->가장 큰 dist를 각 weak 지점들로 부터 탐색 시작 & 각 경우에 대해서 시계 방향과 반시계 방향 모두 탐색
DP: 1. -> 각 dist가 weak 지점에서 탐색을 시작할때, 이미 방문한 weak 값들에 대해서 필요한 친구의 수 저장
       -> ex: count[0][1][00100(2)] => 0번째 dist를 1번째 weak 지점에서 시작하고, 0~4번째 weak 지점 중 2번째 weak 지점을 이미 보수 공사 완료 했을때
    2. -> 각 dist가 weak 지점에서 탐색을 시작할때, 시계 또는 반시계 방향으로 도달할 수 있는 지점들 저장
       -> ex: reach[0][2][0] -> 0번째 dist가 2번째 weak 저짐에서 시계 방향으로 도달 가능한 weak 지점들
       -> ex: reach[0][2][1] -> 0번째 dist가 2번째 weak 저짐에서 반시계 방향으로 도달 가능한 weak 지점들
*/
class Solution {
    
    static int w;
    static List<Integer> d = new ArrayList<>();
    static final int INF = 1000000;
    static int[][][] reach;
    static int[][][] count;
    
    public int solution(int n, int[] weak, int[] dist) {
        reach = new int[dist.length][weak.length][2];
        count = new int[dist.length][weak.length][1 << weak.length];
        
        for(int i = 0; i < dist.length; i++){
            d.add(dist[i]);
        }
        d.sort(Comparator.reverseOrder());
        
        for(int i = 0; i < dist.length; i++){
            for(int j = 0;  j < weak.length; j++){
                Arrays.fill(count[i][j], INF);
            }
        }
        
        int answer = INF;
        for(int i = 0; i < weak.length; i++){
            answer = Math.min(answer, dfs(n, weak.length, weak, 0, i, 0, 1));
        }
        
        return answer > d.size() ? -1 : answer; // answer의 최대값은 d.size(); d.size() 보다 크면 모든 지점들 보수 불가능
    }
    
    public static int dfs(int n, int w, int[] weak, int dIdx, int wIdx, int visited, int depth){
        if(dIdx >= d.size()){ // 모든 지점들을 보수하는게 불가능한 경우
            return d.size() + 1; // INF로 설정 시, 무한 루프 걸릴 수 있음; 그러므로 d.size() + 1반환; 어차피 answer의 최대값은 d.size()임
        }
        if(visited == (1 << w) - 1){
            count[dIdx][wIdx][visited] = depth;
            return depth;
        }
        if(count[dIdx][wIdx][visited] != INF){ // 이미 현재 dist가 현재 weak 지점에서 visited의 점들을 확인한 상태에서 모든 weak 지점들을 확인해서 보수한 적이 있음 -> DP
            return count[dIdx][wIdx][visited];
        }
                
        int curW = weak[wIdx];
        int curD = d.get(dIdx);
        int endA = (curW + curD) % n; // 시계 방향
        int endB = (curW - curD) >= 0 ? (curW - curD) : n + (curW - curD); // 반시계 방향
            
        // 시계 방향
        int tmpA = 0;
        if(reach[dIdx][wIdx][0] != 0){ // 이미 현재 dist가 현재 weak 지점에서 시계 방향으로 탐색한 적이 있을때
            tmpA = reach[dIdx][wIdx][0]; // tmpA 는 도달 가능한 점들을 비트로 저장함
        }else{
            for(int i = 0; i < w; i++){
                if(endA <= curW){
                    // 시계 방향으로 움직일때, 마지막 지점을 넘어갈때:
                    // ex: n = 12->0~11 지점들이 있음; 시작 지점 = 10,현재 dist가 7이고 시계 방향으로 돌면=>10, 11, 0, 1, 2, 3, 4, 5 지점들 확인
                    // curW = 10, endA = 5 -> 10~11, 0~5 지점들 확인
                    if((weak[i] >= 0 && weak[i] <= endA) || (weak[i] < n && weak[i] >= curW)){
                        tmpA = tmpA | (1 << i);
                    }
                }else{
                    if(weak[i] >= endA && weak[i] <= curW){
                        tmpA = tmpA | (1 << i);
                    }
                }
            }
            reach[dIdx][wIdx][0] = tmpA;
        }
        tmpA = tmpA | visited; // tmpA는 현재까지 확인한 점들 + 현재 dist가 현재 weak 지점에서 시작해서 도달 가능한 weak 지점들을 비트로 저장
        
        if(tmpA == (1 << w) - 1){ // 모든 지점들 확인 도달 가능
            count[dIdx][wIdx][visited] = depth;
            return count[dIdx][wIdx][visited];
        }else{
            for(int j = 0; j < w; j++){
                if((tmpA & (1 << j)) != (1 << j)){
                    //현재dist가 현재weak지점에서 visited의 점들을 확인한 상태에서 모든weak지점들을 확인할때 필요한 최소의 친구의 수 업데이트
                    count[dIdx][wIdx][visited] = Math.min(count[dIdx][wIdx][visited], dfs(n, w, weak, dIdx + 1, j, tmpA, depth + 1));
                }
            }
        }
        
        // 반시계 방향 -> 도달 지점들 확인 및 count[dIdx][wIdx][visited]를 확인하는 것은 시계 방향에서와 동일
        int tmpB = 0;
        if(reach[dIdx][wIdx][1] != 0){
            tmpB = reach[dIdx][wIdx][1];
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
            reach[dIdx][wIdx][1] = tmpB;
        }
        tmpB = tmpB | visited;
        
        if(tmpB == (1 << w) - 1){
            count[dIdx][wIdx][visited] = depth;
            return count[dIdx][wIdx][visited];
        }else{
            for(int j = 0; j < w; j++){
                if((tmpB & (1 << j)) != (1 << j)){
                    count[dIdx][wIdx][visited] = Math.min(count[dIdx][wIdx][visited], dfs(n, w, weak, dIdx + 1, j, tmpB, depth + 1));
                }
            }
        }
        
        
        return count[dIdx][wIdx][visited];
    }
    
}