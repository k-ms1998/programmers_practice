import java.util.*;

class Solution {
    
    static int n;
    static Node[] inOrder;
    static Node root;
    static Node[] preOrder;
    static Node[] postOrder;
    static Deque<Integer> tmpPreOrder = new ArrayDeque<>();
    static Deque<Integer> tmpPostOrder = new ArrayDeque<>();

    static final int INF = 100001;
    
    public int[][] solution(int[][] nodeinfo) {
        n = nodeinfo.length;
        inOrder = new Node[n + 1];

        for(int i = 0; i < n; i++){
            inOrder[i] = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
        }
        inOrder[n] = new Node(0, -1, INF);
        Arrays.sort(inOrder, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                if(n1.y == n2.y){
                    return n1.x - n2.x;
                }
                return n2.y - n1.y;
            }
        });
        root = inOrder[1];
        
        Arrays.sort(inOrder, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n1.x - n2.x;
            }
        });
        
        int rootPos = 0;
        for(int i = 1; i < n + 1; i++){
            if(inOrder[i].idx == root.idx){
                rootPos = i;
                break;
            }
        }
        
        createPreOrder(rootPos, root, inOrder);
        createPostOrder(rootPos, root, inOrder);
        
        int[][] answer = new int[2][n];
        for(int i = 0; i < n; i++){
            answer[0][i] = tmpPreOrder.pop();
            answer[1][i] = tmpPostOrder.pop();
        }

        return answer;
    }
    
    public static void createPreOrder(int rootPos, Node root, Node[] tmp){
        if(rootPos == -1){
            return;
        }
        
        Node[] leftTree = new Node[n + 1];
        Node[] rightTree = new Node[n + 1];
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftRootPos = -1;
        int rightRootPos = -1;
        
        Arrays.fill(leftTree, new Node(-1, -1, -1));
        Arrays.fill(rightTree, new Node(-1, -1, -1));
        for(int i = 1; i <= n; i++){
            if(i < rootPos){
               leftTree[i] = tmp[i];
                if(tmp[i].y > leftRoot.y){
                    leftRoot = tmp[i];
                    leftRootPos = i;
                }  
            }else if(i == rootPos){
                continue;
            }else{
                rightTree[i] = tmp[i]; 
                if(tmp[i].y > rightRoot.y){
                    rightRoot = tmp[i];
                    rightRootPos = i;
                }
            }
        }
                
        tmpPreOrder.offer(root.idx);
        createPreOrder(leftRootPos, leftRoot, leftTree); 
        createPreOrder(rightRootPos, rightRoot, rightTree);
    }
    
    public static void createPostOrder(int rootPos, Node root, Node[] tmp){
        if(rootPos == -1){
            return;
        }
        
        Node[] leftTree = new Node[n + 1];
        Node[] rightTree = new Node[n + 1];
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftRootPos = -1;
        int rightRootPos = -1;
        
        Arrays.fill(leftTree, new Node(-1, -1, -1));
        Arrays.fill(rightTree, new Node(-1, -1, -1));
        for(int i = 1; i <= n; i++){
            if(i < rootPos){
               leftTree[i] = tmp[i];
                if(tmp[i].y > leftRoot.y){
                    leftRoot = tmp[i];
                    leftRootPos = i;
                }  
            }else if(i == rootPos){
                continue;
            }else{
                rightTree[i] = tmp[i]; 
                if(tmp[i].y > rightRoot.y){
                    rightRoot = tmp[i];
                    rightRootPos = i;
                }
            }
        }
        
        createPostOrder(leftRootPos, leftRoot, leftTree); 
        createPostOrder(rightRootPos, rightRoot, rightTree);
        tmpPostOrder.offer(root.idx);
    }

    public static class Node{
        int idx;
        int x;
        int y;
        
        public Node(int idx, int x, int y){
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString(){
            return "[idx:" + idx + ", {x=" + x + ", y=" + y + "}]";
        }
    }
}