import java.util.*;

/**
 * 47.6점/100점
 */
class Q3 {

    static int n;

    public long solution(int[][] tasks) {
        long answer = 0;
        int firstStart = tasks[0][0];

        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2){
                if(i1.end == i2.end){
                    return i1.start - i2.start;
                }

                return i1.end - i2.end;
            }
        });

        n = tasks.length;
        for(int i = 0; i < n; i++){
            int start = tasks[i][0];
            int end = tasks[i][1];
            int amount = tasks[i][2];
            queue.offer(new Info(start, end, amount));

            long cur = amount / (end - start + 1);
            if(amount % (end - start + 1) != 0){
                cur++;
            }
            answer = Math.max(answer, cur);
        }

        int total = 0;
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int start = info.start;
            int end = info.end;
            int amount = info.amount;

            total += amount;
            long tmp = total / (end - firstStart + 1);
            if(total % (end - firstStart + 1) != 0){
                tmp++;
            }
            // System.out.println(tmp);
            answer = Math.max(answer, tmp);
        }

        return answer;
    }

    public static class Info{
        int start;
        int end;
        int amount;

        public Info(int start, int end, int amount){
            this.start = start;
            this.end = end;
            this.amount = amount;
        }
    }
}