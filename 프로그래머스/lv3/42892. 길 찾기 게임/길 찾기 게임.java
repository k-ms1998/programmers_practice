import java.util.*;

/**
1. 루트 노드를 찾는다 (y좌표가 가장 큰 노드)
2. 입력 받은 노드들을 x좌표 순으로 정렬 -> 트리를 중위 순회한 결과가 나옴
3. 정렬한 노드들에서 루트 노드의 위치를 찾는다 -> 루트노드 기준 배열의 왼쪽에 있는 값들은 루트 노드 기준 왼쪽 서브 트리, 오른쪽은 오른쪽 서브트리
4. 각 서브트리로 이동해서, 1~3 반복 -> 루트 노드에 도착할때까지 서브트리로 이동
5. 현재 노드 출력 -> 왼쪽 서브트리 방문 -> 오른쪽 서브트리 방문 => 전위 순회
   왼쪽 서브트리 방문 -> 오른쪽 서브트리 방문 -> 현재 노드 출력 => 전위 순회
*/
class Solution {
    
    static int n;
    static Node[] inOrder;
    static Node root;
    static Node[] preOrder;
    static Node[] postOrder;
    static int preOrderIdx = -1;
    static int postOrderIdx = -1;
    static int[][] answer;

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
                
        answer = new int[2][n];
        createPreOrder(rootPos, root, inOrder);
        createPostOrder(rootPos, root, inOrder);

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
                        
        preOrderIdx++;
        answer[0][preOrderIdx] = root.idx;
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
        postOrderIdx++;
        answer[1][postOrderIdx] = root.idx;
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