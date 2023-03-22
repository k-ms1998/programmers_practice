import java.util.*;

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
        // System.out.println(ansList);
        
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