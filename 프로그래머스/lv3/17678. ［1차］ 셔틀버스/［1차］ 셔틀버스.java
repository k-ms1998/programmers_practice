import java.io.*;
import java.util.*;

class Solution {
    
    static int size;
    static int totalCap;
    static PriorityQueue<Integer> timeQ = new PriorityQueue<>();
    static Deque<Integer> busQ = new ArrayDeque<>();
    
    public String solution(int n, int t, int m, String[] timetable) {
        totalCap = n * m;
        String answer = "";
        
        size = timetable.length;
        for(int i = 0; i < size; i++){
            timeQ.offer(convertToInt(timetable[i]));
        }
        
        int startTime = convertToInt("09:00");
        for(int i = 0; i < n; i++){
            busQ.offer(startTime);
            startTime += t;
        }
        
        int aboard = 0;
        int lastBus = 0;
        int lastCrew = 0;
        while(!busQ.isEmpty() && !timeQ.isEmpty()){
            int curBus = busQ.poll();
            int curCnt = 0;
            lastBus = curBus;
            while(curCnt < m && !timeQ.isEmpty()){
                int curCrew = timeQ.peek();
                lastCrew = curCrew;

                if(curCrew > curBus){
                    if(curCnt < m && !busQ.isEmpty()){
                        totalCap -= (m - curCnt);
                    }
                    break;
                }
                aboard++;
                curCnt++;
                timeQ.poll();
            }
        }
        // System.out.println("aboard=" + aboard + ", totalCap=" + totalCap);
        // System.out.println("lastBus=" + lastBus);
        // System.out.println("lastCrew=" + lastCrew);
        // System.out.println("busQ=" + busQ);
        // System.out.println("timeQ=" + timeQ);

        int answerTime = 0;
        if(aboard == 0){
            answerTime = lastBus;
        }else if(aboard < totalCap){
            answerTime = lastBus;
        }else if(aboard == totalCap){
            answerTime = Math.min(lastCrew, lastBus) - 1;
        }else{
            answerTime = lastCrew - 1;
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