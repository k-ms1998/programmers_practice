import java.util.*;

/**
1. 항목의 조합을 key로 설정, value는 점수
ex) java,backend,junior,chicken,150을 입력 받으면 -> (javabackendjuniorchicken, 150)
이때, 각 항목에 대해서 고려하지 않는 경우에도 150점을 반환해줘야함
    -> (-, backend, junior, chicken), (-, -, junior, chicken), (java, -, junior, chicken) 등등 도 모두 150점 반환
    -> 해당 값들을 key로 해서 (key, 150) 저장 해줌
    
2. query문 추출해서, 추출한 쿼리문이 key 가 됨 -> 해당 key에 저장된 모든 점수들을 가져와서, 목표 점수를 넘는 개수 확인
3. 시간 단축을 위해, 데이터를 입력 받고, 정렬해줌. 2에서 점수를 찾을때, 이분 탐색으로 찾아줌
*/
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
                
                // left는 목표 점수(score) 보다 크거나 같은 값의 최초 위치 -> left보다 오른쪽에 있는 값들은 모두 score보다 크거나 같음 -> 그러므로 해당 값들의 개수는 size - left
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