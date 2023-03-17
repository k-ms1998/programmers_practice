import java.util.*;

class Solution {
    
    static int n;
    static Info[] infos;
    static Map<String, String> user = new HashMap<>();
    
    public String[] solution(String[] record) {
        n = record.length;
        
        int ansSize = 0;
        infos = new Info[n];
        for(int i = 0; i < n; i++){
            String[] arr = record[i].split(" ");
            String type = arr[0];
            String id = arr[1];
            if(!type.equals("Leave")){
                String name = arr[2];
                user.put(id, name);
            }
            if(!type.equals("Change")){
                ansSize++;
            }
            
            infos[i] = new Info(type, id);
        }
                
        int ansIdx = 0;
        String[] answer = new String[ansSize];
        for(int i = 0; i < n; i++){
            // System.out.println(ansSize);
            Info info = infos[i];
            String type = info.type;
            String id = info.id;
            String name = user.get(id);
            
            if(type.equals("Change")){
                continue;
            }else if(type.equals("Enter")){
                answer[ansIdx] = name + "님이 들어왔습니다.";
                ansIdx++;
            }else{
                answer[ansIdx] = name + "님이 나갔습니다.";
                ansIdx++;
            }
        }
        

        return answer;
    }
    
    public static class Info{
        String type;
        String id;
        
        public Info(String type, String id){
            this.type = type;
            this.id = id;
        }
    }
}