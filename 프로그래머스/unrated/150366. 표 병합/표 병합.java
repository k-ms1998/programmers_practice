import java.util.*;

class Solution {
    
    static int n;
    static int[] parent = new int[2501];
    static List<Integer>[] children = new List[2501];
    static String[][] grid = new String[50][50];
    static final int MOD = 50;
    
    public String[] solution(String[] commands) {
        n = commands.length;
        List<String> ansList = new ArrayList<>();
        
        for(int y = 0; y < 50; y++){
            for(int x = 0; x < 50; x++){
                grid[y][x] = "";
                
                int idx = MOD*y + x;
                parent[idx] = idx;
                children[idx] =  new ArrayList<>();
            }
        }
    
        for(int i = 0; i < n; i++){
            String command = commands[i];
            String[] arr = command.split(" ");
            String comm = arr[0];
            
            if(comm.equals("UPDATE")){
                if(arr.length == 4){ // UPDATE r c value
                    int r = Integer.parseInt(arr[1]) - 1;
                    int c = Integer.parseInt(arr[2]) - 1;
                    String value = arr[3];
                    grid[r][c] = value;
                    
                    int root = findRoot(MOD*r + c);
                    int rr = root / MOD;
                    int cc = root % MOD;
                    grid[root/MOD][root%MOD] = value;
                    
                }else{ // UPDATE value1 value2
                    for(int y = 0; y < 50; y++){
                        for(int x = 0; x < 50; x++){
                            if(grid[y][x].equals(arr[1])){
                                grid[y][x] = arr[2];
                                
                                // int idx = MOD*y + x;
                                // int root = findRoot(idx);
                                // grid[root/MOD][root%MOD] = arr[2];
                            }
                        }
                    }
                }
            }else if(comm.equals("MERGE")){
                int r1 = Integer.parseInt(arr[1]) - 1;
                int c1 = Integer.parseInt(arr[2]) - 1;
                int r2 = Integer.parseInt(arr[3]) - 1;
                int c2 = Integer.parseInt(arr[4]) - 1;
                
                int idx1 = MOD * r1 + c1;
                int idx2 = MOD * r2 + c2;
                
                int root1 = findRoot(idx1);
                int root2 = findRoot(idx2);
                
                String word1 = grid[root1/MOD][root1%MOD];
                String word2 = grid[root2/MOD][root2%MOD];
                
                if(root1 != root2){
                    if(!word1.equals("") && !word2.equals("")){    
                        parent[idx2] = root1;
                        parent[root2] = root1;
                        children[root1].add(idx2);
                        children[root1].add(root2);

                    }else if(!word1.equals("") && word2.equals("")){
                        parent[idx2] = root1;
                        parent[root2] = root1;
                        children[root1].add(idx2);
                        children[root1].add(root2);
                        
                    }else if(word1.equals("") && !word2.equals("")){
                        parent[idx1] = root2;
                        parent[root1] = root2;
                        children[root2].add(idx1);
                        children[root2].add(root1);
                        
                    }else if(word1.equals("") && word2.equals("")){
                        parent[idx2] = root1;
                        parent[root2] = root1;
                        children[root1].add(idx2);
                        children[root1].add(root2);
                    }
                }
                
                if(idx1 != root1){
                    children[root1].add(idx1);
                }
                if(idx2 != root2){
                    children[root2].add(idx2);
                }
                
            }else if(comm.equals("UNMERGE")){
                int r = Integer.parseInt(arr[1]) - 1;
                int c = Integer.parseInt(arr[2]) - 1;
                int idx = MOD*r + c;
                
                int root = findRoot(idx);
                String cur = grid[root/MOD][root%MOD];
                unmergeChildren(root);
                grid[r][c] = cur;
                
            }else{ // PRINT
                int r = Integer.parseInt(arr[1]) - 1;
                int c = Integer.parseInt(arr[2]) - 1;
                int root = findRoot(MOD*r + c);

                if(grid[root/MOD][root%MOD].equals("")){
                    ansList.add("EMPTY");
                }else{
                    ansList.add(grid[root/MOD][root%MOD]);
                }
            }
        }
        
        String[] answer = new String[ansList.size()];
        int idx = 0;
        for(String s : ansList){
            answer[idx] = s;
            idx++;
        }

        return answer;
    }
    
    public static void unmergeChildren(int node){
        for(int child : children[node]){
            int r = child / MOD;
            int c = child % MOD;
            
            parent[child] = child;
            grid[r][c] = "";
            unmergeChildren(child);
        }
        parent[node] = node;
        grid[node/MOD][node%MOD] = "";
        children[node] = new ArrayList<>();
    }
    
    public static int findRoot(int node){
        if(parent[node] == node){
            return node;
        }
        
        int root = findRoot(parent[node]);
        parent[node] = root;
        return root;
    }
    
    public static void printGrid(){
        for(int y = 1; y <= 5; y++){
            for(int x = 1; x <= 5; x++){
                int root = findRoot(MOD*y + x);  
                System.out.print((grid[root/MOD][root%MOD].equals("") ? "EMPTY" : grid[root/MOD][root%MOD]) + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }
    
}