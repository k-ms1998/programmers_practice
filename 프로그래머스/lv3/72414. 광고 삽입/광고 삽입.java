import java.util.*;

/**
Solution: 누적합
1. 각 logs에 대해서 시작시간과 끝나느 시간 사이의 count 증가
2. 00:00:00~adv_time 까지의 누적합 구하기
3. adv_time ~ play_time 까지 시간을 1씩 증가시키면서 누적합 업데이트
    누적합 = 직전의 누적합 + count[end] - count[start-1]
    => start~end의 누적합 = 직전의 누적합 - 직전의 누적합의 시작 지점의 count + 현재의 끝나는 지점의 count 
4. 누적합이 업데이트 될때 최대값이 업데이트 되면, answer 갱신
*/
class Solution {
    
    static int n;
    static int playTime;
    static int advTime;        
    static long[] count;

    
    public String solution(String play_time, String adv_time, String[] logs) {
        n = logs.length;
                
        playTime = convertTime(play_time);
        advTime = convertTime(adv_time);
        count = new long[playTime + 1];
        
        for(int i = 0; i < n; i++){
            String[] arr = logs[i].split("-");
            
            int startTime = convertTime(arr[0]);
            int endTime = convertTime(arr[1]);
            
            for(int j = startTime; j < endTime; j++){
                count[j]++;
            }
        }
        
        int answer = 0;
        long curTime = 0L;
        for(int i = 0; i < advTime; i++){
            curTime += count[i];
        }
        long totalTime = curTime;
        
        for(int i = 1; i <= playTime; i++){
            int endTime = i + advTime - 1;
            if(endTime > playTime){
                break;
            }
        
            curTime = (curTime + count[endTime]) - count[i - 1];
            
            if(curTime > totalTime){
                totalTime = curTime;
                answer = i;
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
}