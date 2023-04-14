import java.util.*;

class Solution {
    
    static Map<String, Integer> set1 = new HashMap<>();
    static Map<String, Integer> set2 = new HashMap<>();
    
    public int solution(String str1, String str2) {
        int answer = 0;
        
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        
        for(int i = 0; i < str1.length() - 1; i++){
            String tmp = "";
            char a = str1.charAt(i);
            char b = str1.charAt(i + 1);
            if((a >= 'a' && a <= 'z') && (b >= 'a' && b <= 'z')){
                tmp = (String.valueOf(a) + String.valueOf(b));
                if(set1.containsKey(tmp)){
                    int cnt = set1.get(tmp);
                    set1.put(tmp, cnt + 1);
                }else{
                    set1.put(tmp, 1);
                }
            }
        }
        for(int i = 0; i < str2.length() - 1; i++){
            String tmp = "";
            char a = str2.charAt(i);
            char b = str2.charAt(i + 1);
            if((a >= 'a' && a <= 'z') && (b >= 'a' && b <= 'z')){
                tmp = (String.valueOf(a) + String.valueOf(b));
                if(set2.containsKey(tmp)){
                    int cnt = set2.get(tmp);
                    set2.put(tmp, cnt + 1);
                }else{
                    set2.put(tmp, 1);
                }
            }
        }
        
        int num = 0;
        int divi = 0;
        Set<String> keySet1 = set1.keySet();
        for(String k : keySet1){
            if(set2.containsKey(k)){
                int v1 = set1.get(k);
                int v2 = set2.get(k);
                
                num += Math.min(v1, v2);
                divi += Math.max(v1, v2);
            }else{
                int v = set1.get(k);
                divi += v;
            }
        }
        Set<String> keySet2 = set2.keySet();
        for(String k : keySet2){
            if(!set1.containsKey(k)){
                int v = set2.get(k);
                divi += v;
            }
        }
                
        double ans = num == 0 && divi == 0 ? 1.0 : 1.0*num/divi;
        return (int)(ans * 65536);
    }
}