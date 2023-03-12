import java.util.*;

class Solution {
    
    static int users;
    static int[] cnt;
    static int[] attempts;
    
    public int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        
        users = stages.length;
        cnt = new int[N + 2];
        attempts = new int[N + 2];
        for(int i = 0; i < users; i++){
            cnt[stages[i]]++;
            for(int j = 1; j <= stages[i]; j++){
                attempts[j]++;
            }
        }
        
        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2){
                double i1Rate = i1.attempted == 0 ? 0.0 : (1.0) * i1.cleared / i1.attempted;
                double i2Rate = i2.attempted == 0 ? 0.0 : (1.0) * i2.cleared / i2.attempted;

                if(i1Rate == i2Rate){
                    return i1.idx - i2.idx;
                }
                
                // double -> int로 형 변환해서 반환하면 오류 발생 => i2Rate, i1Rate를 비교해서 적절한 값 출력
                // i1Rate = i1의 실패율, i2Rate = i2의 실패율
                // 실패율이 더 큰건 반환 -> i2Rate - i1Rate -> i2Rate > i1Rate 이면 1 반환
                return i2Rate > i1Rate ? 1 : i2Rate == i1Rate ? 0 : -1;
            }
        });
        
        for(int i = 1; i < N + 1; i++){
            queue.offer(new Info(i, cnt[i], attempts[i]));
        }
        
        int i = 0;
        while(!queue.isEmpty()){
            answer[i] = queue.poll().idx;
            i++;
        }
        
        return answer;
    }
    
    public static class Info{
        int idx;
        int cleared;
        int attempted;
        
        public Info(int idx, int cleared, int attempted){
            this.idx = idx;
            this.cleared = cleared;
            this.attempted = attempted;
        }
    }
}