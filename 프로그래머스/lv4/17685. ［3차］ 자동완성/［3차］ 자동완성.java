import java.util.*;

/**
Solution: Trie(트라이)
주의점: 2개의 단어가 주어지고, 하나의 단어의 길이는 2이고 다른 하나는 999,998 일때, 두 번째 단어를 재귀로 트라이로 만들때, 재귀 깊이가 너무 깊어서 StackOverflow 발생 할 수 있음
=> 그러므로, 재귀가 아닌 loop로 풀이
*/
class Solution {
    
    static Trie root;
    static int answer = 0;
    
    public int solution(String[] words) {
        
        root = new Trie(-1, new ArrayList<>(), new boolean[26], 0);
        for(int i = 0; i < words.length; i++){
            updateTrie(words[i]);
        }
        searchTrie(root);
        
        return answer;
    }
    
    public static void searchTrie(Trie trie){ // 트라이 탐색
        if(trie.alphabet != -1){
            answer += trie.cnt; // trie.cnt = 현재 경로를 지나가는 횟수
        }
        
        if(trie.cnt <= 1){ // trie.cnt가 1이면, 다른 알파벳을 입력하지 않아도 어떤 단어인지 확신할 수 있기 때문에 return
            return;
        }
        
        for(Trie child : trie.children){
            searchTrie(child); // 자식들로 이동하기
        }
    }
    
    public static void updateTrie(String word){
        Trie trie = root;
        
        for(int i = 0; i < word.length(); i++){
            int num = word.charAt(i) - 'a';
            trie.cnt++;
            if(trie.used[num]){ // 현재 경로로 이미 방문 했던 적이 있을때
                for(Trie child : trie.children){
                    if(child.alphabet == num){
                        trie = child; // 재귀 대신, 현재 탐색할 노드를 업데이트 시켜줌
                        break;
                    }
                }
            }else{ // 현재 경로를 방문한 적이 없을때
                trie.used[num] = true;
                Trie child = new Trie(num, new ArrayList<>(), new boolean[26], 0);
                trie.children.add(child);
                trie = child; // 재귀 대신, 현재 탐색할 노드를 업데이트 시켜줌
            }
        }
        trie.cnt++;
    }
    
    public static class Trie{
        int alphabet;
        List<Trie> children;
        boolean[] used;
        int cnt;
        
        public Trie(int alphabet, List<Trie> children, boolean[] used, int cnt){
            this.alphabet = alphabet;
            this.children = children;
            this.used = used;
            this.cnt = cnt;
        }
        
        @Override
        public String toString(){
            return "{alphabet=" + alphabet + ", children=" + children + ", cnt=" + cnt + "}";
        }
    }
}