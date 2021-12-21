package myJava.heap;

import java.util.*;

public class no2 {
	static int[][] jobs = { { 0, 3 }, { 1, 9 }, { 2, 6 } };

//	ans = 9; A{0,3}->C{2,6}->B{1,9}
//	(A:3ms + C:7ms + B:17ms)/3
//	A: 0+3ms; C: 3+6-2ms; B: 9+9-1ms
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Solution().solution(jobs));
	}

	static class Solution {
		public int solution(int[][] jobs) {
			int answer = 0;

			PriorityQueue<int[]> qJobs = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
//	        for(int i = 1; i < jobs.length; i++) {
//	        	qJobs.offer(jobs[i]);
//	        }
//	        
			answer += (jobs[0][1] - jobs[0][0]);
			int cur = jobs[0][1];
			int idx = 1;
			while (true) {
				while (true) {
					qJobs.offer(jobs[idx]);
					idx++;
					if (idx >= jobs.length) {
						break;
					} else if (jobs[idx][0] > cur) {
						idx--;
						break;
					}
				}
				while (qJobs.size() > 0) {
					int[] q = qJobs.remove();
					answer += (cur + q[1] - q[0]);
					cur += q[1];
				}

				if (qJobs.size() == 0 && idx >= jobs.length) {
					break;
				}
			}
//	        System.out.println("answer:"+answer);

			return answer / jobs.length;
		}
	}
}
