import java.util.*;

/**
Solution: 구현 + Union-Find

1. 풀이의 기본은 그냥 문제의 조건에 맞게 구현
-> Merge할때 Union-Find를 이용해서 Merge 시켜주는 것이 중요
2. 각 좌표에 대해서 인덱스 값으로 변환
-> (0,0) = 50*0 + 0 = 0; (0,1) = 50*0 + 1 = 1 ....
-> (1,0) = 50*1 + 0 = 50; (1,1) = 50*1 + 1 = 51 ....
-> (r, c)는 (1, 1) ~ (50, 50)이므로, (r-1, c-1)로 바꿔서 인덱스 값 찾아줘야함 (안그러면, 오류 발생)
3. Merge할때, 병할할 두 좌표를 인덱스 값으로 변환하고 조건에 맞게 병합
-> 1. (r1, c1), (r2, c2) 모두 값을 갖고 있을때, (r2, c2)가 (r1, c1)으로 병합
-> 2. (r1, c1), (r2, c2) 중 하나만 값을 갖고 있을때, 값이 없는 좌표가 값이 있는 쪽으로 병함
-> 3. (r1, c1), (r2, c2) 모두 값이 없을때, (r2, c2)가 (r1, c1)으로 병합
-> 병합 후, children도 업데이트 시켜줌
4. Unmerge할때는, unmber할 좌표(r, c)의 root 부모 찾기
-> root 부모부터 시작해서, children을 탐색하면서 자식들로 내려감
-> 내려가면서, grid, parent, children 모두 초기 상태로 초기화 해줌
-> 초기화 모두 시킨 후, (r, c)의 값만 초기화 이전의 값을 저장 시켜줌
5. PRINT와 업데이트를 할때, 항상 (r,c)의 root 부모의 좌표의 값을 출력/업데이트 시켜 줘야함

*/
class Solution {
    
    static int n;
    static int[] parent = new int[2501];
    static List<Integer>[] children = new List[2501];
    static String[][] grid = new String[50][50];
    static final int MOD = 50;
    
    public String[] solution(String[] commands) {
        n = commands.length;
        List<String> ansList = new ArrayList<>();
        
        // 초기화
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
                    String value1 = arr[1];
                    String value2 = arr[2];
                    for(int y = 0; y < 50; y++){
                        for(int x = 0; x < 50; x++){
                            int root = findRoot(MOD*y + x);
                            int r = root/MOD;
                            int c = root%MOD;
                            if(grid[r][c].equals(value1)){
                                grid[r][c] = value2;
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
}