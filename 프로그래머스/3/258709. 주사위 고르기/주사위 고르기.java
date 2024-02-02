import java.io.*;
import java.util.*;

/**
1. n개의 주사위 존재, 각 주사위에 쓰인 수의 구성은 모두 다르다
2. A가 먼저 n/2개의 주사위를 가져가면, 나머지는 B가 가져감
3. 각각 가져간 주사위를 모두 굴린 뒤, 나온 수들을 모두 합해 점수를 계산 -> 점수가 더 큰 쪽이 우승, 같으면 무승부
4. A가 승리할 확률이 가장 높도록 주사위를 가져가야함
    4-1. A가 주사위 n/2개를 가져갈 수 있는 모든 경우 조합 확인 (비트마스킹)
    4-2. 자동으로 나머지 주사위가 B가 가져갈 수 있는 주사위 들
    4-3. 각 주사위마다 만들 수 있는 숫자의 합 구해서 각각 리스트에 저장
    4-4. A의 조합을 저장한 리스트의 각 원소랑 B의 조합을 저장한 리스트의 각 원소를 모두 비교해서 이기는 횟수 구하기
        4-4-1. 이때, 시간 초과를 예방하기 위해 정렬 후 진행
        4-4-2. 정렬 후, 현재 A의 원소가 B의 원소보다 작거나 같아지는 원소를 만나면 탐색 멈춤
        4-4-3. 현재까지 확인한 B의 원소는 현재 A의 원소를 이길 수 있음 -> winCounter 업데이트
        4-4-4. 다음 B의 원소를 확인할때 마지막으로 확인한 B의 원소부터 확인
*/
class Solution {
    
    static int n; // 2의 배수
    static int[] selectedA;
    static int[] selectedB;
    static List<Integer> listA;
    static List<Integer> listB;
    static int[] wins;
    static int answerBit = 0;
    static int answerWins = 0;
    
    public int[] solution(int[][] dice) {
        n = dice.length;
        selectedA = new int[n / 2];
        selectedB = new int[n / 2];
        wins = new int[1 << n];
        
        combination(0, 0, 0, dice);
        
        int[] answer = new int[n/2];
        int idx = 0;
        for(int i = 0; i < n; i++){
            int compareBit = (1<< i);
            if((answerBit & compareBit) == compareBit){
                answer[idx] = i + 1;
                idx++;
            }
        }
        
        return answer;
    }
    
    public static void combination(int sIdx, int eIdx, int bitA, int[][] dice){
        if(sIdx == n/2){
            int bitB = bitA ^ ((1 << n) - 1);
            int idxA = 0;
            int idxB = 0;
            for(int i = 0; i < n; i++){
                int compareBit = (1<< i);
                if((bitA & compareBit) == compareBit){
                    selectedA[idxA] = i;
                    idxA++;
                }
                if((bitB & compareBit) == compareBit){
                    selectedB[idxB] = i;
                    idxB++;
                }
            }
            
            listA = new ArrayList<>();
            listB = new ArrayList<>();
            permutationA(0, 0, dice);
            permutationB(0, 0, dice);
            Collections.sort(listA);
            Collections.sort(listB);
            int winCounter = 0;
            int prevIdx = 0;
            for(int i = 0; i < listA.size(); i++){
                int curA = listA.get(i);
                for(int j = prevIdx; j < listB.size(); j++){
                    int curB = listB.get(j);
                    if(curA <= curB){
                        prevIdx = j;
                        break;
                    }
                    if(j == listB.size() - 1){
                        prevIdx = listB.size();
                    }
                }
                winCounter += (prevIdx - 1);
            }
            if(answerWins < winCounter){
                answerWins = winCounter;
                answerBit = bitA;
            }
            return;
        }
        if(eIdx == n){
            return;
        }
        
        selectedA[sIdx] = eIdx;
        combination(sIdx + 1, eIdx + 1, bitA | (1 << eIdx) ,dice);
 
        selectedA[sIdx] = eIdx;
        combination(sIdx, eIdx + 1, bitA, dice);
    }
    
    public static void permutationA(int dIdx, int total, int[][] dice){
        if(dIdx == n/2){
            listA.add(total);
            return;
        }
        
        for(int i = 0; i < 6; i++){
            permutationA(dIdx + 1, total + dice[selectedA[dIdx]][i],  dice);
        }
    }
    
    public static void permutationB(int dIdx, int totalB, int[][] dice){
        if(dIdx == n/2){
            listB.add(totalB);
            return;
        }
        
        for(int i = 0; i < 6; i++){
            permutationB(dIdx + 1, totalB + dice[selectedB[dIdx]][i], dice);
        }
    }
}