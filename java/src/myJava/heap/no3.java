package myJava.heap;

import java.util.*;

public class no3 {
//	이중우선순위큐(Level 3)

//	static String []operations = {"I 7","I 5","I -5","D -1"};
//	static String[] operations = { "I 16", "D 1" };
	static String[] operations = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] answer = new Solution().solution(operations);
		System.out.println(answer[0] + ":" + answer[1]);
	}

	static class Solution {
		public int[] solution(String[] operations) {
			int[] answer = {0,0};

			List<Integer> q = new ArrayList<>();
			for (String o : operations) {
				String[] tmp = o.split(" ");
				if (tmp[0].equals("I")) {
					q.add(Integer.valueOf(tmp[1]));
					Collections.sort(q);
				} else {
					if(q.size() == 0) {
						continue;
					}
					
					if (tmp[1].equals("-1")) {
						int qMin = q.remove(0);
					} else {
						int qMax = q.remove(q.size() - 1);
					}
				}
			}
			if (q.size() > 0) {
				answer[0] = q.remove(q.size() - 1);
				answer[1] = q.remove(0);
			}

			return answer;
		}
	}
}
