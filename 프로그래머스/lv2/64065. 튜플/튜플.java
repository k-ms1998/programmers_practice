import java.util.*;

class Solution {
    
    static Set<Integer> set = new HashSet<>();
    
    public int[] solution(String s) {
        PriorityQueue<List<Integer>> queue = new PriorityQueue<>(new Comparator<List<Integer>>(){
            @Override
            public int compare(List<Integer> l1, List<Integer> l2){
                return l1.size() - l2.size();
            }
        });
        
        String tmp = "";
        boolean flag = false;
        for(int i = 1; i < s.length() - 1; i++){
            char c = s.charAt(i);
            if(c == '{'){
                flag = true;
            }else if(c == '}'){
                List<Integer> list = new ArrayList<>();
                String[] arr = tmp.split(",");
                for(int j = 0; j < arr.length; j++){
                    list.add(Integer.parseInt(arr[j]));
                }
                queue.offer(list);
                tmp = "";
                flag = false;
            }else{
                if(c == ',' && !flag){
                    continue;
                }
                tmp += String.valueOf(c);
            }
        }

        List<Integer> ansList = new ArrayList<>();
        while(!queue.isEmpty()){
            List<Integer> list = queue.poll();
            for(int num : list){
                if(!set.contains(num)){
                    ansList.add(num);
                    set.add(num);
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