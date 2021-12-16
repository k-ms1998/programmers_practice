package myJava.heap;

import java.util.*;
public class no1 {
	static int[] scoville = { 1, 9, 10, 2, 3, 12 };
	static int k = 7;

	public static void main(String[] args) {
//		섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
		System.out.println((new Solution()).solution(scoville, k));
	}

	public static class Solution {
		public int solution(int[] scoville, int K) {
			PriorityQueue<Integer> newScoville = new PriorityQueue<>();
			for(int s: scoville) {
				newScoville.add(s);
			}
			
			return mySolution(newScoville, K, 0);
		}

		public int mySolution(PriorityQueue scoville, int K, int cnt) {
			if ((int)scoville.peek() >= K) {
				return cnt;
			}
			else if(scoville.size() < 2) {
				return -1;
			}

			int idx1 = (int)scoville.poll();
			int idx2 = (int)scoville.poll();
			int mixed = idx1+2*idx2;
			
			scoville.add(mixed);
//			scoville.forEach(e -> System.out.println(e));
			
			return mySolution(scoville, K, cnt + 1);
		}
	}
}