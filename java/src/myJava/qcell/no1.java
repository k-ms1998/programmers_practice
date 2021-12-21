package myJava.qcell;

import java.util.concurrent.atomic.AtomicInteger;

public class no1 {
	static int n = 4;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Answer:" + new Solution().solution(n));
	}

	public static class Solution {
		public int[] solution(int n) {
			int[] answer = {};

			int sx = 2 * n - 1;
			int sy = 2 * (2 * n - 1) - 1;
			int[][] hive = new int[sy][sx];
			for (int i = 0; i < sy; i++) {
				for (int j = 0; j < sx; j++) {
					if (i % 2 == 0) {
						if (j % 2 != 0) {
							hive[i][j] = (n + 1) % 2;
							continue;
						}
						hive[i][j] = n % 2;
					}
					if (i % 2 != 0) {
						if (j % 2 == 0) {
							hive[i][j] = (n + 1) % 2;
							continue;
						}
						hive[i][j] = n % 2;
					}
				}
			}

			int[] cur = {2};
			int[] pos = { sx / 2, 0};
			for(int i = 0; i < n; i++) {
				if(i > 0) {
					pos[0]=sx/2-1;
					pos[1]=2*i-1;
				}
				rightDown(pos, hive, cur, n, 2*i);
			}

			answer = new int[cur[0]-1];
			int idx = 0;
			answer[idx++] = 1;
			for(int y = 0; y < sy; y++) {
				for(int x = 0; x < sx; x++) {
					if(hive[y][x] > 1) {
						answer[idx++] = hive[y][x];
					}
				}
			}

			return answer;
		}

		public void rightDown(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] + 1 < hive.length && pos[0] + 1 < hive[0].length) {
				if (hive[pos[1] + 1][pos[0] + 1] == 1) {
					int x = ++pos[0];
					int y = ++pos[1];
					hive[y][x] = cur[0]++;

					rightDown(pos, hive, cur, n, sy);
				}
				else {
					down(pos, hive, cur, n, sy);
				}
			} else {
				down(pos, hive, cur, n, sy);
			}
		}

		public void down(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] + 2 < hive.length - n + 1) {
				if (hive[pos[1] + 2][pos[0]] == 1) {
					pos[1] += 2;
					int x = pos[0];
					int y = pos[1];
					hive[y][x] = cur[0]++;

					down(pos, hive, cur, n, sy);
				}
				else {
					leftDown(pos, hive, cur, n, sy);
				}
			} else {
				leftDown(pos, hive, cur, n, sy);
			}
		}

		public void leftDown(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] + 1 < hive.length && pos[0] - 1 >= 0) {
				if (hive[pos[1] + 1][pos[0] - 1] == 1) {
					int x = --pos[0];
					int y = ++pos[1];
					hive[y][x] = cur[0]++;

					leftDown(pos, hive, cur, n, sy);
				} else {
					leftUp(pos, hive, cur, n, sy);
				}
			} else {
				leftUp(pos, hive, cur, n, sy);
			}
		}

		public void leftUp(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] - 1 >= 0 && pos[0] - 1 >= 0) {
				if (hive[pos[1] - 1][pos[0] - 1] == 1) {
					int x = --pos[0];
					int y = --pos[1];
					hive[y][x] = cur[0]++;

					leftUp(pos, hive, cur, n, sy);
				}else {
					up(pos, hive, cur, n, sy);
				}
			} else {
				up(pos, hive, cur, n, sy);
			}
		}

		public void up(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] - 2 >= n - 1) {
				if (hive[pos[1] - 2][pos[0]] == 1) {
					pos[1] -= 2;
					int x = pos[0];
					int y = pos[1];
					hive[y][x] = cur[0]++;

					up(pos, hive, cur, n, sy);
				}else {
					rightUp(pos, hive, cur, n, sy);
				}
			} else {
				rightUp(pos, hive, cur, n, sy);
			}
		}

		public void rightUp(int[] pos, int[][] hive, int[] cur, int n, int sy) {
			if (pos[1] - 1 > sy) {
				if (hive[pos[1] - 1][pos[0] + 1] == 1) {
					int x = ++pos[0];
					int y = --pos[1];
					hive[y][x] = cur[0]++;

					rightUp(pos, hive, cur, n, sy);
				}
			}
		}

	}
}
