class Solution {
    
    static Point[] pos = new Point[12];
    
    public String solution(int[] numbers, String hand) {
        initPos();
        String answer = "";
        
        Point left = new Point(0, 3);
        Point right = new Point(2, 3);
        for(int i = 0; i < numbers.length; i++){
            int cur = numbers[i];
            if(cur == 1 || cur == 4 || cur == 7){
                left = pos[cur];
                answer += "L";
            }else if(cur == 3 || cur == 6 || cur == 9){
                right = pos[cur];
                answer += "R";
            }else{
                Point curPos = pos[cur];
                int leftD = Math.abs(left.x - curPos.x) + Math.abs(left.y - curPos.y);
                int rightD = Math.abs(right.x - curPos.x) + Math.abs(right.y - curPos.y);
                
                if(leftD > rightD){
                    right = curPos;
                    answer += "R";
                }else if(leftD < rightD){
                    left = curPos;
                    answer += "L";
                }else{
                    if(hand.equals("left")){
                        left = curPos;
                        answer += "L";
                    }else{
                        right = curPos;
                        answer += "R";
                    }
                }
            }
        }
        
        return answer;
    }
    
    public static void initPos(){
        pos[0] = new Point(1, 3);
        pos[1] = new Point(0, 0);
        pos[2] = new Point(1, 0);
        pos[3] = new Point(2, 0);
        pos[4] = new Point(0, 1);
        pos[5] = new Point(1, 1);
        pos[6] = new Point(2, 1);
        pos[7] = new Point(0, 2);
        pos[8] = new Point(1, 2);
        pos[9] = new Point(2, 2);
        pos[10] = new Point(0, 3); // '*'
        pos[11] = new Point(2, 3); // '#'
    }
    
    static class Point{
        int x;
        int y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}