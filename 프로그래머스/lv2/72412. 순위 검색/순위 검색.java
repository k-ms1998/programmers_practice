import java.util.*;

class Solution {
    
    static int n;
    // 개발언어, 직군, 경력, 소율푸드, 점수
    static Map<String, List<Integer>> data = new HashMap<>();
    
    public int[] solution(String[] info, String[] query) {
        n = info.length;
        for(int i = 0; i < n; i++){
            String cur[] = info[i].split(" ");
            String lang = cur[0];
            String position = cur[1];
            String experience = cur[2];
            String food = cur[3];
            int score = Integer.valueOf(cur[4]);
            
            String[][] keyArr = {{"-", lang}, {"-", position}, {"-", experience}, {"-", food}};
            combKey(keyArr, 0, "", score);
        }
        for(String key : data.keySet()){
            Collections.sort(data.get(key));
        }

        int qn = query.length;
        int answer[] = new int[qn];
        for(int i = 0; i < qn; i++){
            String[] q = query[i].split(" ");
            String lang = "";
            String position = "";
            String experience = "";
            String food = "";
            int idx = 0;
            for(int j = 0; j < q.length; j++){
                if(q[j].equals("and")){
                    continue;
                }
                if(idx == 0){
                    lang = q[j];
                }else if(idx == 1){
                    position = q[j];
                }else if(idx == 2){
                    experience = q[j];
                }else if(idx == 3){
                    food = q[j];
                }
                idx++;
            }
            
            int cnt = 0;
            int score = Integer.parseInt(q[q.length-1]);
            String key = lang+position+experience+food;
            // System.out.println(key);

            if(data.containsKey(key)){
                List<Integer> scores = data.get(key);
                int size = scores.size();
                int left = 0;
                int right = size - 1;
                while(left <= right){
                    int mid = (left + right) / 2;
                    if(scores.get(mid) < score){
                        left = mid + 1;
                    }else{
                        right = mid - 1;
                    }
                }
                cnt = size - left;
            }

            
            answer[i] = cnt;
        }

        return answer;
    }
    
    public static void combKey(String[][] keyArr, int depth, String key, int score){
        if(depth == 4){
            // System.out.println(key);
            if(data.containsKey(key)){
                List<Integer> values = data.get(key);
                values.add(score);
                data.put(key, values);
            }else{
                List<Integer> values = new ArrayList<>();
                values.add(score);
                data.put(key, values);
            }
            
            return;
        }
        
        combKey(keyArr, depth + 1, key + keyArr[depth][0], score);
        combKey(keyArr, depth + 1, key + keyArr[depth][1], score);
    }
}