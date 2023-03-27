import java.util.*;

class Solution {
    
    static int n, m;
    static int answer = 0;
    static Set<String> unique = new HashSet<>();
    static List<String> comb = new ArrayList<>();
    static Set<String> uniqueComb = new HashSet<>();
    
    static boolean isUnique = false;
    static boolean flag = true;
    
    public int solution(String[][] relation) {
        n = relation.length;
        m = relation[0].length;
        
        for(int i = 0; i < m; i++){
            findComb(0, i + 1, 0, "");   
        }
        
        for(String c : comb){
            String[] arr = c.split("");
            List<Integer> column = new ArrayList<>();
            for(int i = 0; i < arr.length; i++){
                column.add(Integer.parseInt(arr[i]));
            }
            isUnique = false;
            unique = new HashSet<>();
            findAnswer(column, 0, relation);
            if(isUnique){
                String tmp = "";
                boolean isUniqueComb = true;
                for(int cc: column){
                    tmp += String.valueOf(cc);
                    if(uniqueComb.contains(tmp)){
                        isUniqueComb = false;
                        break;
                    }
                }
                if(isUniqueComb){
                    flag = true;
                    for(int i = 0; i < column.size(); i++){
                        findCombOfComb(0, i + 1, 0, "", column);
                    }
                    if(flag){
                        uniqueComb.add(tmp);
                        answer++;                        
                    }
                }
            }

        }           
        // System.out.println(uniqueComb);
        
        return answer;
    }
    
    public static void findCombOfComb(int depth, int target, int idx, String cur, List<Integer> column){
        if(!flag){
            return;
        }
        if(depth == target){
            if(uniqueComb.contains(cur)){
               flag = false; 
            }
            // System.out.println(cur + ", " + column);
            return;
        }
        
        for(int i = idx; i < column.size(); i++){
            findCombOfComb(depth + 1, target, i + 1, cur + String.valueOf(column.get(i)), column);
        }
    }
    
    public static void findComb(int depth, int target, int idx, String cur){
        if(depth == target){
            comb.add(cur);
            return;
        }
        
        for(int i = idx; i < m; i++){
            findComb(depth + 1, target, i + 1, cur + String.valueOf(i));
        }
    }
    
    public static void findAnswer(List<Integer> column, int depth, String[][] relation){
        if(depth == n){
            if(unique.size() == n){       
                // System.out.println(unique);
                isUnique = true;
            }
            return;
        }
        
        String tmp = "";
        for(int c : column){
            tmp += (relation[depth][c] + "|");
        }
        unique.add(tmp);
        
        findAnswer(column, depth + 1, relation);
    }
}