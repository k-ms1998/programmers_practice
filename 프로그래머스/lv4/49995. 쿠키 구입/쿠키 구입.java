/**
Solution: 누적합 +  TwoPointer
1. 바구니들의 누적합 구하기
    -> total[3] = cookie[0] + cookie[1] + cookie[2] = total[2] + cookie[2]
        total[2] = cookie[0] + cookie[1]
        total[1] = cookie[0]
    -> 2번 바구니부터 4번 바구니까지의 합 = cookie[1] + cookie[2] + cookie[3]
                                    = total[4] - total[1]
                                    = (cookie[0] + cookie[1] + cookie[2] + cookie[3]) - (cookie[0])
2. i ~ m의 합과 m+1 ~ r의 합을 구해야함
    -> i은 1부터 시작, r은 n부터 시작
        -> 투포인터를 통해서 m 구하기
            -> 매번 투포인터로 m을 구한후, i~m의 합과 m+1~r의 합을 구해서 같은지 확인
            -> 같으면, answer를 업데이트하고 break
3. 현재 i~r의 합이 2*answer보다 작으면 건너뛰기
    -> i~r의 합을 full이라고 했을때, 문제의 조건을 만족시키기 위해서는 i~m == m+1~r == full/2
    -> 이때, full/2의 값이 answer보다 작거나 같으면, 최대로 담을 수 있는 쿠키의 수가 answer보다 커질 수 없음
        -> 그러므로, full <= 2*answer 이면 건너뛰기
*/
class Solution {
    
    static int n;
    static int[] total;
    
    public int solution(int[] cookie) {
        int answer = 0;
        
        n = cookie.length;
        total = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            total[i] = total[i-1] + cookie[i-1];
        }
              
        for(int i = 1; i < n + 1; i++){
            for(int r = n; r >= 1; r--){
                if(i >= r){
                    break;
                }
                
                int full = total[r] - total[i-1];
                if(full <= 2*answer){ // full <= 2*answer 이면 현재 answer 보다 더 많은 양의 쿠기를 담는 것이 불가능
                    break;
                }
                
                // 투 포인터로 현재 i,r의 값에 대해서 m의 위치 찾아주기
                int left = i;
                int right = r;
                while(left <= right){
                    int m = (left + right) / 2;
                    if(m < i || m + 1 > r){
                        break;
                    }
                    
                    int sumA = total[m] - total[i - 1];
                    int sumB = total[r] - total[(m + 1) - 1];
                    
                    if(sumA == sumB){
                        answer = Math.max(answer, sumA);
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

    
}