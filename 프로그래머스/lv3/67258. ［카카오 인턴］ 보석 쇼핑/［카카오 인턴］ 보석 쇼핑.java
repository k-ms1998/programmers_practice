import java.util.*;

/**
슬라이딩 윈도우/투 포인터
1. left, right 모두 0에서 시작
2. right 위치에 있는 보석을 추가 (bought에 추가, tmp 업데이트; bought는 지금까지 구매한 보석들을 담고 있고, tmp에는 각 보석마다 구매한 갯수를 저장하고 있음)
3. idx의 크기 == 보석 종류의 갯수, 그리므로 bought의 크기가 idx의 크기랑 같으면, 모든 종류의 보석을 1개 이상씩 구매했음
4. 같아 졌을 경우, answer를 업데이트 시켜주고, left의 크기를 1 오른쪽으로 옮김
    -> 이때, 옮기게 되면 현재 위치에 있는 left의 보석은 구매 목록에서 제거 되기 때문에, tmp를 1 감소 시킴
    -> tmp에서 현재 보석의 갯수를 1감소 시켰을때 갯수가 0이 되면, 더 이상 해당 종류의 보석은 구매 목록에 하나도 없으므로 bought에서도 제거 시켜줌
5. right가 끝까지 갈때까지 반복
6. 마지막으로, 현재 구매 목록(bought)를 검사
    -> 이때, 더 이상 각 종류의 보석을 하나 이상 갖고 있지 않을때까지 left를 이동시켜서 범위를 좁혀줌
*/
class Solution {
    
    static int n;
    static Map<String, Integer> idx = new HashMap<>();
    static int[] total;
    static int[] answer = new int[2];
    
    public int[] solution(String[] gems) {
        n = gems.length;
        answer[0] = 1;
        answer[1] = n;
        
        total = new int[n];
        int curIdx = 0;
        for(int i = 0; i < n; i++){
            String gem = gems[i];
            if(!idx.containsKey(gem)){
                total[curIdx]++;
                idx.put(gem, curIdx);
                curIdx++;
            }else{
                int tmp = idx.get(gem);
                total[tmp]++;
            }
        }
        
        Set<String> bought = new HashSet<>();
        int[] tmp = new int[idx.size()];
        int left = 0;
        int right = 0;
        while(right < n){
            if(bought.size() == idx.size()){
                if(right - (left + 1) < answer[1] - answer[0]){
                    answer[0] = left + 1;
                    answer[1] = right;   
                }
                
                int leftIdx = idx.get(gems[left]);
                if(tmp[leftIdx] >= 1){
                    tmp[leftIdx]--;
                    if(tmp[leftIdx] == 0){
                        bought.remove(gems[left]);
                    }
                }
                
                left++;
            }else{
                int rightIdx = idx.get(gems[right]);
                bought.add(gems[right]);
                tmp[rightIdx]++;
                right++;
            }   

            
        }
        while(true){
            // System.out.println(bought + ", left = " + left + ", right = " + right);
            if(bought.size() != idx.size()){
                break;
            }
            if(bought.size() == idx.size()){
                if(right - (left+1) < answer[1] - answer[0]){
                    answer[0] = left + 1;
                    answer[1] = right;   
                }
                int leftIdx = idx.get(gems[left]);
                if(tmp[leftIdx] >= 1){
                    tmp[leftIdx]--;
                    if(tmp[leftIdx] == 0){
                        bought.remove(gems[left]);
                    }
                }
                left++;
            }
        }
        
        
        return answer;
    }
}