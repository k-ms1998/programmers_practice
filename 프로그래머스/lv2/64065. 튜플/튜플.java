import java.util.*;

class Solution {
    
    static boolean[] used;
    
    public int[] solution(String s) {
        PriorityQueue<List<Integer>> queue = new PriorityQueue<>(new Comparator<List<Integer>>(){
            @Override
            public int compare(List<Integer> l1, List<Integer> l2){
                return l1.size() - l2.size();
            }
        }); // 튜플들을 크기가 작은 순으로 정렬
        
        int maxNum = 0;
        String tmp = ""; // 현재 튜플을String으로 저장
        boolean flag = false;
        for(int i = 1; i < s.length() - 1; i++){
            char c = s.charAt(i);
            if(c == '{'){
                flag = true; // 튜플의 시작을 알려줌
            }else if(c == '}'){ // 튜플의 끝을 알려줌
                List<Integer> list = new ArrayList<>();
                String[] arr = tmp.split(","); // 튜플 안에 있는 원소들 가져오기
                for(int j = 0; j < arr.length; j++){
                    maxNum = Math.max(maxNum, Integer.parseInt(arr[j]));
                    list.add(Integer.parseInt(arr[j]));
                }
                queue.offer(list);
                tmp = "";
                flag = false;
            }else{
                if(!flag){
                    continue;
                }
                tmp += String.valueOf(c);
            }
        }

        used = new boolean[maxNum + 1];
        List<Integer> ansList = new ArrayList<>();
        while(!queue.isEmpty()){
            List<Integer> list = queue.poll();
            for(int num : list){
                if(!used[num]){
                    ansList.add(num);
                    used[num] = true;
                }
            }
        }

        int[] answer = new int[ansList.size()];
        int idx = 0;
        for(int num : ansList){
            answer[idx] = num;
            idx++;
        }
        
        return answer;
    }
}