import java.io.*;
import java.util.*;
/**
포화이진트리 문제 -> 높이가 n이면, 2^n - 1 개의 노드를 가진 이진 트리
1. 값을 이진수로 변환
2. 해당 값을 포화이진트리로 변환 -> 해당 이진수를 트리로 만들었을때, 남는 노드 만큼을 이진수 앞에 추가
    -> 만약, 이진수가 101010 -> 높이가 3인 트리 필요 (노드의 갯수 = 7개 (2^3 - 1))
        -> 101010은 총 6개의 노드만 필요하기 때문에, 총 7개가 되도록 앞에 0 추가 -> 0101010 이 됨(앞에 0을 얼마나 많이 추가해도, 십진수의 값은 변하지 않음)
3. 해당 이진수값이 포화 이진트리로 표현이 가능한지 확인
    -> 현재 노드는 (left + right) / 2
        -> 만약 현재 노드가 0인데, 자식 노드가 1이면 리프 노드가 아닌 노드가 더미 노드이므로, 표현 불가능
        
4. 중위 순회 특징 이용 
->   1
    2 3 이라는 트리를 중위 순회로 방문하면, 2->1->3 으로 방문 (x좌표로 완쪽에서 오른쪽으로 차례대로 방문)
-> 만약에, 중위 순회로 방문한 트리가 방문 순서가 [1, 2, 3, 4, 5, 6, 7]
    -> 전체의 루트 노드는 4 => 왼쪽 서브 트리 = [1, 2, 3] & 오른쪽 서브 트리 = [5, 6, 7]
        => [1, 2, 3]의 루트 노드는 2 & [5, 6, 7]의 루트 노드는 6
*/
class Solution {
    
    static String tmp = "";
    static boolean found = true;
    
    public int[] solution(long[] numbers) {
        
        int[] answer = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++){
            found = true;
            long curNum = numbers[i];
                    
            /*
            이진수로 변환
            */
            Stack<Integer> stack = new Stack<>();
            while(curNum > 0){
                if(curNum % 2== 0){
                    stack.push(0);
                }else{
                    stack.push(1);
                }
                curNum /= 2;
            }
            
            /*
            트리의 총 노드 갯수 구하기
            */
            int stackSize = stack.size();
            int nodeCnt = 1;
            while(nodeCnt <= stackSize){
                nodeCnt *= 2;
            }
            nodeCnt--;
            
            /*
            포화이진트리 만들기 -> 이진수 앞에 0 남는 노드의 갯수만큼 0추가
            */
            int[] binary = new int[nodeCnt];
            for(int j = 0; j < nodeCnt - stackSize; j++){
                binary[j] = 0;
            }
            int idx = nodeCnt - stackSize;
            while(!stack.isEmpty()){
                binary[idx] = stack.pop();
                idx++;
            }
            
            int left = 0;
            int right = binary.length - 1;
            checkAnswer(left, right, binary);
            
            answer[i] = found ? 1 : 0;   
        }
        
        return answer;
    }
    
    /*
    표현 가능한지 확인
    1. 리프 노드까지 내려가기
    2. 리프 노드에서 올라오면서, 현재 노드와 부모 노드의 값 확인
    3. 현재 노드가 1인데, 부모 노드가 0이면 표현 불가능 -> 부모 노드가 0이면, 리프 노드가 아닌 노드가 더미 노드 이므로 표현 불가능
    4. 중위 순회 특징 이용 
    ->   1
        2 3 이라는 트리를 중위 순회로 방문하면, 2->1->3 으로 방문 (x좌표로 완쪽에서 오른쪽으로 차례대로 방문)
    -> 만약에, 중위 순회로 방문한 트리가 방문 순서가 [1, 2, 3, 4, 5, 6, 7]
        -> 전체의 루트 노드는 4 => 왼쪽 서브 트리 = [1, 2, 3] & 오른쪽 서브 트리 = [5, 6, 7]
            => [1, 2, 3]의 루트 노드는 2 & [5, 6, 7]의 루트 노드는 6
    */
    public static int checkAnswer(int left, int right, int[] binary){
        int mid = (left + right) / 2;
        if(left >= right){
            return binary[mid];
        }
        
        int leftChild = checkAnswer(left, mid - 1, binary);
        int rightChild = checkAnswer(mid + 1, right, binary);
        
        if((leftChild == 1 || rightChild == 1) && binary[mid] == 0){
            found = false;
        }
        
        return binary[mid];
    }
}