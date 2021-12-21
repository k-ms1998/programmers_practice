package myJava.dfs_bfs;

import java.util.*;

//네트워크 (Level 3)
public class no2 {

//	static int[][] computers = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
//	static int[][] computers = { { 1, 1, 0 }, { 1, 1, 1 }, { 0, 1, 1 } };
//	static int[][] computers = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	static int[][] computers = { { 1, 1, 0, 0, 1 }, { 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 1, 1 },
			{ 1, 0, 0, 0, 1 } };
	static int n = computers.length;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Answer:" + new Solution().solution(n, computers));
	}

	static class Solution {
		public int solution(int n, int[][] computers) {
			int answer = 0;

			int[] visited = new int[n];
			for (int i = 0; i < n; i++) {
				visited[i] = 0;
			}

			for (int i = 0; i < n; i++) {
				if (visited[i] == 0) {
					answer++;
					mySolution(n, computers, i, visited);
				}
			}

			return answer;
		}
	}

	static void mySolution(int n, int[][] com, int pos, int[] visited) {
		visited[pos] = 1;
		for (int i = 0; i < n; i++) {
			if (com[pos][i] == 1 && visited[i] == 0) {
				mySolution(n, com, i, visited);
			}
		}

	}
}
