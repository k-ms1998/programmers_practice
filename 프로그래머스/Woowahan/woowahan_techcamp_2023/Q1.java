package woowahan_techcamp_2023;

import java.util.*;

class Q1 {

    static int n;

    public int solution(int[] boxes, int m, int k) {
        n = boxes.length;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for(int i = 0; i < n; i++){
            queue.offer(boxes[i]);
        }

        for(int i = 0; i < k; i++){
            while(!queue.isEmpty()){
                int box = queue.poll();
                int earned = m / 10000;
                if(box * earned <= 100000){
                    m += box*earned;
                    queue.offer(box);
                    break;
                }
            }
            // System.out.println(m);
        }

        return m;
    }
}