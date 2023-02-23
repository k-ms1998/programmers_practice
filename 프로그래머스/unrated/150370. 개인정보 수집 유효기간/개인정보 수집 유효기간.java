import java.io.*;
import java.util.*;

class Solution {
    
    static int[] termsLength = new int[26];
    
    public int[] solution(String today, String[] terms, String[] privacies) {
        String[] tDate = today.replace(".", "-").split("-");
        int tYear = Integer.parseInt(tDate[0]);
        int tMonth = Integer.parseInt(tDate[1]);
        int tDay = Integer.parseInt(tDate[2]);
        
        for(int i = 0; i < terms.length; i++){
            String[] cur = terms[i].split(" ");
            int idx = cur[0].charAt(0) - 'A';
            termsLength[idx] = Integer.parseInt(cur[1]);
        }
        
        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 0; i < privacies.length; i++){
            String cur[] = privacies[i].split(" ");
            int idx = cur[1].charAt(0) - 'A';
            
            String[] date = cur[0].replace(".", "-").split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            
            year += (termsLength[idx] / 12);
            month += (termsLength[idx] % 12);
            if(month > 12){
                year++;
                month -= 12;
            }
            System.out.println(year + ":" + month + ":" + day);
            if(year == tYear){
                if(month == tMonth){
                    if(day <= tDay){
                        queue.offer(i + 1);
                    }
                }else if(month < tMonth){
                    queue.offer(i + 1);
                }
            }else if(year < tYear){
                queue.offer(i + 1); 
            }
        }
        
        int answerIdx = 0;
        int[] answer = new int[queue.size()];
        while(!queue.isEmpty()){
            answer[answerIdx] = queue.poll();
            answerIdx++;
        }
        
        return answer;
    }
}