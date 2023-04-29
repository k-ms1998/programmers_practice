/**
 * 100점/100점
 */
class Q2 {

    static int size;
    static Info[] infos;

    public int solution(int n, int money, String[] events) {
        int answer = 0;

        size = events.length;
        infos = new Info[size];
        for(int i = 0; i < size; i++){
            String[] arr = events[i].split(" ");
            int cost = Integer.parseInt(arr[0]);

            String s = arr[1];
            int amount = Integer.parseInt(s.substring(1, s.length()));
            if(s.startsWith("+")){
                infos[i] = new Info(cost, amount, 0);
            }else{
                infos[i] = new Info(cost, amount, 1);
            }
        }

        // size = 5, bit = 00000~11111
        for(int bit = 0; bit < (1 << size); bit++){
            int curCost = 0;
            int addition = 0;
            int multiple = 1;
            for(int i = 0; i < size; i++){
                if((bit & (1 << i)) == (1 << i)){
                    int cost = infos[i].cost;
                    int amount = infos[i].amount;
                    int command = infos[i].command;

                    curCost += cost;
                    if(command == 0){
                        addition += amount;
                    }else{
                        multiple *= amount;
                    }
                }
            }
            if(curCost <= money){
                answer = Math.max(answer, (n + addition) * multiple);
            }
        }

        return answer;
    }

    public static class Info{
        int cost;
        int amount;
        int command;

        public Info(int cost, int amount, int command){
            this.cost = cost;
            this.amount = amount;
            this.command = command;
        }

        @Override
        public String toString(){
            return "{cost=" + cost + ", amount=" + amount + ", command=" + command + "}";
        }
    }
}