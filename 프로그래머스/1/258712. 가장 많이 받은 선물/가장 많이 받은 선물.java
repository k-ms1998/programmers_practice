import java.io.*;
import java.util.*;

/**
1. 이번 달까지 선물을 주고받은 기록을 통해 다음 달에 누가 선물을 많이 받을지 예측
    1-1. 서로 선물을 주고 받았음 -> 더 많이 준 사람이 다음달에 선물을 하나 받음
    1-2. 서로 준 적이 없거나 준 횟수가 같으면 -> 선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 하나 받음
        1-2-1. 서로 준 횟수랑 준 개수랑 다름
        1-2-2. 선물 지수 = 서로 주고 받은 선물의 개수의 차이
        1-2-3. 두 사람의 선물 지수도 같으면 다음 달에 선물을 주고받지 않는다
2. 각 사람마다 서로 선물을 주고 받은 횟수 저장
    2-1. info[a][b] = a->b로 선물을 준 횟수
3. 각 사람마다 선물을 총 몇개 주고 몇개 받았는지 확인
    3-1. gifted = 내가 다른 사람들에게 준 선물의 개수
    3-2. received =내가 다른 사람들로부터 받은 선물의 개수
*/
class Solution {
    
    static int size;
    static Map<String, Integer> nameToInt = new HashMap<>();
    static int[][] info;
    static int[] gifted;
    static int[] received;
    
    static StringTokenizer st;
    static int[] nextMonth;
    
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;
        
        size = friends.length;
        for(int idx = 0; idx < size; idx++){
            nameToInt.put(friends[idx], idx);
        }
        
        info = new int[size][size];
        gifted = new int[size];
        received = new int[size];
        nextMonth = new int[size];
        for(int idx = 0; idx < gifts.length; idx++){
            st = new StringTokenizer(gifts[idx]);
            int from = nameToInt.get(st.nextToken());
            int to = nameToInt.get(st.nextToken());
            gifted[from]++;
            received[to]++;
            info[from][to]++;
        }
        
        
        for(int from = 0; from < size; from++){
            for(int to = from + 1; to < size; to++){
                if(info[from][to] != info[to][from]){ // 서로 선물을 주고 받은 적이 없는지 확인
                    if(info[from][to] > info[to][from]){
                        nextMonth[from]++;
                    }else{
                        nextMonth[to]++;
                    } 
                }else{ // 서로 선물을 주고 받은 적이 있는 경우
                    int diffA = gifted[from] - received[from];
                    int diffB = gifted[to] - received[to];
                    if(diffA > diffB){
                        nextMonth[from]++;
                    }else if(diffA < diffB){
                        nextMonth[to]++;
                    }else{
                        continue;
                    }
                }
            }
        }
        
        
        for(int idx = 0; idx < size; idx++){
            answer = Math.max(answer, nextMonth[idx]);
        }
        
        return answer;
    }
}