import java.io.*;
import java.util.*;

class Solution {
    
    static String tmp = "";
    static boolean found = true;
    
    public int[] solution(long[] numbers) {
        
        int[] answer = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++){
            found = true;
            long curNum = numbers[i];
            
            int oneCnt = 0;            
            Stack<Integer> stack = new Stack<>();
            while(curNum > 0){
                if(curNum % 2== 0){
                    stack.push(0);
                }else{
                    stack.push(1);
                    oneCnt++;
                }
                curNum /= 2;
            }
            
            int stackSize = stack.size();
            int nodeCnt = 1;
            while(nodeCnt <= stackSize){
                nodeCnt *= 2;
            }
            nodeCnt--;

            // System.out.println("nodeCnt = " + nodeCnt + ", stackSize = " + stackSize);
            
            int[] binary = new int[nodeCnt];
            for(int j = 0; j < nodeCnt - stackSize; j++){
                binary[j] = 0;
            }
            
            int idx = nodeCnt - stackSize;
            while(!stack.isEmpty()){
                binary[idx] = stack.pop();
                idx++;
            }
            
//             for(int j = 0; j < nodeCnt; j++){
//                 System.out.print(binary[j]);
//             }
            System.out.println();
            int left = 0;
            int right = binary.length - 1;
            checkAnswer(left, right, binary);
            
            answer[i] = found ? 1 : 0;   
            // System.out.println("----------");
        }
        
        return answer;
    }
    
    public static int checkAnswer(int left, int right, int[] binary){
        // System.out.println("left = " + left + ", right = " + right);
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

    public static void inOrder(int idx, int[] tree){
        if(idx >= tree.length){
            return;
        }
        
        inOrder(2*idx, tree);
        tmp += (tree[idx]);
        inOrder(2*idx + 1, tree);
    }
}