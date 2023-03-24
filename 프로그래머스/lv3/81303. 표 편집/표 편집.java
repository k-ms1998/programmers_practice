import java.util.*;

class Solution {
    
    static boolean[] isDeleted;
    static int idx;
    static Stack<Integer> deleted = new Stack<>();
    
    public String solution(int n, int k, String[] cmd) {
        isDeleted = new boolean[n + 100];
        idx = k;
        
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
            // System.out.println(left);
            // System.out.println(right);
//             if(c.length() > 1){
//                 String[] arr = c.split(" ");
//                 int move = Integer.parseInt(arr[1]);
//                 if(arr[0].equals("D")){
//                     for(int j = 0; j < move; j++){
//                         idx++;
//                         while(isDeleted[idx]){
//                             idx++;
//                         }
//                     }
//                 }else{
//                     for(int j = 0; j < move; j++){               
//                         idx--;
//                         while(isDeleted[idx]){
//                             idx--;
//                         }
//                     }
//                 }
//             }else{
//                 if(c.equals("C")){
//                     int tmpIdx = idx;
//                     isDeleted[idx] = true;
//                     deleted.push(idx);
                    
//                     boolean isEnd = false;
//                     while(isDeleted[idx]){
//                         idx++;
//                         if(idx >= n){
//                             isEnd = true;
//                             break;
//                         }
//                     }
                    
//                     if(isEnd){
//                         idx = tmpIdx;
//                         while(isDeleted[idx]){
//                             idx--;
//                             if(idx < 0){
//                                 idx = 0;
//                                 break;
//                             }
//                         }
//                     }
//                 }else{
//                     if(!deleted.isEmpty()){
//                         int restore = deleted.pop();
//                         isDeleted[restore] = false;
//                     }
//                 }
//             }            
            
            // System.out.println(idx);
            // for(int j = 0; j < n; j++){
            //     System.out.print(isDeleted[j] ? "X " : "O ");
            // }
            // System.out.println();
        }
        
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < n; i++){
            ans.append(isDeleted[i] ? "X" : "O");
        }
        
        return ans.toString();
    }
}