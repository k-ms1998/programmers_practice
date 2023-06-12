import java.util.*;

class Solution {
    
    static int n;
    static Time[] times;
    static int playTime;
    static int advTime;        
    static long[] count = new long[360001];

    
    public String solution(String play_time, String adv_time, String[] logs) {
        n = logs.length;
        times = new Time[n];
        for(int i = 0; i < n; i++){
            String[] arr = logs[i].split("-");
            
            int startTime = convertTime(arr[0]);
            int endTime = convertTime(arr[1]);
            
            for(int j = startTime; j < endTime; j++){
                count[j]++;
            }
        }
        
        playTime = convertTime(play_time);
        advTime = convertTime(adv_time);
        
        int answer = 0;
        long curTime = 0L;
        for(int i = 0; i < advTime; i++){
            curTime += count[i];
        }
        long totalTime = curTime;
        
        for(int i = 0; i <= playTime; i++){
            int endTime = i + advTime;
            if(endTime > playTime){
                break;
            }
        
            curTime += count[endTime] - count[i];
            
            if(curTime > totalTime){
                curTime = totalTime;
                answer = i + 1;
            }
        }
        
        return convertString(answer);
    }
    
    public static String convertString(int time){
        int h = time/3600;
        time %= 3600;
        
        int m = time / 60;
        int s = time % 60;
        
        return String.format("%02d:%02d:%02d", h, m, s);
    }
    
    public static int convertTime(String str){
        String[] arr = str.split(":");
        
        return 3600 * Integer.parseInt(arr[0]) + 60 * Integer.parseInt(arr[1]) + Integer.parseInt(arr[2]);
    }
    
    public static class Time{
        int start;
        int end;
        
        public Time(int start, int end){
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString(){
            return convertString(start) + "-" + convertString(end);
        }
    }
}