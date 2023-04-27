import java.io.*;
import java.util.*;

/**
Solution: 정렬
1. timetable을 모두 분으로 변경 (ex: 09:30 = 9 * 60 + 30 = 570)
2. timetable을 오름차순으로 졍렬(timeQ)
3. 각 버스 시간 출발 시간을 오름차순으로 정렬(busQ)
4. 각 버스에 대해서, 버스에 탑승할 수 있는 크루들 확인
    -> 현재 버스에 탑승한 크루의 수가 m보다 작거나, 크루가 대기열에 도착한 시간이 버스의 출발 시간보다 빠르거나 같을때
5. 마지막 버스에 확인 할때, 남은 자리의 수 확인(lastBusCap)
    -> lastBusCap > 0 => 마지막 버스에 자리가 남으므로, 콘은 마지막 버스 시간에 맞춰서 대기열에 도착하면 됨
    -> lastBusCap <= 0 => 콘을 제외한 대기열에 이미 있는 크루들 만으로도 마지막 버스까지 다 차기 때문에, 콘은 마지막 버스 시간 또는 마지막 크루보다 1분 빨리 도착해야함
*/
class Solution {
    
    public String solution(int n, int t, int m, String[] timetable) {
        PriorityQueue<Integer> timeQ = new PriorityQueue<>();
        Deque<Integer> busQ = new ArrayDeque<>();
        
        for(int i = 0; i < timetable.length; i++){
            timeQ.offer(convertToInt(timetable[i]));
        }
        
        int startTime = convertToInt("09:00");
        for(int i = 0; i < n; i++){
            busQ.offer(startTime);
            startTime += t;
        }
        
        int lastBus = 0;
        int lastCrew = 0;
        int lastBusCap = m;
        while(!busQ.isEmpty() && !timeQ.isEmpty()){ 
            int curBus = busQ.poll();
            int curCnt = 0;
            lastBus = curBus;
            
            while(curCnt < m && !timeQ.isEmpty()){ // 현재 버스에 탑슥한 크루의 수(curCnt)가 버스에 탑승할 수 있는 최대 크루의 수부도 작고, 아직 탑승하지 않은 크루의 수가 남아있을때
                int curCrew = timeQ.peek();
                lastCrew = curCrew;

                if(curCrew > curBus){ // 현재 크루가 도착한 시간이, 버스 출발 시간보다 늦을때 -> 다음 버스 확인
                    break;
                }
                
                if(busQ.isEmpty()){ // 마지막 버스일 경우, 남는 자리 수 확인
                    lastBusCap--;
                }
                curCnt++;
                timeQ.poll();
                
            }
        }
        
        int answerTime = 0;
        if(lastBusCap > 0){
            answerTime = lastBus;
        }else{
            answerTime = Math.min(lastCrew, lastBus) - 1;
        }
        
        return convertToString(answerTime);
    }
    
    public int convertToInt(String time){
        int result = 0;
        String[] arr = time.split(":");
        int hour = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
                
        return 60*hour + min;
    }
    
    public String convertToString(int time){
        int hour = time / 60;
        int minute = time % 60;
        
        return String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }
}