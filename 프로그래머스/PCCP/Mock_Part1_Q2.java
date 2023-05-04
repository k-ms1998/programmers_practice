/**
 * PCCP 모의고사 1회 2번:
 * https://school.programmers.co.kr/learn/courses/15008/lessons/121684
 */
class Mock_Part1_Q2 {

    static int n, m;
    static int answer = 0;

    public int solution(int[][] ability) {

        n = ability.length;
        m = ability[0].length;

        findAnswer(0, 0, 0, 0, 0, ability);

        return answer;
    }

    public static void findAnswer(int idx, int depth, int cost, int bit, int used, int[][] ability){
        if(depth == m){
            System.out.println("bit=" + bit);
            answer = Math.max(answer, cost);
            return;
        }

        for(int i = idx; i <= n - (m - depth); i++){
            if((bit & (1 << i)) != (1 << i)){ // i번째 학생이 선택되지 않았을때
                for(int j = 0; j < m; j++){
                    if((used & (1 << j)) != (1 << j)){
                        int next = ability[i][j];
                        findAnswer(i + 1, depth + 1, cost + next, bit | (1 << i), used | (1 << j), ability);
                    }
                }
            }
        }
    }

}