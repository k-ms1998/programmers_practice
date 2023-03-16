/**
완전탐색/조합론:
과녁: 0~10 -> 11개, 각 과녁을 최대 맞출수 있는 획수: n (1 <= n <= 10)
1. 각 점수마다 0~n 개씩 맞출수 있음. 완전 탐색으로 각 점수를 0~n개 맞출수 있는 조합 찾음
2. 모든 조합에 대해서 점수 계산
    -> 어피치와 라이언 둘 다 0번 맞춘 점수는 건너뛰고, 똑같은 횟수 만큼 어피치 한테, 그 외에는 더 많은 맞춘 사람한테 점수 부여
    -> 최종 점수차인 diff보다 크면 최종 조합(ryan) 업데이트
    -> diff 가 같으면, 더 낮은 점수를 많이 맞춘 조합으로 업데이트
3. 최종적으로 diff가 0 이면,diff 가 업데이트 된적 없음->라이언이 어피치를 이긴 조합 존재 X->라이언이 어피치를 이길 방법 X
*/
class Solution {
    
    static int[] ryan =  new int[11];
    static int diff;
    
    public int[] solution(int n, int[] info) {
        
        int[] tmp = new int[11];
        findAnswer(0, n, 0, tmp, info);

        if(diff == 0){
            int[] answer = new int[1];
            answer[0] = -1;
            return answer;
        }else{
            return ryan;
        }
    }
    
    public static void findAnswer(int idx, int n, int used, int[] scores, int[] apeach){
        if(idx == 11){
            if(n != used){
                return;
            }

            calculateScore(apeach, scores);
            return;
        }
        
        int[] tmp = new int[11];
        for(int i = 0; i < 11; i++){
            tmp[i] = scores[i];
        }
        for(int i = 0; i <= n - used; i++){
            tmp[idx] = i;
            findAnswer(idx + 1, n, used + i, tmp, apeach);
        }
    }
    
    public static void calculateScore(int[] a, int[] r){
        int scoreA = 0;
        int scoreR = 0;
        for(int i = 0; i < 11; i++){
            if(a[i] == 0 && r[10 - i] == 0){ // 둘 다 0번을 맞췄으면 무시
                continue;
            }
            if(a[i] >= r[10 - i]){ // 어치피는 0번 인덱스가 10점, 라이언은 0번 인덱스가 0점인걸 감안
                scoreA += (10-i);
            }else{
                scoreR += (10-i);
            }
        }
        
        int curDiff = scoreR - scoreA;
        if(diff < curDiff){
            diff = curDiff;
            for(int i = 0; i < 11; i++){
                ryan[10 - i] = r[i];
            }
        }else if(diff == curDiff){ //현재 조합과 점수 차이가 같으면, 가장 낮은 점수를 더 많이 맞힌 경우가 최종 정답이 됨
            for(int i = 0; i < 11; i++){
                // r은 0점이 앞에 있음
                if(r[i] > ryan[10 - i]){
                    for(int j = 0; j < 11; j++){
                        ryan[10 - j] = r[j];
                    }
                    break;
                }
            }
        }
    }
}