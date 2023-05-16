import java.util.*;

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
    
    public static void searchTrie(Trie trie){
        if(trie.alphabet != -1){
            // System.out.println(trie.alphabet + ", cnt=" + trie.cnt);
            answer += trie.cnt;
        }
        if(trie.cnt <= 1){
            return;
        }
        
        for(Trie child : trie.children){
            searchTrie(child);
        }
    }
    
    public static void updateTrie(String word){
        Trie trie = root;
        for(int i = 0; i < word.length(); i++){
            int num = word.charAt(i) - 'a';
            trie.cnt++;
            if(trie.used[num]){
                for(Trie child : trie.children){
                    if(child.alphabet == num){
                        trie = child;
                        break;
                    }
                }
            }else{
                trie.used[num] = true;
                Trie child = new Trie(num, new ArrayList<>(), new boolean[26], 0);
                trie.children.add(child);
                trie = child;
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