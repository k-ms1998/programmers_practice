package myJava.qcell;

import java.util.*;

public class no2 {
//	static String[] directory = { "/", "/hello", "/hello/tmp", "/root", "/root/abcd", "/root/abcd/etc",
//			"/root/abcd/hello" };
//	static String[] command = { "mkdir /root/tmp", "cp /hello /root/tmp", "rm /hello" };
	static String[] directory = { "/" };
	static String[] command = { "mkdir /a", "mkdir /a/b", "mkdir /a/b/c", "cp /a/b /", "rm /a/b" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Answer: " + new Solution().solution(directory, command));
	}

	public static class Solution {
		public void initDir(HashMap<String, ArrayList<String>> myDir, String[] dir) {
			for (String d : dir) {
				String[] tmpD = d.split("/");
				if (tmpD.length <= 0) {
					continue;
				}
				String myK = "";
				for (int i = 0; i < tmpD.length - 1; i++) {
					myK += (tmpD[i] + "/");
				}
				if (myDir.containsKey(myK)) {
					ArrayList<String> v = myDir.get(myK);
					v.add(tmpD[tmpD.length - 1]);
					myDir.put(myK, v);
				} else {
					ArrayList<String> myV = new ArrayList<>();
					if (tmpD.length >= 2) {
						myV.add(tmpD[tmpD.length - 1]);
					}
					myDir.put(myK, myV);
				}
			}
		}

		public void mkDir(HashMap<String, ArrayList<String>> myDir, String cmd) {
			String[] dir = cmd.split("/");
			if (dir.length <= 0) {
				return;
			} else if (dir.length == 1) {
				myDir.put(dir[1], new ArrayList<>());
				return;
			}

			String k = "";
			for (int i = 0; i < dir.length - 1; i++) {
				k += (dir[i] + "/");
			}
			if (myDir.containsKey(k)) {
				myDir.get(k).add(dir[dir.length - 1]);
			} else {
				ArrayList<String> v = new ArrayList<>();
				v.add(dir[dir.length - 1]);
				myDir.put(k, v);
			}
		}

		public void rmDir(HashMap<String, ArrayList<String>> myDir, String cmd) {
			String[] dir = cmd.split("/");

			String k = "";
			for (int i = 0; i < dir.length - 1; i++) {
				k += (dir[i] + "/");
			}
			
			String v = dir[dir.length-1];
			System.out.println((k+v+"/") + ":" + myDir.containsKey(k+v+"/"));
			if (myDir.containsKey(k + v + "/")) {
				myDir.remove(k + v + "/");
			}
			myDir.get(k).remove(v);

		}

		public String[] solution(String[] directory, String[] command) {
//			mkdir => 틋정 위치에 디렉토리를 생성
//			rm => 특정 디렉토리를 삭제. 삭제한 디렉토리의 하위 디렉토리들 모두 삭제.
//			cp => source 디렉토리를 dest로 복사. source 디렉토리들 또한 함께 삭제.(cp source dest)
			String[] answer = {};

			HashMap<String, ArrayList<String>> myDir = new HashMap<>();
			initDir(myDir, directory);

			for (String c : command) {
				String[] cmd = c.split(" ");
				if (cmd[0].equals("mkdir")) {
					mkDir(myDir, cmd[1]);
				} else if (cmd[0].equals("rm")) {
					rmDir(myDir, cmd[1]);
				}
			}

			myDir.forEach((k, v) -> {
				Iterator iter = myDir.get(k).iterator();
				while (iter.hasNext()) {
					System.out.println(k +"+" +iter.next());
				}

			});

			return answer;
		}
	}
}
