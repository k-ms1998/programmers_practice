import java.io.*;
import java.util.*;

class Solution {
    
    static int mSize = 0;
    
    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";
        int answerTime = 0;
        
        for(int i = 0; i < m.length(); i++){
            char c = m.charAt(i);
            if(c != '#'){
                mSize++;
            }
        }
        
        
        for(int i = 0; i < musicinfos.length; i++){
            String[] musicinfo = musicinfos[i].split(",");
            
            String[] startStr = musicinfo[0].split(":");
            String[] endStr = musicinfo[1].split(":");
            String name = musicinfo[2];
            String notesStr = musicinfo[3];
            
            int startH = Integer.parseInt(startStr[0]);
            int startM = Integer.parseInt(startStr[1]);
            int endH = Integer.parseInt(endStr[0]);
            int endM = Integer.parseInt(endStr[1]);
            int start = 60 * startH + startM;
            int end = 60 * endH + endM;
            
            List<String> notes = new ArrayList<>();
            for(int j = 0; j < notesStr.length() - 1; j++){
                char cur = notesStr.charAt(j);
                char next = notesStr.charAt(j + 1);
                if(cur == '#'){
                    continue;
                }
                if(next == '#'){
                    notes.add(String.valueOf(cur) + "#");
                }else{
                    notes.add(String.valueOf(cur));
                }
            }
            char last = notesStr.charAt(notesStr.length() - 1);
            if(last != '#'){
                notes.add(String.valueOf(last));
            }
            
            List<String> music = new ArrayList<>();
            int size = notes.size();
            for(int j = 0; j < end - start; j++){
                int index = j % size;
                music.add(notes.get(index));
            }
            
            if(checkMusic(m, music)){
                if(answerTime < (end - start)){
                    answerTime = end - start;
                    answer = name;
                }
            }
        }
        
        return answer;
    }
    
    public static boolean checkMusic(String m, List<String> music){
        for(int i = 0; i <= music.size() - mSize; i++){
            String check = "";
            for(int j = i; j < i + mSize; j++){
                check += music.get(j);
            }
            if(m.equals(check)){
                return true;
            }
        }

        return false;
    }
    
}