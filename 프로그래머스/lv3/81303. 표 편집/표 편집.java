import java.util.*;

/**
우선순위 큐

1. idx 기준, 왼쪽에 있는 숫자들, 오른쪽에 있는 숫자들을 각각 우선순위 큐에 저장
    -> 왼쪽은, pop했을때 idx바로 이전의 숫자가 나와야 하기 때문에 MaxHeap (left에는 0 ~ idx-1 저장되어 있음)
    -> 오른쪽은, pop했을때 idx바로 다음의 숫자가 나와야 하기 때문에 MinHeap (right에는 idx+1 ~ n-1 저장되어 있음)
    
2. 명령어 'U X' -> idx의 값을 감소시킴 -> X만큼 left에서 pop; pop할때마다, idx의 값을 right에 넣어주고, idx의 값을 pop한 값으로 업데이트
   명령어 'D X' -> idx의 값을 증가시킴 -> X만큼 right에서 pop; pop할때마다, idx의 값을 left에 넣어주고, idx의 값을 pop한 값으로 업데이트
    -> 값을 pop할때 마다 idx의 값을 갱신하고, 반대쪽 우선순위 큐(left->right, right->left)에 넣어줌으로써, 항상 left는 idx의 왼쪽 값을, right는 idx의 오른쪽 값들 유지

3. 명렁어 'C' -> 현재 idx의 행 제거 -> idx를 right의 첫번째 값으로 업데이트; 없으면 left의 첫번째 값으로 업데이트; 
    이때, 현재 idx를 제거하기 때문에, idx를 left 또는 right에 추가 X -> 그러므로, left랑 right는 언제나 삭제되지 않은 행들만 저장
    다만, 삭제한 값을 북구할수도 있기 떄문에, deleted에 저장
        -> deleted는 스텍으로, 가장 최근에 삭제한 값이 가장 먼저 pop되도록 해줌
4. 명령어 'Z' -> stack에서 값을 pop -> pop된 값은 가장 최든에 삭제된 행 -> 현재 idx에는 변화가 없기 때문에, pop된 값이 현재 idx 기준 왼쪽인지 오른쪽인지에 따라서 left 또는 right에 추가 -> 추가함으로써, pop된 행은 더 이상 삭제되지 않은 행이라는 것을 알수 있음
*/
class Solution {
    
    public String solution(int n, int k, String[] cmd) {
        boolean[] isDeleted = new boolean[n];
        int idx = k;
        Stack<Integer> deleted = new Stack<>();
        PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();
        for(int i = 0; i < k; i++){
            left.offer(i);
        }
        for(int i = k + 1; i < n; i++){
            right.offer(i);
        }
        
        for(int i = 0; i < cmd.length; i++){
            String c = cmd[i];               
            if(c.length() > 1){
                String[] arr = c.split(" ");
                int move = Integer.parseInt(arr[1]);
                if(arr[0].equals("D")){
                    for(int j = 0; j < move; j++){
                        left.offer(idx);
                        idx = right.poll();
                    }
                }else{
                    for(int j = 0; j < move; j++){               
                        right.offer(idx);
                        idx = left.poll();
                    }
                }
            } else{
                if(c.equals("C")){
                    isDeleted[idx] = true;
                    deleted.push(idx);
                    if(!right.isEmpty()){
                        idx = right.poll();
                    }else{
                        idx = left.poll();
                    }
                }else{
                    if(!deleted.isEmpty()){
                        int restore = deleted.pop();
                        isDeleted[restore] = false;
                        if(restore > idx){
                            right.offer(restore);
                        }else if (restore < idx){
                            left.offer(restore);
                        }
                    }
                }
            }
        }
        
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < n; i++){
            ans.append(isDeleted[i] ? "X" : "O");
        }
        
        return ans.toString();
    }
}