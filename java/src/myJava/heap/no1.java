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
		public int solution(int[] scoville, int k) {
			List<Integer> newScoville = new ArrayList<>();
			for(int s: scoville) {
				newScoville.add(s);
			}
			Collections.sort(newScoville);
			
			return mySolution(newScoville, k, 0);
		}

		public int mySolution(List scoville, int k, int cnt) {
			if ((int)scoville.get(0) > k) {
				return cnt;
			}
			else if(scoville.size() < 2) {
				return -1;
			}

			int idx1 = (int)scoville.remove(0);
			int idx2 = (int)scoville.remove(0);
			int mixed = idx1+2*idx2;
			
			int pos = 0;
			Iterator scovilleIter = scoville.iterator();
			while(scovilleIter.hasNext()) {
				int cmp = (int)scovilleIter.next();
				if(mixed < cmp) {
					break;
				}
				pos++;
			}
			scoville.add(pos, mixed);
	
//			scoville.forEach(e -> System.out.println(e));
			
			return mySolution(scoville, k, cnt + 1);
		}
	}
}
