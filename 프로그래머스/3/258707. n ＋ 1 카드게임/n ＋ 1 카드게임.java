import java.io.*;
import java.util.*;

/**
1. 다음과 같이 게임을 진행함
    1-1. 처음에 카드 뭉치에서 앞의 n/3장을 가져감 & 동전 coin개를 가지고 있음
    1-2. 각 라운드가 시작할떄 카드를 두장 뽑음 -> 더 이상 카드 뭉치에 남은 카드가 없으면 게임 끝
        1-2-1. 카들 뽑았을때, 동전을 써서 카드를 가져오던가 아니면 동전을 안쓰고 버릴 수 있음
    1-3. 카드에 적힌 수의 합이 n + 1이 되도록 카드 두 장을 내고 다음 라운드로 진행
        1-3-1. 만약 카드 두 장을 낼 수 없으면 종료
*/
class Solution {
    
    static int size;
    static int[] used;
    static int[] inHand;
    static int answer = 1;
    static int target;
    
    public int solution(int coin, int[] cards) {
        size = cards.length;
        used = new int[size + 1];
        target = size + 1;
        for(int idx = 0; idx < size / 3; idx++){
            used[cards[idx]] = 1;
        }
        // printUsed();
      
        dfs(coin, 1, size / 3, cards);
        
        return answer;
    }
    
    public static void dfs(int coin, int round, int cardIdx, int[] cards){
        if(cardIdx + 1 > size){
            answer = Math.max(answer, round);
            return;
        }
        answer = Math.max(answer, round);
        
        int cardA = cards[cardIdx];
        int cardB = cards[cardIdx + 1];
        used[cardA] = 2;
        used[cardB] = 2;
        
        // printUsed();
        // System.out.println("coin=" + coin + ", round=" + round + ", cardA=" + cardA + ", cardB=" + cardB);
        
        int inHand = checkInHand();
        int coinUsed = 0;
        if(inHand != -1){
            used[inHand] = 0;
            used[target - inHand] = 0;
            dfs(coin - coinUsed, round + 1, cardIdx + 2, cards);

            return;
        }
        
        int inHandUnPaidOne = checkInHandUnPaidOne();
        int targetUnPaidOneIdx = Math.abs(target - inHandUnPaidOne);
        if(inHandUnPaidOne != -1 && coin >= 1){
            used[inHandUnPaidOne] = 0;
            used[targetUnPaidOneIdx] = 0;
            dfs(coin - 1, round + 1, cardIdx + 2, cards);
            
            return;
        }
        
        int inHandUnPaid = checkInHandUnPaid();
        int inHandUnPaidIdx = Math.abs(target - inHandUnPaid);
        if(inHandUnPaid != -1 && coin >= 2){
            used[inHandUnPaid] = 0;
            used[inHandUnPaidIdx] = 0;
            dfs(coin - 2, round + 1, cardIdx + 2, cards);
            return;
        }
    }
    
    public static int checkInHand(){
        for(int idx = 1; idx < size + 1; idx++){
            int targetIdx = Math.abs(target - idx);
            if(used[idx] == 1 && used[targetIdx] == 1){
                return idx;
            }
        }
        
        return -1;
    }

    public static int checkInHandUnPaid(){
        for(int idx = 1; idx < size + 1; idx++){
            int targetIdx = Math.abs(target - idx);
            if(used[idx] == 2 && used[targetIdx] == 2){
                return idx;
            }
        }
        
        return -1;
    }
    
    public static int checkInHandUnPaidOne(){
        for(int idx = 1; idx < size + 1; idx++){
            int targetIdx = Math.abs(target - idx);
            if(used[idx] == 2 && used[targetIdx] == 1){
                return idx;
            }
        }
        
        return -1;
    }
    
    public static int checkInHand(int nextCard){
        int targetIdx = Math.abs(target - nextCard);
        if(used[targetIdx] == 1){
            return targetIdx;
        }
        
        return -1;
    }
    
    public static void printUsed(){
        for(int idx = 1; idx < size + 1; idx++){
            System.out.print(used[idx] + " ");
        }
        System.out.println();
    }
}