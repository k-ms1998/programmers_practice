import java.util.*;

class Solution {
    
    static int n;
    
    public int solution(String[] lines) {
        int answer = 1;
        n = lines.length;
        
        List<Info> infos = new ArrayList<>();
        for(int i = 0; i < n; i++){
            String[] arr = lines[i].split(" ");
            String startTime = arr[1];
            String[] startArr = startTime.split(":");
            int hour = Integer.parseInt(startArr[0]);
            int minute = Integer.parseInt(startArr[1]);
            double sec = Double.valueOf(startArr[2]);

            int dur = (int)(1000*Double.valueOf(lines[i].substring(24, lines[i].length() - 1)));
            int end = 3600*hour*1000 + 60*minute*1000 + (int)(sec*1000);
            int start = end - dur;
            
            infos.add(new Info(start + 1, end));
        }
            
        for(int i = 0; i < n; i++){
            int cnt = 1;
            Info cur = infos.get(i);
            for(int j = i + 1; j < n; j++){
                Info target = infos.get(j);
                if((target.start - cur.end) < 1000){
                    cnt++;
                }
            }
            answer = Math.max(answer, cnt);
        }
        
        return answer;
    }
    
    public static class Info{
        int start;
        int end;
        
        public Info(int start, int end){
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString(){
            return "{start=" + start + ", end=" + end + "}";
        }
    }
}