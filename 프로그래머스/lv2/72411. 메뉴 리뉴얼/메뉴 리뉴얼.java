import java.util.*;

/**
조합론(Combination)
1. 각 주문에 대해서, 만들수 있는 모든 조합 확인
    -> 최소 2개의 메뉴를 선택해야함
        -> 그러므로, 길이가 2이상, orders[i]의 길이 이하인 모든 조합 찾기
        -> 각 유니크한 조합을 찾기 위해 HashSet에 넣기
2. 각 order[i]에 대해서 생성된 HashSet을 탐색하면사, count 업데이트
    -> count는 (k, v)로 각 조합을 만들 수 있는 횟수 저장
3. count의 모든 key에 대해서, byCourseLength에 저장
    -> byCouseLength는 조합의 길이에 대해서, 해당 길이에 해당하는 조합과 갯수(Info) 저장
        -> ex: byCourseLength[2] => 길이가 2인 조합들 저장
4. 조건에 맞게 정답 구하기
*/
class Solution {
    
    static HashMap<String, Integer> count = new HashMap<>();
    static List<Info>[] byCourseLength;
    
    public String[] solution(String[] orders, int[] course) {
        
        int maxLength = 0;
        for(int i = 0; i < orders.length; i++){
            String[] order = orders[i].split("");
            Arrays.sort(order);
            maxLength = Math.max(maxLength, order.length);
            Set<String> comb = new HashSet<>();
            for(int j = 2; j <= order.length; j++){
                findCombination(0, j, 0, order, "", comb);
            }
            for(String s : comb){
                if(count.containsKey(s)){
                    int cnt = count.get(s);
                    count.put(s, cnt + 1);
                }else{
                    count.put(s, 1);
                }
            }
        }
        
        byCourseLength = new List[maxLength + 2];
        for(int i = 2; i <= maxLength + 1; i++){
            byCourseLength[i] = new ArrayList<>();
        }
        for(String k: count.keySet()){
            int cnt = count.get(k);
            int sLength = k.length();
            if(cnt >= 2){
                byCourseLength[sLength].add(new Info(k, cnt));
            }
        }
        for(int i = 2; i <= maxLength; i++){
            Collections.sort(byCourseLength[i], new Comparator<Info>(){
                @Override
                public int compare(Info i1, Info i2){
                    return i2.cnt - i1.cnt;
                }
            });
        }
        
        List<String> ansList = new ArrayList<>();
        for(int i = 0; i < course.length; i++){
            int curL = course[i];
            if(byCourseLength[curL].size() > 0){
                int maxCnt = byCourseLength[curL].get(0).cnt;
                if(maxCnt >= 2){
                    
                }
                for(Info info: byCourseLength[curL]){
                    if(info.cnt == maxCnt){
                        ansList.add(info.s);
                    }else{
                        break;
                    }
                }
            }
        }
        Collections.sort(ansList);
        
        String[] answer = new String[ansList.size()];
        for(int i = 0; i < ansList.size(); i++){
            answer[i] = ansList.get(i);
        }
        return answer;
    }
    
    public static void findCombination(int depth, int target, int idx, String[] order, String cur, Set<String> comb){
        if(depth == target){
            comb.add(cur);
            return;
        }
        
        for(int i = idx; i < order.length; i++){
            findCombination(depth + 1, target, i + 1, order, cur + order[i], comb);
        }
    }
    
    public static class Info{
        String s;
        int cnt;
        
        public Info(String s, int cnt){
            this.s = s;
            this.cnt = cnt;
        }
    }
}