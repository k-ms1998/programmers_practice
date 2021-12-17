package myJava.dfs_bfs;

import java.util.*;

//Å¸°Ù ³Ñ¹ö (Level 2)
public class no1 {
	static int []numbers = {1, 1, 1, 1, 1};
	static int target = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Ans:"+new Solution().solution(numbers, target));
	}
	static class Solution {
	    public int solution(int[] numbers, int target) {
	        List<Integer> myNum = new ArrayList<>();
	        for(int n: numbers) {
	        	myNum.add(n);
	        }
	    	return mySolution(myNum, target, 0, 0);
	    }
	}
	static int mySolution(List numbers, int target, int cnt, int cur) {
//		System.out.println(numbers+","+cur);
		List<Integer> myNum = new ArrayList<>();
		Iterator numIter = numbers.iterator();
		while(numIter.hasNext()) {
			myNum.add((Integer) numIter.next());
		}
//		Must deep copy list

		if(numbers.size() > 0) {
			int n = (int)myNum.remove(0);
			cnt = mySolution(myNum, target, cnt, cur-n);
			cnt = mySolution(myNum, target, cnt, cur+n);
		}
		
		if(cur == target && numbers.size() <= 0) {
//			System.out.println(numbers+","+cur);
			return cnt+1;	
		}
		else {
			return cnt;
		}
	}
}
