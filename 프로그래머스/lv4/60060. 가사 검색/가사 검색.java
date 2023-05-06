import java.util.*;

/**
Solution: Trie(트라이 알고리즘)
1. 단어가 주어졌을때, 해당 단어에 대해서 Trie 생성(trie)
-> queries에서 '?'가 뒤에(접미사) 있을때 해당 Trie 탐색
2. 단어가 주어졌을때, 해당 단어를 reverse한 Trie 생성(reverseTrie)
-> queries에서 '?'가 앞에(접두사) 있을때 해당 Trie 탐색
*/
class Solution {
    
    static int n;
    static int maxLen = 0;
    static Node[] trie;
    static Node[] reverseTrie;
    
    public int[] solution(String[] words, String[] queries) {       
        n = words.length;
        for(int i = 0; i < n; i++){
            maxLen = Math.max(maxLen, words[i].length());
        }
        
        trie = new Node[maxLen + 1];
        reverseTrie = new Node[maxLen + 1];
        for(int i = 0; i < maxLen + 1; i++){
            trie[i] = new Node(0, 0, new Node[27], new boolean[27]);
            reverseTrie[i] = new Node(0, 0, new Node[27], new boolean[27]);
        }
        
        for(int i = 0; i < n; i++){
            String word = words[i];
            int l = word.length();
            
            Node node = trie[l];
            node.cnt++;
            createTrie(0, l, word, node);
            
            Node reverseNode = reverseTrie[l];
            reverseNode.cnt++;
            createReverseTrie(l - 1, 0, word, reverseNode);
            // System.out.println(reverseNode);
        }
        
        int[] answer = new int[queries.length];
        for(int i = 0; i < queries.length; i++){
            String query = queries[i];
            int ql = query.length();
            if(ql > maxLen){
                answer[i] = 0;
                continue;
            }
            
            Node node = trie[ql];
            Node reverseNode = reverseTrie[ql];
            if(query.startsWith("?") && query.endsWith("?")){
                answer[i] = node.cnt;
            }else if(query.startsWith("?")){
                answer[i] = findNodeSuffix(ql - 1, 0, query, reverseNode);
            }else{
                answer[i] = findNode(0, ql, query, node);
            }
        }
        
        return answer;
    }
    
    public static int findNodeSuffix(int depth, int target, String query, Node node){
        if(depth < target){
            return 0;
        }
        
        char c = query.charAt(depth);
        if(c == '?'){
           return node.cnt;
        }
        
        int curNum = c - 'a';
        Node[] child = node.child;
        boolean[] visited = node.visited;

        if(visited[curNum]){
            return findNodeSuffix(depth - 1, target, query, child[curNum]);
        }
       
        return 0;
    }
    
    public static int findNode(int depth, int target, String query, Node node){
        if(depth >= target){
            return 0;
        }
        
        char c = query.charAt(depth);
        if(c == '?'){
           return node.cnt;
        }
        
        int curNum = c - 'a';
        Node[] child = node.child;
        boolean[] visited = node.visited;

        if(visited[curNum]){
            return findNode(depth + 1, target, query, child[curNum]);
        }
       
        return 0;
    }
    
    public static void createTrie(int depth, int target, String word, Node node){
        if(depth >= target){
            return;
        }
        
        int curNum = word.charAt(depth) - 'a';
        Node[] child = node.child;
        boolean[] visited = node.visited;
        
        if(visited[curNum]){
            child[curNum].cnt++;
            createTrie(depth + 1, target, word, child[curNum]);
        }else {
            Node next = new Node(curNum, 1, new Node[27], new boolean[27]);
            child[curNum] = next;
            visited[curNum] = true;
            createTrie(depth + 1, target, word, child[curNum]);
        }
    }
    
    public static void createReverseTrie(int depth, int target, String word, Node node){
        if(depth < target){
            return;
        }
        
        int curNum = word.charAt(depth) - 'a';
        Node[] child = node.child;
        boolean[] visited = node.visited;
        
        if(visited[curNum]){
            child[curNum].cnt++;
            createReverseTrie(depth - 1, target, word, child[curNum]);
        }else {
            Node next = new Node(curNum, 1, new Node[27], new boolean[27]);
            child[curNum] = next;
            visited[curNum] = true;
            createReverseTrie(depth - 1, target, word, child[curNum]);
        }
    }
    
    public static class Node{
        int num;
        int cnt;
        Node[] child;
        boolean[] visited;
        
        public Node(int num, int cnt, Node[] child, boolean[] visited){
            this.num = num;
            this.cnt = cnt;
            this.child = child;
            this.visited = visited;
        }
        
        @Override
        public String toString(){
            return "{num=" + num + ", cnt=" + cnt + ", child=" + child + "}";
        }
    }
}