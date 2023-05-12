import java.util.*;

class Solution {
    
    static int n;
    static Node[] nodes; // inOrder
    static List<Node> preOrder = new ArrayList<>();
    static List<Node> postOrder = new ArrayList<>();

    
    public int[][] solution(int[][] nodeinfo) {
        n = nodeinfo.length;
        
        nodes = new Node[n];
        Node root = new Node(-1, -1, -1);
        for(int i = 0; i < n; i++){
            nodes[i] = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            if(root.y < nodeinfo[i][1]){
                root = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            }
        }
        Arrays.sort(nodes, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n1.x - n2.x;
            }
        });
        // for(int i = 0; i < n; i++){
        //     System.out.print(nodes[i] + " ");
        // }
        // System.out.println();
        
        for(int i = 0; i < n; i++){
            if(nodes[i].num ==  root.num){
                findPreOrder(0, n - 1, i, 1);
                findPostOrder(0, n-1, i, 1);
                break;
            }
        }

        int[][] answer = new int[2][n];
        for(int i = 0; i < n; i++){
            Node pre = preOrder.get(i);
            Node post = postOrder.get(i);
            
            answer[0][i] = pre.num;
            answer[1][i] = post.num;
        }
        
        return answer;
    }
    
    public static void findPreOrder(int left, int right, int mid, int idx){
        if(mid == -1){
            return;
        }
        
        preOrder.add(nodes[mid]);
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftIdx = -1;
        int rightIdx = -1;
        
        for(int i = left; i < mid; i++){
            if(nodes[i].y > leftRoot.y){
                leftRoot = nodes[i];
                leftIdx = i;
            }
        }
        for(int i = mid + 1; i <= right; i++){
            if(nodes[i].y > rightRoot.y){
                rightRoot = nodes[i];
                rightIdx = i;
            }
        }
        
        findPreOrder(left, mid - 1, leftIdx, idx);
        findPreOrder(mid + 1, right, rightIdx, idx);
    }
    
    public static void findPostOrder(int left, int right, int mid, int idx){
        if(mid == -1){
            return;
        }
        
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftIdx = -1;
        int rightIdx = -1;
        
        for(int i = left; i < mid; i++){
            if(nodes[i].y > leftRoot.y){
                leftRoot = nodes[i];
                leftIdx = i;
            }
        }
        for(int i = mid + 1; i <= right; i++){
            if(nodes[i].y > rightRoot.y){
                rightRoot = nodes[i];
                rightIdx = i;
            }
        }
        
        findPostOrder(left, mid - 1, leftIdx, idx);   
        findPostOrder(mid + 1, right, rightIdx, idx);
        postOrder.add(nodes[mid]);
    }
    
    public static class Node{
        int num;
        int x;
        int y;
        
        public Node(int num, int x, int y){
            this.num = num;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString(){
            return "[num=" + num + ", x=" + x + ", y=" + y + "}";
        }
    }
}