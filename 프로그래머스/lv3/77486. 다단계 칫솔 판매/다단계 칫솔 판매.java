import java.util.*;

class Solution {
    
    static int n;
    static Map<Integer, String> idxNum = new HashMap<>();
    static Map<String, Integer> idxString = new HashMap<>();
    static Map<String, String> parent = new HashMap<>();
    static Map<String, Integer> total = new HashMap<>();
    static Map<String, List<String>> children = new HashMap<>();
    static List<String>[] byHeight;
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        n = enroll.length;
    
        int[] answer = new int[n];
        byHeight = new List[n + 1];
        for(int i = 0; i < n; i++){
            byHeight[i] = new ArrayList<>();
            String name = enroll[i];
            idxNum.put(i, name);
            idxString.put(name, i);
            parent.put(name, name);
            total.put(name, 0);
            children.put(name, new ArrayList<>());
        }
        byHeight[n] = new ArrayList<>();
        parent.put("root", "root");
        total.put("root", 0);
        children.put("root", new ArrayList<>());
        
        for(int i = 0; i < n; i++){
            String name = referral[i];
            String curNode = idxNum.get(i);
            if(name.equals("-")){
                name = "root";
                parent.put(curNode, "root");
            }else{
                parent.put(curNode, name);
            }

                        
            List<String> list = children.get(name);
            list.add(curNode);
            children.put(name, list);
        }
        

        
        for(String node: parent.keySet()){
            if(node.equals("root")){
                continue;
            }
            String curParent = parent.get(node);
            if(curParent.equals("root")){
                // System.out.println(curParent);
                findLeaf(node, 1);
            }
        }
        
        int tmpN = seller.length;
        for(int i = 0; i < tmpN; i++){
            String name = seller[i];
            int count = amount[i];
            int curTotal = total.get(name);
            total.put(name, curTotal + 100*count);
            updateTotal(name, 100*count);
        }
        // for(int i = n; i >= 0; i--){
        //    for(String curNode : byHeight[i]){               
        //        int curTotal = total.get(curNode);
        //        updateTotal(curNode, curTotal);
        //     }
        // }
        for(int i = 0; i < n; i++){
            String node = idxNum.get(i);
            int totalAmount = total.get(node);
            
            answer[i] = totalAmount;
        }
        // System.out.println(total.get("root"));

        return answer;
    }
    
    public static void updateTotal(String node, int tmpTotal){
        // System.out.println("node = " + node + ", tmpTotal = " + tmpTotal);
        if(node.equals("root") || (tmpTotal) / 10 == 0){
            return;
        }
        
        String curParent = parent.get(node);
        int curTotal = total.get(node);
        int parentTotal = total.get(curParent);
        
        int referralAmount = tmpTotal / 10;
        int updatedCurTotal = curTotal - referralAmount;
        int udpatedParentTotal = parentTotal + referralAmount;
        
        total.put(node, updatedCurTotal);
        total.put(curParent, udpatedParentTotal);
        
        updateTotal(curParent, referralAmount);
    }
    
    public static void findLeaf(String node, int h){
        List<String> child = children.get(node);
        if(child.size() == 0){
            byHeight[h].add(node);
            return;
        }
        
        byHeight[h].add(node);
        for(String c : child){
            findLeaf(c, h + 1);
        }
    }
}