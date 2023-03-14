import java.util.*;

/**
1. (k, v) -> (차량번호, List<입차, 출차 정보>) 로 데이터 저장. 시간 계산에 편하기 위해, 시간->분으로 변환해서 저장
2. key들을 가져와서 오름차순으로 정렬
3. 각 key들을 순차적으로 돌면서 value 가져오기
4. 가져온 value들로 총 주차 시간계산
    -> 입차하면 스텍에 넣고, 출차하면 pop해서 주차한 시간 총 계산
    -> 모든 value를 돌았는 스텍에 값이 남아 있으면, 23:59전에 출차한 적이 없기 때문에, 23:59에 출차를 햇다고 간주하고 계산
5. 총 주차 시간에 대해서 요금 계산
    -> 단위 시간에 대해서 요금 계산을 할때, 기본 시간을 제외한 시간이 단위 시간으러 나누었을때 나누어 떨어지지 않으면 올림해서 계산해야 하므로, 단위 시간 만큼 더해준 다음에 요금 계산
6. key들에 대해서 오름차순으로 정렬했기 때문에, 요금 계산한 순서대로 answer에 값 저장
*/
class Solution {
    
    static Map<String, List<Info>> data = new HashMap<>();
    static final int END_OF_DAY = 23*60 + 59;
    
    public int[] solution(int[] fees, String[] records) {
        for(int i = 0; i < records.length; i++){
            String[] cur = records[i].split(" ");
            String timeStr = cur[0];
            String key = cur[1];
            String enter = cur[2];
            
            // 총 시간을 분으로 변환
            String[] timeArr = timeStr.split(":");
            int hour = Integer.parseInt(timeArr[0]);
            int minute = Integer.parseInt(timeArr[1]);
            int time = 60*hour + minute;
            
            List<Info> value;
            if(data.containsKey(key)){
                value = data.get(key);
            }else{
                value = new ArrayList<>();
            }
            
            value.add(new Info(time, enter.equals("IN") ? 1 : -1));
            data.put(key, value);
        }
        // key로 정렬
        List<String> keys = new ArrayList<>();
        for(String key: data.keySet()){
            keys.add(key);
        }
        Collections.sort(keys);
        
        int[] answer = new int[keys.size()];
        int idx = -1;
        for(String key: keys){
            idx++;
            Stack<Integer> stack = new Stack<>();
            List<Info> value = data.get(key);
            int totalFee = 0;
            int totalTime = 0;
            for(Info info : value){
                if(info.enter == 1){
                    stack.push(info.time);
                }else{
                    if(!stack.isEmpty()){
                        int enteredTime = stack.pop();
                        int exitedTime = info.time;
                        totalTime += (exitedTime - enteredTime);
                    }
                }
            }
            if(!stack.isEmpty()){
                int enteredTime = stack.pop();
                int exitedTime = END_OF_DAY;
                totalTime += (exitedTime - enteredTime);
            }
            
            // 기본 시간,요금 계산
            totalFee += fees[1];
            totalTime -= fees[0];
            if(totalTime > 0){
                // 나누어 떨어지지 않으면, 올림해야 하므로 단위시간(fees[2])만큼 더해줌
                if(totalTime % fees[2] != 0){
                    totalTime += fees[2];
                }
                
                totalFee += (totalTime / fees[2]) * fees[3];
            }
            answer[idx] = totalFee;
        }
        
        return answer;
    }
    
    public static class Info{
        int time;
        int enter;
        
        public Info(int time, int enter){
            this.time = time;
            this.enter = enter;
        }
    }
}