package myJava.dfs_bfs;

public class no3 {
	static String begin = "hit";
	static String target = "cog";
	static String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Solution().solution(begin, target, words));
	}
	static class Solution {
	    public int solution(String begin, String target, String[] words) {
	        int answer = 0;
	        
	        int flag = 0;
	        for(int i = 0; i < words.length; i++) {
	        	if(target.equals(words[i])) {
	        		flag = 1;
	        		break;
	        	}
	        }
	        if(flag == 0) {
	        	return -1;
	        }
	        
	        String []c = begin.split("");
	        String []d = target.split("");
	        
	        for(int i = 0; i < words.length; i++) {
	        	String []w = words[i].split("");
	        	System.out.println(cmpWords(w,c));
	        	if(cmpWords(w, c)) {
	        		
	        		System.out.println(w[0]+","+c[0]);
	        	}
	        	
	        }
	        
	        return answer;
	    }
	}
	public static boolean cmpWords(String []w, String []c) {
		int cnt = 0;
		for(int i = 0; i < w.length; i++) {
			if(w[i] != c[i]) {
				cnt++;
			}	
			if(cnt > 1) {
				return false;
			}
			
		}
		
		return true;
	}
}
