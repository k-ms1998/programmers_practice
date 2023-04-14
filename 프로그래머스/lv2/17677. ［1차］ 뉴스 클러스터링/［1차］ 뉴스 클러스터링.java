import java.util.*;

/**
다순 구현문제:
1. 각 문자열에 대해서, 원소가 몇개씩 있는지 계산해서 Map에 저장
2. set1과 set2의 모든 key 값들을 한번씩 탐색
    -> set1을 탐색할때는, set2에 동일한 원소가 존재하는지 확인
        -> 동일한 원소가 존재하면, 분자는 두 원소가 나타나는 횟수 중 작은 값 추가
        -> 동일한 원소가 존재하면, 분모는 두 원소가 나타나는 횟수 중 큰 값 추가
        -> 동일한 원소가 존재하지 않으면, 해당 원소가 set1에서 나타나는 횟수를 분모에 추가
    -> set2을 탐색할때는, set1에 동일한 원소가 존재하는지 확인
        -> 동일한 원소가 존재하면, 이미 set1 탐색할때 확인했기 때문에 무시 => 한번 더 탐색하면 중복
        -> 동일한 원소가 존재하지 않으면, 해당 원소가 set2에서 나타나는 횟수를 분모에 추가
*/
class Solution {
    
    static Map<String, Integer> set1 = new HashMap<>();
    static Map<String, Integer> set2 = new HashMap<>();
    
    public int solution(String str1, String str2) {
        // 대소문자는 구별 안하기 때문에, 문자열 두개 다 소문자로 변경
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        
        for(int i = 0; i < str1.length() - 1; i++){
            String tmp = "";
            char a = str1.charAt(i);
            char b = str1.charAt(i + 1);
            if((a >= 'a' && a <= 'z') && (b >= 'a' && b <= 'z')){ // 알파벳만 포함
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
            if((a >= 'a' && a <= 'z') && (b >= 'a' && b <= 'z')){ // 알파벳만 포함
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
        Set<String> keySet1 = set1.keySet(); // 첫번째 문자열의 모든 원소들 확인
        for(String k : keySet1){
            if(set2.containsKey(k)){ // 두 문자열에 모두 동일한 원소가 존재할때 -> 분자, 분모 모두 업데이트
                int v1 = set1.get(k);
                int v2 = set2.get(k);
                
                num += Math.min(v1, v2);
                divi += Math.max(v1, v2);
            }else{      // 첫번째 문자열에만 현재 원소가 존재할때, 분모만 업데이트
                int v = set1.get(k);
                divi += v;
            }
        }
        Set<String> keySet2 = set2.keySet(); // 두번째 문자열의 모든 원소들 확인
        for(String k : keySet2){
            // 이미 두 문자열에서 공통되는 원소에 대해서는 set1 탐색할때 확인했기 때문에 다시 확인 X -> 다시 확인시 중복
            if(!set1.containsKey(k)){ // 두번째 문자열에만 현재 원소가 존재할때, 분모만 업데이트
                int v = set2.get(k);
                divi += v;
            }
        }
                
        double ans = num == 0 && divi == 0 ? 1.0 : 1.0*num/divi;
        return (int)(ans * 65536);
    }
}